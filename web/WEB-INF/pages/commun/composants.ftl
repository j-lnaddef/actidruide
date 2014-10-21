<#-- Macros d'objets utilisés régulièrement en des endroits variés de l'interface -->
<#-- IMPORTANT : importer ce fichier avec le nom "composants" -->
<#-- Exemple : <#import "composants.ftl" as composants /> -->

<#-- Affiche une page XHTML 1.0 Transitional (Doctype, head, body) standard -->
<#macro pageXhtml titre>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <title>Actidruide - ${titre}</title>
            <meta name="description" content="Actidruide" />
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <#include "css_js.ftl" />
        </head>
        <body>
            <#nested>
        </body>
    </html>
</#macro>

<#-- Boutons 3D -->
<#-- id = id de l'input/bouton sur lequel effectuer l'action -->
<#-- action = nom de la méthode JS à effectuer sur le click du bouton -->
<#-- dernier = "dernier" si le bouton est le derniere de la liste des boutons -->
<#macro bouton id="" action="" dernier="dernier" actif=true idBouton3D="">
    <div <#if idBouton3D!="">id="${idBouton3D}"</#if>class="bouton_3d alignement_droite<#if !actif> bouton_inactif</#if>">
        <div class="b_gauche"></div>
        <div class="b_milieu"><#nested></div>
        <div class="b_droite"></div>
        <div class="clearfloat"></div>
    </div>
    <#if dernier == "dernier">
        <div class="clearfloat"></div>
    </#if>
    <#if id!="" && action!="" && actif>
        <script type="text/javascript">
            $("#${id}").click(function() {
               ${action}(); 
            });
        </script>
    </#if>
</#macro>

<#-- Lien 3D -->
<#macro boutonLien namespace="" action="" dernier="dernier" actif=true>
    <@s.url id="lien" namespace="${namespace}" action="${action}" />
    <@bouton "" "" dernier actif>
        <#if actif>
            <a href="${lien}"><#nested></a>
        <#else>
            <#nested>
        </#if>
    </@bouton>
</#macro>

<#-- Boutons Ajouter --> 
<#macro boutonAjouter id>
    <div class="boutonAjouter" id="${id}">
    </div>
    <div class="clearfloat"></div>
</#macro>

<#macro boutonAffichePopup idBouton action classeCss="creation">
    <div <#if idBouton != "">id="${idBouton}"</#if> class="lien_droite ${classeCss}">
        <#nested>
    </div>
    <#if action != "">
        <script type="text/javascript">
            $("#${idBouton}").click(function(){
                ${action}();
            });
        </script>
    </#if>
</#macro>

<#-- Boutons Ajouter --> 
<#macro boutonAjouter>
    <div class="boutonAjouter" id="boutonAjouter">
        <div class="clearfloat"></div>
    </div>
</#macro>


<#-- Titre mis en valeur -->
<#macro conteneurTitre titre>
    <div class="titre_3d">
        <div class="effet_bordeau_gauche"></div>
        <div class="effet_bordeau_milieu">${titre?html}</div>
        <div class="effet_bordeau_droite"></div>
        <div class="clearfloat"></div>
    </div>
</#macro>

<#-- Conteneur par défaut -->
<#macro conteneur titre="">
    <div class="conteneur_3d">
        <div class="effet_gris"></div>
        <div class="corps_gris">
            <#if titre?? && titre != "">
                <@conteneurTitre titre />
            </#if>
            <div class="contenu">
                <#nested>
            </div>
        </div>
    </div>
</#macro>

<#-- Conteneur destiné aux formulaires -->
<#macro conteneurFormulaire titre="${action.getText('global.action.recherche.titre')}">
    <div class="formulaire">
        <div class="form_titre">
            ${titre}
        </div>
        <div class="form_champs">
            <#nested>
        </div>
    </div>
</#macro>

<#-- Conteneur popup -->
<#macro conteneurPopup titre="">
    <div class="contenu_popup">
        <#if titre?? && titre != "">
            <@conteneurTitre titre />
        </#if>
        <div class="contenu">
            <#nested>
        </div>
    </div>
</#macro>

<#-- Affiche le lien de création d'un nouvel élément -->
<#macro lienCreation>
    <div class="lien_droite creation">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien de regénération de mot de passe -->
<#macro lienRegenerer id>
    <div id="${id}" class="lien_gauche regenerer">
        <#nested>
    </div>
    <div class="clearfloat"></div>
</#macro>

<#-- Affiche le bouton d'export de résultats d'une liste en CSV. L'export fait la soumission d'un formulaire. -->
<#macro boutonExportCsv idBouton selecteurFormulaire>
    <div id="${idBouton}" class="lien_droite exportcsv">
        <#nested>
    </div>
    <script type="text/javascript">
        $("#${idBouton}").click( function() {
            $("${selecteurFormulaire}").submit();
        });
    </script>
</#macro>

<#-- Affiche le lien de suppression d'un nouvel élément -->
<#macro lienSuppressionGroupe id>
    <div id="${id}" class="boutonSupprimer">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien de suppression d'un nouvel élément -->
<#macro lienSuppression id>
    <div id="${id}" class="lien suppression">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien d'ajout / retrait groupé d'un cahier dans une session. -->
<#macro lienActionCahierSession id action>
    <div id="${id}" class="lien ${action}_cahier">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien d'impression groupée. -->
<#macro lienImpression id>
    <div id="${id}" class="lien impression">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien d'activation d'un utilisateur/groupe -->
<#macro lienActivation id>
    <div id="${id}" class="lien activation">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien de désactivation d'un utilisateur/groupe -->
<#macro lienDesactivation id>
    <div id="${id}" class="lien desactivation">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien d'export de résultats -->
<#macro lienExport>
    <div class="lien export">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien de prévisualisation/impression. -->
<#macro lienPrevisualisationImpression>
    <div class="lien lien_previsualisation_impression">
        <#nested>
    </div>
</#macro>

<#-- Affiche le lien d'export/impression. -->
<#macro lienExportImpression actif=true raison="" >
    <div class="lien_export_impression <#if !actif>bouton_inactif</#if>"  <#if !actif>title="${raison}"</#if>>
        <#nested>
    </div>
</#macro>

<#-- Affichage du titre d'un tableau -->
<#macro titreTableau>
    <h3 class="titre_tableau"><#nested></h3>
</#macro>

<#-- Affiche le titre d'un bloc quelconque -->
<#macro titreBloc>
    <h3 class="titre_bloc"><#nested></h3>
</#macro>

<#-- Barre d'affichage au dessus et en dessous du tableau -->
<#-- Au dessus du tableau, peu contenir le titre du tableau et le lien pour ajout d'un nouvel élément -->
<#macro titreEtActionsDuTableau>
    <div class="sus_sous_tableau">
        <#nested>
        <div class="clearfloat"></div>
    </div>
</#macro>

<#-- Flèche indiquant les lignes sélectionnées -->
<#macro actionsGlobalesTableau>
    <div class="selecteur"></div>
    <#nested>
</#macro>

<#-- Génération de bouton à l'intérieur du tableau -->
<#macro boutonTableau type tooltip actif repertoire="global">
    <#assign var="">
    <#if !actif>
        <#assign var="bouton_inactif">
    </#if>
    <img src="<@s.url value='/img/${repertoire}/icone_${type}.png' />" class="bouton_tableau ${var}" alt="${tooltip?html}" title="${tooltip?html}" />
</#macro>

<#-- Génération de boutons de montée / descente à l'intérieur du tableau -->
<#macro boutonTableauOrdreLigne type tooltip actif idBouton>
    <#assign completionCss>
        <#if type="montee">Monter</#if>
        <#if type="descente">Descendre</#if>
    </#assign>
    <#assign var="">
    <#assign grise="">
    <#assign inactif="">
    <#if !actif>
        <#assign grise="_grise">
        <#assign inactif="bouton_inactif">
    </#if>
    <img <#if idBouton != "">id="${idBouton}"</#if> src="<@s.url value='/img/global/icone_${type}${grise}.png' />" 
        class="bouton_tableau position_question_reponse ${inactif}" alt="${tooltip?html}" title="${tooltip?html}" />
</#macro>

<#-- Affiche un message de succès ou d'erreurs, ainsi que les différentes erreurs de la page -->
<#macro messagesSuccesOuErreur>
    <#assign enErreur=(actionErrors?? && actionErrors?size > 0) />
    <#assign enSucces=!enErreur />
    <#if (actionMessages?? && actionMessages?size > 0)>
        <#assign id="message_${composants.getNumeroUnique()}" />
        <div id="${id}" class="message_appli <#if enSucces>message_succes<#else>message_erreur</#if>">
            <@s.actionmessage />
            <#if enErreur>
                <@s.actionerror />
            </#if>
        </div>
        <#if enSucces>
            <@composants.scriptDisparition "#${id}" />
        </#if>
    </#if>
</#macro>

<#-- Affiche les messages de succès en vert -->
<#macro messagesSucces>
    <#if (actionMessages?? && actionMessages?size > 0)>
        <#assign id="message_succes_${composants.getNumeroUnique()}" />
        <div id="${id}" class="message_appli message_succes">
            <@s.actionmessage />
        </div>
        <@composants.scriptDisparition "#${id}" />
    </#if>
</#macro>

<#-- Affiche un script qui fait disparaite des éléments après une courte période -->
<#macro scriptDisparition selecteurElements>
    <script type="text/javascript">
        setTimeout("$('${selecteurElements}').fadeOut('slow');", 5000);
    </script>
</#macro>

<#-- Affiche les messages d'avertissements en orange. -->
<#macro messagesAvertissement message>
    <div class="message_appli message_avertissement">
        ${message?html}
    </div>
</#macro>

<#-- Affiche les messages d'erreurs en rouge. -->
<#macro messagesErreur>
    <#if (actionErrors?? && actionErrors?size > 0)>
        <div class="message_appli message_erreur">
            <@s.actionerror />
        </div>
    </#if>
</#macro>

<#-- Affiche les messages d'information en gris. -->
<#macro messagesInfo>
    <div class="message_appli message_info">
        <#nested>
    </div>
</#macro>

<#-- Affiche le texte riche, et gère les url d'upload d'images. -->
<#macro texteRicheEpreuve epreuveId id name lectureSeule class valeur="">
    <@s.url namespace="/banque/epreuve" action="${epreuveId}/images/selection" var="urlImageBrowse" />
    <@s.url namespace="/banque/epreuve" action="${epreuveId}/documents/upload" var="urlImageUpload" />
    <@texteRiche id name lectureSeule class valeur "standard" urlImageBrowse urlImageUpload />
</#macro>

<#-- Affiche une zone de texte riche -->
<#-- La configuration du texte riche est placée dans le corps de la macro. -->
<#macro texteRiche id name lectureSeule class valeur="" toolbar="standard" urlImageBrowse="" urlImageUpload="">
    <#assign css><#if class="">texte_riche largeur800<#else>${class}</#if></#assign>
    <@s.textarea id="${id}" name="${name}" readonly=lectureSeule cssClass=css value=valeur />
    <script type="text/javascript">
        CKEDITOR.replace( '${id}', {
                filebrowserWindowWidth : '750',
                filebrowserWindowHeight : '250',
                <#if urlImageBrowse != "">
                    filebrowserImageBrowseUrl : '${urlImageBrowse}',
                </#if>
                <#if urlImageUpload != "">
                    filebrowserImageUploadUrl : '${urlImageUpload}',
                </#if>
                fullPage: false,
                <@toolbarCkeditor toolbar />
                allowedContent: true
                <#if lectureSeule>
                    , readOnly: true
                </#if>
        });
    </script>
</#macro>

<#-- Configure la toolbar ckeditor. -->
<#macro toolbarCkeditor type>
    height : '150px',
    <#if type="minimaliste">
        toolbar : [
            ['Bold', 'Italic', 'Underline'],
            ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
            ['Table']
        ],
    </#if>
</#macro>

<#-- Affiche un texte HTML sans le style de l'application -->
<#macro texteSansStyle texte class="" renduExport=false>
    <#-- l'attribut "srcdoc" n'est pas valide en XHTML et ne devrait pas être utilisé -->
    <#-- A la place, il faudrait utiliser l'attribut "src" et avoir une URL valide qui renvoie le texte de l'iframe -->
    <iframe srcdoc="<#rt>
            <#if renduExport>                
                <#assign texteRenduExport>
                    <div style="font-family : helvetica, arial, sans-serif; font-size : 11px;"><#t>
                        ${texte}<#t>
                    </div><#t>
                </#assign>
                ${texteRenduExport?html}<#t>
            <#else>
                ${texte?html}<#t>
            </#if>" <#rt>
            <#if class != "">class="${class}"</#if><#rt>
           ><#rt>
    </iframe><#t>
</#macro>

<#-- Obtient un numéro unique basée sur l'heure actuelle -->
<#function getNumeroUnique>
    <#-- Nombre de secondes depuis 1er janvier 1970 -->
    <#return .now?long />
</#function>

<#-- Obtient un numéro unique -->
<#-- Le premier numéro retourné est 1 -->
<#-- Au deuxième appel de la méthode, ce sera 2, puis 3, etc. -->
<#function getNouveauNumero>
    <#if !.globals.numeroCourant?has_content>
        <#-- Valeur initiale renvoyee -->
        <#global numeroCourant = 1>
    </#if>
    <#assign nouveauNumero = .globals.numeroCourant>
    <#global numeroCourant = .globals.numeroCourant + 1>
    <#return nouveauNumero />
</#function>