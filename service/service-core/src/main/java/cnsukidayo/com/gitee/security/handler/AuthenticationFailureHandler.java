package cnsukidayo.com.gitee.security.handler;

import cnsukidayo.com.gitee.exception.AbstractWWordException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Authentication failure handler.
 *
 * @author johnniang
 */
public interface AuthenticationFailureHandler {

    /**
     * Calls when a user has been unsuccessfully authenticated.
     *
     * @param request   http servlet request
     * @param response  http servlet response
     * @param exception api exception
     * @throws IOException      io exception
     * @throws ServletException service exception
     */
    void onFailure(HttpServletRequest request, HttpServletResponse response,
                   AbstractWWordException exception) throws IOException, ServletException;
}
