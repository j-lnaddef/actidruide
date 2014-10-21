
<table>
    <#list model.historiqueActions as ligne>
        <tr class="ligne_evenement">
            
                <#if ligne.action == "donner_points">
                <td>
                    <img src="img/points_up.jpg" width="40" height="40" alt="Image donner points"/>
                </td>
                <td>
                    <span class="source"> ${ligne.source.prenom} </span> a donné <span class="points"> ${ligne.nombrePoints} points </span> à 
                    <span class="beneficiaire">${ligne.cible.prenom}</span> pour <span class="raison"> "${ligne.raison?html}" </span>
                </td>

                <#elseif ligne.action == "retirer_points">
                <td>
                    <img src="img/points_down.jpg" width="40" height="40" alt="Image enlever points"/>
                </td>
                <td>
                    <span class="source"> ${ligne.source.prenom} </span> a retiré <span class="points"> ${ligne.nombrePoints} points </span> à
                    <span class="victime">${ligne.cible.prenom}</span> pour <span class="raison"> "${ligne.raison?html}" </span>
                </td>
                <#elseif ligne.action == "creation_cagnotte">
                <td>
                    <img src="img/cagnotte_cree.jpg" width="40" height="40" alt="Image cagnotte"/>
                </td>
                <td>
                    <span class="source"> ${ligne.source.prenom} </span> a créé une cagnotte pour <span class="raison"> "${ligne.raison?html}" </span>
                </td>

                <#elseif ligne.action == "gain_cagnotte">
                <td>
                    <img src="img/cagnotte_cree.jpg" width="40" height="40" alt="Image cagnotte"/>
                </td>
                <td>
                    <span class="beneficiaire">${ligne.cible.prenom}</span> a gagné les <span class="points"> ${ligne.nombrePoints} points </span>
                    de la cagnotte de <span class="source"> ${ligne.source.prenom} </span> pour <span class="raison"> "${ligne.raison?html}" </span>
                </td>

                <#elseif ligne.action == "changement_rang">
                <td>
                    <img src="img/rank_up.jpg" width="40" height="40" alt="Image rang"/>
                </td>
                <td>
                    <span class="beneficiaire">${ligne.cible.prenom}</span> a un nouveau rang ! <span class="beneficiaire">${ligne.cible.prenom}</span>
                     est désormais <span class="source"> ${ligne.raison?html} </span>
                </td>

                <#elseif ligne.action == "triche">
                <td>
                    <img src="img/points_down.jpg" width="40" height="40" alt="Image enlever points"/>
                </td>
                <td>
                    <span class="source"> ${ligne.cible.prenom} </span> a perdu <span class="points"> ${ligne.nombrePoints} points </span> 
                    pour <span class="raison"> "${ligne.raison?html}" </span>
                </td>

                </#if>
            
        </tr>
    </#list>
</table>