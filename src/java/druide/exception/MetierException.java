//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 24/11/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

package druide.exception;

/**
 * Exception pouvant survenir durant l'exécution d'une opération métier.
 * @author Clément VASSEUR
 */
public abstract class MetierException extends RuntimeException {
    
    /**
     * Initialise une nouvelle instance de la classe MetierException.
     * @param message Un message informatif sur l'exception métier qui est survenu.
     */
    public MetierException(String message) {
        super(message);
    }
}
