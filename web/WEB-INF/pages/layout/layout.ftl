<#import "../commun/composants.ftl" as composants /> 

<#macro affiche titre>
    <@composants.pageXhtml titre>
        <#if action.utilisateur??>
            <div id="infos_connexion">
                <@s.form action="deconnexion" method="post" id="deconnexion_form">
                </@s.form>
                <div class="deconnexion" onclick="deconnecter()"></div>
            </div>
        </#if>

        <div id="header">
            <#include "header.ftl" />
        </div>

        <div id="menu">
            <#include "../commun/menu.ftl" />
        </div>

        <div id="contenu">
            <#if action.filAriane??>
                <#include "../commun/fil_ariane.ftl" />
            </#if>
            <#nested>
        </div>

        <div id="footer">
            <#include "footer.ftl" />
        </div>

        <#-- Div destinée à contenir le HTML des erreurs qui surviennent lors d'un appel Ajax-->
        <div id="popup_erreur" class="popup"></div>
        <#-- Formulaire caché servant à l'affichage de la popup de choix d'imprimante. -->
        <@s.form namespace="/" action="choixImprimante" method="get" id="choix_imprimante_form">
            <input type="hidden" name="identifiant" value="" />
        </@s.form>
    </@composants.pageXhtml>
</#macro>


<script type="text/javascript">
    function deconnecter() {
        $("#deconnexion_form").submit();
    }
</script>