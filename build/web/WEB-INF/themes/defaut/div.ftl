<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<div<#rt/>
<#if parameters.id??> id="${parameters.id?html}"<#rt/></#if>
<#if parameters.name??> name="${parameters.name?html}"<#rt/></#if>
<#if parameters.cssClass??> class="${parameters.cssClass?html}"<#rt/></#if>
<#if parameters.cssStyle??> style="${parameters.cssStyle?html}"<#rt/></#if>
<#if parameters.title??> title="${parameters.title?html}"<#rt/></#if>
<#include "/${parameters.templateDir}/defaut/scripting-events.ftl" />
<#include "/${parameters.templateDir}/defaut/common-attributes.ftl" />
<#include "/${parameters.templateDir}/defaut/dynamic-attributes.ftl" />
>