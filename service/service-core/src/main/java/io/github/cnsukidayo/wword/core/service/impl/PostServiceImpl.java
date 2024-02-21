package io.github.cnsukidayo.wword.core.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.client.AuthFeignClient;
import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import io.github.cnsukidayo.wword.core.dao.PostMapper;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.global.support.constant.MQConst;
import io.github.cnsukidayo.wword.global.support.enums.FileBasePath;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.dto.PostDTO;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.vo.PostAbstractVO;
import io.github.cnsukidayo.wword.model.vo.PostDetailVo;
import io.github.cnsukidayo.wword.mq.service.RabbitService;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final RabbitService rabbitService;

    private final OSSComponent ossComponent;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthFeignClient authFeignClient;

    public PostServiceImpl(RabbitService rabbitService,
                           OSSComponent ossComponent,
                           AuthFeignClient authFeignClient) {
        this.rabbitService = rabbitService;
        this.ossComponent = ossComponent;
        this.authFeignClient = authFeignClient;
    }

    @Override
    public void publishPost(PublishPostParam publishPostParam, User user) {
        Assert.notNull(publishPostParam, "publishPostParam must not be null");
        Assert.notNull(user, "user param must not be null");
        // 参数校验
        ValidationUtils.validate(publishPostParam);
        DateTime dateTime = new DateTime();
        // 文件上传
        String originUrl = null;
        MultipartFile multipartFile = publishPostParam.getFile();
        try {
            originUrl = ossComponent.fileUpLoadAutoRename(multipartFile.getInputStream(),
                FileUtils.separatorFilePath(WWordConst.separatorChar,
                    FileBasePath.FileNameSpace.USER.getBasePath(),
                    FileBasePath.FileCategory.POST.getBasePath(),
                    FileBasePath.FileBasePathDIR.UPLOAD_DIR.getBasePath(),
                    dateTime.toString(WWordConst.dateFormat)),
                multipartFile.getOriginalFilename());
        } catch (IOException e) {
            logger.error("PostServiceImpl-publishPost-uploadFileError;FileName:[{}]", multipartFile.getOriginalFilename());
            throw new RuntimeException(e);
        }

        // 构造消息
        Post post = new Post();
        post.setUuid(user.getUuid());
        post.setTitle(publishPostParam.getTitle());
        post.setPostStatus(PostStatus.TO_CHECK);
        post.setOriginUrl(originUrl);
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        post.setCreateTime(localDateTime);

        // 发布到MQ中
        rabbitService.sendMessage(MQConst.EXCHANGE_POST_DIRECT, MQConst.ROUTING_PUBLISH_POST, post);
    }

    @Override
    public List<PostAbstractVO> getPostListUncheck() {
        // 查询出所有的帖子
        List<Post> list = this.list();
        Collections.shuffle(list);
        List<PostAbstractVO> result = new ArrayList<>();
        // 所有帖子的用户id
        List<Long> userIdList = new ArrayList<>();
        for (int i = 0; i < list.size() && i < 10; i++) {
            PostAbstractVO postAbstractVO = new PostAbstractVO();
            PostDTO postDTO = new PostDTO().convertFrom(list.get(i));
            postAbstractVO.setPostDTO(postDTO);
            result.add(postAbstractVO);
            userIdList.add(postDTO.getUuid());
        }
        // 获取所有帖子的用户id
        Map<Long, UserProfileDTO> userProfileDTOMap = authFeignClient.getByIdList(userIdList)
            .stream()
            .map((Function<User, UserProfileDTO>) user -> new UserProfileDTO().convertFrom(user))
            .collect(Collectors.toMap(UserProfileDTO::getUuid, userProfileDTO -> userProfileDTO));
        for (PostAbstractVO postAbstractVO : result) {
            postAbstractVO.setPostCreateUser(userProfileDTOMap.get(postAbstractVO.getPostDTO().getUuid()));
        }
        return result;
    }

    @Override
    public PostDetailVo getPostDetailUncheck(Long id) {
        Assert.notNull(id, "id must not be null");

        PostDetailVo postDetailVo = new PostDetailVo();
        Post post = this.getById(id);
        // 查询帖子的创建者
        User postCreateUser = authFeignClient.getById(post.getUuid());
        postDetailVo.setPostCreateUser(new UserProfileDTO().convertFrom(postCreateUser));
        postDetailVo.setPostDTO(new PostDTO().convertFrom(post));
        postDetailVo.setContent(post.getContent());
        return postDetailVo;
    }
}
