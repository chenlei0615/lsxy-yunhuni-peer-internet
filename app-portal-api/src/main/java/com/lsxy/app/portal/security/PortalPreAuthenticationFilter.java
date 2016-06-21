package com.lsxy.app.portal.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liups on 2016/6/20.
 */
public class PortalPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter{
    public static final String SSO_TOKEN = "X-Auth-Token";
    public static final String SSO_CREDENTIALS = "N/A";

    public PortalPreAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(SSO_TOKEN);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return SSO_CREDENTIALS;
    }
}
