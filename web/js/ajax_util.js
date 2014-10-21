//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 16/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

/**
 * Effectue un appel Ajax avec jQuery.
 * @param {String} url L'URL utilisé pour effectuer l'appel Ajax.
 * @param {String} methode La méthode HTTP utilisé : 'GET' ou 'POST'. Par défaut, 'GET.
 * @param {Boolean} async Indique si l'appel doit être asynchrone ou non. Par défaut, oui (true).
 * @param {String} selecteurForm La chaine du sélecteur jQuery du formulaire. Par exemple "#monformulaire".
 * @param {Function} enCasDeSucces La fonction à appeler en cas de succès de l'appel Ajax. Par défaut, rien n'est effectué.
 * @param {Function} enCasDErreur La fonction à appeler en cas d'erreur de l'appel Ajax. 
 *                                  Par défaut, affichage d'une popup avec le détail de l'erreur.
 * @returns {Object} L'objet jQuery Ajax.
 */
function xhr(url, methode, async, selecteurForm, enCasDeSucces, enCasDErreur) {
    
    
    var donneesEnvoyees = (selecteurForm != null) ? $(selecteurForm).serialize() : null;
    methode = (methode != null && methode.toUpperCase() == "POST") ? "POST" : "GET";
    async = (async === null) ? false : async;
    var xhr = $.ajax({
            "type"      : methode,
            "dataType"  : "json",
            "url"       : url,
            "async"     : async,
            "data"      : donneesEnvoyees
        })
        .done(function(data) {
            if ((enCasDeSucces != null) && (typeof(enCasDeSucces) == 'function')) {
                // Appel du callback d'erreur personnalisé
                enCasDeSucces(data);
            }
        })
        .fail(function(data) {
            if ((enCasDErreur != null) && (typeof(enCasDErreur) == 'function')) {
                // Appel du callback d'erreur personnalisé
                enCasDErreur(data);
            }
            else {
                traiterErreurAjax(data);
            }
        });
        
    return xhr;
}

/**
 * Définit le mode de soumissions d'un formulaire en Ajax : sur le click d'un bouton, on soumet le formulaire.
 * 
 * @param {String} selecteurForm La chaine du sélecteur jQuery du formulaire. Par exemple "#monformulaire".
 * @param {Boolean} async Indique si l'appel doit être asynchrone ou non. Par défaut, oui (true).
 * @param {Function} enCasDeSucces Facultatitf :  La fonction à appeler en cas de succès de l'appel Ajax.
 * @param {Function} enCasDErreur Facultatitf : La fonction à appeler en cas d'erreur de l'appel Ajax. 
 *                                  Par défaut, affichage d'une popup avec le détail de l'erreur.
 * @param {Function} preTraitement Facultatitf : une fonction appelée juste avant d'exécuter l'appel Ajax sur le formulaire.
 */
function xhrForm(selecteurForm, async, enCasDeSucces, enCasDErreur, preTraitement) {
    $(selecteurForm).on('submit' , function(event) {
        var url = $(selecteurForm).attr('action');
        var methode = $(selecteurForm).attr('method');
        var executionrXhr = true;
        if (preTraitement != null && (typeof(preTraitement) == 'function')) {
            executionrXhr = preTraitement();
        }
        
        if (executionrXhr) {
            // Appel Ajax
            xhr(url, methode, async, selecteurForm, enCasDeSucces, enCasDErreur);
        }
        
        // Empêcher le chargement d'une nouvelle page
        event.preventDefault();
    });
}

/**
 * Traite une erreur qui survient lors d'un appel Ajax : affiche l'erreur dans une popup.
 * @param {Object} data Les données renvoyées par le serveur au moment de l'appel Ajax.
 */
function traiterErreurAjax(data) {
    if (data != null && data.responseText != null) {
        var contenuHtml = data.responseText;
        var indexDebutBody = data.responseText.indexOf("<body>");
        var indexFinBody = data.responseText.indexOf("</body>");
        if (indexDebutBody != -1 &&  indexFinBody != -1) {
            // Le corps de la réponse contient la balise HTML, le serveur a renvoyé une page entière.
            // On va extraire le HTML contenu dans le "body"
            contenuHtml = data.responseText.substring(indexDebutBody + 6, indexFinBody);
        }
       
        // Mettre le détail de l'erreur dans une div dédié à cette effet, présente sur toutes les pages
        $("#popup_erreur").html(contenuHtml);
        // Afficher le contenu de l'erreur dans une popup
        $("#popup_erreur").dialog({
            "title" : "Résultat serveur invalide",
            "width" : 900
        });
    }
    else {
        alert("Erreur serveur inconnue");
    }
}

/**
 * Insère du HTML, renvoyé par un appel Ajax, dans une balise quelconque de la page.
 * @param {String} selecteur Le sélecteur de la balise, dans la page.
 * @param {Object} resultatAjax Le résultat Ajax du serveur
 *                              Objet javascript obtenu à partir du JSON de la classe Java "ResultatAjax".
 * @param {Integer} position Facultatif : La position du HTML dans le tableau des chaines de caractères HTML de l'objet resultatAjax. 
 *                                  0 par défaut.
 * @param {Boolean} effetApparition Facultatif : si le HTML doit apparaître avec un effet d'apparition. 
 *                                  Faux par défaut.
 */
function insererHtmlAjax(selecteur, resultatAjax, position, effetApparition) {
    position = (position == null) ? 0 : position;
    if (resultatAjax != null && resultatAjax.htmls != null && resultatAjax.htmls[position] != null) {
        if (effetApparition) {
            $(selecteur).hide();
            $(selecteur).html(resultatAjax.htmls[position]);
            $(selecteur).fadeIn("fast");
        }
        else {
            $(selecteur).html(resultatAjax.htmls[position]);
        }
    }
}

/**
 * Remplace la balise dont le sélecteur est passé en paramètre, par le contenu html issu du réultat de l'appel Ajax.
 * @param {String} selecteur Le sélecteur de la balise, dans la page.
 * @param {Object} resultatAjax Le résultat Ajax du serveur
 *                              Objet javascript obtenu à partir du JSON de la classe Java "ResultatAjax".
 * @param {Integer} position La position du HTML dans le tableau des chaines de caractères HTML de l'objet resultatAjax. 
 *                           0 par défaut.
 * @param {Boolean} effetApparition Facultatif : si le HTML doit apparaître avec un effet d'apparition. 
 *                                  Faux par défaut.
 */
function remplacerHtmlAjax(selecteur, resultatAjax, position, effetApparition) {
    position = (position == null) ? 0 : position;
    if (resultatAjax != null && resultatAjax.htmls != null && resultatAjax.htmls[position] != null) {
        if (effetApparition) {
            $(selecteur).css("visibility", "hidden");
            $(selecteur).replaceWith(resultatAjax.htmls[position]);
            $(selecteur).fadeIn("fast");
        }
        else {
            $(selecteur).replaceWith(resultatAjax.htmls[position]);
        }
    }
}

/**
 * Annule l'évènement "submit" associé à un formulaire.
 * @param {String} selecteurForm Le sélecteur du formulaire.
 */
function annulerXhrForm(selecteurForm) {
    $(selecteurForm).unbind("submit");
}
