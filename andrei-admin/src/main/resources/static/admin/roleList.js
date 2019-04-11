var $;
var curr;
;layui.use(['form', 'laydate', 'table', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        table = layui.table;
    $ = layui.jquery;

    // 根路径
    var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');

    // 常量
    var data_map = {
        'role_list_url': _ctx + 'role/page',
        'role_list_table_id': '#role-list-table',
        'role_add_url': _ctx + 'role/toAdd',
        'role_list_market_url':_ctx+'role/market/{roleId}/{disable}',
        'role_list_disabled_url':_ctx+'role/disable/{roleId}',
        'role_edit_url':_ctx+'role/edit',
        'role_resource_edit_url':_ctx+'role/allocateResource'
    };

    // 日期
    laydate.render({
        elem: '#createTimeBegin'
    });
    laydate.render({
        elem: '#createTimeEnd'
    });

    // 渲染列表
    var roleTable = table.render({
        elem: data_map.role_list_table_id,
        page: {
            limit: 10,
            layout: ['first', 'prev', 'page', 'next', 'last', 'count'],
            prev: '上一页',
            next: '下一页',
            first: '首页',
            last: '尾页'
        },
        url: data_map.role_list_url,
        cols: [[
            { field: 'roleName', title: '角色名称', width: 200,align: 'center',},
            { field: 'roleDesc', title: '角色描述', align: 'center',width: 200},
            { field: 'status', title: '状态', width: 100, align: 'center',templet: '#role-list-status-tpl' },
            { field: 'createTime', title: '添加时间', width: 250 , align: 'center',templet: '#role-list-createTime-tpl'},
            { field: 'guestId', title: '操作',align: 'center',toolbar: '#role-list-option-tpl3'},
        ]],
        response: {
            statusCode: 200
        }
    });

    // 处理表格行内事件
    table.on('tool(role-list-table-filter)', function(obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if(layEvent === 'upmarket') {
            layer.confirm('确认启用吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var roleId = data.roleId;
                // 冻结用户
                $.ajax({
                    url: data_map.role_list_market_url.replace("{roleId}", roleId ).replace("{disable}", 0),
                    dataType: 'json',
                    type: 'post',
                    success: function(result) {
                        if(result.code === 200) {
                            reloadTable();
                        } else {
                            layer.msg(result.msg, { icon: 2 });
                        }
                    }
                });
            });
        } else if(layEvent === 'downmarket') {
            layer.confirm('确认停用吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var roleId = data.roleId;
                // 冻结用户
                $.ajax({
                    url: data_map.role_list_market_url.replace("{roleId}", roleId).replace("{disable}", 1),
                    dataType: 'json',
                    type: 'post',
                    success: function(result) {
                        if(result.code === 200) {
                            reloadTable();
                        } else {
                            layer.msg(result.msg, { icon: 2 });
                        }
                    }
                });
            });
        }else if(layEvent === 'delete') {
            layer.confirm('确认删除此角色吗?', { icon: 2 }, function(index) {
                layer.close(index);
                var roleId = data.roleId;
                // 冻结用户
                $.ajax({
                    url: data_map.role_list_disabled_url.replace("{roleId}", roleId),
                    dataType: 'json',
                    type: 'post',
                    success: function(result) {
                        if(result.code === 200) {
                            reloadTable();
                        } else {
                            layer.msg(result.msg, { icon: 2 });
                        }
                    }
                });
            });
        } else if(layEvent === 'roleEdit'){
            layer.open({
                type: 2,
                title:'编辑角色',
                scrollbar: false,
                area: ['670px', '450px'],
                content: data_map.role_edit_url+'/'+data.roleId
            });
        }else if(layEvent === 'allocateResource'){
            layer.open({
                type: 2,
                title:'分配资源',
                scrollbar: false,
                area: ['700px', '450px'],
                content: data_map.role_resource_edit_url+'/'+data.roleId
            });
        }

    });

    // 重新加载列表
    function reloadTable() {
        console.log("重新加载表格");
        roleTable.reload({
            where: {
                content: $.trim($('input[name=roleName]').val()),
                createTimeBegin: $('#createTimeBegin').val(),
                createTimeEnd: $('#createTimeEnd').val()
            }
        });
    }

    curr = function () {
        roleTable.reload({
            where: {
                content: $.trim($('input[name=roleName]').val()),
                createTimeBegin: $('#createTimeBegin').val(),
                createTimeEnd: $('#createTimeEnd').val()
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }
    $('#btnSearch').on('click', function() {
        reloadTable();
    });

    $('#addnewRole').on('click', function() {
        var editUrl = data_map.role_add_url
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            area: ['670px', '450px'], //宽高
            content:editUrl
        });
    });

});

function reload() {
    curr();
}