package io.github.cnsukidayo.wword.third.oss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sukidayo
 * @date 2023/9/13 18:42
 */
@FeignClient("service-third-party")
public interface OSSFeignClient {

    /**
     * 上传文件到阿里云OSS对象存储服务中
     *
     * @param multipartFile 上传的文件流不为null
     * @return 返回上传成功后的文件路径
     */
    @PostMapping("/remote/third/party/oss/fileUpLoad")
    String fileUpLoad(@RequestParam("file") MultipartFile multipartFile);

}
