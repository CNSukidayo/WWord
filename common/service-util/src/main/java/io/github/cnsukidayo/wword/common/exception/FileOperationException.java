package io.github.cnsukidayo.wword.common.exception;

/**
 * @author sukidayo
 * @date 2023/8/1 18:44
 */
public class FileOperationException extends ServiceException{
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
