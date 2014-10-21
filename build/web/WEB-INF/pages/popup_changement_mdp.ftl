<div id="popup_mdp">

<@s.form action="modification_mdp" method="post" id="changement_mdp_form" >
    <@s.hidden name="ajax" value="true" />
    <table>
        <tr>
            <td>
                Ancien mot de passe
            </td>
            <td>
                <@s.password name="model.ancienMdp"/>
            <td>
        </tr>
        <tr>
            <td>
                Nouveau mot de passe
            </td>
            <td>
                <@s.password name="model.nouveauMdp"/>
            <td>
        </tr>
        <tr>
            <td>
                Confirmation mot de passe
            </td>
            <td>
                <@s.password id="confirmation_mdp"/>
            <td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <input id="bouton_changer_mdp" 
                        type="button" class="bouton" value="Valider" onclick="validateFormChangerMdp()"/>
                <#--<@s.submit type="input" value="Valider" onclick="validateFormChangerMdp()"/>-->
            </td>
        </tr>
    </table>
</@s.form>

<script type="text/javascript">

    var popup = getPopup("changement_mdp");

    function fermerPopupMdp() {
        popup.fermer();
    }
    
    function retourChangementMdp(resultatAjax) {
        if (resultatAjax.erreurs.length != 0) {
            alert("Le mot de passe actuel est incorrect");
        }
        else {
            alert("Mot de passe changé!");
            // Fermer la popup
            fermerPopupMdp();
        }
    }

    xhrForm("#changement_mdp_form", false, retourChangementMdp);

    function validateFormChangerMdp() {
        var nouveauMdp = document.forms["changement_mdp_form"]["model.nouveauMdp"].value;
        var confirmation = document.forms["changement_mdp_form"]["confirmation_mdp"].value;
        if (nouveauMdp != confirmation) {
            alert("La confirmation du mot de passe ne correspond pas");
            return false;
        }
        else {
            $("#changement_mdp_form").submit();
        }
    }

</script>

</div>