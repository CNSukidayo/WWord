package cnsukidayo.com.gitee.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractWWordException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
