<@s.form action="ajouter_points_cagnotte" id="popup_cagnotte">
    <@s.hidden name="model.id"/>
    <table>
        <tr>
            <td style="font-weight:bold;">
                Titre : 
            </td>
            <td colspan="3">
                <#if model.titre??>
                    ${model.titre?html}
                </#if>
            </td>
        </tr>
        <tr>
            <td style="width:160px;font-weight:bold;">
                Nombre de points : 
            </td>
            <td style="width:120px;">
                <#if model.nombrePoints??>
                    ${model.nombrePoints}
                </#if>
            </td>
            <td style="width:200px;font-weight:bold;">
                Date d'échéance : 
            </td>
            <td style="width:130px;">
                <#if model.dateEcheance??>
                    ${model.dateEcheance}
                </#if>
            </td>
        </tr>
        <tr>
            <td style="font-weight:bold;">
                Action à réaliser : 
            </td>
            <td colspan="3">
                <#if model.actionARealiser??>
                    ${model.actionARealiser?html}
                </#if>
            </td>
        </tr>
        <tr>
        <td style="font-weight:bold;">
            Ajouter des points :
        </td>
        <td>
            <@s.textfield name="nbPointsAjoutes" size="5"/>
        </td>
        <td>
            <@s.submit value="Contribuer"/>
        </td>
        </tr>
    </table>
</@s.form>

<!--
<script type="text/javascript">

    <#-- Est appelée en cas d'annulation de création/modification d'une réponse. -->
    function fermerPopupClassement() {
        var popupClassement = getPopup("popupClassement");
        popupClassement.fermer();
    }

    // Traite la soumission en ajax du formulaire contenu dans la popup.
    xhrForm(
        "popup_cagnotte", 
        false, 
        retourCagnotte);
</script>
-->
