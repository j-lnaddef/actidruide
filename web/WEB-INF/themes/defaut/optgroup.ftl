<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#if parameters.optGroupInternalListUiBeanList??>
<#assign optGroupInternalListUiBeans=parameters.optGroupInternalListUiBeanList />
<#list optGroupInternalListUiBeans as optGroupInternalListUiBean>
<optgroup 
	<#if optGroupInternalListUiBean.parameters.label??>
	label="${optGroupInternalListUiBean.parameters.label}"
	</#if>
	<#if optGroupInternalListUiBean.parameters.disabled?default(false)>
	disabled="disabled"
	</#if>
<#include "/${parameters.templateDir}/defaut/dynamic-attributes.ftl" />
>

<#list optGroupInternalListUiBean.parameters.list as optGroupBean>
<#assign trash=stack.push(optGroupBean) />
	<#assign tmpKey=stack.findValue(optGroupInternalListUiBean.parameters.listKey) />
	<#assign tmpValue=stack.findValue(optGroupInternalListUiBean.parameters.listValue) />
	<#assign tmpKeyStr = tmpKey.toString() />
	<option value="${tmpKeyStr?html}"
	<#if tag.contains(parameters.nameValue, tmpKeyStr) == true>
	selected="selected"
	</#if>
	>${tmpValue?html}
	</option>
<#assign trash=stack.pop() />
</#list>
</optgroup>
</#list>
</#if>