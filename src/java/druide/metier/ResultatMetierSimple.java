//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 19/11/2013
// Auteur  : Serge NJIKI
//-------------------------------------------------------------

package druide.metier;

import java.util.LinkedList;
import java.util.List;

/**
* Resultat d'une méthode métier, avec uniquement des codes métiers.
* @author Serge NJIKI
*/
public class ResultatMetierSimple {

    /**
     * Ensemble des erreurs métiers qui sont survenus durant l'exécution de l'opération métier.
     */
    private List<ErreurMetier> erreurs;

    /**
     * Initialise une nouvelle instance de la classe ResultatMetierSimple.
     */
    public ResultatMetierSimple() {
        this.erreurs = new LinkedList<>();
    }

    /**
     * Recherche et renvoie l'erreur métier ayant le code donné en paramètre, ou null s'il n'existe aucune erreur métier avec ce code.
     * @param codeErreur Le code métier à rechercher.
     * @return L'erreur métier avec le code à rechercher.
     */
    public ErreurMetier getErreur(CodeMetier codeErreur) {
        if (codeErreur == null) {
            throw new NullPointerException("Le code de l'erreur metier ne peut etre null");
        }

        for (ErreurMetier erreur : this.erreurs) {
            if (codeErreur.equals(erreur.getCodeErreur())) {
                return erreur;
            }
        }

        return null;
    }

    /**
     * Ajoute une erreur métier au résultat métier.
     * @param erreur L'erreur métier à ajouter.
     */
    public void ajouterErreur(ErreurMetier erreur) {
        if (erreur == null) {
            throw new NullPointerException("L'erreur metier ne peut etre null");
        }

        this.erreurs.add(erreur);
    }
    
    /**
     * Retourne la première erreur métier du résultat métier.
     * Utile quand on sait qu'un résultat métier ne contient qu'une seul erreur.
     * S'il n'y en a pas, une exception est lancée.
     * @return La première erreur du résultat métier.
     */
    public ErreurMetier getPremiereErreur() {
        if (this.erreurs.isEmpty()) {
            throw new IllegalStateException("Aucune erreur dans le résultat métier : impossible d'accéder à la première");
        }
        
        return this.erreurs.get(0);
    }
    
    /**
     * Retourne le code métier de la première erreur métier du résultat métier.
     * Utile quand on sait qu'un résultat métier ne contient qu'un seul code d'erreur.
     * S'il n'y en a pas, une exception est lancée.
     * @return Le code métier de la première erreur métier.
     */
    public CodeMetier getPremierCodeErreur() {
        return this.getPremiereErreur().getCodeErreur();
    }
    
    /**
     * Ajoute une erreur métier à partir d'un code et éventuellement de paramètres.
     * @param code Le code métier d'erreur.
     * @param parametres Les paramètres de l'erreur.
     */
    public void ajouterErreur(CodeMetier code, Object... parametres) {
        ErreurMetier erreur = new ErreurMetier(code);
        for (int i = 0; i < parametres.length; i++) {
            erreur.ajouterParametre(parametres[i]);
        }
        
        this.ajouterErreur(erreur);
    }

    /**
     * Obtient la liste des erreurs métiers.
     * @return La liste des erreurs.
     */
    public List<ErreurMetier> getErreurs() {
        return this.erreurs;
    }

    /**
     * Vérifie si le résultat métier possède des erreurs quelconques ou non.
     * @return Vrai s'il y a des erreurs métiers, faux sinon.
     */
    public boolean estEnErreur() {
        return !this.estValide();
    }

    /**
     * Vérifie si le résultat métier ne possède pas d'erreurs.
     * @return Vrai s'il n'y a pas d'erreurs métiers, faux sinon.
     */
    public boolean estValide() {
        return this.erreurs == null || this.erreurs.isEmpty();
    }

    /**
     * Ajoute une liste d'erreurs métier.
     * @param erreurs La liste des erreurs métier.
     */
    public void setErreurs(List<ErreurMetier> erreurs) {
        this.erreurs = erreurs;
    }    
}
