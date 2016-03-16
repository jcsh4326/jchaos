package me.jcis.chaos.entity.groovy

import com.alibaba.fastjson.JSON

/**
* ${classAlias}
* @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
* @version v1.0  2015/12/25
*/
class ${className} implements Serializable {
	<#list fields as field>
	/**
	 * ${field.alias!}
	 */
    ${field.modifier!} ${field.type} ${field.name} <#if field.defaultValue??&&field.defaultValue?trim != "">= ${field.defaultValue!}</#if>

		<#if field.getter>
			<#if (field.type?lower_case) == "boolean">
	${field.type} is${field.name?cap_first}(){
        return this.${field.name}
    }
		    <#else>
    ${field.type} get${field.name?cap_first}(){
        return this.${field.name}
    }
			</#if>
		</#if>

		<#if field.setter>
		/**
		 *
		 **/
	void set${field.name?cap_first}(${field.type} value){
		this.${field.name} = value
    }
		</#if>
	</#list>

	String toString(){
		JSON.toJSONString(this)
	}
}