//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

package druide.metier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Erreur métier pouvant survenir durant l'exécution d'une méthode métier.
 * @author Clément VASSEUR
 */
public class ErreurMetier {
    
    /**
     * Le code métier identifiant l'erreur.
     */
    private CodeMetier codeErreur;
    
    /**
     * Les paramètres de l'erreur métier, donnant davantage de précision sur ce qu'il s'est passé.
     */
    private List<Object> parametres;
    
    /**
     * Initialise une nouvelle instance de la classe ErreurMetier.
     * @param codeErreur Le code d'erreur métier à l'origine de l'erreur.
     * @param parametres les paramètres associés à l'erreur.
     */
    public ErreurMetier(CodeMetier codeErreur, Object ... parametres) {
        if (codeErreur == null) {
            throw new NullPointerException("Le code d'erreur ne peut etre null");
        }
        
        this.codeErreur = codeErreur;
        this.parametres = new LinkedList<>(Arrays.asList(parametres));
    }
    
    /**
     * Obtient le code d'erreur d'origine.
     * @return Le code d'erreur.
     */
    public CodeMetier getCodeErreur() {
        return this.codeErreur;
    }

    /**
     * Obtient l'ensemble des paramètres de l'erreur.
     * @return L'ensemble des paramètres.
     */
    public List<Object> getParametres() {
        return this.parametres;
    }
    
    /**
     * Ajoute un paramètre à l'erreur métier.
     * @param parametre Le paramètre de l'erreur.
     */
    public void ajouterParametre(Object parametre) {
        this.parametres.add(parametre);        
    }
    
    /**
     * Obtient le paramètre de l'erreur qui est à un index donné.
     * @param index L'index du paramètre.
     * @return Le paramètre à l'index donné.
     */
    public Object getParametre(int index) {
        try {
            return this.parametres.get(index);
        }
        catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
}
