/**
 * @project jchaos
 * @package
 * @file text
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/4.
 **/
define(['text!components/form/text.html','components/item/textItem','js/utils/html/layout',
    'thirdpart/vue/vue'], function (View, TextItem, Layout, Vue) {
    'use strict';
    return Vue.component('chaos-text', {
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
            classObject: function(){
                return this.$data.col.class;
            },
            value: function(){
                return this.$data.col.value||'';
            },
            placeholder: function () {
                return this.$data.col.defaultValue||'';
            }
        },
        props: {
            col: {
                validator: function(value){
                    return value instanceof Layout.Col ? value.type === 'text' : false;
                }
            }
        }
    })
});