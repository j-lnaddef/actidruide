//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.metier;

/**
 * Code métier utilisé pour indiquant le résultat d'une action métier.
 * @author Clément VASSEUR
 */
public enum CodeMetier {

    
    /**
     * Erreur : indique que l'utilisateur à l'origine de l'action n'est pas authentifié.
     */
    UtilisateurNonAuthentifie,
    /**
     * Erreur : authentification : Indique que le mot de passe saisi n'est pas valide pour le login donné.
     */
    MauvaisMotDePasse,
    /**
     * Erreur : authentification : Indique qu'aucun utilisateur avec le login renseigné n'existe.
     */
    LoginInexistant,
    
    /**
     * Erreur : un champ obligatoire n'a pas été saisi.
     */
    ChampRequisNonRenseigne,
    
    
    /**
     * Erreur : l'utilisateur n'a pas assez de points pour pouvoir en donner ou enlever autant.
     */
    PasAssezDePointsDisponibles, 
    
    /**
     * Erreur : le nombre de points à donner ou retirer n'est pas valide.
     */
    NombreDePointsNonValide
}
