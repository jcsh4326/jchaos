/**
 * @project jchaos
 * @package
 * @file list
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/1.
 **/
define(["text!components/list/list.html","thirdpart/vue/vue"], function (List,Vue) {
    'use strict';
    var chaosList = Vue.component('chaos-list',{
        template: List,
        props: ['items']
    });
    return chaosList;
});

