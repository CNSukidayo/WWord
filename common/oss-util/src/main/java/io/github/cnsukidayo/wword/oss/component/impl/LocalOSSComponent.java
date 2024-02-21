package io.github.cnsukidayo.wword.oss.component.impl;

import cn.hutool.core.io.IoUtil;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.oss.component.AbstractOSSComponent;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import io.github.cnsukidayo.wword.oss.config.properties.LocalOSSProperties;

import java.io.*;
import java.nio.file.Files;

/**
 * 本地的Oss存储服务
 *
 * @author sukidayo
 * @date 2024/2/19 19:45
 */
public class LocalOSSComponent extends AbstractOSSComponent implements OSSComponent {

    private final LocalOSSProperties localOSSProperties;

    public LocalOSSComponent(LocalOSSProperties localOSSProperties) {
        this.localOSSProperties = localOSSProperties;
    }

    @Override
    public String fileUpLoadAutoRename(InputStream inputStream, String basePath, String fileName) {
        BufferedOutputStream bos = null;
        try {
            // 生成文件的名称,随机名称
            String objectName = super.generateFilePath(basePath, fileName);
            // 创建文件输出流
            File outputFile = new File(localOSSProperties.getUploadDir(), objectName);
            Files.createDirectories(outputFile.getParentFile().toPath());
            bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            inputStream.transferTo(bos);
            bos.flush();
            return objectName;
        } catch (Exception e) {
            // 这里需要抛出自定义异常
            throw new BadRequestException(ResultCodeEnum.FILE_UPLOAD_ERROR, e);
        } finally {
            IoUtil.close(bos);
            IoUtil.close(inputStream);
        }
    }

    @Override
    public void fileUpLoadOriginName(InputStream inputStream, String objectName) {
        File filePath = new File(localOSSProperties.getUploadDir(), objectName);
        BufferedOutputStream bos = null;
        try {
            Files.createDirectories(filePath.getParentFile().toPath());
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            inputStream.transferTo(bos);
            bos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(bos);
        }
    }

    @Override
    public InputStream getDownLoadInputStream(String originUrl) throws IOException {
        // 得到本地工作的目录前缀
        String uploadDir = localOSSProperties.getUploadDir();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(uploadDir, originUrl)));
        return bis;
    }
}
