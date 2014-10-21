//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 07/01/2014
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------

var popups = new Array();

/**
 * Crée une nouvelle instance de popup utilisée dans l'application.
 * La popup créée initialise son contenu à partir d'un appel Ajax retournant le code HTML du contenu de la popup.
 * @param {String} identifiant Le nom unique de la popup.
 * @param {String} selecteurFormulaireCreation Le sélecteur jQuery du formulaire utilisé pour obtenir le contenu de la popup.
 * @param {Function} actionValidation La méthode exécutée lorsque la popup est validée. 
 *                                    Permet d'exécuter une action quelconque à la fermeture de la popup.
 * @returns {Popup}
 */
function Popup(identifiant, selecteurFormulaireCreation, actionValidation) {
    this.setParametres(identifiant, selecteurFormulaireCreation, actionValidation);
}

Popup.prototype.setParametres = function(identifiant, selecteurFormulaireCreation, actionValidation) {
    this.identifiant = identifiant;
    this.selecteurFormulaireCreation = selecteurFormulaireCreation;
    this.actionAvantOuverture = null;
    this.actionValidation = actionValidation;
    this.hauteur = "auto";
    this.largeur = 500;
    this.titre = '';
    this.modal = true;
};

Popup.prototype.creerPopupDiv = function() {
    $("<div />").attr("id", 'popup_' + this.identifiant).appendTo("body");
};

Popup.prototype.getPopupDiv = function() {
    return $('#popup_' + this.identifiant);
};

/**
 * Ouvre la popup. 
 */
Popup.prototype.ouvrir = function() {
    // Si une action avant ouverture est définie
    if (this.actionAvantOuverture != null) {
        // Appeler cette action (pré - traitement)
        this.actionAvantOuverture();
    }

    // Ajouter la popup au tableau global
    popups[this.identifiant] = this;

    // crée/Obtient une div dédiée à la popup
    this.creerPopupDiv();

    // Appelle l'action du formulaire d'ouverture de la popup afin de remplir celle-ci en Ajax.
    var url = $(this.selecteurFormulaireCreation).attr('action');
    var methode = $(this.selecteurFormulaireCreation).attr('method');

    // ouvre la popup avec jquery Dialog (en modal)
    var popup = this;
    xhr(url, methode, false, this.selecteurFormulaireCreation, function(resultatAjax) {
        popup.getPopupDiv().html(resultatAjax.htmls[0]);
        popup.getPopupDiv().dialog({
            "title": popup.titre,
            "width": popup.largeur,
            "height": popup.hauteur,
            "modal": popup.modal,
            "close": function(event) {
                // Détruire complétement la popup quand on la ferme, et supprimer son contenu HTML.
                popup.getPopupDiv().dialog("destroy");
                popup.getPopupDiv().remove();

                if (popups.hasOwnProperty(popup.identifiant)) {
                    // Supprimer popup du tableau global
                    delete popups[popup.identifiant];
                }
            }
        });
    });
};

/**
 * Ferme la popup.
 */
Popup.prototype.fermer = function() {
    // Ferme la popup 
    var popupDiv = this.getPopupDiv();
    popupDiv.closest('.ui-dialog-content').dialog('close');
};

/**
 * Exécute l'action de validation de la popup.
 * @param {Object} paramValidation Les paramètres à utiliser pour appeler la méthode de validation de la popup. 
 */
Popup.prototype.executerActionValidation = function(paramValidation) {
    this.actionValidation(paramValidation);
};

/**
 * Obtient une popup quelconque, à partir de son identifiant.
 * @param {String} identifiant L'identifiant unique de la popup.
 * @returns {Popup} La popup trouvée, ou null sinon.
 */
function getPopup(identifiant) {
    if (popups.hasOwnProperty(identifiant)) {
        return popups[identifiant];
    }
    else {
        return null;
    }
}

/**
 * Définit la largeur de la popup.
 * @param {type} largeur la largeur de la popup.
 * @returns {undefined}
 */
Popup.prototype.setLargeur = function(largeur) {
    this.largeur = largeur;
};

/**
 * Définit la hauteur de la popup.
 * @param {type} hauteur la hauteur de la popup.
 * @returns {undefined}
 */
Popup.prototype.setHauteur = function(hauteur) {
    this.hauteur = hauteur;
};

/**
 * Définit le titre de la popup.
 * @param {type} titre le titre de la popup.
 * @returns {undefined}
 */
Popup.prototype.setTitre = function(titre) {
    this.titre = titre;
};

// Héritage de PopupImpression avec comme classe mère Popup.
PopupImpression.prototype = new Popup();

/**
 * Crée une nouvelle instance de PopupImpression.
 * @param {String} idPopupImpression L'identifiant de la popup d'impression.
 * @param {String} actionValidation La méthode exécutée lorsque la popup est validée. La méthode prend en paramètre un objet
 * avec la propriété suivante :
 *      "noirEtBlanc" : vrai si l'utilisateur a choisi d'imprimer en noir et blanc, faux sinon.
 * @returns {PopupImpression} La popup d'impression.
 */
function PopupImpression(idPopupImpression, actionValidation) {
    // Le formulaire de création de la popup d'imprimante se trouve sur toutes les pages (voir layout.ftl).
    this.setParametres(idPopupImpression, "#choix_imprimante_form", actionValidation);
    this.selecteurMessageSucces = "#popup_impression .message_succes";
    this.selecteurMessageErreur = "#popup_impression .message_erreur";
    this.selecteurMessageInfo = "#popup_impression .message_info";
    this.actionAvantOuverture = function() {
        // Avant l'ouverture de la popup, on renseigne le champ de l'identifiant de la popup dans le formulaire de création de la popup.
        $("#choix_imprimante_form input[name='identifiant']").val(idPopupImpression);
    };
}
;

/**
 * Affiche le message d'information.
 * @param {String} message Le message de succès.
 */
PopupImpression.prototype.afficheMessageInfo = function(message) {
    $(this.selecteurMessageErreur).hide();
    $(this.selecteurMessageSucces).hide();
    $(this.selecteurMessageInfo).show();
    $(this.selecteurMessageInfo).html(message);
    this.cacherBoutonValider();
};

/**
 * Affiche le message de succès d'impression.
 * @param {String} message Le message de succès.
 */
PopupImpression.prototype.afficheMessageSucces = function(message) {
    $(this.selecteurMessageInfo).hide();
    $(this.selecteurMessageErreur).hide();
    $(this.selecteurMessageSucces).show();
    $(this.selecteurMessageSucces).html(message);
    this.cacherBoutonValider();
};

/**
 * Cache le bouton de validation, après avoir lancé l'impression.
 */
PopupImpression.prototype.cacherBoutonValider = function() {
    $("#id_bouton3d_valider_impression").hide();
};

/**
 * Affiche le message d'échec de l'impression.
 * @param {String} message Le message d'échec de l'impression.
 */
PopupImpression.prototype.afficheMessageErreur = function(message) {
    $(this.selecteurMessageInfo).hide();
    $(this.selecteurMessageSucces).hide();
    $(this.selecteurMessageErreur).show();
    $(this.selecteurMessageErreur).html(message);
};

/**
 * Cache l'affichage des messages de succès et d'erreur.
 */
PopupImpression.prototype.cacherMessages = function() {
    $(this.selecteurMessageInfo).hide();
    $(this.selecteurMessageSucces).hide();
    $(this.selecteurMessageErreur).hide();
};
