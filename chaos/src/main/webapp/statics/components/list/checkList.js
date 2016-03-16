/**
 * 可选列表
 * 父组件如果需要当前选中的值，需要在父组件中显式使用checked-items.sync
 * 该组件默认允许多选，如果需要单选，需要在父组件中显示声明 :multiple="false"
 * @project jchaos
 * @package
 * @file checkList
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/2.
 **/
define(['text!components/list/checkList.html','components/item/checkItem', 'thirdpart/vue/vue'],
    function(View, CheckItem ,Vue){
    'use strict';
    Vue.config.debug = true;
    Vue.component('chaos-ckb-list',{
        template: View,
        computed: {
            /** items 是由父组件传入的值[props]，同时也是需要传入子组件的值[data]，所以需要有一个getter方法 **/
            items: function(){
                return this.$data.items;
            },
            checkedItems: function(){
                return this.$data.checkedItems;
            }
        },
        /* 期望从父组件传入的数据 */
        /* prop 默认是单向绑定：当父组件的属性变化时，将传导给子组件，但是反过来不会 */
        /* Object和Array不在此约束内，这两种数据类型是引用传递，在子组件中的修改会自然影响父组件 */
        props: {
            items: {
                validator: function(value){
                    // list 应该是Array
                    // 当list为[]时，不对list做判断
                    // 当list确实有值时，应确保list中的值有id，以供checkbox使用
                    // 默认传入的数据是正确的
                    return value instanceof Array ?
                        ( value.length > 0 ? typeof value[0].id !== 'undefined' : true) :
                        false;
                }
            },
            // checkedItems 保存着items的id
            checkedItems: {
                type: Array
            },
            multiple: {
                type: Boolean,
                default: true
            },
            // 是否允许单击item时触发单击事件
            immed: {
                type: Boolean,
                default: function(){
                    // 当允许单选时默认触发，不允许单选时默认不触发
                    return !this.$data.multiple;
                }
            }
        },
        methods: {
            getProp: function(key) {
                return this._props[key];
            },
            change: function(event){

            },
            checked: function(id){
                for( var i=0; i<this.$data.checkedItems.length;i++){
                    if(id===this.$data.checkedItems[i])
                        return true;
                }
                return false;
            },
            addItem: function(item) {
                var items = [].concat(this.$data.checkedItems);
                if(items.indexOf(item)<0)
                    items.push(item);
                this.$data.checkedItems = items;
            },
            removeItem: function(item){
                var items = [].concat(this.$data.checkedItems);
                var i=items.indexOf(item);
                if(i>0)
                    items.splice(i,1);
                this.$data.checkedItems = items;
            },
            removeOthers: function(item){
                // 重新赋值会导致与父组件不是同一个引用传值
                // 在父组件中需要显式地声明:checkedItems.sync，否则子组件的值影响父组件
                 this.$data.checkedItems=[];
                this.addItem(item);
            }
        },
        events: {
            'chaos-item-click': function(id, event){
                if(!this.$data.multiple){
                    // 单选
                    this.removeOthers(id);
                }
                // Vue 事件在冒泡过程中第一次触发回调之后自动停止冒泡，除非回调明确返回 true。
                return this.$data.immed;
            }
        }
    });
});