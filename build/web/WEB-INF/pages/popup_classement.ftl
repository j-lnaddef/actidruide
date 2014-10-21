<div id="popup_classement">
    <table style="margin:auto">
        <tr>
            <td>
                <table style="border:solid 1px #458B00; margin:15px;">
                    <#list model.utilisateursGentils as ligne>
                    <tr>   
                        <td style="font-size:20px; font-weight:bold; text-align:center">
                            ${ligne.nbPoints} <img src="img/Dactidruide.png" width="22" height="22" alt="Points"/>
                        </td>
                        <td style="padding:10px;">
                            ${ligne.prenom}
                        </td>
                        <td>
                            <img id="${ligne.id}_picture" src="img/photo/${ligne.login}.jpg" width="40" height="40" alt="Photo"/>
                            <script type="text/javascript">
                                $('#${ligne.id}_picture').error(function() {
                                    $('#${ligne.id}_picture').attr("src", "img/photo/pingouin.jpg");
                                });
                            </script>
                        </td>
                    </tr>
                    </#list>
                </table>
            </td>
            <td>
                <table style="border:solid 1px #7F0000; margin:15px;">
                    <#list model.utilisateursMechants as ligne>
                    <tr>   
                        <td style="font-size:20px; font-weight:bold; text-align:center">
                            ${ligne.nbPoints} <img src="img/Dactidruide.png" width="22" height="22" alt="Points"/>
                        </td>
                        <td style="padding:10px;">
                            ${ligne.prenom}
                        </td>
                        <td>
                            <img id="${ligne.id}_picture" src="img/photo/${ligne.login}.jpg" width="40" height="40" alt="Photo"/>
                            <script type="text/javascript">
                                $('#${ligne.id}_picture').error(function() {
                                    $('#${ligne.id}_picture').attr("src", "img/photo/pingouin.jpg");
                                });
                            </script>
                        </td>
                    </tr>
                    </#list>
                </table>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript">

    <#-- Est appelée en cas d'annulation de création/modification d'une réponse. -->
    function fermerPopupClassement() {
        var popupClassement = getPopup("popupClassement");
        popupClassement.fermer();
    }

    // Traite la soumission en ajax du formulaire contenu dans la popup.
    xhrForm(
        "ouvrir_popup_classement", 
        false, 
        retourClassement);
</script>