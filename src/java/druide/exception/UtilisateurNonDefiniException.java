//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 17/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

package druide.exception;

/**
 * Exception indiquant que l'utilisateur effectuant l'action métier n'a pas été initialisé, alors que sa présence est requise.
 * @author Clément VASSEUR
 */
public class UtilisateurNonDefiniException extends MetierException {

    /**
     * Initialise une nouvelle instance de la classe UtilisateurNonDefiniException.
     * @author Clément VASSEUR
     */
    public UtilisateurNonDefiniException() {
        super("L'utilisateur n'a pas été défini.");
    }
}
