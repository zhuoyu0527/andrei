var curr;
;layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.jquery;
    var layerIndex = 0;


    // 根路径
    var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');

    // 常量
    var data_map = {
        'sysuser_list_url': _ctx + 'sysuser/page',
        'sysuser_list_table_id': '#sysuser-list-table',
        'sysuser_disable_url': _ctx + 'sysuser/disable/{sysUserId}/{option}',
        'reset_password_url': _ctx + 'sysuser/password/reset/{sysUserId}',
        'sysuser_add_url': _ctx + 'sysuser/add',
        'sysuser_edit_url': _ctx + 'sysuser/edit',
        'sysuser_save_url': _ctx + 'sysuser/save'
    };


    // 渲染列表
    var sysUserTable = table.render({
        elem: data_map.sysuser_list_table_id,
        page: {
            limit: 10,
            layout: ['first', 'prev', 'page', 'next', 'last', 'count'],
            prev: '上一页',
            next: '下一页',
            first: '首页',
            last: '尾页'
        },
        url: data_map.sysuser_list_url,
        cols: [[
            { type: 'numbers', title: '#', width: 60,align: 'center' },
            { field: 'username', title: '用户名', width: 100 ,align: 'center'},
            { field: 'realname', title: '姓名', width: 100 ,align: 'center'},
            { field: 'department', title: '部门', width: 120 ,align: 'center'},
            { field: 'position', title: '职位', width: 120 ,align: 'center'},
            { field: 'createTime',align: 'center', title: '创建时间', width: 250, templet: '#sysuser-list-createtime-tpl' },
            /*{ field: 'remark',title: '备注' },*/
            { field: 'status', title: '状态', width: 80, align: 'center', templet: '#sysuser-list-status-tpl' },
            { fixed: 'right',title: '操作', align: 'center', toolbar: '#sysuser-list-toolbar' }
        ]],
        response: {
            statusCode: 200
        }
    });

    // 处理表格行内事件
    table.on('tool(sysuser-list-table-filter)', function(obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if(layEvent === 'sysUserDisable') {
            layer.confirm('确认停用此用户吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var sysUserId = data.sysUserId;
                // 停用用户
                $.post(data_map.sysuser_disable_url.replace("{sysUserId}/{option}", sysUserId+'/disable'), '', function(result) {
                    if(result.code === 200) {
                        curr();
                    } else {
                        layer.msg(result.msg, { icon: 2 });
                    }
                });
            });
        } else if(layEvent === 'sysUserResetPassword') {
            layer.confirm('确认重置此用户密码吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var sysUserId = data.sysUserId;
                // 重置密码
                $.post(data_map.reset_password_url.replace("{sysUserId}", sysUserId), '', function(result) {
                    if(result.code === 200) {
                        layer.msg(result.msg, { icon: 2 });
                    }
                });
            });
        }else if(layEvent === 'sysUserEdit'){
            layerIndex = layer.open({
                type: 2,
                scrollbar: false,
                area: ['670px', '495px'],
                content: data_map.sysuser_edit_url+'/'+data.sysUserId
            });
        }else if(layEvent == 'sysUserenable'){
            layer.confirm('确认启用此用户吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var sysUserId = data.sysUserId;
                // 启用用户
                $.post(data_map.sysuser_disable_url.replace("{sysUserId}/{option}", sysUserId+'/enable'), '', function(result) {
                    if(result.code === 200) {
                       curr();
                    }else{
                        layer.msg(result.msg, { icon: 2 });
                    }
                });
            });
        }
    });

    // 重新加载列表
    function reloadTable() {

        sysUserTable.reload({
            where: {
                username: $.trim($('input[name=username]').val()),
                realname: $.trim($('input[name=realname]').val()),

            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }

    $('#btnSearch').on('click', function() {
        reloadTable();
    });

    $('#createNewSysUser').on('click', function() {
        layerIndex = layer.open({
            type: 2,
            scrollbar: false,
            area: ['670px', '495px'],
            content: data_map.sysuser_add_url

        });
    });

    curr = function() {
        sysUserTable.reload({
            where: {
                username: $.trim($('input[name=username]').val()),
                realname: $.trim($('input[name=realname]').val()),
            }
        });
    }

});

function reload() {
    curr();
}
