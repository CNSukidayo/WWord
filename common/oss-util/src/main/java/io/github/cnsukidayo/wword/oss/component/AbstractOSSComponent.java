package io.github.cnsukidayo.wword.oss.component;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

/**
 * @author sukidayo
 * @date 2024/2/20 9:49
 */
public abstract class AbstractOSSComponent implements OSSComponent {

    @Override
    public String downloadFile(String originUrl, String targetBasePath) {
        Assert.hasText(originUrl, "objectName must not be null");
        Assert.hasText(targetBasePath, "targetBasePath must not be null");
        // 得到文件名称
        String fileName = FileUtil.getName(originUrl);
        // 得到文件前缀
        String filePrefix = Optional.ofNullable(FileUtil.getPrefix(fileName))
            .filter(StringUtils::hasText)
            .orElseThrow(() -> new IllegalStateException(fileName + "不是一个合法的文件!"));

        BufferedOutputStream outputStream = null;
        // 获得下载流
        InputStream content = null;
        // 下载到的目标路径
        File targetFilePath = new File(FileUtil.mkdir(FileUtils.separatorFilePath(WWordConst.separatorChar, targetBasePath, filePrefix)), fileName);
        try {
            Files.createDirectories(targetFilePath.getParentFile().toPath());
            content = this.getDownLoadInputStream(originUrl);
            outputStream = new BufferedOutputStream(new FileOutputStream(targetFilePath));
            content.transferTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(content);
        }
        return targetFilePath.getAbsolutePath();
    }

    /**
     * 生成文件的路径
     *
     * @param basePath       基础路径
     * @param originFileName 源文件名称
     * @return 生成该文件对应的路径
     */
    protected String generateFilePath(String basePath, String originFileName) {
        Assert.notNull(originFileName, "objectName must not be null");
        String filePre = UUID.randomUUID().toString().replaceAll("-", "");
        int suffixIndex = originFileName.lastIndexOf('.');
        if (suffixIndex == -1) {
            throw new BadRequestException(ResultCodeEnum.FILE_UPLOAD_ERROR, "文件上传失败!找不到文件后缀名");
        }
        originFileName = filePre + originFileName.substring(suffixIndex);
        originFileName = FileUtils.separatorFilePath(WWordConst.separatorChar,
            basePath,
            filePre,
            originFileName);
        return originFileName;
    }
}
