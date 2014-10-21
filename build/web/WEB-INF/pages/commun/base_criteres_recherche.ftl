<#macro affiche nomCriteres="model.criteres">

    <#-- Champs présents dans tous les formulaires de recherche -->
    <@s.hidden name="${nomCriteres}.page" />
    <@s.hidden name="${nomCriteres}.colonneTri" />
    <@s.hidden name="${nomCriteres}.ascendant" />

</#macro>