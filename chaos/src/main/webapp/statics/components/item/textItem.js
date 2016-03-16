/**
 * @project jchaos
 * @package
 * @file textItem
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/4.
 **/
define(['text!components/item/textItem.html', 'thirdpart/vue/vue'], function(View, Vue){
    'use strict';
    Vue.component('chaos-text-item',{
        template: View,
        props: {
            id: {
                required: true
            },
            name: {
                required: true
            },
            value: {
                defalut: function(){
                    return '';
                }
            },
            placeholder: {
                defalut: function(){
                    return '';
                }
            },
            classObject: {
                defalut: function(){
                    return '';
                }
            },
            styleObject: {
                defalut: function(){
                    return '';
                }
            }
        },
        methods: {
            verify: function(id, event){
                // do verity
            }
        }
    });
});