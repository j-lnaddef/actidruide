<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#if parameters.cssErrorClass??>
    <#assign cssErrorClass=parameters.cssErrorClass />
<#else>
    <#-- Valeur par défaut de la classe CSS d'erreur -->
    <#assign cssErrorClass="champ_erreur" />
</#if>

<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if parameters.cssClass?? && !hasFieldErrors>
 class="${parameters.cssClass?html}"<#rt/>
<#elseif parameters.cssClass?? && hasFieldErrors>
 class="${parameters.cssClass?html} ${cssErrorClass?html}"<#rt/>
<#elseif !(parameters.cssClass??) && hasFieldErrors>
 class="${cssErrorClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?? && !(hasFieldErrors && parameters.cssErrorStyle??)>
 style="${parameters.cssStyle?html}"<#rt/>
<#elseif hasFieldErrors && parameters.cssErrorStyle??>
 style="${parameters.cssErrorStyle?html}"<#rt/>
</#if>