
<#-- Lien qui ouvre la pop-up du guide utilisateur -->

<div id="bouton_guide_utilisateur">
</div>

<div id="popup_guide_utilisateur" title="Guide utilisateur">
    <div id="page_un" style="display:none;">
        <h1 style="text-align:center">Bienvenue chez <span style="color:#002855">Acti</span><span style="color:#E26B0D">Druide</span> !</h1>

        <p>
            Le but du site est simple : récompenser les <span style="color:green">bonnes actions</span> (petit-déjeuner, organisation d’after-work, …), et punir les <span style="color:red">mauvaises</span> (ne pas refaire le café après l’avoir fini, ne pas venir aux after-work, …).
        </p>
        <p>
            Les points sont attribués ou retirés par le système, et également par vous-même.
        </p>

    </div>

    <div id="page_deux" style="display:none">
        <table> 
            <tr>
                <td colspan="3" style="text-align:center">
                    <img src="img/bandeau_haut.png" alt="Bandeau haut"/>
                </td>
            </tr>
            <tr>
                <td> <img src="img/bandeau_gauche.png"  alt="Bandeau gauche"/> </td>
                <td style="padding:10px; border: 1px solid grey;">
                    Sur le <strong>bandeau du haut</strong>, vous pouvez voir apparaître le nombre de points que vous avez, votre rang, et le nombre de points qu’il vous reste à distribuer aujourd’hui. <br/>
                    Votre rang est déterminé par le nombre de points que vous avez. <br/>
                    Le nombre de points à distribuer est remis à jour à minuit toutes les nuits. Le nombre de points que vous pouvez distribuer par jour dépend de votre rang. <br/><br/><br/>
                    Le <strong>bandeau de droite</strong> permet d’effectuer différentes action :
                    <ul> 
                    <li> Donner des points à un collègue. </li>

                    <li> Retirer des points à un collègue.</li>

                    <li> Créer une cagnotte. La personne remplissant les conditions d’attribution se verra l’intégralité de la cagnotte reversée.</li>
                    </ul>

                    <p> Les actions que vous avez le droit d’effectuer dépendent de votre rang. </p><br/>

                    <p> La <strong>partie centrale</strong> (ci-dessous) est un historique des dernières actions effectuées</p><br/>

                    <p>Pour finir le <strong>bandeau de gauche</strong> permet de voir un aperçu du classement d’ActiDruide. <br/>

                    Il permet également de consulter les cagnottes actives. </p>
                </td>
                <td> <img src="img/bandeau_droite.png" width="200" height="480" alt="Bandeau haut"/> </td>
            </tr>
            <tr>
                <td colspan="3" style="text-align:center">
                    <img src="img/main.PNG" alt="Bandeau haut"/>
                </td>
            </tr>
        </table>
    </div>
    
    <table style="margin:auto">
        <tr>
            <td>
                <div id="page_precedente" class="lien inactif" onclick="pagePrecedente()" style="display:none; padding-right:20px">
                    <img src="img/previous.jpg" width="30" height="30" alt="Page Précedente"/>
                </div>
            </td>
            <td>
                <div id="page_suivante" class="lien" onclick="pageSuivante()" style="display:none; padding-left:20px">
                    <img src="img/next.jpg" width="30" height="30" alt="Page Suivante"/>
                </div>
            </td>
        </tr>
    </table>
    
</div>



<script type="text/javascript">

    var pageCourante = "page_un";

    $(function() {
        
        // this initializes the dialog (and uses some common options that I do)
        $("#popup_guide_utilisateur").dialog({autoOpen : false, modal : true, show : "blind", hide : "blind", height:300, width:575});

        // next add the onclick handler
        $("#bouton_guide_utilisateur").click(function() {
            $("#popup_guide_utilisateur").dialog("open");
            
            $("#page_un").show();
            $("#page_suivante").show();
            $("#page_precedente").show();
            $("#page_deux").hide();
            $("#page_trois").hide();
            $("#page_precedente").prop('disabled', true);

            return false;
        });
    });
    
    function pageSuivante() {
        switch(pageCourante) {
            case "page_un" : 
                $("#page_un").hide();
                $("#page_deux").show();
                pageCourante = "page_deux";
                $("#popup_guide_utilisateur").dialog({width : 1200, height :810});
                $("#page_precedente").attr('class', 'lien');
                $("#page_suivante").attr('class', 'lien inactif');
                break;

            case "page_deux" : 
                break;
        }
    }

    function pagePrecedente() {
        switch(pageCourante) {
            case "page_un" : 
                break;

            case "page_deux" : 
                $("#page_deux").hide();
                $("#page_un").show();
                pageCourante = "page_un";
                $("#popup_guide_utilisateur").dialog({height:300, width:575});
                $("#page_precedente").attr('class', 'lien inactif');
                $("#page_suivante").attr('class', 'lien');
                break;
        }
    }
    
</script>