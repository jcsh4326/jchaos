<@com.main>
<@core.link name="entity/editor.css"/>
<@core.link name="utils/animate/chaos-animate.css"/>
<div class="row cl">
	<div id="entity_list" class="col-3 chaos-aside chaos-col">
		<!-- 如果希望使用v-if指令进行权限控制，控制得并不完全 -->
		<!-- 以addable为false为例，此时当entity_list view create时，<button />不会被写入dom树中
		<!-- 但是如果在js中更改了addable的值为true，<button />就出现了 -->
		<!-- 或者可以使用单次插值， * addable , 后续addable的数据变化就不会再引起插值更新了 -->
        <button class="btn btn-info" v-if="* addable" v-on:click="addEntity">新增</button>
        <ul>
            <li v-for="entity in entities">
                <button class="editBtn" v-on:click="editEntity($index)">{{ entity.className }}</button>
            </li>
        </ul>
	</div>
	<div id="entity" class="col-9 chaos-section chaos-col">
		<div id="flipper" class="flipper">
            <div class="desc">
                请选择要编辑的实体
            </div>
            <div class="editor">
				<@com.row>
					<@com.col num=15 cls="text-r">类名</@com.col>
					<@com.col num=15><input v-model="entity.className"></@com.col>
					<@com.col num=15 cls="text-r">别名</@com.col>
					<@com.col num=15><input v-model="entity.classAlias"></@com.col>
					<@com.col num=15 cls="text-r">类别</@com.col>
					<@com.col num=15><input v-model="entity.dataSource"></@com.col>
					<@com.col num=3 cls="text-r"><button v-on:click="addField">增加字段</button></@com.col>
				</@com.row>
			<#-- 利用vue循环获得字段内容 -->
				<@com.row v_append="v-for=\"field in entity.fields\"">
					<@com.col num=15 cls="text-r">字段名</@com.col>
					<@com.col num=15><input v-model="field.name"></@com.col>
					<@com.col num=15 cls="text-r">字段别名</@com.col>
					<@com.col num=15><input v-model="field.alias"></@com.col>
					<@com.col num=15 cls="text-r">字段类型</@com.col>
					<@com.col num=15><input v-model="field.type"></@com.col>
					<@com.col num=15 cls="text-r">默认值</@com.col>
					<@com.col num=15><input v-model="field.defaultValue"></@com.col>
				</@com.row>
            </div>
		</div>
	</div>
</div>
<script>
	// TODO：通过props在两个组件间通信
	// demo: http://codepen.io/ygjack/pen/NGozOa
	var el_entity_list = "#entity_list", data_entity_list = {
				addable: false,
				entities:${entities}
			},
			methods_entity_list = {
                addEntity: function() {
                    this.entities.push({className: 'new'});
                },
                editEntity: function(index) {
                    var select = function (index) {
                        _linkView.entity = this.entities[index];
                    };
                    $('.desc').css('display')==='none' ?
                        // 当前页面是编辑页面
                            $('.editor').turn('.editor',{
                                outCallback: function(){
                                    select(index);
                                }
                            }) :
                            $('.desc').turn('.editor',{
                                outCallback: function () {
                                    select(index);
                                }
                            });
                }
			};
    var el_entity = "#entity",
            entity = {
                "classAlias": "",
                "className": "",
                "dataSource": "",
                "fields": []
            }, data_entity = {entity: entity},
            methods_entity = {
                addField: function(){
                    var field = {
                        "alias": "空",
                        "defaultValue": "",
                        "modifier": "private",
                        "name": "newField",
                        "getter": true,
                        "setter": true,
                        "type": "String"
                    };
                    !this.entity.fields?
                            this.entity.fields=[field]:
                            this.entity.fields.push(field);
                }
            }
    requirejs(["js/entity/editor"],function(editor){
//        debugger;
	    var entityView = editor.init(el_entity, data_entity, methods_entity);
        editor.init(el_entity_list, data_entity_list, methods_entity_list, entityView);
    });


</script>

</@com.main>