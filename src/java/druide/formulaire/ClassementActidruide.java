//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 24/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;

import druide.hibernate.pojo.Utilisateur;
import java.util.List;


/**
 * Permet de formater le classement en 2 tableaux.
 * @author Jean-Loup Naddef
 */
public class ClassementActidruide {
    
    /**
     * Première liste d'utilisateurs.
     */
    private List<Utilisateur> utilisateursGentils;
    
    /**
     * Deuxième liste d'utilisateurs.
     */
    private List<Utilisateur> utilisateursMechants;
    
    /**
     * Initialise une nouvelle instance de la classe {@link ClassementActidruide}.
     * @author Jean-Loup Naddef
     */
    public ClassementActidruide() {
    }

    public List<Utilisateur> getUtilisateursGentils() {
        return utilisateursGentils;
    }

    public void setUtilisateursGentils(List<Utilisateur> utilisateursGentils) {
        this.utilisateursGentils = utilisateursGentils;
    }

    public List<Utilisateur> getUtilisateursMechants() {
        return utilisateursMechants;
    }

    public void setUtilisateursMechants(List<Utilisateur> utilisateursMechants) {
        this.utilisateursMechants = utilisateursMechants;
    }
    
}
