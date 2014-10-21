/**
 * Initialise un objet TableauPaginationTri permettant de facilement trier et paginer un résultat de recherche.
 * Le principe est de soumettre un formulaire lorsque l'utilisateur clique sur une colonne à trier, ou un numéro de page.
 * On modifie des inputs dans un formulaires avant de soumettre le formulaire.
 * 
 * @param {String} selecteurForm Le sélecteur du formulaire de recherche.
 * @param {Array} selecteursColonnes L'ensemble des sélecteurs jQuery de chaque colonne triable (un sélecteur par colonne)
 * @param {Array} nomsColonnes L'ensemble des noms des colonnes triables
 * @param {String} selecteurPagination Le sélecteur de la div où se trouvent les liens des pages.
 */
function TableauPaginationTri(selecteurForm, selecteursColonnes, nomsColonnes, selecteurPagination) {
    this.selecteurForm = selecteurForm;
    this.selecteursColonnes = selecteursColonnes;
    this.nomsColonnes = nomsColonnes;
    this.selecteurInputColonneTri = selecteurForm + ' input[name="model.criteres.colonneTri"]';
    this.selecteurInputAscendant = selecteurForm + ' input[name="model.criteres.ascendant"]';
    this.selecteurPagination = selecteurPagination;
    this.selecteurPages = selecteurPagination + ' .lien_tri';
    this.selecteurInputPage = selecteurForm + ' input[name="model.criteres.page"]';
}

/**
 * Soumet le formulaire de recherche.
 */
TableauPaginationTri.prototype.soumettreFormulaire = function() {
    $(this.selecteurForm).submit();
};

/**
 * Retourne le nom de la colonne actuellement triée.
 * @returns Le nom de la colonne actuellement triée.
 */
TableauPaginationTri.prototype.getNomColonneTri = function() {
    return $(this.selecteurInputColonneTri).val();
};

TableauPaginationTri.prototype.setSelecteurColonneTri = function(selecteurColonneTri) {
    this.selecteurInputColonneTri = selecteurColonneTri;
};

TableauPaginationTri.prototype.setSelecteurOrdreTri = function(selecteurOrdreTri) {
    this.selecteurInputAscendant = selecteurOrdreTri;
};

TableauPaginationTri.prototype.setSelecteurInputPage = function(selecteurInputPage) {
    this.selecteurInputPage = this.selecteurForm + " " + selecteurInputPage;
};



/**
 * Retourne le sélecteur de la colonne actuellement triée, étant donné un nom de colonne.
 * Retourne null si aucune colonne n'est marquée comme triée.
 * @returns {unresolved}
 */
TableauPaginationTri.prototype.getSelecteurColonneTri = function() {
    var nomColonneTriCourante = this.getNomColonneTri();
    var selecteur = null;
    if (nomColonneTriCourante && nomColonneTriCourante.length > 0) {
        for (var i = 0; i < this.selecteursColonnes.length; i++) {
            if (this.nomsColonnes[i] == nomColonneTriCourante) {
                selecteur = this.selecteursColonnes[i];
                break;
            }
        }
    }

    return selecteur;
};

/**
 * Retourne l'ordre de tri actuel.
 * @returns 'asc' si le tri ascendant, 'desc' sinon. Par défaut, le tri est supposé ascendant.
 */
TableauPaginationTri.prototype.getOrdreTri = function() {
    var ascendant = $(this.selecteurInputAscendant).val();
    var ordreTri = (ascendant == null || ascendant === "true") ? "asc" : "desc";
    return ordreTri;
};

/**
 * Définit un nouvel ordre de tri.
 * @param {String} ordreTri Le nouvel ordre de tri : 'asc' ou 'desc'.
 */
TableauPaginationTri.prototype.setOrdreTri = function(ordreTri) {
    if (ordreTri == 'desc') {
        $(this.selecteurInputAscendant).val("false");
    }
    else {
        $(this.selecteurInputAscendant).val("true");
    }
};

/**
 * Inverse l'ordre actuel du tri.
 */
TableauPaginationTri.prototype.inverserOrdreTri = function() {
    if (this.getOrdreTri() == 'asc') {
        this.setOrdreTri('desc');
    }
    else {
        this.setOrdreTri('asc');
    }
};

/**
 * Change le tri défini dans le formulaire.
 * Inverse également l'ordre du tri si l'on retrie sur la même colonne qu'actuellement.
 * @param {String} colonneTri Le nom de la nouvelle colonne à trier.
 */
TableauPaginationTri.prototype.setTriFormulaire = function(colonneTri) {
    var ancienneColonneTri = this.getNomColonneTri();
    if (ancienneColonneTri == colonneTri) {
        this.inverserOrdreTri();
    }
    else {
        this.setOrdreTri('asc');
    }

    $(this.selecteurInputColonneTri).val(colonneTri);
};

/**
 * Ajoute du style à la colonne actuellement triée.
 */
TableauPaginationTri.prototype.setStyleColonneTri = function() {
    var selecteurColonneTri = this.getSelecteurColonneTri();
    if (selecteurColonneTri != null) {
        if (this.getOrdreTri() == "asc") {
            $(selecteurColonneTri).addClass("tri_ascendant");
        }
        else {
            $(selecteurColonneTri).addClass("tri_descendant");
        }
    }
};

/**
 * Définit un numéro de page dans le champ input du numéro de page.
 * @param {Integer} numeroPage Le numéro de la page.
 */
TableauPaginationTri.prototype.setNumeroPage = function(numeroPage) {
    $(this.selecteurInputPage).val(numeroPage);
};

/**
 * Initialise le tableau des résultats du tri/pagination.
 */
TableauPaginationTri.prototype.init = function() {
    var paginationTri = this; // Garder une référence vers l'objet courant dans les handlers d'événement
    
    // Initialisation des colonnes triables
    if (this.selecteursColonnes != null) {
        for (var i = 0; i < this.selecteursColonnes.length; i++) {
            var selecteurColonne = this.selecteursColonnes[i];
            var nomColonne = this.nomsColonnes[i];
            var eventData = {
                "nomColonne": nomColonne
            };

            // Style par défaut de toutes les colonnes triables
            $(selecteurColonne).addClass("triable");

            // Evénement de tri lorsque l'on soumet le formulaire.
            $(selecteurColonne).click(eventData, function(event) {
                paginationTri.setTriFormulaire(event.data.nomColonne);
                paginationTri.setNumeroPage(1); // Lors d'un tri, on revient toujours à la première page
                paginationTri.soumettreFormulaire();
            });
        }
    }

    // Initialisation de la pagination
    if (this.selecteurPagination != null) {
        $(this.selecteurPages).each(function() {
            // Evénement de changement de page lorsque l'on soumet le formulaire.
            $(this).click(function() {
                var numeroPage = $(this).data("numero_page");
                paginationTri.setNumeroPage(numeroPage);
                paginationTri.soumettreFormulaire();
            });
        });
    }

    // Mettre du style sur la colonne actuellement triée
    this.setStyleColonneTri();
};

/*----------------------------------------------------------------------------------------*/
/*---------------------- ACTIONS GLOBALES SUR UN TABLEAU ---------------------------------*/
/*----------------------------------------------------------------------------------------*/

/**
 * Initialise un objet TableauActionGlobal permettant d'appliquer des actions communnes à plusieurs lignes de résultats.
 * Le principe est de soumettre un formulaire lorsque l'utilisateur clique sur un bouton d'action.
 * On ajoute/retire des inputs hidden dans un formulaire, lorsque l'utilisateur coche/décoche des cases à cocher.
 * 
 * @param {String} selecteurForm Le sélecteur du formulaire d'action.
 * @param {String} selecteurCasesACocher Le sélecteur des cases à cocher du tableau.
 * @param {String} selecteurLienAction Facultatif : le sélecteur du lien permettant d'effectuer l'action sur le formulaire. Si non renseigné,
 *                                     le formulaire doit être soumis "manuellement" avec la méthode "soumettreFormulaire".
 * @param {String} nomInputGenere Le nom de l'input ajouté/retiré dans le formulaire
 * @param {String} messageAlerte Le message d'alerte avant soumission du formulaire.
 * @param {String} messageAucunElement Le message affiché si aucun élément n'est sélectionné lors de la soumission du formulaire.
 */
function TableauActionGlobal(selecteurForm, selecteurCasesACocher, selecteurLienAction, nomInputGenere, messageAlerte, messageAucunElement) {
    this.selecteurForm = selecteurForm;
    this.selecteurCasesACocher = selecteurCasesACocher;
    this.selecteurLienAction = selecteurLienAction;
    this.nomInputGenere = nomInputGenere;
    this.messageAlerte = messageAlerte;
    this.messageAucunElement = messageAucunElement;
    
    this.nomDonneeVerification = null;
    this.messageActionImpossible = null;
    this.fonctionApresSelectionCheckbox = null;
    this.fonctionApresDeselectionCheckbox = null;
}

/**
 * Définit une vérification de l'action à effectuer, avant soumission du formulaire.
 * Chaque case à cocher doit avoir une données de nom "nomDonneeVerification" qui est à false si l'action est imposible pour
 * la ligne, et vrai si l'action est possible. Il faut utiliser jQuery.data() sur chaque case à cocher.
 * Exemple : $("selecteurCheckBox").data(nomDonneeVerification, false);
 *           ===> Indique que la case à cocher n'est pas valide pour effectuer l'action.
 * @param {String} nomDonneeVerification Le nom de la donnée à vérifier (nom utilisé avec jQuery.data()).
 * @param {String} messageActionImpossible Le message à afficher si l'action n'est pas possible, lors de la soumission du formulaire.
 */
TableauActionGlobal.prototype.setVerificationAction = function(nomDonneeVerification, messageActionImpossible) {
    this.nomDonneeVerification = nomDonneeVerification;
    this.messageActionImpossible = messageActionImpossible;
};

/**
 * Vérifie si l'action à effectuer est possible, selon les cases qui sont cochées.
 * @returns Vrai si l'action est possible, faux sinon.
 */
TableauActionGlobal.prototype.verifierActionValide = function() {
    var resultat = true;
    if (this.nomDonneeVerification == null) {
        resultat = true;
    }
    else {
        var tableauActionGlobal = this;
        $(this.selecteurCasesACocher).each(function() {
            var parametre = $(this).data(tableauActionGlobal.nomDonneeVerification);
            if ($(this).prop("checked") && parametre == "false") {
                resultat = false;
            }
        });
    }
    
    return resultat;
};

/**
 * Vérifie s'il existe une occurence de l'input généré.
 * @returns Vrai s'il existe une occurence de l'input généré, Faux sinon.
 */
TableauActionGlobal.prototype.existeOccurenceInputGenere = function() {
    var nombreOccurences = $(this.selecteurForm + " :input[name='" + this.nomInputGenere + "']").length;
    return nombreOccurences > 0;
};

/**
 * Soumet le formulaire correspondant à l'action courante.
 */
TableauActionGlobal.prototype.soumettreFormulaire = function() {
    if (!this.existeOccurenceInputGenere() || this.formulaireVide()) {
        alert(this.messageAucunElement);
    }
    else {
        if (!this.verifierActionValide()) {
            alert(this.messageActionImpossible);
        }
        else if (!this.messageAlerte || confirm(this.messageAlerte)) {
            $(this.selecteurForm).submit();
        }
    }
};

/**
 * Ajoute au formulaire caché, l'input d'identifiant passé en paramètre.
 * @param {int} valeur La valeur de l'input correspondant.
 */
TableauActionGlobal.prototype.ajouterIdFormulaire = function(valeur) {
    var inputHidden = '<input name="' + this.nomInputGenere + '" type="hidden" value="' + valeur + '"/>';
    $(this.selecteurForm).append(inputHidden);
};

/**
 * Ajoute au formulaire caché, les inputs et valeurs relatives passées en paramètre.
 * @param {Map} valeurs L'ensemble des inputs et valeurs correspondantes. La map est définie comme suit :
 *              - En clé : le nom de l'input
 *              - En valeur : la valeur associée au nom de l'input.
 */
TableauActionGlobal.prototype.ajouterValeursFormulaire = function(valeurs) {
    for (var nomInput in valeurs) {
        var inputHidden = '<input name="' + nomInput + '" type="hidden" value="' + valeurs[nomInput] + '"/>';
        $(this.selecteurForm).append(inputHidden);
    }
};

/**
 * Retire du formulaire caché, l'input d'identifiant passé en paramètre.
 * @param {int} valeur La valeur de l'input correspondant.
 */
TableauActionGlobal.prototype.retirerIdFormulaire = function(valeur) {
    $(this.selecteurForm + ' input[name="' + this.nomInputGenere + '"][value="' + valeur + '"]').remove();
};

/**
 * Retire du formulaire caché, les inputs passés en paramètre.
 * @param {Map} valeurs L'ensemble des inputs et valeurs correspondantes. La map est définie comme suit :
 *              - En clé : le nom de l'input
 *              - En valeur : la valeur associée au nom de l'input.
 */
TableauActionGlobal.prototype.retirerValeursFormulaire = function(valeurs) {
    for (var nomInput in valeurs) {
        $(this.selecteurForm + ' input[name="' + nomInput + '"][value="' + valeurs[nomInput] + '"]').remove();
    }
};

/**
 * Teste si le formulaire est vide.
 */
TableauActionGlobal.prototype.formulaireVide = function() {
    if($(this.selecteurForm+" > input").size() === 0) {
        return true;
    }
    return false;
};

/**
 * Initialise les éléments et actions de sélection mutilple.
 * @returns {undefined}
 */
TableauActionGlobal.prototype.init = function() {
    var tableauActionGlobal = this;

    $(this.selecteurCasesACocher).each(function() {
       $(this).change(function() {
            var checkbox = $(this);
            var valeur = checkbox.val();
            if (checkbox.prop("checked")) {
                tableauActionGlobal.ajouterIdFormulaire(valeur);
                if (tableauActionGlobal.fonctionApresSelectionCheckbox != null) {
                    tableauActionGlobal.fonctionApresSelectionCheckbox(checkbox, tableauActionGlobal);
                }
            }
            else {
                tableauActionGlobal.retirerIdFormulaire(valeur);
                if (tableauActionGlobal.fonctionApresDeselectionCheckbox != null) {
                    tableauActionGlobal.fonctionApresDeselectionCheckbox(checkbox, tableauActionGlobal);
                }
            }
        }); 
    });
    
    if (this.selecteurLienAction != null) {
        $(this.selecteurLienAction).click(function() {
            tableauActionGlobal.soumettreFormulaire();
        });
    }
};


/*----------------------------------------------------------------------------------------*/
/*----------------------- ACTIONS SIMPLES SUR UN TABLEAU ---------------------------------*/
/*----------------------------------------------------------------------------------------*/

/**
 * Initialise un objet TableauActionSimple permettant d'effectuer une action spécifique à une ligne de résultat.
 * Le principe est de renseigner la valeur de l'input dans le formulaire caché et de le soumettre.
 * 
 * @param {String} selecteurForm Le sélecteur du formulaire d'action.
 * @param {String} nomInput Le nom de l'input du formulaire caché.
 * @param {String} selecteurBouton Le sélecteur du bouton.
 * @param {String} messageAlerte Le message d'alerte avant soumission du formulaire.
 */
function TableauActionSimple(selecteurForm, nomInput, selecteurBouton, messageAlerte) {
    this.selecteurForm = selecteurForm;
    this.nomInput = nomInput;
    this.selecteurBouton = selecteurBouton;
    this.messageAlerte = messageAlerte;
    
    this.fonctionAvantSoumissionFormulaire = null;
}

/**
 * Soumet le formulaire correspondant à une action spécifique d'une ligne de résultat.
 */
TableauActionSimple.prototype.soumettreFormulaire = function() {
    if(this.messageAlerte == null || confirm(this.messageAlerte)) {
        $(this.selecteurForm).submit();
    }
};

/**
 * Définit l'identifiant de l'objet impacté par l'action à envoyer via le formulaire.
 * @param {Integer} id
 */
TableauActionSimple.prototype.renseignerId = function(id) {
    $(this.selecteurForm + " input[name='" + this.nomInput + "']").val(id);
};

/**
 * Définit les valeurs des objets impactés par l'action à envoyer via le formulaire.
 * @param {Array} valeurs La liste des valeurs d'inputs. La map est définie comme suit :
 *              - En clé : le nom de l'input
 *              - En valeur : la valeur associée au nom de l'input.
 */
TableauActionSimple.prototype.renseignerValeurs = function(valeurs) {
    for (var nomInput in valeurs) {
        $(this.selecteurForm).append("<input name='" + nomInput + "' type='hidden' value='" + valeurs[nomInput] + "' />");
    }
};

/**
 * Initialise chaque bouton d'action correspondant à une ligne de résultat.
 * @returns {undefined}
 */
TableauActionSimple.prototype.init = function() {
    var tableauActionSimple = this;
    $(this.selecteurBouton).each(function() {
        $(this).click(function() {
            tableauActionSimple.renseignerId($(this).data("id"));
            if (tableauActionSimple.fonctionAvantSoumissionFormulaire != null) {
                tableauActionSimple.fonctionAvantSoumissionFormulaire($(this), tableauActionSimple);
            }
            
            tableauActionSimple.soumettreFormulaire();
        });
    });
};

/*----------------------------------------------------------------------------------------*/
/*----------------------- ACTIONS POPUP SUR UN TABLEAU ---------------------------------*/
/*----------------------------------------------------------------------------------------*/

/**
 * Initialise un objet TableauActionPopup permettant d'ouvrir une popup d'une ligne de résultat.
 * Le principe est de renseigner la valeur de l'identifiant d'une ligne dans un formulaire, puis d'ouvrir la popup.
 * @param {String} selecteurForm Le sélecteur du formulaire dans lequel renseigner l'identifiant de la ligne (généralement, c'est aussi 
 *                               le formulaire qui ouvre la popup).
 * @param {String} nomInput Le nom de l'input à renseigner dans le formulaire (valeur contenant généralement l'identifiant de la ligne).
 * @param {String} selecteurBouton Le sélecteur des boutons de chaque ligne, sur lesquels on déclenche l'ouverture de la popup.
 * @param {Popup} popup La popup qui va s'ouvrir lors du clic sur un des boutons.
 * @returns {TableauActionPopup}
 */
function TableauActionPopup(selecteurForm, nomInput, selecteurBouton, popup) {
    this.selecteurForm = selecteurForm;
    this.nomInput = nomInput;
    this.selecteurBouton = selecteurBouton;
    this.popup = popup;
    this.fonctionAvantSoumissionFormulaire = null;
}

/**
 * Définit l'identifiant de l'objet impacté par l'action à envoyer via le formulaire.
 * @param {Integer} id L'identifiant de l'objet impacté par l'action à envoyer via le formulaire
 */
TableauActionPopup.prototype.renseignerId = function(id) {
    $(this.selecteurForm + " input[name='" + this.nomInput + "']").val(id);
};

/**
 * Définit les valeurs d'inputs à renseigner dans le conteneur "<span></span>" du formulaire.
 * @param {Array} valeurs La liste des valeurs d'inputs. La map est définie comme suit :
 *              - En clé : le nom de l'input
 *              - En valeur : la valeur associée au nom de l'input.
 */
TableauActionPopup.prototype.renseignerValeursDansSpan = function(valeurs) {
    $(this.selecteurForm + " span :input").remove();
    for (var nomInput in valeurs) {
        $(this.selecteurForm + " span").append("<input name='" + nomInput + "' type='hidden' value='" + valeurs[nomInput] + "' />");
    }
};

/**
 * Ouvre la popup correspondant à la ligne du tableau cliquée.
 */
TableauActionPopup.prototype.ouvrirPopup = function() {
    this.popup.ouvrir();
};

/**
 * Initialise chaque bouton d'action correspondant à une ligne de résultat.
 */
TableauActionPopup.prototype.init = function() {
    var tableauActionPopup = this;
    $(this.selecteurBouton).each(function() {
        $(this).click(function() {
            tableauActionPopup.renseignerId($(this).data("id"));
            
            if (tableauActionPopup.fonctionAvantSoumissionFormulaire != null) {
                tableauActionPopup.fonctionAvantSoumissionFormulaire($(this), tableauActionPopup);
            }
            
            tableauActionPopup.ouvrirPopup();
        });
    });
};

/*----------------------------------------------------------------------------------------*/
/*----------------------- ACTIONS DYNAMIQUES SUR UN TABLEAU ------------------------------*/
/*----------------------------------------------------------------------------------------*/

/**
 * Initialise un objet TableauLignesDynamiques permettant de remplir dynamiquement une table HTML.
 * 
 * @param {String} selecteurTable Le sélecteur de la table HTML.
 * @param {String} modelNouvelElement Le modèle HTML de l'élément à créer. Le modèle peut être variable avec des chaines délimitées
 *                 par "%". Le modèle ne contient PAS les balises "<tr>", mais uniquement le contenu de la ligne, délimité par des 
 *                 balises "<td>".
 */
function TableauLignesDynamiques(selecteurTable, modelNouvelElement) {
    this.selecteurTable = selecteurTable;
    this.modelNouvelElement = modelNouvelElement;
    
    // Sélecteur permettant de récupérer toutes les lignes de la table HTML
    this.selecteurLignes = selecteurTable + " tr";
    
    // Sélecteur permettant de récupérer le bouton de suppression de la ligne, à partir d'une ligne
    this.selecteurBoutonSuppresion = ".bouton_tableau";
}

/**
 * Initialise le tableau dynamique.
 * @param {Array} elementsInitiaux Les éléments initialement dans le tableau (tableau d'objets).
 */
TableauLignesDynamiques.prototype.init = function(elementsInitiaux) {
    // Création de chacune des lignes du tableau
    for (var i = 0; i < elementsInitiaux.length; i++) {
        this.creerNouvelleLigne(elementsInitiaux[i]);
    }
};

/**
 * Recherche une ligne du tableau dynamique par rapport à l'élément contenu dans cette ligne.
 * @param {Integer} id L'identifiant de l'élément à rechercher.
 */
TableauLignesDynamiques.prototype.rechercherLigne = function(id) {
    var ligne = null;
    $(this.selecteurLignes).each(function() {
        var elementLigne = $(this).data("element");
        if (elementLigne != null && elementLigne.id == id) {
            ligne = $(this);
        }
    });
    
    return ligne;
};

/**
 * Ajoute un élément dans un tableau, si celui-ci n'existe pas déjà.
 * @param {String} element valeur de l'élément à ajouter
 */
TableauLignesDynamiques.prototype.creerNouvelleLigne = function(element) {
    var tableauDynamique = this;
    var nouvelleLigne = $("<tr></tr>");

    var contenuNouvelleLigne = this.modelNouvelElement;
    for (var nomPropriete in element) {
        if (element.hasOwnProperty(nomPropriete)) {
            var reg = new RegExp('%' + nomPropriete + '%', 'g');
            contenuNouvelleLigne = contenuNouvelleLigne.replace(reg, element[nomPropriete]);
        }
    }

    nouvelleLigne.html(contenuNouvelleLigne);
    
    // Associer l'élément à la ligne
    nouvelleLigne.data("element", element);
    
    $(this.selecteurTable).append(nouvelleLigne);
    
    $(this.selecteurBoutonSuppresion, nouvelleLigne).click(function() {
        tableauDynamique.supprimerElement(element);
    });
};

/**
 * Ajoute un nouvel élément au tableau et créé une nouvelle ligne dans le tableau.
 * Si l'élément existe déjà, l'élémentn n'est pas ajouté.
 * @param {Object} element L'élément à ajouter.
 */
TableauLignesDynamiques.prototype.ajouterElement = function(element) {
    // Si l'élément n'existe pas déjà
    if (!this.rechercherLigne(element.id)) {
        this.creerNouvelleLigne(element);
    }
};

/**
 * Supprime un élément dans un tableau.
 * @param {Object} element L'élément à supprimer.
 */
TableauLignesDynamiques.prototype.supprimerElement = function(element) {
    var ligne = this.rechercherLigne(element.id);
    if (ligne != null) {
        ligne.removeData("element");
        ligne.remove();
    }
};