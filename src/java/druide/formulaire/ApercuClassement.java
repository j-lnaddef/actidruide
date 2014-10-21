//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 23/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;

import java.util.List;
import druide.hibernate.pojo.Utilisateur;
import java.util.ArrayList;


/**
 * Permet de visualiser un aperçu du classement d'Actidruide.
 * @author Jean-Loup Naddef
 */
public class ApercuClassement {
    
    /**
     * Les 3 premiers du classement.
     */
    private List<Utilisateur> topUtilisateurs;
    
    /**
     * Les 3 derniers du classement.
     */
    private List<Utilisateur> bottomUtilisateurs;

    /**
     * Initialise une nouvelle instance de la classe {@link ApercuClassement}.
     * @author Jean-Loup Naddef
     */
    public ApercuClassement() {
        topUtilisateurs = new ArrayList<>();
        bottomUtilisateurs = new ArrayList<>();
    }

    public List<Utilisateur> getTopUtilisateurs() {
        return topUtilisateurs;
    }

    public void setTopUtilisateurs(List<Utilisateur> topUtilisateurs) {
        this.topUtilisateurs = topUtilisateurs;
    }

    public List<Utilisateur> getBottomUtilisateurs() {
        return bottomUtilisateurs;
    }

    public void setBottomUtilisateurs(List<Utilisateur> downUtilisateurs) {
        this.bottomUtilisateurs = downUtilisateurs;
    }
}
