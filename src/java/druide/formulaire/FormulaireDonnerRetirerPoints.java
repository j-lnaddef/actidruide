//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 15/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class FormulaireDonnerRetirerPoints {

    /**
     * Nombre de points à donner ou retirer.
     */
    private Integer nbPoints;
    
    /**
     * Utilisateur cible de l'action.
     */
    private Integer destinataire;
    
    /**
     * Raison du don ou retrait de point.
     */
    private String raison;
    
    /**
     * Don ou retrait.
     */
    private String action;
    
    /**
     * Si l'action est anonyme ou non.
     */
    private boolean anonyme;
    
    /**
     * Initialise une nouvelle instance de la classe {@link FormulaireDonnerRetirerPoints}.
     * @author Jean-Loup Naddef
     */
    public FormulaireDonnerRetirerPoints() {
    }
    
    public void remettreAZero() {
        nbPoints = null;
        destinataire = null;
        raison = "";
        anonyme = false;
    }

    public Integer getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(Integer nbPoints) {
        this.nbPoints = nbPoints;
    }

    public Integer getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Integer destinataire) {
        this.destinataire = destinataire;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isAnonyme() {
        return anonyme;
    }

    public void setAnonyme(boolean anonyme) {
        this.anonyme = anonyme;
    }
}
