/**
 * 一个单独使用的checkbox，
 * checkbox被勾选时，数组checkedItems中会增加当前checkbox的item的id，
 * 被取消勾选时，当前id会被从checkedItems中移除（如果有）
 * @project jchaos
 * @package
 * @file checkItem
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/3.
 **/
define(['text!components/item/checkItem.html', 'thirdpart/vue/vue'], function (View, Vue) {
    'use strict';
    Vue.component('chaos-ckb-item', {
        template: View,
        props: {
            id: {
                required: true
            },
            name: {
                required: true
            },
            checkedItems: {
                type: Array,
                default: function () {
                    return [];
                }
            }
        },
        computed: {
            checked: function(){
                for( var i=0; i<this.$data.checkedItems.length;i++){
                    if(this.$data.id===this.$data.checkedItems[i])
                        return true;
                }
                return false;
            }
        },
        methods: {
            edit: function(id, event){
                this.$dispatch('chaos-item-click', id, event);
            }
        }
    });
});
