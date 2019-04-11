package com.telfa.andrei.auth;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 自定义授权管理
 * @since 1.8
 */
@Component
public class ResourceAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if(grantedAuthority instanceof DynamicGrantedAuthority) {
                if(matchers(((DynamicGrantedAuthority) grantedAuthority).getUrl(), request)) {
                    return;
                }
            } else if(grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS")) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/login");
                if(antPathRequestMatcher.matches(request)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("您没有权限访问此页面");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private boolean matchers(String url, HttpServletRequest request) {
        return new AntPathRequestMatcher(url).matches(request);
    }
}
