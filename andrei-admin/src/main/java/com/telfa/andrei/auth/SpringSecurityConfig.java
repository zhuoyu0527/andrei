package com.telfa.andrei.auth;

import com.alibaba.fastjson.JSONObject;
import com.telfa.andrei.model.SysUser;
import com.telfa.andrei.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SpringSecurity安全配置
 * @since 1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Autowired
    private SysUserUserDetailService sysUserUserDetailService;

    @Autowired
    private AuthFilterInvocationSecurityMetadataSource authFilterInvocationSecurityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).clearAuthentication(true).permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setSecurityMetadataSource(authFilterInvocationSecurityMetadataSource);
                        return fsi;
                    }
                })
                .antMatchers("/403").permitAll()
                .antMatchers("/").authenticated()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**",
                "/images/**",
                "/js/**",
                "/layui/**",
                "/ueditor/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * @return 自定义密码加密类
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            /**
             * 对客户端密码加密
             * @param charSequence 客户端未加密的密码
             * @return 加密后的密码
             */
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.md5(charSequence.toString());
            }

            /**
             * 客户端与数据库中保存的密码匹配
             * @param charSequence 客户端未加密的密码
             * @param s 数据库中的密码
             * @return 如果匹配返回true, 否则false
             */
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return StringUtils.equals(encode(charSequence), s);
            }
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        // 用户访问没有权限的资源时的处理, 转向403页面
        return (httpServletRequest, httpServletResponse, e) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null) {
                LOGGER.info("用户[{}]试图访问受保护资源[{}].", authentication.getName(), httpServletRequest.getRequestURI());
            }
            if(httpServletRequest.getMethod() == HttpMethod.GET.name()){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath().concat("/noPermission/403"));
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath().concat("/noPermission/error"));
            }
        };
    }

    /**
     * 用户登录成功后的处理类
     */
    public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            SysUser sysUser = ContextHelper.currentSysUser();
            request.getSession().setAttribute("sysUser", sysUser);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpServletResponse.SC_OK);
            jsonObject.put("msg", "");

            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(jsonObject.toString());
            printWriter.flush();
        }
    }

    /**
     * 用户登录失败后的处理类
     */
    public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            jsonObject.put("msg", "认证失败");

            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(jsonObject.toString());
            printWriter.flush();
        }
    }
}
