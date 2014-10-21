<@s.form action="initialisation_changement_mdp" method="get" id="ouvrir_popup_mdp_form">
</@s.form>
<div id="popup_mdp"></div>
<div class="password" onclick="ouvrirMdp()">
</div>

<script type="text/javascript">

    function ouvrirMdp() {
        popup_changement_mdp.ouvrir();
    }

    var popup_changement_mdp = new Popup("changement_mdp", "#ouvrir_popup_mdp_form");
    popup_changement_mdp.setTitre("Changement du mot de passe");

</script>