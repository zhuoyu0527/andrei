package com.telfa.andrei.service;

import com.github.pagehelper.PageHelper;
import com.telfa.andrei.common.Constants;
import com.telfa.andrei.mapper.*;
import com.telfa.andrei.mapper.UserRoleMapper;
import com.telfa.andrei.model.*;
import com.telfa.andrei.utils.DateUtil;
import com.telfa.andrei.vo.RoleVO;
import com.telfa.andrei.vo.Xtree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务逻辑处理
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 获取所有角色列表
     * @param roleVO
     * @param pageNo
     * @param pageRows
     * @return
     */
    public List<Role> listAllRole(RoleVO roleVO, int pageNo, int pageRows){
        RoleExample example = buildRoleExample(roleVO);
        PageHelper.startPage(pageNo,pageRows);
        List<Role> roles = roleMapper.selectByExample(example);
        return (roles != null && roles.size() > 0)?roles:null;
    }

    private RoleExample buildRoleExample(RoleVO roleVO){
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        //封装角色名称
        if(roleVO != null && StringUtils.isNotBlank(roleVO.getContent())){
            criteria.andRoleNameLike("%"+roleVO.getContent()+"%");
        }
        //封装选择日期
        if(roleVO!=null&& StringUtils.isNotBlank(roleVO.getCreateTimeBegin())&& StringUtils.isNotBlank(roleVO.getCreateTimeEnd())){
            criteria.andCreateTimeBetween((int) DateUtil.strToSeconds(roleVO.getCreateTimeBegin().concat(" 00:00:00")),(int)DateUtil.strToSeconds(roleVO.getCreateTimeEnd().concat(" 23:59:59")));
        }else if(roleVO!=null&& StringUtils.isBlank(roleVO.getCreateTimeBegin())&& StringUtils.isNotBlank(roleVO.getCreateTimeEnd())){
            criteria.andCreateTimeLessThanOrEqualTo((int)DateUtil.strToSeconds(roleVO.getCreateTimeEnd().concat(" 23:59:59")));
        }else if(roleVO!=null&& StringUtils.isNotBlank(roleVO.getCreateTimeBegin())&& StringUtils.isBlank(roleVO.getCreateTimeEnd())){
            criteria.andCreateTimeGreaterThanOrEqualTo((int)DateUtil.strToSeconds(roleVO.getCreateTimeBegin().concat(" 00:00:00")));
        }
        criteria.andDisabledEqualTo(Constants.NO);
        return example;
    }

    public List<Role> getAllRole(){
        RoleExample example =  new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Constants.NO).andDisabledEqualTo(Constants.NO);
        List<Role> roles = roleMapper.selectByExample(example);
        return (roles != null && roles.size() > 0)?roles:null;
    }


    /**
     * 角色停用/启用 0 启用 1 停用
     * @param roleId
     * @param status
     * @return
     */
    public int market(int roleId,int status){
        Role role = roleMapper.selectByPrimaryKey(roleId);
        role.setStatus(status);
        return roleMapper.updateByPrimaryKey(role);
    }
    /**
     * 新增角色
     * @param role 角色实体
     */
    public int insertRole(Role role,String sysUserIds) {
        List<Integer> roleIdList = new ArrayList<>();
        int length = 0;
        int insertUserRoleResult = 0;
        if(sysUserIds != null && !"".equals(sysUserIds)){
            String[] strings = sysUserIds.split("-");
            for(int i = 0;i < strings.length;i++){
                roleIdList.add(Integer.valueOf(strings[i].trim()));
            }
        }
       int insert = roleMapper.insert(role);
        //添加用户
        if(insert >0){
            if((roleIdList != null) && (roleIdList.size() > 0)){
                length = roleIdList.size();
                for (Integer sysUserId:roleIdList) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(role.getRoleId());
                    userRole.setSysUserId(sysUserId);
                    insertUserRoleResult += userRoleMapper.insert(userRole);
                }
            }
        }
        return ((length >0) && (length ==insertUserRoleResult))?length+insert:insert;
    }

    /**
     * 删除一个角色, 逻辑删除
     * @param roleId 要删除的角色id
     */
    public int deleteRoleByRoleId(Integer roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setStatus(Constants.NO);
        role.setDisabled(Constants.YES);
       return roleMapper.updateByPrimaryKeySelective(role);
    }

    public int update(Role role,String sysUserIds){
        List<Integer> roleIdList = new ArrayList<>();
        List<Integer> oldRoleIdList = new ArrayList<>();
        int updateUserRoleResult = 0;
        int length = 0;
        if(sysUserIds != null && !"".equals(sysUserIds)){
            String[] strings = sysUserIds.split("-");
            for(int i = 0;i < strings.length;i++){
                roleIdList.add(Integer.valueOf(strings[i].trim()));
            }
        }
        //根据角色ID查出原本选定的用户
        List<SysUser> sysUsers = listsysUserByRole(role.getRoleId());
        //取出每一个用户ID
        if((sysUsers != null) && (sysUsers.size() > 0)){
            for (SysUser c:sysUsers) {
                //将原本绑定的角色ID记入list集合
                oldRoleIdList.add(c.getSysUserId());
            }
        }
        //如果以前有绑定用户 全部删掉
        if((oldRoleIdList != null) && (oldRoleIdList.size() > 0)){
            for (Integer i:oldRoleIdList) {
                //删除
                UserRoleExample example1 = new UserRoleExample();
                UserRoleExample.Criteria criteria1 = example1.createCriteria();
                criteria1.andRoleIdEqualTo(role.getRoleId()).andSysUserIdEqualTo(i);
                userRoleMapper.deleteByExample(example1);
            }
        }
        //重新添加用户
        if((roleIdList != null) && (roleIdList.size() > 0)){
            length = roleIdList.size();
            for (Integer sysUserId:roleIdList) {
                UserRole  r = new UserRole();
                r.setSysUserId(sysUserId);
                r.setRoleId(role.getRoleId());
                updateUserRoleResult += userRoleMapper.insert(r);
            }
        }else{
            //前台没有选择任何用户 全部删除
            UserRoleExample example2 = new UserRoleExample();
            UserRoleExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andRoleIdEqualTo(role.getRoleId());
            userRoleMapper.deleteByExample(example2);
        }
        roleMapper.updateByPrimaryKeySelective(role);
        return  ((length > 0) &&(length == updateUserRoleResult))?length+1:1;
    }

    public Role getRoleById(int roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 获取用户所属角色
     * @param sysUserId 用户id
     *
     */
    public List<Role> listRoleBySysUserId(Integer sysUserId) {
        return roleMapper.listRoleBySysUserId(sysUserId);
    }

    /**
     * 根据系统用户查询对应角色
     * @param sysUserId
     * @return
     */
    public List<Role> listRoleByUser(Integer sysUserId){
        List<Role> result = new ArrayList<Role>();
        UserRoleExample e = new UserRoleExample();
        final UserRoleExample.Criteria c = e.createCriteria();
        c.andSysUserIdEqualTo(sysUserId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(e);
        if(userRoles != null && userRoles.size() >0){
            for (UserRole r:userRoles) {
                RoleExample ex = new RoleExample();
                RoleExample.Criteria criteria = ex.createCriteria();
                criteria.andRoleIdEqualTo(r.getRoleId());
                Role role = roleMapper.selectOneByExample(ex);
                result.add(role);
            }
        }
        return (result != null && result.size() >0)?result:null;
    }


    public List<SysUser> listsysUserByRole(Integer roleId){
        List<SysUser> sysUsers = new ArrayList<>();
        UserRoleExample e = new UserRoleExample();
        final UserRoleExample.Criteria c = e.createCriteria();
        c.andRoleIdEqualTo(roleId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(e);
        if(userRoles != null && userRoles.size() >0){
            for (UserRole rr:userRoles) {
                    SysUserExample ex = new SysUserExample();
                SysUserExample.Criteria criteria = ex.createCriteria();
                criteria.andSysUserIdEqualTo(rr.getSysUserId());
                 SysUser sysUser = sysUserMapper.selectOneByExample(ex);
                sysUsers.add(sysUser);
            }
        }

        return (sysUsers != null && sysUsers.size()>0)?sysUsers:null;
    }
    public List<Xtree> listAllResource(int roleId){
        List<Xtree> result = new ArrayList<>();

        //查出父资源
        ResourceExample ex = new ResourceExample();
        ex.setOrderByClause("show_order asc");
        ResourceExample.Criteria criteria = ex.createCriteria();
        criteria.andParentIdEqualTo(0);
        List<Resource> pResources = resourceMapper.selectByExample(ex);
        if(pResources != null && pResources.size() >0){
            for (Resource r: pResources) {
                Xtree pTree = new Xtree();
                pTree.setTitle(r.getResourceName());
                pTree.setValue(String.valueOf(r.getResourceId()));

                //查出子菜单
                ResourceExample ex1 = new ResourceExample();
                ex1.setOrderByClause("show_order asc");
                ResourceExample.Criteria criteria1 = ex1.createCriteria();
                criteria1.andParentIdEqualTo(r.getResourceId());
                List<Resource> cResources = resourceMapper.selectByExample(ex1);

                if(cResources != null && cResources.size()>0){
                    List<Xtree> childXtree = new ArrayList<>();
                    for (Resource child: cResources) {
                        Xtree cXtree = new Xtree();
                        cXtree.setTitle(child.getResourceName());
                        cXtree.setValue(String.valueOf(child.getResourceId()));
                        cXtree.setData(new ArrayList<Xtree>());
                        childXtree.add(cXtree);

                    }
                    pTree.setData(childXtree);
                }
                result.add(pTree);
            }
        }

        if(result != null && result.size() >0){
            //根据roleId查出已有的资源
            RoleResourceExample e = new RoleResourceExample();
            RoleResourceExample.Criteria criteria1 = e.createCriteria();
            criteria1.andRoleIdEqualTo(roleId);
            List<RoleResource> roleResources = roleResourceMapper.selectByExample(e);
            if(roleResources != null && roleResources.size() >0){
                //遍历result
                for (Xtree tree:result) {
                    for(RoleResource rr:roleResources){
                        //判断父菜单
                        if(tree.getValue().equals(String.valueOf(rr.getResourceId()))){
                                tree.setChecked(true);
                                if(tree.getData() != null && tree.getData().size() >0){
                                    for(Xtree childTree:tree.getData()){
                                        for(RoleResource rrr:roleResources){
                                            if(childTree.getValue().equals(String.valueOf(rrr.getResourceId()))){
                                                childTree.setChecked(true);
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
        return (result != null  && result.size()>0)?result:null;
    }


    /**
     * 绑定资源
     * @param resourceIds
     * @param roleId
     * @return
     */
    public int bindResourceForRole(String resourceIds,int roleId){
         int result = 0;

        if(resourceIds != null &&!resourceIds.equals("")){

            //查询出已有的资源
            RoleResourceExample e = new RoleResourceExample();
            RoleResourceExample.Criteria criteria1 = e.createCriteria();
            criteria1.andRoleIdEqualTo(roleId);
            List<RoleResource> roleResources = roleResourceMapper.selectByExample(e);
            if(roleResources != null && roleResources.size() >0){
                for(RoleResource rrr:roleResources){
                    RoleResourceExample ex = new RoleResourceExample();
                    RoleResourceExample.Criteria criteria = ex.createCriteria();
                    criteria.andRoleIdEqualTo(roleId).andResourceIdEqualTo(rrr.getResourceId());
                    result +=roleResourceMapper.deleteByExample(ex);
                }
            }

            int resourceId = 0;
            String[] split = resourceIds.split(",");
            for(int i = 0;i < split.length;i++){
                resourceId = Integer.valueOf(split[i]);
                RoleResource rr = new RoleResource();
                rr.setRoleId(roleId);
                rr.setResourceId(resourceId);
                result += roleResourceMapper.insert(rr);
            }

        }
        return result;
    }

}
