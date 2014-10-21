<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#if (parameters.dynamicAttributes?? && parameters.dynamicAttributes?size > 0)><#rt/>
<#assign aKeys = parameters.dynamicAttributes.keySet()><#rt/>
<#list aKeys as aKey><#rt/>
  <#assign keyValue = parameters.dynamicAttributes[aKey]/>
  <#if keyValue?is_string>
      <#assign value = struts.translateVariables(keyValue)!keyValue/>
  <#else>
      <#assign value = keyValue?string/>
  </#if>
 ${aKey}="${value?html}"<#rt/>
</#list><#rt/>
</#if><#rt/>