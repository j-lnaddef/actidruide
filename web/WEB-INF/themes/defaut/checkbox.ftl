<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<input type="checkbox" value="${parameters.fieldValue?html}"<#rt/>
<#if parameters.name??>
 name="${parameters.name?html}"
</#if>
<#if parameters.nameValue?? && parameters.nameValue>
 checked="checked"<#rt/>
</#if>
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
 id="${parameters.id?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/defaut/css.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/defaut/scripting-events.ftl" />
<#include "/${parameters.templateDir}/defaut/common-attributes.ftl" />
<#include "/${parameters.templateDir}/defaut/dynamic-attributes.ftl" />
/><input type="hidden" id="__checkbox_${parameters.id?html}" value="${parameters.fieldValue?html}"<#rt/>
<#if parameters.name??>
 name="__checkbox_${parameters.name?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
 />