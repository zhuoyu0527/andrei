var choosedguests = '';

;layui.use(['form', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    if($('input[name=username]').val() !=  ""){
        $('input[name=username]').attr("disabled","disabled");
        $('input[name=username]').removeAttr('lay-verify');
    }else{
       $('input[name=username]').after("<div class='layui-input-inline'><font style='color: red'>密码默认为用户名加123</font></div>");
    }
    // 根路径
    var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');

    // 常量
    var data_map = {
        'sysuser_save_url': _ctx + 'sysuser/save',
        'sysuser_checkUsername_url': _ctx + 'sysuser/checkUsername',
        'show_role_list_url':_ctx+'role/showRole/{sysUserId}'
    };

    var sysUserId = $('input[name=sysUserId]').val();

    //判断是否为编辑页面 且是否已经绑定了角色
    if(sysUserId) {
        $.get({
            url: _ctx+'role/listRoleBySysuserId',
            dataType: 'json',
            data: {sysUserId:$('input[name=sysUserId]').val()},
            success: function(result) {
                if(result.code == 200){
                    var json = JSON.parse(result.data);
                    var guestStr = '';
                    for(var i = 0; i < json.length;i++){
                        if(guestStr == ''){
                            guestStr = guestStr + json[i].roleName;
                        }else{
                            guestStr = guestStr +','+ json[i].roleName;
                        }
                    }
                    if(guestStr != ''){
                        $('#guestSpan').attr('placeholder',guestStr);
                        $('#guestSpan').attr("disabled","disabled");
                        $('#guestDiv').css('display','block');
                    }
                }
            },
            error: function(result) {

            }
        });
    }







    //自定义验证规则
    form.verify({
        usernameUnique: function(value) {
            var flag = 0;
            var reg = /^[0-9a-zA-Z_]{1,50}$/;
            if (!reg.test(value)) {
                return '用户名只能包含字母/数字/下划线, 最多50字';
            }
            $.post({
                async:false,
                url:data_map.sysuser_checkUsername_url,
                data:{username:value},
                dataType:'json',
                success:function(result){
                    if(result.code == 500){
                        flag = 1;
                    }
                }
            })

            if(flag == 1){
                return "该用户名已存在";
            }
        },
        realname: function(value) {
                if(value.length > 6) {
                    return '真实姓名最多6个字';
                }
            }
    });

    //监听指定开关
    form.on('switch(isFreeSwitch)', function(data){
        var priceObj = $('input[name=price]');
        priceObj.prop('disabled', data.elem.checked);
        if(this.checked) {
            priceObj.val('');
        }
    });

    $("#sysuserEdit_cancel").click(function(){
        var idx = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(idx);
    });


    function sleep(n)
    {
        var start=new Date().getTime();
        while(true) if(new Date().getTime()-start>n) break;
    }




    //监听分配角色按钮
    $('#chooseRole').bind('click',function () {
        var showUrl = data_map.show_role_list_url
        var sysUserId = $('input[name=sysUserId]').val();
        var frameIndex = layer.open({
            type: 2,
            skin: 'layui-layer-molv', //加上边框
            area: ['300px', '400px'],
            title:'添加角色',
            content: (sysUserId != null && sysUserId != '')?showUrl.replace('{sysUserId}',sysUserId):showUrl.replace('{sysUserId}',-1),
            btn: ['保存','取消'],
            yes: function (layero, index) {
                var newpsw = window[index.find('iframe')[0]['name']];
                var value=newpsw.getChoseId();
                if(value == null || value == ''){
                    $('#guestDiv').css('display','none');
                }else{
                    //组装成字符串
                    var guestIdArrayStr = value.join("%");
                    //数组
                    var valueArray = guestIdArrayStr.split("%");
                    //移除并拿出最后一个元素(角色ID)
                    var guestIdss = valueArray.pop();
                    //将角色名称拼接字符串
                    var showGuestNames = valueArray.join(', ');
                    //将子页面回传的值赋值给全局变量
                    choosedguests = showGuestNames;
                    $('#roleIds').val(guestIdss);
                    console.log('传给页面的角色ID:'+$('#roleIds').val());
                    if(choosedguests != ''){
                        $('#guestSpan').attr('placeholder',choosedguests);
                        $('#guestSpan').attr("disabled","disabled");
                        $('#guestDiv').css('display','block');
                    }
                }
                //关闭弹框
                layer.close(frameIndex);
            } ,
            btn2:function(){
                layer.close(frameIndex);
            },
            cancel: function(index, layero){
                layer.close(index);
                if(choosedguests != ''){
                    $('#guestSpan').attr('placeholder',choosedguests);
                    $('#guestSpan').attr("disabled","disabled");
                    $('#guestDiv').css('display','block');
                }
                return false;
            }
        });
    })

        //监听保存操作
        form.on('submit(sysuserEdit_save)',function (data) {
            $.ajax({
                url:data_map.sysuser_save_url,
                type:'post',
                dataType: 'json',
                data: data.field,
                success:function (result) {
                    if(result.code == 200){
                        layer.msg("操作成功",{time:4000,icon:5});
                        window.parent.reload();
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index);
                    }else if(result.code  == 500){
                        if(result.msg == '请为用户分配角色'){
                            layer.msg("操作失败,"+result.msg);
                        }else if(result.msg == '该用户还没有任何角色,请分配角色'){
                            layer.msg("操作失败,"+result.msg);
                        }else{
                            layer.msg("操作失败");
                        }
                    }
                },
                error:function(d){
                    layer.msg("添加失败");
                }
                    
            })
            return false;
        })
});