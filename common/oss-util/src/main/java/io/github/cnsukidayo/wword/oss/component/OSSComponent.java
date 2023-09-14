package io.github.cnsukidayo.wword.oss.component;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sukidayo
 * @date 2023/9/14 13:01
 */
public interface OSSComponent {

    /**
     * 上传文件到阿里云OSS对象存储服务中
     *
     * @param multipartFile 上传的文件流不为null
     * @return 返回上传成功后的文件路径
     */
    String fileUpLoad(MultipartFile multipartFile);

    /**
     * 从阿里云下载文件到本机
     *
     * @param objectName 下载的文件地址不为空
     * @param targetBasePath 存放的基本路径不为空
     * @return 下载到本机的位置路径不为null
     */
    String downloadFile(String objectName, String targetBasePath);

}
