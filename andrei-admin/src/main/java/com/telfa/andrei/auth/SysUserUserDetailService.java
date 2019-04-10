package com.telfa.andrei.auth;

import com.telfa.andrei.enums.MemberStatusEnum;
import com.telfa.andrei.model.Role;
import com.telfa.andrei.model.SysUser;
import com.telfa.andrei.service.RoleService;
import com.telfa.andrei.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * SpringSecurity相关的获取用户类
 * @since 1.8
 */
@Service
public class SysUserUserDetailService implements UserDetailsService, Serializable {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<SysUser> sysUsers = sysUserService.listSysUserByUsername(s);
        if(CollectionUtils.isEmpty(sysUsers)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 获取列表中用户状态为可用的用户对象
        Optional<SysUser> optionalSysUser = sysUsers.stream()
                .filter(d -> d.getStatus().equals(MemberStatusEnum.NORMAL.getCode().byteValue()))
                .findFirst();
        if(!optionalSysUser.isPresent()) {
            // 可登录状态的用户不存在
            throw new DisabledException("用户已被限制登录");
        }
        // 获取用户
        SysUser sysUser = optionalSysUser.get();

        // 用户权限
        List<SimpleGrantedAuthority> grantedAuthorities = null;

        // 获取用户所属角色
        List<Role> roles = roleService.listRoleBySysUserId(sysUser.getSysUserId());
        if(!CollectionUtils.isEmpty(roles)) {
            grantedAuthorities = roles.stream()
                    .map(Role::getRoleCode)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        SysUserUserDetails sysUserUserDetails = new SysUserUserDetails(sysUser.getUsername(), sysUser.getPassword(), sysUser.getStatus(), grantedAuthorities);

        try {
            BeanUtils.copyProperties(sysUser, sysUserUserDetails);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return sysUserUserDetails;
    }

}
