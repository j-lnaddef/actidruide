<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->


<#if parameters.label??>
<label>${parameters.label?html}</label>
</#if>
<input<#rt/>
 type="${parameters.type?default("text")?html}"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")??>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.maxlength??>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue??>
    value="${parameters.nameValue?html}"<#rt/>
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
<#include "/${parameters.templateDir}/defaut/fielderror.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/defaut/scripting-events.ftl" />
<#include "/${parameters.templateDir}/defaut/common-attributes.ftl" />
<#-- Enlever les dynamic attributes car l'attribut custom 'date' s'affiche également, ce qui produit un code XHTML non valide -->
<#-- include "/${parameters.templateDir}/defaut/dynamic-attributes.ftl" -->
/>
<#if parameters.date?? && parameters.id??>
    <script type="text/javascript"><#rt/>
        $("#${parameters.id}").datepicker({dateFormat: "dd/mm/yy"});<#rt/>
    </script> <#rt/>
</#if>