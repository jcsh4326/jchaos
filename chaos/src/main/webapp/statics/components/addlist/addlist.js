/**
 * @project jchaos
 * @package
 * @file addlist
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/1.
 **/
define(["text!components/addlist/addlist.html","thirdpart/vue/vue","components/list/list"], function(AddList,Vue, List){
    'use strict';
    var chaosAddList = Vue.component('chaos-add-list',{
        template: AddList,
        //components: {chaosList:List},
        props:['entitys'],
        methods: {
            add: function(){
                /**
                 * 沿父链派发事件
                 */
                this.$dispatch('chaos-add-added');
            }
        }

    });
    return chaosAddList;
});