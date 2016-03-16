<#assign js="statics/js">
<#assign css="statics/css">
<#assign third="statics/thirdpart">
<#assign rootUrl=springMacroRequestContext.getContextPath()>
<#macro rootPath>${springMacroRequestContext.getContextPath()}</#macro>

<#-- <base> 标签为页面上的所有链接规定默认地址或默认目标，使用需谨慎 -->
<#macro headBase href="" target="">
        <base<#if href??> href="${href}"</#if><#if target??>target="${target}"/></#if>
</#macro>

<#-- meta本身不闭合，为了配合一些xml读取器，人为闭合 -->
<#macro _meta meta><meta ${meta}/></#macro>

<#-- 外部链接要配合http:// 或 https:// 使用 -->
<#-- 如果有指定src，优先使用src -->
<#macro script name="" src="" append="">
	<#if src??&&src!="">
    <script src="${src}" type="text/javascript" ${append!}></script>
	<#else>
		<#if name??>
                <script src=
			<#if name?starts_with("http://")||name?starts_with("https://")>
                    "${name}"
			<#elseif name?starts_with("!")>
            "<@rootPath/>/${third}/${name?substring(1)}"
			<#else>
            "<@rootPath/>/${js}/${name}"
			</#if>
        type="text/javascript"></script>
		</#if>
	</#if>
</#macro>

<#-- link 本身在html中不闭合，配合xhtml人为闭合 -->
<#-- 外部链接要配合http:// 或 https:// 使用 -->
<#macro link name="">
	<#if name??>
    <link href=
		<#if name?starts_with("http://")||name?starts_with("https://")>
                "${name}"
		<#elseif name?starts_with("!")>
        "<@rootPath/>/${third}/${name?substring(1)}"
		<#else>
        "<@rootPath/>/${css}/${name}"
		</#if>
    type="text/css" rel="stylesheet"/>
	</#if>
</#macro>
<#-- script 不在head里加载，防止阻塞 -->
<#macro head meta=[] base={} title="" style="">
<title>${title!}</title>

	<#if base??>
		<@headBase base["href"] base["target"]></@headBase>
	</#if>

	<@_meta meta='http-equiv="Content-Type" content="text/html;charset=UTF-8"'></@_meta>
	<@_meta meta='http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"'></@_meta>
	<@_meta meta='name="viewport" content="width=device-width"'></@_meta>

	<#if meta??>
		<#list meta as m>
			<@_meta meta=m></@_meta>
		</#list>
	</#if>

	<#if style??&&style!="">
		<#list style?split(",") as s>
			<@link name="${s}"></@link>
		</#list>
	</#if>
	<#nested />
</#macro>

<#macro body>
<body>
<#-- 在这里放body的内容 -->
	<#nested />
<#-- 在这里放script -->
<#--<#nested >-->
</body>
</#macro>

<#macro html>
<html>
<#-- 这里放head and body -->
	<#nested />
</html>
</#macro>