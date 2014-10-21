//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 22/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;

import druide.hibernate.pojo.Cagnotte;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Utilisateur;
import java.util.List;


/**
 * Classe contenant les informations à afficher sur la page principale.
 * @author Jean-Loup Naddef
 */
public class InformationsPagePrincipale {
    
    /**
     * Le nombre de points de l'utilisateur.
     */
    private int nbPointsDetenus;
    
    /**
     * Le rang de l'utilisateur.
     */
    private String rang;
    
    /**
     * Le nombre de points que l'utilisateur peut encore distribuer.
     */
    private int nbPointsRestants;
    
    /**
     * Liste de l'historique d'action à afficher sur la page principale.
     */
    private List<HistoriqueAction> historiqueActions;
    
    /**
     * L'aperçu du classement d'Actidruide.
     */
    private ApercuClassement apercuClassement;
    
    /**
     * Liste des 5 premières cagnottes actives.
     */
    private List<Cagnotte> cagnottesActives;
    
    /**
     * La liste des utilisateurs pouvant être sélectionnés pour une action.
     */
    private List<Utilisateur> utilisateursPossibles;

    /**
     * Initialise une nouvelle instance de la classe {@link InformationsPagePrincipale}.
     * @author Jean-Loup Naddef
     */
    public InformationsPagePrincipale() {
    }

    public List<HistoriqueAction> getHistoriqueActions() {
        return historiqueActions;
    }

    public void setHistoriqueActions(List<HistoriqueAction> historiqueActions) {
        this.historiqueActions = historiqueActions;
    }

    public ApercuClassement getApercuClassement() {
        return apercuClassement;
    }

    public void setApercuClassement(ApercuClassement apercuClassement) {
        this.apercuClassement = apercuClassement;
    }

    public List<Cagnotte> getCagnottesActives() {
        return cagnottesActives;
    }

    public void setCagnottesActives(List<Cagnotte> cagnottesActives) {
        this.cagnottesActives = cagnottesActives;
    }

    public List<Utilisateur> getUtilisateursPossibles() {
        return utilisateursPossibles;
    }

    public void setUtilisateursPossibles(List<Utilisateur> utilisateursPossibles) {
        this.utilisateursPossibles = utilisateursPossibles;
    }
        public int getNbPointsDetenus() {
        return nbPointsDetenus;
    }

    public void setNbPointsDetenus(int nbPointsDetenus) {
        this.nbPointsDetenus = nbPointsDetenus;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public int getNbPointsRestants() {
        return nbPointsRestants;
    }

    public void setNbPointsRestants(int nbPointsRestants) {
        this.nbPointsRestants = nbPointsRestants;
    }
}
