/**
 * @project jchaos
 * @package
 * @file checkbox
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/4.
 **/
define(['text!components/form/checkbox.html','components/item/checkItem', 'js/utils/html/layout',
    'thirdpart/vue/vue'], function(View, CheckItem, Layout, Vue){
    'use strict';
    var Checkbox = Vue.component('chaos-checkbox', {
        template: View,
        computed: {
            id: function(){
                return this.$data.col.name;
            },
            name: function(){
                return this.$data.col.title;
            },
            styleObject: function () {
                var obj = {};
                if(this.$data.col.width!==''){
                    obj.width = this.$data.col.width;
                }
                return obj;
            },
            checkedItems: function(){
                return [];
            }
        },
        props: {
            col: {
                validator: function(value){
                    return value instanceof Layout.Col ? value.type === 'checkbox' : false;
                }
            }
        }
    });
    return Checkbox;
});
