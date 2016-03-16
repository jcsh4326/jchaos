/**
 * @project jchaos
 * @package
 * @file selectItem
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/16.
 **/
define(['text!components/item/selectItem.html', 'thirdpart/vue/vue'], function (View, Vue) {
    'use strict';
    Vue.component('chaos-sel-item', {
        template: View,
        props: {
            id: {
                required: true
            },
            name: {
                required: true
            },
            opts: {
                type: Array,
                validator: function (value) {
                    return value instanceof Array ?
                        ( value.length === 0 ? false : value[0].val === undefined && value[0].name === undefined ) :
                        false;
                },
                default: function () {
                    return [{val:'',name:''}];
                }
            },
            selected: {
                validator: function (value) {
                    return value.val === undefined && value.name === undefined;
                }
            }
        }
    });
});