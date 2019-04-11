var choosedguests;
layui.use(['form', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    // 根路径
    var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');

    var roleId = $('input[name=roleId]').val();
    if(roleId) {
        $.get({
            url: _ctx+'role/listSysuserName',
            dataType: 'json',
            data: {roleId:$('input[name=roleId]').val()},
            success: function(result) {
                if(result.code == 200){
                    var json = JSON.parse(result.data);
                    var guestStr = '';
                    for(var i = 0; i < json.length;i++){
                        if(guestStr == ''){
                            guestStr = guestStr + json[i].username;
                        }else{
                            guestStr = guestStr +','+ json[i].username;
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

    form.verify({
        roleName:function(value){
            var reg=/[%_]/gi;
            if(value.length > 50){
                return "角色名称不能超过50个字";
            }
            if(reg.test(value)){
                return "角色名称不能含有特殊字符";
            }
        },
        roleDesc:function(value){
            if(value.length > 50){
                return "角色描述不能超过50个字";
            }
        }
    })



    //监听分配用户按钮
    $('#chooseSysUser').bind('click',function () {
        var showUrl = _ctx+'role/showAllUser/{roleId}';
        var roleId = $('input[name=roleId]').val();
        var frameIndex = layer.open({
            type: 2,
            skin: 'layui-layer-molv', //加上边框
            area: ['60%', '90%'],
            title:'添加用户',
            content: (roleId != null && roleId != '')?showUrl.replace('{roleId}',roleId):showUrl.replace('{roleId}',-1),
            btn: ['保存','取消'],
            yes: function (layero, index) {
                var newpsw = window[index.find('iframe')[0]['name']];
                var value=newpsw.getChoseId();
                if(value == null || value == ''){
                    $('#guestDiv').css('display','none');
                }else{
                    //组装成字符串
                    var guestIdArrayStr = value.join("%");
                    //console.log(guestIdArrayStr);
                    //数组
                    var valueArray = guestIdArrayStr.split("%");
                    //移除并拿出最后一个元素(角色ID)
                    var guestIdss = valueArray.pop();
                    //将角色名称拼接字符串
                    var showGuestNames = valueArray.join(', ');
                    //将子页面回传的值赋值给全局变量
                    choosedguests = showGuestNames;
                    $('#sysUserIds').val(guestIdss);
                    console.log('传给页面的角色ID:'+$('#sysUserIds').val());
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

    form.on('submit(addRole)', function(d) {
        $.ajax({
            url: _ctx+'role/addNewRole',
            dataType: 'json',
            data: {
                roleName:$('input[name=uestName]').val(),
                roleCode:$('input[name=roleCode]').val(),
                roleDesc:$('#roleDesc').val(),
                roleId:$('input[name=roleId]').val(),
                sysUserIds:$('#sysUserIds').val()
            },
            type: 'post',
            success: function(result) {
                if(result.code == 200){
                    layer.msg("添加成功");
                    window.parent.reload();
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index);
                }else{
                    layer.msg("添加失败");
                }
            },
            error:function(d){
                layer.msg("添加失败");
            }
        })

        return false;
    });
});


