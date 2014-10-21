<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#if !stack.findValue("#inputtransferselect_js_included")??><#t/>
	<script type="text/javascript" src="<@s.url value="/struts/inputtransferselect.js" encode='false' includeParams='none'/>"></script>
	<#assign temporaryVariable = stack.setValue("#inputtransferselect_js_included", "true") /><#t/>
</#if><#t/>
<table border="0">
<tr>
<td>
<#if parameters.leftTitle??><#t/>
	<label for="leftTitle">${parameters.leftTitle}</label><br />
</#if><#t/>


<input type="text"<#rt/>
 name="${parameters.name?default("")?html}_input"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}_input"<#rt/>
</#if>
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/defaut/scripting-events.ftl" />
<#include "/${parameters.templateDir}/defaut/common-attributes.ftl" />
/>


</td>
<td valign="middle" align="center">
	<#assign addLabel=parameters.addLabel?default("->")?html /><#t/>
	<input type="button"
		<#if parameters.buttonCssClass??><#t/>
		 class="${parameters.buttonCssClass?html}"
		</#if><#t/>
		<#if parameters.buttonCssStyle??>
		 style="${parameters.buttonCssStyle?html}"
		</#if><#t/>
		 value="${addLabel}" onclick="addOption(document.getElementById('${parameters.id?html}_input'), document.getElementById('${parameters.id?html}'))" /><br /><br />
	<#t/>
	<#assign removeLabel=parameters.removeLabel?default("<-")?html /><#t/>
	<input type="button"
  		<#if parameters.buttonCssClass??><#t/>
		 class="${parameters.buttonCssClass?html}"
		</#if><#t/>
		<#if parameters.buttonCssStyle??>
		 style="${parameters.buttonCssStyle?html}"
		</#if><#t/>
		 value="${removeLabel}" onclick="removeOptions(document.getElementById('${parameters.id?html}'))" /><br /><br />
	<#t/>
	<#assign removeAllLabel=parameters.removeAllLabel?default("<<--")?html /><#t/>
	<input type="button"
	    		<#if parameters.buttonCssClass??><#t/>
		 class="${parameters.buttonCssClass?html}"
		</#if><#t/>
		<#if parameters.buttonCssStyle??>
		 style="${parameters.buttonCssStyle?html}"
		</#if><#t/>
		 value="${removeAllLabel}" onclick="removeAllOptions(document.getElementById('${parameters.id?html}'))" /><br /><br />
</td>
<td>
<#if parameters.rightTitle??><#t/>
	<label for="rightTitle">${parameters.rightTitle}</label><br />
</#if><#t/>
<#include "/${parameters.templateDir}/defaut/select.ftl" />
<#if parameters.allowUpDown?default(true)>
<input type="button" 
<#if parameters.headerKey??>
	onclick="moveOptionDown(document.getElementById('${parameters.id}'), 'key', '${parameters.headerKey}');"
<#else>
	onclick="moveOptionDown(document.getElementById('${parameters.id}'), 'key', '');"
</#if>
<#if parameters.downLabel??>
	value="${parameters.downLabel?html}"
</#if>
/>
<input type="button" 
<#if parameters.headerKey??>
	onclick="moveOptionUp(document.getElementById('${parameters.id}'), 'key', '${parameters.headerKey}');"
<#else>
	onclick="moveOptionUp(document.getElementById('${parameters.id}'), 'key', '');"
</#if>
<#if parameters.upLabel??>
	value="${parameters.upLabel?html}"
</#if>
/>
</#if>
</td>
</tr>
</table>
