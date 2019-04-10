package com.telfa.andrei.service;

import com.github.pagehelper.PageHelper;
import com.telfa.andrei.common.Constants;
import com.telfa.andrei.enums.MemberStatusEnum;
import com.telfa.andrei.mapper.ResourceMapper;
import com.telfa.andrei.mapper.RoleResourceMapper;
import com.telfa.andrei.mapper.SysUserMapper;
import com.telfa.andrei.mapper.UserRoleMapper;
import com.telfa.andrei.model.*;
import com.telfa.andrei.utils.DateUtil;
import com.telfa.andrei.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台系统用户业务逻辑处理
 * @since 1.8
 */
@Service
public class SysUserService {

    private final SysUserMapper sysUserMapper;

    @Autowired
    private final UserRoleMapper userRoleMapper;

    private final RoleService roleService;

    private final RoleResourceMapper roleResourceMapper;

    private final ResourceMapper resourceMapper;
    @Autowired
    public SysUserService(SysUserMapper sysUserMapper, UserRoleMapper userRoleMapper, RoleService roleService, RoleResourceMapper roleResourceMapper, ResourceMapper resourceMapper) {
        this.sysUserMapper = sysUserMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleService = roleService;
        this.roleResourceMapper = roleResourceMapper;
        this.resourceMapper = resourceMapper;
    }

    /**
     * 根据用户名获取后台用户对象
     * @param username 用户名
     * @return 后台用户对象
     */
    public List<SysUser> listSysUserByUsername(String username) {
        if(StringUtils.isBlank(username)) {
            throw new RuntimeException("用户名不能为空");
        }
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria()
                .andUsernameEqualTo(username.toLowerCase());
        return sysUserMapper.selectByExample(sysUserExample);
    }

    /**
     * 获取所有系统用户列表
     * @param sysUser 查询条件
     * @param page 当前页
     * @param pageRows 每页行数
     * @return 系统用户列表
     */
    public List<SysUser> listAllSysUsers(SysUser sysUser, int page, int pageRows) {
        PageHelper.startPage(page, pageRows);
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        if(StringUtils.isNotBlank(sysUser.getUsername())) {
            criteria.andUsernameLike("%".concat(sysUser.getUsername().trim()).concat("%"));
        }
        if(StringUtils.isNotBlank(sysUser.getRealname())) {
            criteria.andRealnameLike("%".concat(sysUser.getRealname().trim()).concat("%"));
        }
        sysUserExample.setOrderByClause("create_time desc");
        return sysUserMapper.selectByExample(sysUserExample);
    }

    /**
     * 停用或者启用后台用户
     * @param sysUserId 后台用户id
     * @return 更新数据的行数
     */
    public int disable(Integer sysUserId, SysUser currentSysUser,String option) {
        SysUser sysUserInDb = sysUserMapper.selectByPrimaryKey(sysUserId);
        if(StringUtils.equalsIgnoreCase("admin", sysUserInDb.getUsername())) {
            throw new RuntimeException("不能删除管理员");
        }

        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(sysUserId);
        sysUser.setUpdator(currentSysUser.getSysUserId());
        sysUser.setUpdateTime((int) DateUtil.getDateline());
        if("disable".equals(option)){
            sysUser.setStatus(MemberStatusEnum.DISABLED.getCode().byteValue());
        }else if ("enable".equals(option)){
            sysUser.setStatus(MemberStatusEnum.NORMAL.getCode().byteValue());
        }
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 获取所有系统用户对象
     * @return
     */
    public List<SysUser> getAllSysuser(){
        return sysUserMapper.selectAll();
    }


    /**
     * 获取所有正常状态的用户
     * @return
     */
    public List<SysUser> getAllAvailableSysuser(){
        SysUserExample e = new SysUserExample();
        SysUserExample.Criteria criteria = e.createCriteria();
        criteria.andStatusEqualTo(new Integer(Constants.NO).byteValue());
        List<SysUser> sysUsers = sysUserMapper.selectByExample(e);
        return (sysUsers != null && sysUsers.size()>0)?sysUsers:null;
    }

    public List<UserRole> getAllUserIdThatAlreadyRole(int roleId){
        UserRoleExample e = new UserRoleExample();
        UserRoleExample.Criteria criteria = e.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(e);
        return (userRoles != null && userRoles.size()>0)?userRoles:null;
    }

    /**
     * 根据主键获取系统用户信息
     * @param sysUserId 主键id
     * @return 系统用户对象
     */
    public SysUser getSysUserById(Integer sysUserId) {
        return sysUserMapper.selectByPrimaryKey(sysUserId);
    }

    /**
     * 重置系统用户密码
     * @param passwordPrefix 重置后的密码前缀
     * @param sysUserId 系统用户id
     * @return 大于0成功, 否则失败
     */
    public int resetPasswordBySysUserId(String passwordPrefix, Integer sysUserId) {
        SysUser updateSysUser = new SysUser();
        updateSysUser.setSysUserId(sysUserId);
        updateSysUser.setPassword(MD5Util.md5(passwordPrefix + "123"));
        return sysUserMapper.updateByPrimaryKeySelective(updateSysUser);
    }

    /**
     * 查询用户绑定的角色个数
     * @param sysuserId
     * @return
     */
    public List<UserRole> getRoleByUserId (int sysuserId){
        int count = 0;
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andSysUserIdEqualTo(sysuserId);
        final List<UserRole> userRoles = userRoleMapper.selectByExample(example);
        return (userRoles != null && userRoles.size()>0)?userRoles:null;
    }

    /**
     * 新增系统用户
     * @param user
     * @return 0 新增失败 1 新增成功
     */
    public int insert(SysUser user,String roleIds){
        List<Integer> roleIdList = new ArrayList<>();
        int length = 0;
        int insertUserRoleResult = 0;
        if(roleIds != null && !"".equals(roleIds)){
            String[] strings = roleIds.split("-");
            for(int i = 0;i < strings.length;i++){
                roleIdList.add(Integer.valueOf(strings[i].trim()));
            }
        }
        int insert = sysUserMapper.insertSelective(user);
        //添加角色
        if(insert >0){
            if((roleIdList != null) && (roleIdList.size() > 0)){
                length = roleIdList.size();
                for (Integer roleId:roleIdList) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setSysUserId(user.getSysUserId());
                    insertUserRoleResult += userRoleMapper.insert(userRole);
                }
            }
        }
        return ((length >0) && (length ==insertUserRoleResult))?length+insert:insert;
    }

    /**
     * 编辑系统用户
     * @param user
     * @return
     */
    public int update(SysUser user,String roleIds){
        List<Integer> roleIdList = new ArrayList<>();
        List<Integer> oldRoleIdList = new ArrayList<>();
        int updateUserRoleResult = 0;
        int length = 0;
        if(roleIds != null && !"".equals(roleIds)){
            String[] strings = roleIds.split("-");
            for(int i = 0;i < strings.length;i++){
                roleIdList.add(Integer.valueOf(strings[i].trim()));
            }
        }
        //根据用户ID查出原本选定的角色
        List<Role> userRoles = roleService.listRoleByUser(user.getSysUserId());
        //取出每一个角色ID
        if((userRoles != null) && (userRoles.size() > 0)){
            for (Role c:userRoles) {
                //将原本绑定的角色ID记入list集合
                oldRoleIdList.add(c.getRoleId());
            }
        }
        //如果以前有绑定角色 全部删掉
        if((oldRoleIdList != null) && (oldRoleIdList.size() > 0)){
            for (Integer i:oldRoleIdList) {
                //删除
                UserRoleExample example1 = new UserRoleExample();
                UserRoleExample.Criteria criteria1 = example1.createCriteria();
                criteria1.andRoleIdEqualTo(i).andSysUserIdEqualTo(user.getSysUserId());
                userRoleMapper.deleteByExample(example1);
            }
        }
        //重新添加角色
        if((roleIdList != null) && (roleIdList.size() > 0)){
            length = roleIdList.size();
            for (Integer guestId:roleIdList) {
                UserRole  r = new UserRole();
                r.setSysUserId(user.getSysUserId());
                r.setRoleId(guestId);
                updateUserRoleResult += userRoleMapper.insert(r);
            }
        }else{
            //前台没有选择任何角色 全部删除
            UserRoleExample example2 = new UserRoleExample();
            UserRoleExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andSysUserIdEqualTo(user.getSysUserId());
            userRoleMapper.deleteByExample(example2);
        }
        user.setPassword(getSysUserById(user.getSysUserId()).getPassword());
         sysUserMapper.updateByPrimaryKey(user);
        return  ((length > 0) &&(length == updateUserRoleResult))?length+1:1;
    }

    public int updatePwd(SysUser user){
        return sysUserMapper.updateByPrimaryKey(user);
    }

}
