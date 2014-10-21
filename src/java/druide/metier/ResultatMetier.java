//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.metier;

/**
 * Resultat d'une méthode métier avec un objet quelconque.
 *
 * @param <T> Type de resultat.
 * @author Clément VASSEUR
 */
public class ResultatMetier<T> extends ResultatMetierSimple {

    /**
     * Le résultat de l'opération métier.
     */
    private T resultat;

    /**
     * Initialise une nouvelle instance de la classe ResultatMetier.
     */
    public ResultatMetier() {
        this(null);
    }

    /**
     * Initialise une nouvelle instance de la classe ResultatMetier.
     * @param resultat Le résultat de l'opération métier.
     */
    public ResultatMetier(T resultat) {
        super();
        this.resultat = resultat;
    }

    /**
     * Obtient l'objet métier de résultat.
     * @return L'objet métier.
     */
    public T getResultat() {
        return this.resultat;
    }

    /**
     * Définit l'objet métier de résultat.
     * @param resultat L'objet métier.
     */
    public void setResultat(T resultat) {
        this.resultat = resultat;
    }
}
