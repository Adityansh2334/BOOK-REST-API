package org.library.com.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Aditya
 * Security Configuratin Class
 */
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    /**
     * This Overriden Method is for show Authentication Error
     * Message when Authentication Fails with Status code 401
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @param authEx AuthenticationException object
     * @throws IOException throws  IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    /**
     * Overriden method of BasicAuthenticationEntryPoint class
     * use for setting RealmName.
     */
    @Override
    public void afterPropertiesSet() {
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }
}
