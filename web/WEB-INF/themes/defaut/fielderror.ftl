<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#-- Affiche l'attribut "title" (=> tooltip) avec toutes les erreurs du champ dans cet attribut title -->
<#-- Si l'attribut title est spécifié, rien n'est affiché -->
<#assign hasFieldErrors = (parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]?? && fieldErrors[parameters.name]?size > 0) /><#t>
<#if hasFieldErrors && !parameters.title??><#t/>
    title="<#t>
    <#list fieldErrors[parameters.name] as error><#t>
        ${error?html}<#t>
        <#if error_has_next>,</#if><#t>
    </#list><#t>
    "<#t>
</#if><#t/>