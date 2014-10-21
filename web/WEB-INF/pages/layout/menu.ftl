<div class="menu_arr_plan">
    <#if menu??>
    <div id="nav" class="aligner">
        <#-- =========================  Début Bloc Navigation  ================================ -->
        <div id="bloc_nav" class="largeur_site">

            <#if menu.sousElements??>
            <ul class="navigation_5_items">

                <#list menu.sousElements as element>
                <@s.url var="redirectionMenu" namespace="${element.namespace}" action="${element.action}" />
                <#if element_has_next>
                    <#if element.actif>
                        <li class="selected">
                    <#else>
                        <li>
                    </#if>
                <#else>
                    <#if element.actif>
                        <li class="last selected">
                    <#else>
                        <li class="last">
                    </#if>
                </#if>
                    <div class="nav_li_bg_left">
                        <div class="nav_li_bg_right">
                            <a href="${redirectionMenu}"><span class="vertical_align_container"><span class="vertical_align">${element.libelle}</span></span></a>
                        </div>
                    </div>
                    <#if element.sousElements??>
                    <ul class="sous_nav">
                        <#list element.sousElements as sousElement>
                            <@s.url var="redirectionSousMenu" namespace="${sousElement.namespace}" action="${sousElement.action}" />
                            <li><a href="${redirectionSousMenu}">${sousElement.libelle}</a></li>
                        </#list>
                    </ul>
                    </#if>
                </li>
                </#list>
            </ul>
            </#if>
            <div class="aligner"></div>
        </div>
        <#--==========================  Fin Bloc Navigation  =================================-->
    </div>
    </#if>
</div>
