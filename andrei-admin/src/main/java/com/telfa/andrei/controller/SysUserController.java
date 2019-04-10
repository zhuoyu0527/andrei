package com.telfa.andrei.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.telfa.andrei.auth.ContextHelper;
import com.telfa.andrei.base.WebResult;
import com.telfa.andrei.enums.MemberStatusEnum;
import com.telfa.andrei.model.Resource;
import com.telfa.andrei.model.SysUser;
import com.telfa.andrei.model.UserRole;
import com.telfa.andrei.service.ResourceService;
import com.telfa.andrei.service.SysUserService;
import com.telfa.andrei.utils.DateUtil;
import com.telfa.andrei.utils.MD5Util;
import com.telfa.andrei.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台系统用户控制器
 * @since 1.8
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController {

    private final SysUserService sysUserService;

    private final ResourceService resourceService;

    @Autowired
    public SysUserController(SysUserService sysUserService, ResourceService resourceService) {
        this.sysUserService = sysUserService;
        this.resourceService = resourceService;
    }

    @GetMapping("/list")
    public String list() {
        return "admin/sysuser/sysUserList";
    }

    /**
     * 获取分页查询数据, ajax返回
     * @param page 要跳转到的页
     * @param pageRows 每页数据行数
     * @return 后台用户列表
     */
    @GetMapping("/page")
    @ResponseBody
    public WebResult listByPage(SysUser sysUser,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "limit", defaultValue = "20") int pageRows) {
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserService.listAllSysUsers(sysUser, page, pageRows));
        return new WebResult(HttpStatus.OK.value(), "", pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 停用或者启用后台用户操作
     * @param sysUserId 后台用户id
     * @return 操作结果
     */
    @PostMapping("/disable/{sysUserId}/{option}")
    @ResponseBody
    public WebResult disable(@PathVariable("sysUserId") Integer sysUserId,@PathVariable("option") String option) {
        try {
            sysUserService.disable(sysUserId, ContextHelper.currentSysUser(),option);
        } catch (Exception e) {
            e.printStackTrace();
            return new WebResult().error();
        }
        return new WebResult().success();
    }

    /**
     * 转到新增系统用户界面
     * @return 新增用户页面
     */
    @GetMapping("/add")
    public String add(Model model) {
        SysUser sysUser = new SysUser();
        sysUser.setStatus(MemberStatusEnum.NORMAL.getCode().byteValue());
        model.addAttribute("sysUser", sysUser);
        return "admin/sysuser/sysUserEdit";
    }

    /**
     * 新增编辑系统用户
     * @param sysUser 系统用户数据对象
     * @return WebResult 对象 ajax返回
     */
    @PostMapping("/save")
    @ResponseBody
    public WebResult save(SysUser sysUser,String roleIds){

        Integer sysId = -1;
        int result = 0;
        SysUser user = ContextHelper.currentSysUser();
        if(user != null){
           sysId = user.getSysUserId();
        }
        if(sysUser.getSysUserId() == null){
            //新增
            sysUser.setCreateTime(Integer.valueOf(String.valueOf(DateUtil.getDateline())));
            sysUser.setPassword(MD5Util.md5(sysUser.getUsername()+"123"));
            sysUser.setCreator(sysId);
            if(user != null){
                sysUser.setCreator(user.getSysUserId());
           }
            /*if(roleIds == null || "".equals(roleIds)){
                return new WebResult().error("请为用户分配角色");
            }*/
            result = sysUserService.insert(sysUser,roleIds);
        }else{

            //编辑
            List<UserRole> roleByUserId = sysUserService.getRoleByUserId(sysUser.getSysUserId());
            //根据sysuserId查询用户有没有相关角色
            if ((roleIds == null || "".equals(roleIds)) && roleByUserId != null){
                StringBuilder sb = new StringBuilder();
                for (int k = 0;k< roleByUserId.size();k++) {
                    if(k == roleByUserId.size()-1){
                        sb.append(roleByUserId.get(k).getRoleId());
                    }else{
                        sb.append(roleByUserId.get(k).getRoleId()+"-");
                    }
                }
                roleIds = sb.toString();
            }
            sysUser.setUpdateTime(Integer.valueOf(String.valueOf(DateUtil.getDateline())));
            sysUser.setUpdator(sysId);
            result = sysUserService.update(sysUser,roleIds);
        }
        if(result >0){
            return new WebResult(200,"操作成功");
        }
        return new WebResult(500,"操作失败");
    }

    /**
     * 转到编辑页面
     * @param sysUserId 系统用户id
     * @return 编辑用户页面
     */
    @GetMapping("/edit/{sysUserId}")
    public String edit(@PathVariable("sysUserId") Integer sysUserId, Model model) {
        SysUser sysUser = sysUserService.getSysUserById(sysUserId);
        model.addAttribute("sysUser", sysUser);
        return "admin/sysuser/sysUserEdit";
    }

    @PostMapping("/password/reset/{sysUserId}")
    @ResponseBody
    public WebResult resetPassword(@PathVariable("sysUserId") Integer sysUserId) {
        SysUser sysUser = sysUserService.getSysUserById(sysUserId);
        if(sysUser == null) {
            return new WebResult().error("用户不存在");
        }

        int result = sysUserService.resetPasswordBySysUserId(sysUser.getUsername(), sysUserId);
        return new WebResult().result(result > 0);
    }


    /**
     * 验证用户名是否重复
     * @param username
     * @return
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public WebResult checkUsername(String username){
        try {
            List<SysUser> users = sysUserService.getAllSysuser();
            for (SysUser u:users) {
                if(username.trim().equals(u.getUsername())){
                    return new WebResult().error();
                }
            }
            return new WebResult().success();
        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }

    @RequestMapping("/toeditPage")
    public String toeditPage(){
        return "admin/sysuser/changePwd";
    }

    @RequestMapping("/changePasswd")
    @ResponseBody
    public WebResult changePasswd(String newPwd){
        int result = 0;
        try{
            SysUser sysUser = ContextHelper.currentSysUser();
            if(sysUser == null){
                return new WebResult().error();
            }else{
                sysUser.setPassword(MD5Util.md5(newPwd));
                result = sysUserService.updatePwd(sysUser);
                if(result > 0){
                    return new WebResult().success();
                }else{
                    return new WebResult().error();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }


    @RequestMapping("/oldPwd")
    @ResponseBody
    public WebResult oldPwd(String oldPwd){
        try{
            SysUser sysUser = ContextHelper.currentSysUser();
            if(sysUser == null){
                return new WebResult().error();
            }else{
                Integer userId = sysUser.getSysUserId();
                SysUser user = sysUserService.getSysUserById(userId);
                String password = user.getPassword();
                String md5 = MD5Util.md5(oldPwd);
                if(password.equals(md5)){
                   return new WebResult().success();
                }else{
                    return new WebResult().error();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
            return new WebResult().error();
        }
    }



    @RequestMapping("/getResource")
    @ResponseBody
    public  String getResource(){
        List<ResourceVo> jsonList = new ArrayList<ResourceVo>();
        Map<String,Object> map = new HashMap<>();
       //List<Resource> resource = sysUserService.getResource(ContextHelper.currentSysUser().getSysUserId());
      List<Resource> resource = resourceService.listResourceBySysUserId(ContextHelper.currentSysUser().getSysUserId());
        if(resource != null && resource.size()>0){
            for(Resource r:resource) {
                ResourceVo parentResourceVo = new ResourceVo();
                if(r.getParentId() == 0){
                    parentResourceVo.setTitle(r.getResourceName());
                    parentResourceVo.setHref("");
                    parentResourceVo.setIcon(r.getIcon());
                    parentResourceVo.setSpread(false);
                    List<ResourceVo> childrenResource = new ArrayList<ResourceVo>();
                    for(Resource ch:resource){
                        if(r.getResourceId().equals(ch.getParentId())){
                            ResourceVo child = new ResourceVo(ch.getResourceName(),ch.getIcon(),ch.getUrl(),false);
                            childrenResource.add(child);
                        }
                    }
                    parentResourceVo.setChildren(childrenResource);
                }
                if(parentResourceVo.getTitle() != null){
                    jsonList.add(parentResourceVo);
                }
            }
            map.put("nav",jsonList);
            return JSONObject.toJSONString(map);
        }else{
            return null;
        }
    }
}
