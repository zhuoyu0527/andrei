var $, tab, dataStr, layer;
var _ctx = document.querySelector('meta[name=ctx]').getAttribute('content');
layui.config({
    base: _ctx + "js/"
}).extend({
    "bodyTab": "bodyTab"
});
layui.use(['bodyTab', 'form', 'element', 'layer', 'jquery'], function () {
    var form = layui.form,
        element = layui.element;
    $ = layui.$;
    layer = parent.layer === undefined ? layui.layer : top.layer;
    tab = layui.bodyTab({
        openTabNum: "10",
        url: _ctx + "sysuser/getResource"
    });

    function getData(json) {
        $.getJSON(tab.tabConfig.url, function (data) {
            dataStr = data.nav;
            //重新渲染左侧菜单
            tab.render();
        });
    }

    getData("nav");

    $(".hide-menu").click(function () {
        $(".layui-layout-admin").toggleClass("showMenu");
    });

    // 添加新窗口
    $("body").on("click", ".layui-nav .layui-nav-item a:not('.mobileTopLevelMenus .layui-nav-item a')", function () {
        //如果不存在子级
        if ($(this).siblings().length == 0) {
            addTab($(this));
            $('body').removeClass('site-mobile');
        }
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    });
});

//打开新窗口
function addTab(_this) {
    tab.tabAdd(_this);
}