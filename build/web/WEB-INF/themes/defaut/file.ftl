<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<input type="file"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")??>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
 value=""<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.accept??>
 accept="${parameters.accept?html}"<#rt/>
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
/>