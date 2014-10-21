//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 02/12/2013
// Auteur  : Anthony FOULON
//-------------------------------------------------------------

/**
 * Sélectionne automatiquement la valeur d'une liste déroulante, si celle-ci ne contient qu'une seule valeur.
 * On ne prend pas en compte pour cela les valeurs vides (ex: --- choisir ---).
 * Les listes multiples sont ignorées.
 * @param {String} selecteurSelects Le sélecteur des sélect dont on doit modifier la valeur.
 */
function autoSelectionValeurSelect(selecteurSelects) {
    $(selecteurSelects).each(function() {
        var optionsAvecValeur = $(this).children("option[value!='']");
        var selectMultiple = $(this).attr('multiple'); 
        if (optionsAvecValeur.length == 1 && !selectMultiple) {
            $(this).val(optionsAvecValeur.attr('value'));
        }
    });
}

/**
 * Réagit à un événement de type keypress pour empêcher l'écriture d'un caractère autre qu'un nombre entier.
 * @param {Object} event L'événement keypress.
 * @param {boolean} nombreAvecVirgule Indique s'il s'agit d'un nombre avec virgule.
 */
function empecherSaisieAutreQueNombreOuVirgule(event, nombreAvecVirgule) {
    // Compatibilité IE / Firefox
    if(!event && window.event) {
        event = window.event;
    }

    var code = event.keyCode; 
    var which = event.which;
    
    
    // Détection de chrome et firefox. 
    var firefox = typeof InstallTrigger !== 'undefined';
    
    var conditionCode = (code < 48 || code > 57) && code != 8 && code != 9 && code != 16 && code != 13;
    if (firefox) {
        conditionCode = conditionCode && code != 46;
    }
        
    var conditionWhich = (which < 48 || which > 57) //&& (code < 37 || code > 40) 
                        && code != 8 && code != 9 && code != 16 && code != 13 || event.ctrlKey;
                    
    var condition = conditionCode && conditionWhich;
                    
    if (nombreAvecVirgule) {
        condition = condition && which != 46;
    }

//    // IE
    if (condition) {
        event.returnValue = false;
        event.cancelBubble = true;
    }

    if (condition) {
        event.preventDefault();
        event.stopPropagation();
    }
}

/**
 * Permet de rendre des champs de types de texte (input texte) saisissables avec uniquement des nombres entiers.
 * @param {String} selecteurChamps Le sélecteur des champs des nombres entiers;
 */
function champNombreEntier(selecteurChamps) {
    $(selecteurChamps).each(function() {
        $(this).bind('keypress', function(event) {
            empecherSaisieAutreQueNombreOuVirgule(event, false);
        });
    });
}

/**
 * Permet de rendre des champs de types de texte (input texte) saisissables avec uniquement des nombres à virgule.
 * @param {String} selecteurChamps Le sélecteur des champs des nombres entiers;
 */
function champNombreAVirgule(selecteurChamps) {
    $(selecteurChamps).each(function() {
        $(this).bind('keypress', function(event) {
            empecherSaisieAutreQueNombreOuVirgule(event, true);
        });
    });
}

/**
 * Met à jour une liste déroulante en redéfinissant l'ensemble des options contenus dans cette liste.
 * @param {String} selecteurSelect Le sélecteur jQuery de la liste à mettre à jour.
 * @param {Array} nouvellesValeursPossibles Les nouvelles valeurs de la liste : tableau d'objet avec les attributs "id" et "libelle".
 * @param {String} nouvelleValeur Facultatif : la nouvelle valeur sélectionnée dans la liste déroulante, après modification
 */
function changerValeursSelect(selecteurSelect, nouvellesValeursPossibles, nouvelleValeur) {
    // Conserver l'ancienne valeur du select
    var valeurListeAReaffecter;
    if (!nouvelleValeur) {
        valeurListeAReaffecter = $(selecteurSelect).val();
    }
    else {
        valeurListeAReaffecter = nouvelleValeur;
    }
    
    // Supprimer les éléments actuels de la liste
    $(selecteurSelect).children().remove();
    
    // Créer chacun des éléments "<option>"
    if (nouvellesValeursPossibles != null) {
        $.each(nouvellesValeursPossibles, function(index, objet) {
            var option = $("<option />").val(objet.id).html(objet.libelle);
            
            if ($(selecteurSelect).attr('multiple')) {
                // Sur les listes multiples, on met la même valeur que le libellé en title, pour permettre
                // de voir la totalité du texte au passage de la souris (souvent, l'espace disponible est trop court)
                option.attr('title', objet.libelle);
            }
            
            $(selecteurSelect).append(option);
        });
    }

    // Remettre l'ancienne valeur de la liste (si possible)
    $(selecteurSelect).val(valeurListeAReaffecter);
}

/*----------------------*/
/*---- Autocomplete ----*/
/*----------------------*/

/**
 * Crée une nouvelle instance d'autocompletion sur un champ texte.
 * Si la saisie d'un utilisateur correspond exactement avec une valeur possible, alors on renseigne automatiquement un autre champ
 * avec l'identifiant de l'objet.
 * @param {String} selecteurChampRecherche Sélecteur jQuery du champ texte sur lequel l'autocomplétion doit s'effectuer.
 * @param {String} selecteurChampIdentifiant Sélecteur jQuery du champ (hidden) destiné à contenir l'identifiant de la chaîne sélectionnée.
 * @param {Array} listeObjets La liste de tous les objets proposés en autocomplétion. 
 *                            La liste est une liste d'objets avec les attributs "id" et "libelle"
 * @param {String} selecteurPopup si l'autocomplete est dans une popup, mettre ici le sélecteur du HTML contenant la popup.
 */
function Autocompletion(selecteurChampRecherche, selecteurChampIdentifiant, listeObjets, selecteurPopup) {
    this.selecteurChampRecherche = selecteurChampRecherche;
    this.selecteurChampIdentifiant = selecteurChampIdentifiant;
    this.listeObjets = listeObjets;
    this.selecteurPopup = selecteurPopup;
}

/**
 * Obtient le texte saisi dans le champ de recherche.
 * @returns {String} Le texte saisi.
 */
Autocompletion.prototype.getTexteSaisi = function () {
    return $(this.selecteurChampRecherche).val();
};

/**
 * Définit le texte saisi dans le champ de recherche.
 * @param {String} texte Le texte saisi.
 */
Autocompletion.prototype.setTexteSaisi = function (texte) {
    return $(this.selecteurChampRecherche).val(texte);
};

/**
 * Définit la valeur du champ contenant l'identifiant du texte saisi.
 * @param {String} valeur La nouvelle valeur de l'identifiant.
 */
Autocompletion.prototype.setIdentifiant = function (valeur) {
    if (this.selecteurChampIdentifiant != null) {
        $(this.selecteurChampIdentifiant).val(valeur);
    }
};

/**
 * Obtient la valeur du champ contenant l'identifiant du texte saisi.
 * @return {String} valeur La valeur de l'identifiant.
 */
Autocompletion.prototype.getIdentifiant = function () {
    var identifiant = null;
    if (this.selecteurChampIdentifiant != null) {
        identifiant = $(this.selecteurChampIdentifiant).val();
    }
    
    return identifiant;
};

/**
 * Désactive le champ en autocomplétion.
 */
Autocompletion.prototype.activer = function () {
    $(this.selecteurChampRecherche).prop('disabled', false);
};

/**
 * Active le champ en autocomplétion.
 */
Autocompletion.prototype.desactiver = function () {
    $(this.selecteurChampRecherche).prop('disabled', true);
};

/**
 * Efface le champ texte saisi.
 */
Autocompletion.prototype.effacerChamp = function () {
    $(this.selecteurChampRecherche).val("");
    $(this.selecteurChampIdentifiant).val("");
};

/**
 * Obtient l'identifiant de l'objet correspondant à la saisie de l'utilisateur.
 * @return id Identifiant de l'objet si on l'a trouvé, -1 sinon.
 */
Autocompletion.prototype.getIdSelectionne = function() {
    var texteSaisi =  this.getTexteSaisi();
    for (var elt in this.listeObjets) {
        if (this.listeObjets[elt].libelle == texteSaisi) {
            return this.listeObjets[elt].id;
        }
    }
    
    return -1;
};

/**
 * Définit la liste de tous les objets proposés en autocomplétion.
 * @param {Array} listeObjets La liste des objets.
 */
Autocompletion.prototype.setListeObjets = function (listeObjets) {
    if (listeObjets == null) {
        // Eviter les valeurs nulles
        this.listeObjets = new Array();
    } 
    else {
        this.listeObjets = listeObjets;
    }
};

/**
 * Filtre la liste des objets par rapport à un mot tapé par l'utilisateur.
 * @param {String} texteSaisi Le texte saisi par l'utilisateur.
 * @return {Array} La liste des objets filtrés.
 */
Autocompletion.prototype.filtrerListe = function(texteSaisi) {
    var listeFiltree = new Array();
    
    // Récupération de tous les mots du texte saisi.
    var mots = texteSaisi.split(/\s+/);
    
    if (mots.length > 0) {
        for (var indexListeObjet = 0; (indexListeObjet < this.listeObjets.length); indexListeObjet++) {
            var objet = this.listeObjets[indexListeObjet];
            
            // Recherche de type : contient tous les mots
            var contientTousLesMots = true;
            for (var indexMot = 0; (indexMot < mots.length && contientTousLesMots); indexMot++) {
                var mot = mots[indexMot];
                var regMot = new RegExp($.ui.autocomplete.escapeRegex(mot), "i");
                contientTousLesMots = (objet.libelle.match(regMot) != null);
            }

            if (contientTousLesMots) {
                listeFiltree.push(objet);
            }
            
            // Maximum 10 mots
            if (listeFiltree.length == 10) {
                break;
            }
        }
    }
    
    return listeFiltree;
};

/**
 * Initialise l'autocomplétion.
 */
Autocompletion.prototype.init = function() {
    var autocompletion = this;
    $(this.selecteurChampRecherche).autocomplete({
        // Définit l'ensemble des valeurs proposés en autocomplétion
        source: function(request, response) {
            var listeFiltree = autocompletion.filtrerListe(request.term);
            response($.map(listeFiltree, function(item) {
                return {
                    value: item.id,
                    label: item.libelle
                };
            }));
        },
        // Evénement lancé lorsque l'utilisateur quitte le champ texte (événement blur)
        change: function(event, ui) {
            if (ui.item !== null) {
                autocompletion.setIdentifiant(ui.item.value);
            }
            else {
                autocompletion.setIdentifiant('');
            }
            event.preventDefault();
        },
        // Evénement lancé lorsque le menu d'autocomplétion est fermé
//        close: function(event, ui) {
//            // Permet de setter définitivement la valeur de la chaine dans l'input
//            $(autocompletion.selecteurChampRecherche).focus();
//        },
        // Evénement lancé lorsque l'utilisateur clique sur une valeur dans l'autocomplétion
        select: function(event, ui) {
            autocompletion.setIdentifiant(ui.item.value);
            autocompletion.setTexteSaisi(ui.item.label);
            event.preventDefault();
        },
        // Evénement lancé lorsque l'utilisateur sélectionne une valeur dans l'autocomplétion avec le clavier
        // Ou en passant la souris sur la valeur, mais ne la choisit pas
// 
        focus: function(event, ui) {
//            autocompletion.setIdentifiant(ui.item.value);
//            autocompletion.setTexteSaisi(ui.item.label);
            event.preventDefault();
        },
        // Utile uniquement avec les popups : sans cette option, l'autocomplétion apparait derrière la popup.
        appendTo : this.selecteurPopup
    });
    
    $(this.selecteurChampRecherche).focusout(function(){
        var id = autocompletion.getIdSelectionne();
        if (id != -1){
            autocompletion.setIdentifiant(id);
        }
        else {
            autocompletion.setIdentifiant('');
        }
    });
};


/*------------------------------------------------*/
/*---- Listes dépendantes les unes des autres ----*/
/*------------------------------------------------*/

/**
 * Initialise un nouvel objet DependanceListes, permettant de gérer facilement les listes se mettant à jour les unes par rapport
 * aux autres. Exemple: Liste des concours modifiant une liste de disciplines.
 * @param {String} selecteurChampDeclencheur Le sélecteur du champ (input/select) déclenchant la mise à jour de la liste. 
 *                                           Dans l'exemple, c'est le sélecteur de la balise "select" des concours.
 * @param {Array} mapListes Tableau associatif [identifiant => liste d'objets] contenant la liste des valeurs dépendantes.
 *                          La liste d'objets contient des objets avec deux attributs : "id" et "libelle".
 *                          Dans l'exemple, c'est une liste de disciplines, indexées par identifiant de concours.
 * @param {String} cible Le sélecteur de la liste déroulante mis à jour
 *                         OU
 *                        L'objet Autocompletion à mettre à jour
 *                          OU 
 *                        Fonction quelconque (comportement personnalisé). 
 *                        Si non renseigné, rien ne se passe lorsque la valeur du champ déclencheur est modifiée.
 *                        Dans l'exemple, c'est le sélecteur de la balise "select" des disciplines.
 * @returns {DependanceListes}
 */
function DependanceListes(selecteurChampDeclencheur, mapListes, cible) {
    this.selecteurChampDeclencheur = selecteurChampDeclencheur;
    this.mapListes = mapListes;
    this.listeCourante = null;
    this.valeurPrecedenteDeclencheur = null;
    this.cible = cible;
}

/**
 * Obtient la valeur actuelle du champ déclencheur.
 * @returns {String} La valeur actuelle du champ déclencheur.
 */
DependanceListes.prototype.getValeurDeclencheur = function() {
    return $(this.selecteurChampDeclencheur).val();
};

/**
 * Obtient une liste quelconque de valeurs, à partir d'un identifiant indexant cette liste.
 * (dans l'exemple, identifiant de concours).
 * @param {String} identifiant L'idenfiant indexant la liste à trouver;
 * @returns {Array} La liste des éléments de la liste.
 */
DependanceListes.prototype.getListe = function(identifiant) {
    if (identifiant in this.mapListes) {
        return this.mapListes[identifiant];
    }
    
    return null;
};

/**
 * Met à jour la liste des éléments courants, en fonction de la valeur actuelle du champ déclencheur.
 */
DependanceListes.prototype.metAJourListeCourante = function() {
    var valeurDeclencheur = this.getValeurDeclencheur();
    // Vérifier que la valeur a bien changé
    if (valeurDeclencheur != this.valeurPrecedenteDeclencheur) {
        // Si le champ déclencheur a une valeur
        if (valeurDeclencheur) {
            // Retrouver la nouvelle liste
            this.listeCourante = this.getListe(valeurDeclencheur);
        }
        else {
            // Pas de valeur, donc liste vide.
            this.listeCourante = null;
        }

        // Mémoriser la valeur précédente
        this.valeurPrecedenteDeclencheur = valeurDeclencheur;
        
        // Appeler le handler chargé de réagir au changement de valeur du déclencheur.
        this.propagerChangement();
    }
};

/**
 * Après une modification de valeur du champ déclencheur, exécute une action en conséquence.
 */
DependanceListes.prototype.propagerChangement = function() {
    if (this.cible instanceof Autocompletion) {
        this.metAJourAutocompletion();
    }
    else if (typeof(this.cible) == "string") {
        this.metAjourListeDeroulante();
    }
    else if (typeof(this.cible) == "function") {
        this.cible(this);
    }
};

/**
 * Met à jour la liste une liste avec auto complétion.
 */
DependanceListes.prototype.metAJourAutocompletion = function() {
    this.cible.effacerChamp();
    
    if (!this.listeCourante) {
        // Désactiver le champ texte
        this.cible.desactiver();
    }
    else {
        // Activer le champ texte
        this.cible.activer();
        
        // Mettre à jour la liste des objets de l'autocomplete
        this.cible.setListeObjets(this.listeCourante);
    }
};

/**
 * Met à jour une liste déroulante en la peuplant avec les éléments de la liste courante.
 * S'il n'y a pas de liste courante, la liste déroulante est désactivée.
 */
DependanceListes.prototype.metAjourListeDeroulante = function() {
    if (!this.listeCourante) {
        // Désactiver la liste
        $(this.cible).prop('disabled', true);
    }
    else {
        // Activer la liste
        $(this.cible).prop('disabled', false);
    }
    
    changerValeursSelect(this.cible, this.listeCourante);
};

/**
 * Initialise l'objet DependanceListes.
 * @param {Object/String} valeurInitiale Facultatif : la valeur initiale de la liste déroulante (String) ou de l'autocomplétion (Objet {id, libelle}).
 */
DependanceListes.prototype.init = function(valeurInitiale) {
    var dependances = this;
    this.metAJourListeCourante();
    $(this.selecteurChampDeclencheur).change(function() {
        dependances.metAJourListeCourante();
    });
    
    if (valeurInitiale != undefined) {
        // Bien positionner la liste déroulante /autocomplétion à une valeur initiale.
        if (this.cible instanceof Autocompletion) {
            this.cible.setIdentifiant(valeurInitiale.id);
            this.cible.setTexteSaisi(valeurInitiale.libelle);
        }
        else if (typeof(this.cible) == "string") {
            $(this.cible).val(valeurInitiale);
        }
    }
};

/**
 * Initialise un nouvel objet ChampDependance. Décrit un champ de métadonnées, source ou cible.
 * @param {Autocompletion/String} champ L'objet Autocompletion OU le selecteur jQuery d'une liste déroulante.
 * @param {String} selecteurFormulaireDependance Facultatif : le sélecteur, dans le formulaire des dépendances, 
 *                                     du champ destiné à contenir la valeur du champ de dépendance. Requis pour les champs sources.
 * @param {Object} autresDonnees Facultatif : Objet quelconque contenant des informations supplémentaires sur le champ.
 * @returns {ChampDependance}
 */
function ChampDependance(champ, selecteurFormulaireDependance, autresDonnees) {
    this.champ = champ;
    this.selecteurFormulaireDependance = selecteurFormulaireDependance;
    this.autresDonnees = autresDonnees;
}

/**
 * Teste si le champ est de type Autocompletion.
 * @returns {Boolean} Vrai si le champ est une autocomplétion, faux sinon.
 */
ChampDependance.prototype.estAutocompletion = function() {
    return (this.champ instanceof Autocompletion);
};

/**
 * Teste si le champ est de type liste déroulante.
 * @returns {Boolean} Vrai si le champ est une liste déroulante, faux sinon.
 */
ChampDependance.prototype.estListeDeroulante = function() {
    return (typeof(this.champ) == "string");
};

/**
 * Obtient la valeur du champ (identifiant).
 * @returns {Integer} La valeur du champ.
 */
ChampDependance.prototype.getValeur = function() {
    var valeur = null;
    if (this.estAutocompletion()) {
        valeur = this.champ.getIdentifiant();
    }
    else if (this.estListeDeroulante()) {
        valeur = $(this.champ).val();
    }
    
    return valeur;
};

/**
 * Efface la valeur du champ en la remettant à vide.
 */
ChampDependance.prototype.effacerValeur = function() {
    if (this.estAutocompletion()) {
        this.champ.effacerChamp();
    }
    else if (this.estListeDeroulante()) {
        $(this.champ).val('');
    }
};

/**
 * Met à jour les valeurs possibles (proposées à l'utilisateur) du champ.
 * @param {Array} valeursPossibles Les valeurs possibles.
 */
ChampDependance.prototype.changerValeursPossibles = function(valeursPossibles) {
    if (this.estAutocompletion()) {
        this.champ.setListeObjets(valeursPossibles);
    }
    else if (this.estListeDeroulante()) {
        changerValeursSelect(this.champ, valeursPossibles);
    }
};

/**
 * Rend le champ actif (enabled).
 */
ChampDependance.prototype.activer = function() {
    if (this.estAutocompletion()) {
        this.champ.activer();
    }
    else if (this.estListeDeroulante()) {
        $(this.champ).prop('disabled', false);
    }
};

/**
 * Rend le champ inactif (disabled).
 */
ChampDependance.prototype.desactiver = function() {
    if (this.estAutocompletion()) {
        this.champ.desactiver();
    }
    else if (this.estListeDeroulante()) {
        $(this.champ).prop('disabled', true);
    }
};

/**
 * Copier la valeur du champ dans le formulaire de dépendances.
 * Utile pour les champs sources.
 */
ChampDependance.prototype.copierValeurDansFormulaireDependance = function() {
    if (!this.selecteurFormulaireDependance) {
        alert("Erreur : la propriété selecteurFormulaireDependance n'est pas définie");
    }
    else {
        $(this.selecteurFormulaireDependance).val(this.getValeur());
    }
};

/**
 * Définit une méthode appelée lorsque la valeur du champ change.
 * @param {Function} enCasDeChangement la fonction à appeler en cas de changement.
 */
ChampDependance.prototype.setEnCasDeChangementDeValeur = function(enCasDeChangement) {
    if (this.estAutocompletion()) {
        alert("ChampDependance.prototype.setEnCasDeChangementDeValeur : Fonction non implémentée");
    }
    else if (this.estListeDeroulante()) {
        $(this.champ).change(function() {
            enCasDeChangement();
        });
    }
};

/**
 * Initialise un nouvel objet DependanceListesAjax, permettant de gérer les dépendances entre les listes via Ajax.
 * Utile lorsque les mises à jours sont plus complexes, lorsque PLUSIEURS champs cibles dépendent de PLUSIEURS champs sources.
 * Le principe est le suivant :
 *     1. Lorsqu'une valeur d'un champ source est modifié, on recopie les valeurs des champs sources dans un formulaire, 
 *     appelé formulaire de dépendances.
 *     2. On soumet ce formulaire via Ajax.
 *     3. A partir du retour de cet appel ajax, on retrouve les nouvelles valeurs possibles des champs cibles (calculées côté serveur).
 *     4. On met à jour les champs cibles avec les nouvelles valeurs possibles obtenues.
 * @param {Array} sources Les champs sources déclenchant la mise à jour des champs cibles. Array de ChampDependance
 * @param {Array} cibles Les champs cibles, mis à jour par les champs sources. Array de ChampDependance.
 * @param {String} selecteurForm Le sélecteur jQuery du formulaire soumis pour calculer les valeurs possibles des champs cibles.
 * @param {Function} obtenirListeRetourAjax La méthode utilisée pour retrouver la bonne liste d'un champ cible lors du retour 
 *                                          de l'appel ajax. La méthode doit prendre deux paramètres : 
 *                                             - ChampDependance champCible
 *                                             - ResultatAjax resultatAjax (voir méthode xhr).
 *                                           Et retourner :
 *                                             - Array de Object {id, libelle} : les nouvelles valeurs possibles du champ cible
 *                                               (ou null ou Array vide si aucune nouvelle valeur).
 * @returns {DependanceListesAjax}
 */
function DependanceListesAjax(sources, cibles, selecteurForm, obtenirListeRetourAjax) {
    this.sources = sources;
    this.cibles = cibles;
    this.selecteurForm = selecteurForm;
    this.obtenirListeRetourAjax = obtenirListeRetourAjax;
}

/**
 * Effectue un appel Ajax pour récupérer les nouvelles valeurs possibles des métadonnées cibles.
 * Au retour de l'appel Ajax, les champs des métadonnées cibles sont mis à jour.
 */
DependanceListesAjax.prototype.obtenirNouvellesValeursPossibles = function() {
    var url = $(this.selecteurForm).attr('action');
    var methode = $(this.selecteurForm).attr('method');
    var dependance = this;
    
    // Appel Ajax 
    xhr(
        url, 
        methode, 
        true, 
        this.selecteurForm, 
        function(resultatAjax) {
            // Mise à jour des champs cibles
            dependance.metAJourChampsCibles(resultatAjax);
        }
    );
};

/**
 * Met à jour les champs cibles à partir d'un ensemble de nouvelles valeurs, contenus dans un résultat Ajax.
 * Si un champ n'a pas de nouvelles valeurs, il est mis comme inactif, sans valeur possible.
 * Si un champ a des nouvelles valeurs, il est mis comme actif, avec ses nouvelles valeurs.
 * @param {Object} resultatAjax Le résultat 
 */
DependanceListesAjax.prototype.metAJourChampsCibles = function(resultatAjax) {
    var dependance = this;
    // Mise à jour de champ de métadonnées cibles
    $.each(this.cibles, function(index, champCible) {
        var nouvellesValeursChamp = dependance.obtenirListeRetourAjax(champCible, resultatAjax);
        champCible.effacerValeur();
        
        // Teste si un champ cible a des nouvelles valeurs
        if (nouvellesValeursChamp != null && nouvellesValeursChamp.length > 0) {

            // Activer et mettre à jour des valeurs possibles du champ cible
            champCible.changerValeursPossibles(nouvellesValeursChamp);
            champCible.activer();
        }
        else {
            // Désactiver et enlever toutes les valeurs possibles du champ cible
            champCible.changerValeursPossibles(null);
            champCible.desactiver();
        }
    });
};

/**
 * Initialise l'objet DependanceListesAjax.
 */
DependanceListesAjax.prototype.init = function() {
    var dependances = this;
    var champsSourcesTousSaisis = true;
    // Définir une action à effectuer dès que la valeur d'une des données sources est modifiée.
    $.each(this.sources, function(index, champSource) {
        var valeurChampSource = champSource.getValeur();
        champsSourcesTousSaisis = champsSourcesTousSaisis && valeurChampSource != "";
        champSource.setEnCasDeChangementDeValeur(function() {
            // Changer la valeur de l'input dans le formulaire
            champSource.copierValeurDansFormulaireDependance();
            
            // Effectuer l'appel Ajax qui va mettre à jour les champs cibles
            dependances.obtenirNouvellesValeursPossibles();
        });
    });
    
    // Si au moins un des champs sources n'est pas saisi
    if (!champsSourcesTousSaisis) {
        // Alors chacune des cibles doit être désactivée et sans valeur
        $.each(this.cibles, function(index, champCible) {
            champCible.effacerValeur();
            champCible.desactiver();
        });
    }
};

/*------------------------------------------------*/
/*---- Gestion des cases à cocher et boutons -----*/
/*--------- radio imbriqués et dépendants --------*/
/*------------------------------------------------*/

/**
 * Initialise un nouvel objet DependanceBoutonRadioCaseACocher, permettant de gérer facilement les boutons radio et 
 * cases à cocher imbriquées et dépendants. Lorsqu'un bouton radio ou une case à cocher est sélectionné, cela a un impact
 * sur d'autres champs quelconques.
 * Deux cas possibles en fonction du paramètre propriete : 
 *  - Le paramètre propriete vaut : 'checked'
 *    Lorsque la case déclencheur est cochée, l'ensemble des boutons radio cibles sont sélectionnés. Dans le cas contraire, ils
 *    sont désélectionnés.
 *  - Le paramètre propriete vaut : 'disabled'
 *    Lorsque la case déclencheur est cochée, l'ensemble des champs cibles sont activés. Dans le cas contraire, ils
 *    sont désactivés.
 * @param {String} selecteurElementDeclencheur Sélecteur de la case à cocher ou du bouton radio déclencheur.
 * @param {String} selecteurElementsCibles Sélecteur des champs impactés par le déclencheurs.
 * @param {String} propriete Propriété à modifier sur éléments cibles : "checked" pour la sélection / désélection; "disabled" pour l'activation / désactivation.
 */
function DependanceBoutonRadioCaseACocher(selecteurElementDeclencheur, selecteurElementsCibles, propriete) {
    this.selecteurElementDeclencheur = selecteurElementDeclencheur;
    this.selecteurElementsCibles = selecteurElementsCibles;
    this.propriete = propriete;
}

/**
 * Initialise l'objet DependanceBoutonRadioCaseACocher.
 */
DependanceBoutonRadioCaseACocher.prototype.init = function() {
    var dependance = this;
    
    $(this.selecteurElementDeclencheur).click(function() {
        dependance.metAJourProprieteElementsCibles();
    });
    
    // Par défaut au chargement de la page, on désactive / décoche / initialise la valeur des éléments dépendants.
    dependance.metAJourProprieteElementsCibles();
};

/**
 * Obtient une valeur indiquant si l'élément déclencheur est coché.
 * @returns Vrai si l'élément déclencheur est coché, faus sinon.
 */
DependanceBoutonRadioCaseACocher.prototype.isElementDeclencheurCoche = function() {
    return $(this.selecteurElementDeclencheur).prop("checked");
};

/**
 * Met à jour la propriété des éléments cibles selon que l'élément déclencheur est coché ou non.
 */
DependanceBoutonRadioCaseACocher.prototype.metAJourProprieteElementsCibles = function() {
    var dependance = this;
    var nouvelleValeur;
    if (dependance.propriete == "checked") {
        // Si case à cocher multiple => 
        // Case coché => éléments cochés => checked = true
        // Case non coché => éléments décochés => checked = false
        nouvelleValeur = dependance.isElementDeclencheurCoche();
    }
    else {
        // Si case à cocher pour activer/désactiver un formulaire => 
        // Case coché => éléments actifs => disabled = false
        // Case non coché => éléments inactifs => disabled = true
        nouvelleValeur = !dependance.isElementDeclencheurCoche();
    }
    
    $(this.selecteurElementsCibles).each(function() {
        $(this).prop(dependance.propriete, nouvelleValeur);
        if (dependance.propriete == "checked") {
            // Forcer l'événement "change" sur les cases à cocher, car on vient de cocher/décocher via un script.
            // L'événément "change" n'est pas levé par défaut dans ce cas (seulement par un vrai click utilisateur).
            $(this).change();
        }
    });
};

/**
 * Initialise un nouvel objet DependanceRadiosMap, permettant de gérer facilement une dépendance entre
 * un ensemble de boutons radios, venant activer/désactiver d'autres champs, chacun de ces champs étant
 * lié à une valeur du bouton radio.
 * @param {String} selecteurRadiosDeclencheur Le sélecteur des boutons radios déclenchant l'activation/désactivation des champs.
 * @param {Object} mapSelecteurElementsCibles Le tableau, indexé par valeur des boutons radios, contenant les
 *                                           sélecteurs des éléments à activer/désactiver.
 * @returns {DependanceRadiosMap}
 */
function DependanceRadiosMap(selecteurRadiosDeclencheur, mapSelecteurElementsCibles) {
    this.selecteurRadiosDeclencheur = selecteurRadiosDeclencheur;
    this.mapSelecteurElementsCibles = mapSelecteurElementsCibles;
}

/**
 * Initialise l'objet DependanceRadiosMap.
 */
DependanceRadiosMap.prototype.init = function() {
    var dependance = this;
    $(this.selecteurRadiosDeclencheur).change(function() {
        dependance.metAJourActivationElementsCible();
    });
    
    dependance.metAJourActivationElementsCible();
};

/**
 * Obtient la valeur des boutons radio déclencheurs (valeur du bouton qui est sélectionné).
 * @returns {String} Valeur des boutons radio déclencheurs.
 */
DependanceRadiosMap.prototype.getValeurRadio = function() {
    return $(this.selecteurRadiosDeclencheur).filter(":checked").val();
};

/**
 * Met à jour l'activation/désactivation des champs cibles, selon la valeur des boutons radios.
 */
DependanceRadiosMap.prototype.metAJourActivationElementsCible = function() {
    var valeurRadio = this.getValeurRadio();
    // Parcours des sélecteurs des éléments cibles
    $.each(this.mapSelecteurElementsCibles, function(cleValeurRadio, selecteurElementsCibles) {
        // Vaudra vrai si l'élément cible est à mettre en disabled => si ce n'est pas celui qui correspond à la valeur sélectionné
        var champsVerrouilles = (valeurRadio != cleValeurRadio);
        $(selecteurElementsCibles).prop('disabled', champsVerrouilles);
    });
};
