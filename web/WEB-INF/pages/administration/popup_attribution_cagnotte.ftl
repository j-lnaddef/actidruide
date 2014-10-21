<@s.form action="attribuer_cagnotte" id="popup_attribution_cagnotte">
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
            Attribuer à :
        </td>
        <td>
            <@s.textfield name="destinataire" size="30"/>
        </td>
        <td>
            <@s.submit value="Attribuer"/>
        </td>
        </tr>
    </table>
</@s.form>


<script type="text/javascript">

    // Traite la soumission en ajax du formulaire contenu dans la popup.
    xhrForm(
        "popup_attribution_cagnotte", 
        false, 
        retourCagnotte);
</script>