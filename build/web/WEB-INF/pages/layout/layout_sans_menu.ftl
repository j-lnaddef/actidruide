<#import "../commun/composants.ftl" as composants /> 

<#macro affiche titre>
    <@composants.pageXhtml titre>
        <#if action.utilisateur??>
            <div id="infos_connexion">
                <#include "../deconnexion.ftl"/>
                <#include "../changement_mdp.ftl"/>
                <#include "../guide/guide_utilisateur.ftl"/>
            </div>
        </#if>
        <div id="header">
            <#include "header.ftl" />
        </div>

        <div id="contenu" class="arr_plan_connexion" >
            <#nested>
        </div>

        <#if action.utilisateur?? && action.utilisateur.rang.nom == "administrateur">
            <@s.url id="administrer" action="administrer"/>
            <a href="${administrer}"> Partie administrateur </a>
        </#if>
        <div id="footer">
            <#include "footer.ftl" />
        </div>
    </@composants.pageXhtml>
</#macro>
