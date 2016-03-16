/**
 * @project jchaos
 * @package
 * @file editor
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/1/21.
 **/
define(["text!/chaos/statics/module?module=test","vue","jquery","jquery.turn"], function(module,Vue,$, $turn){
    'use strict';
    var _linkView;
    return {
        init: function(el,data, methods, linkView){
            /* 渲染页面 */
            var menuView = new Vue({
                el: el,
                data: data,
                methods: methods
            });
            _linkView = linkView;
            return menuView;
        },
        turn: function(back, front){

        }
    }
});