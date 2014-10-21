<#if action.filAriane??>
    <div class="fil_arianne">
        <#assign liensAriane = action.filAriane.liens>

        <#list liensAriane as lien>
            <@s.url namespace=lien.namespace action=lien.action var="urlLien"/>
            <a href="${urlLien}">${lien.libelle?html}</a>
            <#-- On affiche le chevron tant que ce n'est pas le dernier lien. -->
            <#if lien_has_next>
                &gt;
            </#if>
        </#list>
    </div>
</#if>
