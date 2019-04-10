// 根路径
var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');

if(self !== top) {
    top.location.href = _ctx + 'login';
}

layui.use(['form','layer','jquery'],function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(login)",function(data){
        var loading = layer.load(3);
        $.ajax({
            url: _ctx + 'login',
            method: 'post',
            type: 'json',
            data: data.field,
            success: function(result) {
                result = JSON.parse(result);
                if(result.code === 200) {
                    layer.msg('登录成功...', { icon: 1 });
                    location.href = _ctx + 'index';
                } else {
                    layer.close(loading);
                    layer.msg(result.msg, { icon: 5, anim: 6 });
                }
            },
            error: function(result) {
                layer.close(loading);
                layer.msg('出现问题了, 一会儿再试');
            }
        });
        return false;
    });
});