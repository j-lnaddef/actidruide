<#import "../layout/layout_sans_menu.ftl" as layout>
<@layout.affiche "Actidruide - Administration">

<div id="validation_cagnotte">
    <table style="border:1px solid grey; width : 300px; margin : auto">
        <caption style="background-color:grey; color:white; font-weight:bold"> Cagnottes non-attribuées </caption>
        <tr>
            <th>
                Titre
            </th>
            <th>
                Points
            </th>
        </tr>
        <#list model as cagnotte>
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

    <@s.form action="consulter_cagnotte_admin" method="get" id="ouvrir_popup_consultation_cagnotte">
        <@s.hidden name="model.id" value=""/>
    </@s.form>

</div>

<script type="text/javascript">

    // POP-UP de consultation d'une cagnotte
    
    // Retour Ajax après modification d'une cagnotte.
    function retourCagnotte(resultatAjax) {
        remplacerHtmlAjax("#validation_cagnotte", resultatAjax);
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

</@layout.affiche>