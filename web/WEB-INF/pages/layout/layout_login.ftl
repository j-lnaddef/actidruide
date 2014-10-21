<#import "../commun/composants.ftl" as composants /> 

<#macro affiche titre>
    <@composants.pageXhtml titre>
        <div id="header">
            <#include "header.ftl" />
        </div>

        <div id="contenu" class="arr_plan_connexion" >
            <#nested>
        </div>
        <div id="footer">
            <#include "footer.ftl" />
        </div>
    </@composants.pageXhtml>
</#macro>
