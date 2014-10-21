<#import "commun/composants.ftl" as composants>

<#assign aPermissionDonnerPoints = false>
<#list utilisateur.rang.permissions as permission>
    <#if permission.nom == "donner_points">
        <#assign aPermissionDonnerPoints = true>
    </#if>
</#list>

<#assign aPermissionRetirerPoints = false>
<#list utilisateur.rang.permissions as permission>
    <#if permission.nom == "retirer_points">
        <#assign aPermissionRetirerPoints = true>
    </#if>
</#list>

<#assign aPermissionCreerCagnotte = false>
<#list utilisateur.rang.permissions as permission>
    <#if permission.nom == "creer_cagnotte">
        <#assign aPermissionCreerCagnotte = true>
    </#if>
</#list>

<#if aPermissionDonnerPoints>
<div>
<#else>
<div class="div_inactive">
<span> Vous n'avez pas les droits suffisants pour effectuer cette action </span>
</#if>
<img src="img/points_up.jpg" width="20" height="20" alt="Donner des points"/> Etre un gentil druide
<@s.form action="donner_retirer_points" method="post" id="donner_points_form" onsubmit="return validateFormDonnerPoints()">    
    <@s.hidden name="model.action" value="donner_points"/>
    <table class="form_table">
        <tr>
            <td colspan="3">
                <@composants.messagesErreur />
            </td>
        </tr>
        <tr>
            <td> Donner </td>
            <td>
                <@s.textfield name="model.nbPoints"/>
            </td>
            <td> points </td>
        </tr>
        <tr>
            <td> À </td>
            <td>
                <@s.textfield id="utilisateurDonner"/>
                <@s.hidden name="model.destinataire" id="utilisateurDonnerId" />
            </td>
            <td></td>
        </tr>
        <tr>
            <td> Pour </td>
            <td colspan="2">
                <@s.textarea name="model.raison" cols="24" rows="3" cssStyle="resize:none;" maxlength="500"/>
            </td>
        </tr>
        <tr>
            <td> Anonyme </td>
            <td>
                <@s.checkbox name="model.anonyme" value="false"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
                <#if aPermissionDonnerPoints>
                <div class="bouton" onClick="javascript:$('#donner_points_form').submit();"> <img src="img/points_up.jpg" width="40" height="40" alt="Donner points"/> </div>
                <#else>
                <div class="bouton_inactif"> <img src="img/points_up.jpg" width="40" height="40" alt="Donner points"/> </div>
                </#if>
            </td>
        </tr>
    </table>
</@s.form>
</div>

<#if aPermissionRetirerPoints>
<div>
<#else>
<div class="div_inactive">
<span> Vous n'avez pas les droits suffisants pour effectuer cette action </span>
</#if>
<img src="img/points_down.jpg" width="20" height="20" alt="Enlever des points"/> Etre un méchant druide
<@s.form action="donner_retirer_points" method="post" id="retirer_points_form" onsubmit="return validateFormRetirerPoints()">    
    <@s.hidden name="model.action" value="retirer_points"/>
    <table class="form_table">
        <tr>
            <td colspan="3">
                <@composants.messagesErreur />
            </td>
        </tr>
        <tr>
            <td> Retirer </td>
            <td>
                <@s.textfield name="model.nbPoints"/>
            </td>
            <td> points </td>
        </tr>
        <tr>
            <td> À </td>
            <td>
                <@s.textfield id="utilisateurRetirer"/>
                <@s.hidden name="model.destinataire" id="utilisateurRetirerId" />
            </td>
            <td></td>
        </tr>
        <tr>
            <td> Pour </td>
            <td colspan="2">
                <@s.textarea name="model.raison" cols="24" rows="3" cssStyle="resize:none;" maxlength="500"/>
            </td>
        </tr>
        <tr>
            <td> Anonyme </td>
            <td>
                <@s.checkbox name="model.anonyme" value="false"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
                <#if aPermissionRetirerPoints>
                <div class="bouton" onClick="javascript:$('#retirer_points_form').submit();"> <img src="img/points_down.jpg" width="40" height="40" alt="Retirer points"/> </div>
                <#else>
                <div class="bouton_inactif"> <img src="img/points_down.jpg" width="40" height="40" alt="Retirer points"/> </div>
                </#if>
            </td>
        </tr>
    </table>
</@s.form>
</div>

<#if aPermissionCreerCagnotte>
<div>
<#else>
<div class="div_inactive">
<span> Vous n'avez pas les droits suffisants pour effectuer cette action </span>
</#if>
<img src="img/cagnotte_cree.jpg" width="20" height="20" alt="Créer une cagnotte"/> Créer une cagnotte
<@s.form action="creation_cagnotte" method="post" id="creer_cagnotte_form" onsubmit="return validateFormCreerCagnotte()">
    <table class="form_table" style="width:100%">
        <tr>
            <td colspan="3">
                <@composants.messagesErreur />
            </td>
        </tr>
        <tr>
            <td> Titre </td>
            <td>
                <@s.textfield name="model.titre" />
            </td>
            <td/>
        </tr>
        <tr>
            <td> Mettre </td>
            <td>
                <@s.textfield name="model.montant" />
            </td>
            <td> points </td>
        </tr>
        <tr>
            <td> Pour qui </td>
            <td colspan="2">
                <@s.textarea name="model.actionARealiser" cols="24" rows="3" cssStyle="resize:none;" maxlength="500"/>
            </td>
        </tr>
        <tr>
            <td> Date limite </td>
            <td>
                <@s.textfield name="model.dateEcheance" date="true"/>
            </td>
        </tr>
        <tr>
            <td/>
            <td/>
            <td>
                <#if aPermissionCreerCagnotte>
                <div class="bouton" onClick="javascript:$('#creer_cagnotte_form').submit();"> <img src="img/cagnotte_cree.jpg" width="40" height="40" alt="Créer cagnotte"/> </div>
                <#else>
                <div class="bouton_inactif"> <img src="img/cagnotte_cree.jpg" width="40" height="40" alt="Créer cagnotte"/> </div>
                </#if>
            </td>
        </tr>
    </table>
</@s.form>
</div>

<script type="text/javascript">

    champNombreEntier("input[name='model.nbPoints']");

    <#-- Autocomplétion des utilisateurs -->
    var utilisateurs = [];
    <#list model.utilisateursPossibles as u>
        utilisateurs.push({id: "${u.id}", libelle: "${u.prenom!''?json_string} ${u.nom?json_string}"});
    </#list>

    var autocompletionUtilisateursDonner = new Autocompletion('#utilisateurDonner', '#utilisateurDonnerId', utilisateurs, null);
    autocompletionUtilisateursDonner.init();
    var autocompletionUtilisateursRetirer = new Autocompletion('#utilisateurRetirer', '#utilisateurRetirerId', utilisateurs, null);
    autocompletionUtilisateursRetirer.init();

    function validateFormDonnerPoints() {
        var nbPoints = document.forms["donner_points_form"]["model.nbPoints"].value;
        if (nbPoints == null || nbPoints == "" || nbPoints == 0) {
            alert("Le nombre de points est obligatoire");
            return false;
        }
        var destinataire = document.forms["donner_points_form"]["model.destinataire"].value;
        if (destinataire == null || destinataire == "") {
            alert("Veuillez sélectionner un destinataire pour l'action");
            return false;
        }
    }

    function validateFormRetirerPoints() {
        var nbPoints = document.forms["retirer_points_form"]["model.nbPoints"].value;
        if (nbPoints == null || nbPoints == "" || nbPoints == 0) {
            alert("Le nombre de points est obligatoire");
            return false;
        }
        var destinataire = document.forms["retirer_points_form"]["model.destinataire"].value;
        if (destinataire == null || destinataire == "") {
            alert("Veuillez sélectionner un destinataire pour l'action");
            return false;
        }
    }

    function validateFormCreerCagnotte() {
        var titre = document.forms["creer_cagnotte_form"]["model.titre"].value;
        if (titre == null || titre == "") {
            alert("Le titre de la cagnotte est obligatoire");
            return false;
        }
        var montant = document.forms["creer_cagnotte_form"]["model.montant"].value;
        if (montant == null || montant == "" || montant == 0) {
            alert("Le nombre de points est obligatoire");
            return false;
        }
        var actionARealiser = document.forms["creer_cagnotte_form"]["model.actionARealiser"].value;
        if (actionARealiser == null || actionARealiser == "") {
            alert("L'action à réaliser doit être renseignée");
            return false;
        }
    }

    // Mise des formulaires à "disabled"
    <#if !aPermissionDonnerPoints>
    $('#donner_points_form :input').attr("disabled", "disabled");
    $('#donner_points_form :submit').attr("disabled", "disabled").css('cursor','default').fadeTo(500,0.2);;
    $( "#donner_points_form" ).prop( "disabled", true );
    </#if>
    <#if !aPermissionRetirerPoints>
    $('#retirer_points_form :input').attr("disabled", "disabled");
    $('#retirer_points_form :submit').attr("disabled", "disabled").css('cursor','default').fadeTo(500,0.2);;
    $( "#retirer_points_form" ).prop( "disabled", true );
    </#if>
    <#if !aPermissionCreerCagnotte>
    $('#creer_cagnotte_form :input').attr("disabled", "disabled");
    $('#creer_cagnotte_form :submit').attr("disabled", "disabled").css('cursor','default').fadeTo(500,0.2);;
    $( "#creer_cagnotte_form" ).prop( "disabled", true );
    </#if>
</script>