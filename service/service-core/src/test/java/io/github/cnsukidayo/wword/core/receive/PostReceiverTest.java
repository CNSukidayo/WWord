package io.github.cnsukidayo.wword.core.receive;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.cnsukidayo.wword.global.exception.FileHandlerException;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author sukidayo
 * @date 2023/9/14 15:44
 */
public class PostReceiverTest extends TestCase {

    public void testPublishPost() {
        String filePath = "D:\\Program\\Java Project\\WWord\\wwordResources\\post\\temp\\e1c7b5c86595459e96b50272e3cbd2e2\\e1c7b5c86595459e96b50272e3cbd2e2.zip";
        File file = Paths.get(filePath).toFile();
        boolean hasMarkdown = false;
        ZipFile zipFile = null;
        BufferedReader bufferedReader = null;
        try {
            zipFile = new ZipFile(file, Charset.forName("GBK"));
            Iterator<? extends ZipEntry> iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry zipEntry = iterator.next();
                String entryName = zipEntry.getName();
                if (FileUtil.isFile(entryName) && !WWordConst.allowMarkdownSuffix.contains(FileUtil.getSuffix(entryName)))
                    throw new FileHandlerException("上传压缩包中含有不允许的文件类型: " + entryName);

                if (FileUtil.getSuffix(entryName).equalsIgnoreCase(".md")) {
                    if (hasMarkdown) throw new FileHandlerException("markdown文件数量多于一个!");
                    hasMarkdown = true;
                }
            }
            // 解压文件
            File unzipFile = ZipUtil.unzip(file, file.getParentFile(), Charset.forName("GBK"));
            // 得到唯一的markdown文件
            File markdown = null;
            for (File listFile : Optional.ofNullable(unzipFile.listFiles())
                .orElseThrow(() -> new FileHandlerException("上传文件内容为空!"))) {
                String walkFileName = listFile.getName();
                if (FileUtil.getSuffix(walkFileName).equalsIgnoreCase("md")) {
                    markdown = listFile;
                }
            }
            Optional.ofNullable(markdown).orElseThrow(() -> new FileHandlerException("没有找到markdown文件!"));
            // 读取markdown文件
            bufferedReader = new BufferedReader(new FileReader(markdown, StandardCharsets.UTF_8));
            // 更多的操作

            // 将markdown文件的内容更新到数据库中

            System.out.println("233333");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtil.close(zipFile);
            IoUtil.close(bufferedReader);
        }

    }
}