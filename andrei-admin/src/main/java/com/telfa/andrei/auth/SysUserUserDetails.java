package com.telfa.andrei.auth;

import com.telfa.andrei.enums.MemberStatusEnum;
import com.telfa.andrei.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;


/**
 * @since 1.8
 */
public class SysUserUserDetails extends SysUser implements org.springframework.security.core.userdetails.UserDetails {


    private long serialVersionUID = 1001L;
    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Byte status;

    /**
     * 权限集合
     */
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public SysUserUserDetails(String username, String password, Byte status, Collection<SimpleGrantedAuthority> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    // 账户是否未过期
    public boolean isAccountNonExpired() {
        return status.equals(MemberStatusEnum.NORMAL.getCode().byteValue());
    }

    @Override
    // 账户是否未锁定
    public boolean isAccountNonLocked() {
        return status.equals(MemberStatusEnum.NORMAL.getCode().byteValue());
    }

    @Override
    // 密码是否未过期
    public boolean isCredentialsNonExpired() {
        return status.equals(MemberStatusEnum.NORMAL.getCode().byteValue());
    }

    @Override
    // 是否可用
    public boolean isEnabled() {
        return status.equals(MemberStatusEnum.NORMAL.getCode().byteValue());
    }

    public void setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }
}
