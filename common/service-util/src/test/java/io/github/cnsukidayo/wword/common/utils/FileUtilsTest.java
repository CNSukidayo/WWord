package io.github.cnsukidayo.wword.common.utils;


/**
 * @author sukidayo
 * @date 2023/9/12 17:01
 */

public class FileUtilsTest {

    public static void main(String[] args) {
        String path = FileUtils.separatorFilePath('/', "/123", "/2023/5/", "/AAA.PNG/");
        System.out.println(path);
    }

}