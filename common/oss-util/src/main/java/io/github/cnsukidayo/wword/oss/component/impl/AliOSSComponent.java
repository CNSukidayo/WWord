package io.github.cnsukidayo.wword.oss.component.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.oss.component.AbstractOSSComponent;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import io.github.cnsukidayo.wword.oss.config.properties.AliOSSProperties;

import java.io.InputStream;

/**
 * @author sukidayo
 * @date 2023/9/14 13:01
 */
public class AliOSSComponent extends AbstractOSSComponent implements OSSComponent {

    private final OSS ossClient;

    private final AliOSSProperties aliOssProperties;

    public AliOSSComponent(OSS ossClient,
                           AliOSSProperties aliOssProperties) {
        this.ossClient = ossClient;
        this.aliOssProperties = aliOssProperties;
    }

    @Override
    public String fileUpLoadAutoRename(InputStream inputStream, String basePath, String fileName) {
        try {
            // 获取文件输入流
            // 生成文件的名称,随机名称
            String objectName = super.generateFilePath(basePath, fileName);
            /*
            args0:bucket名称
            args1:上传到阿里云的文件的路径(包含名称)
            args2:文件的输入流
             */
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliOssProperties.getBucketName(),
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
        } finally {
            IoUtil.close(inputStream);
        }
    }

    @Override
    public void fileUpLoadOriginName(InputStream inputStream, String objectName) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliOssProperties.getBucketName(),
            objectName,
            inputStream);
        // 必须设置协议,否则返回的response为空
        putObjectRequest.setProcess("true");
        this.ossClient.putObject(putObjectRequest);
    }

    @Override
    public InputStream getDownLoadInputStream(String originUrl) {
        // 调用ossClient.getObject返回一个OSSObject实例,该实例包含文件内容及文件元信息.
        OSSObject ossObject = ossClient.getObject(aliOssProperties.getBucketName(), originUrl);
        // 调用ossObject.getObjectContent获取文件输入流,可读取此输入流获取其内容.
        return ossObject.getObjectContent();
    }
}
