<#macro main>
	<@core.html>
		<@core.head style="!h-ui/css/H-ui.min.css,layout.css">
            <!-- vue.js 放在最前面加载，放在body末尾加载会因为vue.js阻塞，导致看见占位内容 -->
			<#--<@core.script name="http://cdn.jsdelivr.net/vue/1.0.13/vue.min.js"/>-->
			<!-- 不归require管理的js放前面 -->
			<@core.script src="${core.rootUrl}/statics/require.js" append="data-main=\"${core.rootUrl}/statics/main.debug.js\""/>
		</@core.head>
		<@core.body>
			<#nested />
			<@core.script name="utils/jsbetter.js"/>
		</@core.body>
	</@core.html>
</#macro>
<#-- 表单布局函数 -->
<#macro row v_append="" cls="">
    <div class="row cl<#if cls??&&cls!=""> ${cls?trim}</#if>" ${v_append}>
	    <#nested />
    </div>
</#macro>
<#macro col v_append="" num=12 cls="" inline=true>
    <div class="col-${num}<#if cls??&&cls!=""> ${cls?trim}</#if><#if inline> chaos-col</#if>" ${v_append}>
	    <#nested />
    </div>
</#macro>