<#macro lienPagination baseId page libelle estPageCourante>
    <#assign idLien="${baseId}${page}" /><#t>
    <li id="${idLien}" class="lien_tri<#t>
        <#if estPageCourante> page_courante</#if>"<#t>
        ><#t>
        ${libelle}<#t>
        <script type="text/javascript"><#t>
            $("#${idLien}").data("numero_page", ${page});<#t>
        </script><#t>
    </li><#t>
</#macro>

<#macro affiche resultatRecherche baseId="pagination_">
    <#assign pageCourante=resultatRecherche.page />
    <#assign nbPagesTotal=resultatRecherche.nbPagesTotal />
    <#if (nbPagesTotal > 1)>
        <ul class="pagination">
            <#-- Lien premiere page -->
            <#assign p=1 />
            <#assign estPageCourante=(pageCourante == p) />
            <@lienPagination baseId 1 1 estPageCourante />
<#t>
            <#-- Calcul des indexes debut/fin -->
            <#assign suspensionAvant=false />
            <#assign suspensionApres=false />
<#t>
            <#if (pageCourante <= 5)>
                <#assign indexDebut=2 />
            <#else>
                <#assign indexDebut=pageCourante-3 />
                <#assign suspensionAvant=true />
            </#if>
<#t>
            <#if (nbPagesTotal - pageCourante <= 4)>
                <#assign indexFin=nbPagesTotal-1 />
            <#else>
                <#assign indexFin=pageCourante+3 />
                <#assign suspensionApres=true />
            </#if>
<#t>
            <#-- Liens avant et après la page courante -->
            <#if indexDebut <= indexFin >
                <#if suspensionAvant><li>...</li></#if><#t>
                <#list indexDebut..indexFin as p>
                    <#assign estPageCourante=(pageCourante == p) />
                    <@lienPagination baseId p p estPageCourante />
                </#list>
                <#if suspensionApres><li>...</li></#if><#t>
            </#if>
<#t>
            <#-- Lien dernière page -->
            <#assign estPageCourante=(pageCourante == nbPagesTotal) />
            <@lienPagination baseId nbPagesTotal nbPagesTotal estPageCourante />
       </ul>
   </#if>
</#macro>
