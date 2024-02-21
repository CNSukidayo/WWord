package io.github.cnsukidayo.wword.oss.component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sukidayo
 * @date 2023/9/14 13:01
 */
public interface OSSComponent {

    /**
     * 上传文件并自动重命名文件
     *
     * @param inputStream 上传的文件流不为null
     * @param basePath    基础路径
     * @param fileName    上传的文件名
     * @return 返回上传成功后的文件路径;返回的是相对路径
     */
    String fileUpLoadAutoRename(InputStream inputStream, String basePath, String fileName);

    /**
     * 上传文件,该方法不会重命名文件,文件的路径即最终真实上传的路径
     *
     * @param inputStream 文件上传流
     * @param objectName  文件名称
     */
    void fileUpLoadOriginName(InputStream inputStream, String objectName);

    /**
     * 下载文件
     *
     * @param originUrl      下载的文件地址不为空(是源路径,即相对路径不是绝对路径)
     * @param targetBasePath 存放的基本路径不为空
     * @return 下载到本机的位置路径不为null
     */
    String downloadFile(String originUrl, String targetBasePath);

    /**
     * 得到文件下载流;该方法是一个回调,传递一个需要下载文件的相对路径<br>
     * 该方法返回该文件的输入流,因为不同的OSS实现获取输入流的方式也是不同的
     *
     * @param originUrl 下载的文件地址不为空(是源路径,即相对路径不是绝对路径)
     * @return 返回文件流input
     */
    InputStream getDownLoadInputStream(String originUrl) throws IOException;

}
