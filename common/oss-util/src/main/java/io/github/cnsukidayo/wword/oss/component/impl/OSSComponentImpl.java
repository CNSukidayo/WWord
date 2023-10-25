package io.github.cnsukidayo.wword.oss.component.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.environment.FileSystemConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import io.github.cnsukidayo.wword.oss.config.properties.OSSProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

/**
 * @author sukidayo
 * @date 2023/9/14 13:01
 */
@Component
public class OSSComponentImpl implements OSSComponent {

    private final OSS ossClient;

    private final OSSProperties ossProperties;

    public OSSComponentImpl(OSS ossClient,
                            OSSProperties ossProperties) {
        this.ossClient = ossClient;
        this.ossProperties = ossProperties;
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
            Assert.notNull(objectName, "objectName must not be null");
            String filePre = UUID.randomUUID().toString().replaceAll("-", "");
            int suffixIndex = objectName.lastIndexOf('.');
            if (suffixIndex == -1) {
                throw new BadRequestException(ResultCodeEnum.FILE_UPLOAD_ERROR, "文件上传失败!找不到文件后缀名");
            }
            objectName = filePre + objectName.substring(suffixIndex);
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
            return URLUtil.getPath(result.getResponse().getUri()).substring(1);
        } catch (Exception e) {
            // 这里需要抛出自定义异常
            throw new BadRequestException(ResultCodeEnum.FILE_UPLOAD_ERROR, e);
        }
    }

    @Override
    public String downloadFile(String objectName, String targetBasePath) {
        Assert.hasText(targetBasePath, "targetBasePath must not be null");
        Assert.hasText(objectName, "objectName must not be null");
        // 得到文件名称
        String fileName = FileUtil.getName(objectName);
        // 得到文件前缀
        String filePrefix = Optional.ofNullable(FileUtil.getPrefix(fileName))
            .filter(StringUtils::hasText)
            .orElseThrow(() -> new IllegalStateException(fileName + "不是一个合法的文件!"));

        // 调用ossClient.getObject返回一个OSSObject实例,该实例包含文件内容及文件元信息.
        OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), objectName);
        // 调用ossObject.getObjectContent获取文件输入流,可读取此输入流获取其内容.
        InputStream content = ossObject.getObjectContent();
        BufferedOutputStream outputStream = null;
        // 下载到的目标路径
        File targetFilePath = new File(FileUtil.mkdir(FileUtils.separatorFilePath(FileSystemConst.separatorChar, targetBasePath, filePrefix)), fileName);
        if (content != null) {
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(targetFilePath));
                content.transferTo(outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    // 数据读取完成后，获取的流必须关闭,否则会造成连接泄漏,导致请求无连接可用,程序无法正常工作.
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return targetFilePath.getAbsolutePath();
    }
}
