package io.github.cnsukidayo.wword.global.utils;

import cn.hutool.core.io.FileUtil;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

/**
 * @author sukidayo
 * @date 2023/9/12 16:45
 */
public class FileUtils {

    public static final String ZIP_SUFFIX = "zip";
    public static final String RAR_SUFFIX = "rar";
    public static final String TAR_SUFFIX = "tar";
    public static final String SevenZ_SUFFIX = "7z";
    public static final String TAZ_SUFFIX = "taz";

    /**
     * 分隔文件路径,该方法会自动将所有的paths中间只拼接上一个separator分隔符<br>
     * 并且开头和结尾不带分隔符
     *
     * @param separatorChar 文件路径符号
     * @param paths         每个路径数组
     * @return 返回凭借的内容不为null
     */
    public static String separatorFilePath(char separatorChar, String... paths) {
        Assert.notEmpty(paths, "paths must not be empty");
        StringBuilder result = new StringBuilder();
        for (String path : paths) {

            if (path.indexOf(String.valueOf(separatorChar)) == 0) {
                path = path.substring(1);
            }
            result.append(path);
            if (path.lastIndexOf(separatorChar) != path.length() - 1) {
                result.append(separatorChar);
            }
        }

        if (result.lastIndexOf(String.valueOf(separatorChar)) == result.length() - 1) {
            return result.substring(0, result.length() - 1);
        }
        return result.toString();
    }

    public static void copyFolder(Path source, Path target, String... ignoreSuffix) throws IOException {
        Set<String> ignoreSuffixSet = Set.of(ignoreSuffix);
        Files.walkFileTree(source, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path current = target.resolve(source.relativize(dir).toString());
                Files.createDirectories(current);
                if (dir.toFile().isFile() && ignoreSuffixSet.contains(FileUtil.getSuffix(dir.toFile()))) {
                    return FileVisitResult.SKIP_SIBLINGS;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }


}
