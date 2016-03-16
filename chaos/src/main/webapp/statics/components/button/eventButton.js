/**
 * button组件，向外派发事件
 * @project jchaos
 * @package
 * @file eventButton
 * Created by <a href="mailto:jincheng.jcs@gmail.com">瑾诚</a> on 2016/2/2.
 **/
define(['text!components/button/eventButton.html','thirdpart/vue/vue'], function (View, Vue) {
    'use strict';
    var EventButton = function(type, value, title, evt){
        this._type = type;
        this._value = value===undefined ? EventButton.getDefault(type).getValue() : value;
        this._title = title===undefined ?
            ( EventButton.getDefault(type).getType()=== -1 ? this._value : EventButton.getDefault(type).getTitle() ) : title;
        this._evt = evt===undefined ? EventButton.getDefault(type).getEvt() : evt;
    };
    EventButton.getDefault = function(type){
        if(typeof type === 'number'){
            if(type===0)
                return EventButton.Add;
            else if(type===1)
                return EventButton.Del;
        }
        return EventButton.Undfined;
    };
    EventButton.Undfined = new EventButton(-1, '未定义', '', 'chaos-btn-undfined');
    EventButton.Add = new EventButton(0, '新增', '单击新增', 'chaos-btn-add');
    EventButton.Del = new EventButton(1, '删除', '单击删除', 'chaos-btn-del');
    EventButton.Edit = new EventButton(2, '编辑', '单击编辑', 'chaos-btn-edit');
    EventButton.prototype.getType = function () {
        return this._type;
    };
    EventButton.prototype.getValue = function () {
        return this._value;
    };
    EventButton.prototype.getTitle = function () {
        return this._title;
    };
    EventButton.prototype.getEvt = function() {
        return this._evt;
    };
    var chaosEventButton = Vue.component('chaos-evt-btn',{
        template: View,
        methods: {
            dispatch: function(event){
                this.$dispatch(this.eventBtn.getEvt(), event);
            }
        },
        props: {
            eventBtn:{
                validator: function (value) {
                    return value instanceof EventButton;
                }
            }
        }
    });

    return EventButton;
});