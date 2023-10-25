package io.github.cnsukidayo.wword.global.utils;

import org.springframework.util.Assert;

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

}
