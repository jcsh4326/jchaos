<@com.main>
<@core.link name="entity/editor.css"/>
<@core.link name="utils/animate/chaos-animate.css"/>
<div class="row cl" id="app">
	<!-- prop在js中用驼峰式，在HTML中要改为 kebab-case -->
    <chaos-evt-btn :event-btn="addBtn"></chaos-evt-btn>
    <chaos-evt-btn :event-btn="delBtn"></chaos-evt-btn>
    <chaos-evt-btn :event-btn="editBtn"></chaos-evt-btn>
    <#--<div is="chaos-checkbox" :col="col"></div>-->
    <component :is="checkbox" :col="col"></component>
    <component :is="text" :col="col1"></component>
    <chaos-form :form="form"></chaos-form>
    <#--<chaos-checkbox :col="col"></chaos-checkbox>-->
    <span>Checked names: {{ checkedItems | json }}</span>
    <chaos-ckb-list :multiple="false" :checked-items.sync="checkedItems" :items="items">
        <#--<span>我不要看见反馈</span>-->
    </chaos-ckb-list>
</div>
<script>
    requirejs(['components/list/checkList', 'components/button/eventButton', 'components/form/checkbox',
            'components/form/text', 'components/form/form',
        'js/utils/html/layout', 'thirdpart/vue/vue'], function(List,EventButton,CheckBox,Text,
                                                               Form,Layout,Vue){

        var item = {
                    'name':'',
                    "classAlias": "",
                    "className": "",
                    "dataSource": "",
                    "fields": []
                }, field = {
                    "alias": "",
                    "defaultValue": "",
                    "modifier": "private",
                    "name": "",
                    "getter": true,
                    "setter": true,
                    "type": "String"
                }, layout = [[{
            "name": "alias",
            "type": "text",
            "title": "别名",
            "defaultValue": "",
            "value": ""
        }]];

	    new Vue({
		    el: '#app',
            data: function () {
                return {
                    items:['ad1','ad2'],
                    checkedItems:['1'],
	                buttons: function (type) {
                        var _evtBtn = new EventButton(type);
		                return _evtBtn;
                    },
                    checkbox: 'ckb',
                    text: 'text'
                }
            },
            computed:{
	            delBtn: function () {
		            var _delBtn = this.$data.buttons(EventButton.Del.getType());
		            return _delBtn;
                },
                addBtn: function () {
	                var _addBtn = this.$data.buttons(EventButton.Add.getType());
	                return _addBtn;
                },
                editBtn: function () {
	                var _editBtn = new EventButton(2, '编辑', '单击编辑', 'chaos-btn-edit');
	                return _editBtn;
                },
                items: function () {
                    var entitys = [];
                    for(var i=0;i<this.$data.items.length; i++){
                        entitys.push({name: this.$data.items[i],id: i+''});
                    }
                    return entitys;
                },
                col: function(){
                    var col = new Layout.Col({});
                    col.type = 'checkbox';
                    col.name = 'test';
                    col.title = '测试';
                    col.width = '30%';
                    return col;
                },
                col1: function(){
                    var col = new Layout.Col({});
                    col.name = 'test';
                    col.title = '测试';
                    col.width = '30%';
                    col.value = '我是测试值';
                    col.defaultValue = '输入值';
                    return col;
                },
                form: function(){
                    var col = new Layout.Col({});
                    col.type = 'checkbox';
                    col.name = 'test';
                    col.title = '测试';
                    col.width = '30%';
                    var row = new Layout.Row({});
                    row.cols = [col,new Layout.Col(col),new Layout.Col(col)];
                    row.colNum = row.cols.length;
                    var rows = new Layout.Rows({});
                    rows.rows = [row];
                    rows.rowNum = rows.rows.length;
                    return rows;
                }
            },
		    events: {
                'chaos-btn-add': function(event){
					this.$data.items.push('ad3');
			    },
			    'chaos-btn-del': function(event){
				    this.$data.items.pop();
			    },
                'chaos-btn-edit': function(event){
	                alert('编辑！');
                },
                'chaos-item-click': function(id, event){

                }
		    },
            components: {
                ckb: CheckBox,
                text: Text
            }
	    });
    });
</script>
</@com.main>