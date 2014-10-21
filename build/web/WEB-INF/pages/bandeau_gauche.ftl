
<div id="apercu_classement">
    <table style="border:1px solid grey; width : 150px; margin : auto">
        <caption style="background-color:grey; color:white; font-weight:bold">
            Classement
        </caption>
        <#list model.apercuClassement.topUtilisateurs as topLigne>
        <tr>
            <td>
                <img src="img/points_up.jpg" width="40" height="40" alt="Image donner points"/>
            </td>
            <td>    
                ${topLigne.nbPoints}
            </td>
            <td>    
                ${topLigne.prenom}
            </td>
            <td>
                <img id="${topLigne.id}_photo" src="img/photo/${topLigne.login}.jpg" width="40" height="40" alt="Photo"/>
                <script type="text/javascript">
                    $('#${topLigne.id}_photo').error(function() {
                        $('#${topLigne.id}_photo').attr("src", "img/photo/pingouin.jpg");
                    });
                </script>
            </td>
        </tr>
        </#list>
        <tr>
            <td>
            . <br/>
            . <br/>
            . <br/>
            </td>
        </tr>
        <#list model.apercuClassement.bottomUtilisateurs as bottomLigne>
        <tr>
            <td>
                <img src="img/points_down.jpg" width="40" height="40" alt="Image retirer points"/>
            </td>
            <td>    
                ${bottomLigne.nbPoints}
            </td>
            <td>    
                ${bottomLigne.prenom}
            </td>
            <td>
                <img id="${bottomLigne.id}_photo" src="img/photo/${bottomLigne.login}.jpg" width="40" height="40" alt="Photo"/>
                <script type="text/javascript">
                    $('#${bottomLigne.id}_photo').error(function() {
                        $('#${bottomLigne.id}_photo').attr("src", "img/photo/pingouin.jpg");
                    });
                </script>
            </td>
        </tr>
        </#list>
    </table>
    <div id="voirClassementBouton" class="lien"> Classement complet</div>
</div>

<@s.form action="voir_classement" method="get" id="ouvrir_popup_classement">
</@s.form>

<br/>
<br/>

<div id="cagnottes_en_cours">
    <table style="border:1px solid grey; width : 300px; margin : auto">
        <caption style="background-color:grey; color:white; font-weight:bold"> Cagnottes actives </caption>
        <tr>
            <th>
                Titre
            </th>
            <th>
                Points
            </th>
        </tr>
        <#list model.cagnottesActives as cagnotte>
            <tr>
                <td>
                    <div id="id_bouton_consultation_${cagnotte.id}" class="lien">
                        ${cagnotte.titre}
                    </div>
                    <script type="text/javascript">
                        $("#id_bouton_consultation_${cagnotte.id}").data("id", ${cagnotte.id});
                    </script>
                </td>
                <td>
                    ${cagnotte.nombrePoints}
                </td>
            </tr>
        </#list>
    </table>
</div>

<@s.form action="consulter_cagnotte" method="get" id="ouvrir_popup_consultation_cagnotte">
    <@s.hidden name="model.id" value=""/>
</@s.form>

<script type="text/javascript">

    
    // POP-UP de consultation du classement
    
    function retourClassement(resultatAjax) {
    }

    var popupClassement = new Popup(
                "popupClassement", 
                "#ouvrir_popup_classement", 
                retourClassement);

    function ouvrirPopupClassement() {
        popupClassement.ouvrir();
    }

    $("#voirClassementBouton").click(function(){
        ouvrirPopupClassement();
    });

    popupClassement.setTitre("Classement Actidruidien");
    popupClassement.setLargeur(500);

    // POP-UP de consultation d'une cagnotte
    
    // Retour Ajax après modification d'une cagnotte.
    function retourCagnotte(resultatAjax) {
        remplacerHtmlAjax("#cagnottes_en_cours", resultatAjax);
    }

    var popupConsultationCagnotte = new Popup(
                "popupConsultationCagnotte", 
                "#ouvrir_popup_consultation_cagnotte", 
                retourCagnotte);

    function ouvrirPopupConsultationCagnotte() {
        popupConsultationCagnotte.ouvrir();
    }

    popupConsultationCagnotte.setTitre("Cagnotte active");
    popupConsultationCagnotte.setLargeur(600);


    // Tableau d'action des cagnottes.

    var actionsPopupCagnotte = new TableauActionPopup(
        "#ouvrir_popup_consultation_cagnotte",
        "model.id",
        "div[id^='id_bouton_consultation_']",
        popupConsultationCagnotte
    );

    actionsPopupCagnotte.init();

</script>