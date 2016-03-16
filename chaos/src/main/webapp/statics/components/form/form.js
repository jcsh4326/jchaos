/**
 * @project jchaos
 * @package
 * @file form
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/4.
 **/
define(['text!components/form/form.html', 'components/form/checkbox', 'js/utils/html/layout',
    'thirdpart/vue/vue'], function(View, Checkbox, Layout, Vue){
    'use strict';
    Vue.config.debug = true;
    Vue.component('chaos-form',{
        template: View,
        data: function(){
            return {
                checkbox:'checkbox'
            }
        },
        computed:{
            form: function () {
                return this.$data.form;
            }
        },
        props:{
            form:{
                validator: function(value){
                    return value instanceof Layout.Rows;
                }
            }
        },
        components: {
            checkbox: Checkbox
        }
    });
});