//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 23/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.dao.UtilisateurDao;
import druide.exception.UtilisateurNonDefiniException;
import druide.formulaire.ApercuClassement;
import druide.formulaire.ClassementActidruide;
import druide.hibernate.pojo.Utilisateur;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Métier du classement d'Actidruide.
 * @author Jean-Loup Naddef
 */
public class ClassementMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link ClassementMetier}.
     * @author Jean-Loup Naddef
     */
    public ClassementMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
    /**
     * Récupération des utilisateurs, et création de l'objet d'aperçu du classement.
     * @return l'aperçu du classement.
     */
    public ApercuClassement creationApercuClassement() {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        List<Utilisateur> utilisateurs = utilisateurDao.trouverTous();
        
        // On retire au préalable les utilisateur ayant le profil "administrateur".
        List<Utilisateur> utilisateursCopie = new ArrayList<>(utilisateurs);
        
        for (Utilisateur utilisateur : utilisateursCopie) {
            if (utilisateur.getRang().getNom().equals("administrateur")) {
                utilisateurs.remove(utilisateur);
            }
        }
                
        ApercuClassement apercuClassement = new ApercuClassement();
        
        // Ajout des top utilisateurs
        for (int i = 0; i < 3; i++) {
            apercuClassement.getTopUtilisateurs().add(utilisateurs.get(i));
        }
        
        // Ajout des bottom utilisateurs
        
        List<Utilisateur> bottomUtilisateurs = new ArrayList<>();
        for (int i = utilisateurs.size() - 1; i > utilisateurs.size() - 4; i--) {
            bottomUtilisateurs.add(utilisateurs.get(i));
        }
        Collections.reverse(bottomUtilisateurs);
        apercuClassement.getBottomUtilisateurs().addAll(bottomUtilisateurs);
        
        return apercuClassement;
    }
    
    /**
     * Retourne le classement Actidruide.
     * @return le classement Actidruide.
     */
    public ResultatMetier<ClassementActidruide> retournerClassementActidruide() {
        
        if (this.getUtilisateur() == null) {
            this.lancerException(new UtilisateurNonDefiniException());
        }
        
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        
        this.ouvrirSessionHibernate();
        List<Utilisateur> utilisateurs = utilisateurDao.trouverTous();
        
        // On retire au préalable les utilisateur ayant le profil "administrateur".
        List<Utilisateur> utilisateursCopie = new ArrayList<>(utilisateurs);
        
        for (Utilisateur utilisateur : utilisateursCopie) {
            if (utilisateur.getRang().getNom().equals("administrateur")) {
                utilisateurs.remove(utilisateur);
            }
        }
        this.fermerSessionHibernate();
        
        List<Utilisateur> utilisateursGentils;
        List<Utilisateur> utilisateursMechants;
        
        int nbUtilisateurs = utilisateurs.size();
        boolean nombrePair = (nbUtilisateurs % 2 == 0) ? true : false;
        
        if (nombrePair) {
            utilisateursGentils = utilisateurs.subList(0, nbUtilisateurs / 2);
            utilisateursMechants = utilisateurs.subList(nbUtilisateurs / 2, nbUtilisateurs);
        }
        
        else {
            utilisateursGentils = utilisateurs.subList(0, (nbUtilisateurs + 1) / 2);
            utilisateursMechants = utilisateurs.subList((nbUtilisateurs + 1) / 2, nbUtilisateurs);
        }
        
        ClassementActidruide classementActidruide = new ClassementActidruide();
        classementActidruide.setUtilisateursGentils(utilisateursGentils);
        classementActidruide.setUtilisateursMechants(utilisateursMechants);
        
        ResultatMetier<ClassementActidruide> resultatMetier = new ResultatMetier<>();
        resultatMetier.setResultat(classementActidruide);
        
        return resultatMetier;
    }
    
}
