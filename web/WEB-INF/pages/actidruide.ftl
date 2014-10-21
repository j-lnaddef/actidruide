<#import "layout/layout_sans_menu.ftl" as layout>

<@layout.affiche "Cool people Inc.">

    <table class="centre">
        <tr id="bandeau_haut">
                <#include "bandeau_haut.ftl"/>
        </tr>
        
        <tr>
            <td>
                <div id="bandeau_gauche">
                <#include "bandeau_gauche.ftl"/>
                </div>
            </td>
            <td colspan="3">
                <div id="main">
                    <#include "main.ftl"/>
                </div>
            </td>
            <td>
                <div id="bandeau_droite">
                    <#include "bandeau_droite.ftl"/>
                </div>
            </td>
        </tr>

    </table>

</@layout.affiche>