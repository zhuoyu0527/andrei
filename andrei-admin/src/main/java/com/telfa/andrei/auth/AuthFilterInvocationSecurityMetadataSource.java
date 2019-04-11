package com.telfa.andrei.auth;

import com.telfa.andrei.model.RoleResource;
import com.telfa.andrei.service.RoleResourceService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 */
@Component
public class AuthFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    @Autowired
    private RoleResourceService roleResourceService;

    private Map<String, List<RoleResource>> roleResourceMaps;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        return getAttributesByRequestUrl(requestUrl);
    }

    private List<ConfigAttribute> getAttributesByRequestUrl(String requestUrl) {
        if(CollectionUtils.isEmpty(roleResourceMaps)) {
            return null;
        }
        List<RoleResource> roleResources = roleResourceMaps.get(requestUrl);
        if(CollectionUtils.isEmpty(roleResources)) {
            return null;
        }
        return SecurityConfig.createList(roleResources.stream().map(RoleResource::getRoleCode).toArray(String[]::new));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<RoleResource> roleResources = roleResourceService.listAllRoleResource();
        if(!CollectionUtils.isEmpty(roleResources)) {
            this.roleResourceMaps = roleResources.stream()
                    .collect(Collectors.groupingBy(RoleResource::getUrl));
        }
    }
}
