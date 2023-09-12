package io.github.cnsukidayo.wword.third.oss.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.github.cnsukidayo.wword.common.utils.FileUtils;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.third.oss.config.properties.OSSProperties;
import io.github.cnsukidayo.wword.third.oss.service.OSSService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * OSS对象存储服务
 *
 * @author sukidayo
 * @date 2023/9/12 14:13
 */
@Service
public class OSSServiceImpl implements OSSService {

    private final OSSProperties ossProperties;

    private final OSS ossClient;

    public OSSServiceImpl(OSSProperties ossProperties,
                          OSS ossClient) {
        this.ossProperties = ossProperties;
        this.ossClient = ossClient;
    }

    @Override
    public String fileUpLoad(MultipartFile multipartFile) {
        // 创建OSSClient实例。
        String resultPath = null;
        try {
            // 获取文件输入流
            InputStream inputStream = multipartFile.getInputStream();
            // 生成文件的名称,随机名称
            String objectName = multipartFile.getOriginalFilename();
            String filePre = UUID.randomUUID().toString().replaceAll("-", "");
            objectName = filePre + objectName.substring(objectName.lastIndexOf('.'));
            // 对上传文件进行分组,根据当前年/月/日 objectName:2023/7/1/UUID.png
            objectName = FileUtils.separatorFilePath('/',
                new DateTime().toString("yyyy/MM/dd"),
                filePre,
                objectName);
            /*
            args0:bucket名称
            args1:上传到阿里云的文件的路径(包含名称)
            args2:文件的输入流
             */
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(),
                objectName,
                inputStream);
            // 必须设置协议,否则返回的response为空
            putObjectRequest.setProcess("true");
            PutObjectResult result = this.ossClient.putObject(putObjectRequest);
            // 如果上传成功,则返回200.返回上传之后图片的路径
            return result.getResponse().getUri();
        } catch (Exception e) {
            // 这里需要抛出自定义异常
            throw new BadRequestException(ResultCodeEnum.FILE_UPLOAD_ERROR, e);
        }
    }
}
