package com.telfa.andrei.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.telfa.andrei.auth.ContextHelper;
import com.telfa.andrei.base.WebResult;
import com.telfa.andrei.common.Constants;
import com.telfa.andrei.model.Role;
import com.telfa.andrei.model.SysUser;
import com.telfa.andrei.model.UserRole;
import com.telfa.andrei.service.RoleService;
import com.telfa.andrei.service.SysUserService;
import com.telfa.andrei.utils.DateUtil;
import com.telfa.andrei.vo.RoleVO;
import com.telfa.andrei.vo.Xtree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 角色管理控制器
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {


    @Autowired
    RoleService roleService;
    @Autowired
    SysUserService sysUserService;

    @RequestMapping("/list")
    public String toListPage(){
        return "admin/role/roleList";
    }

    @RequestMapping("/page")
    @ResponseBody
    public WebResult listByPage(RoleVO roleVO,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "limit", defaultValue = "10") int pageRows) {
        PageInfo<Role> pageInfo = new PageInfo<>(roleService.listAllRole(roleVO, page, pageRows));
        return new WebResult(HttpStatus.OK.value(), "", pageInfo.getTotal(), pageInfo.getList());
    }


    @RequestMapping("/toAdd")
    @Secured("ROLE_ADMIN")
    public String toAdd(Model m){
        m.addAttribute("role",new Role());
        return "admin/role/roleAdd";
    }


    @RequestMapping("/market/{roleId}/{disable}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public WebResult market(@PathVariable(name = "roleId") Integer roleId,@PathVariable(name = "disable")Integer status){
        int result = 0;
        try{
            result = roleService.market(roleId, status);
            if(result >0){
                return new WebResult().success();
            }else{
                return new WebResult().error();
            }

        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }

    @RequestMapping("/disable/{roleId}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public WebResult market(@PathVariable(name = "roleId") Integer roleId){
        int result = 0;
        try{
            result = roleService.deleteRoleByRoleId(roleId);
            if(result >0){
                return new WebResult().success();
            }else{
                return new WebResult().error();
            }

        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }

    @RequestMapping("/edit/{roleId}")
    @Secured("ROLE_ADMIN")
    public String toEditPage(@PathVariable("roleId")Integer roleId,Model m){
        if(roleId != null){
            Role role = roleService.getRoleById(roleId);
            m.addAttribute("role",role);
        }
        return "admin/role/roleAdd";
    }

    @RequestMapping("/addNewRole")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public WebResult addNewGuest(Role role,String sysUserIds){
        int result = 0;
        int roleId = 0;
        try{
            if(role != null){
                if(role.getRoleId() != null){

                    //查询该角色绑定的用户个数
                    List<SysUser> sysUsers = roleService.listsysUserByRole(role.getRoleId());
                    //如果前台传递的用户信息没有  而角色原来有用户
                    if(sysUserIds == null || "".equals(sysUserIds) && sysUsers != null){
                        StringBuilder sb = new StringBuilder();
                        for (int k = 0;k< sysUsers.size();k++) {
                            if(k == sysUsers.size()-1){
                                sb.append(sysUsers.get(k).getSysUserId());
                            }else{
                                sb.append(sysUsers.get(k).getSysUserId()+"-");
                            }
                        }
                        sysUserIds = sb.toString();
                    }
                    //编辑
                    role.setUpdateTime((int) DateUtil.getDateline());
                    role.setUpdator(ContextHelper.currentSysUser().getSysUserId());
                    result = roleService.update(role,sysUserIds);
                }else {
                    //添加
                    role.setCreator(ContextHelper.currentSysUser().getSysUserId());
                    role.setCreateTime((int)DateUtil.getDateline());
                    role.setUpdateTime((int)DateUtil.getDateline());
                    role.setRoleCode("ROLE-"+ UUID.randomUUID().toString().replace("-",""));
                    role.setDisabled(Constants.NO);
                    role.setStatus(Constants.NO);
                    result = roleService.insertRole(role,sysUserIds);;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
        return new WebResult().result(result > 0).data(role.getRoleId());
    }


    @RequestMapping("showRole/{sysUserId}")
    public String toBindRolePage(@PathVariable("sysUserId")int sysUserId, Model model){
        List<Role> userRoles = null;
        if(sysUserId != -1){
            //查询出此系统用户所拥有的角色
             userRoles = roleService.listRoleByUser(sysUserId);
        }
        //查询出所有角色
        List<Role> allRoles = roleService.getAllRole();
        model.addAttribute("sysUserId",(sysUserId != -1)?sysUserId:-1);
        model.addAttribute("userRoles", (userRoles != null && userRoles.size() >0)? JSONObject.toJSONString(userRoles):-1);
        model.addAttribute("allRoles",(allRoles != null && allRoles.size() >0)?allRoles:null);
        return "admin/role/roleBind";
    }

    @RequestMapping("/listRoleBySysuserId")
    @ResponseBody
    public WebResult listRoleBySysuserId(int sysUserId){
        List<Role> guests = new ArrayList<>();
        try{
            guests = roleService.listRoleBySysUserId(sysUserId);
        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
        return new WebResult().result((guests != null) && (guests.size() > 0)).data(JSONObject.toJSONString(guests));
    }


    @RequestMapping("/allocateResource/{roleId}")
    @Secured("ROLE_ADMIN")
    public String allocateResource(@PathVariable("roleId")int roleId, Model m){
        m.addAttribute("roleId",roleId);
        return "admin/role/allcateResource";
    }

    @RequestMapping("/getResource/{roleId}")
    @ResponseBody
    public  String getResourceByAjax(@PathVariable("roleId") int roleId){
        List<Xtree>  xtrees = new ArrayList<>();

            xtrees = roleService.listAllResource(roleId);
            if(xtrees != null && xtrees.size() >0){
                return JSONObject.toJSONString(xtrees)
                        .replace("\"title\"","title")
                        .replace("\"value\"","value")
                        .replace("\"checked\"","checked")
                        .replace("\"data\"","data");
            }else{
                return null;
            }

    }


    /**
     * 给角色绑定权限
     * @param resourceIds
     * @param roleId
     * @return
     */
    @RequestMapping("bindResourceForRole")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public WebResult bindResourceForRole(String resourceIds,int roleId){
        try{
            int i = roleService.bindResourceForRole(resourceIds, roleId);
            if(i >0){
                return new WebResult().success();
            }else{
                return new WebResult().error();
            }

        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }


    @RequestMapping("/showAllUser/{roleId}")
    public String toSysUserPage(@PathVariable("roleId") int roleId,Model model){
        List<UserRole> lists = null;
        //列出所有已经拥有角色的用户
        if(roleId != -1){
            lists = sysUserService.getAllUserIdThatAlreadyRole(roleId);
        }
        List<SysUser> guests = sysUserService.getAllAvailableSysuser();
        model.addAttribute("roleId",(roleId != -1)?roleId:-1);
        model.addAttribute("courseGuestList", (lists != null && lists.size() >0)?JSONObject.toJSONString(lists):-1);

        model.addAttribute("allGuestList",(guests != null && guests.size() >0)?guests:null);
        return "admin/role/showSysuser";
    }

    @RequestMapping("/listSysuserName")
    @ResponseBody
    public WebResult listSysuserName(int roleId){
        List<SysUser> sysUsers = new ArrayList<>();
        try{
            sysUsers  = roleService.listsysUserByRole(roleId);
        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
        return new WebResult().result((sysUsers != null) && (sysUsers.size() > 0)).data(JSONObject.toJSONString(sysUsers));
    }
}
