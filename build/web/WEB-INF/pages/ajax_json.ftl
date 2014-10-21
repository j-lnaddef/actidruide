<#-- Affichage en JSON d'un résultat Ajax. -->
<#if resultatAjax??>
{
    <#-- Affichage des champs simples de l'objet -->
    "enErreur"         : ${resultatAjax.enErreur?string},
    "champsEnErreur"   : ${resultatAjax.champsEnErreurJson},
    "erreurs"          : ${resultatAjax.erreursJson},
    "message"          : "<#if resultatAjax.message??>${resultatAjax.message?json_string}</#if>",
    "resultat"         : ${resultatAjax.resultatEnJson},
    "htmls"            : [
        <#-- Affichage du HTML de chaque template, sous la forme d'un tableau de String -->
        <#if resultatAjax.freemarkerTemplates??>
            <#list resultatAjax.freemarkerTemplates as template>
                <#-- Mettre le rendu de chaque template dans une variable 'html' -->
                <#assign html>
                    <#include template>
                </#assign>
                <#-- Afficher la variable 'html', protégé au format JSON, suivi d'une virgule (pour le tableau) -->
                "${html?json_string}"<#if template_has_next>,</#if>
            </#list>
        </#if>
    ]
}
</#if>
