//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 16/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.hibernate.pojo;

import java.util.Date;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class Cagnotte {
    
    private int id;
    
    private String titre;
    
    private int nombrePoints;
    
    private Utilisateur createur;
    
    private String actionARealiser;
    
    private Date dateEcheance;
    
    private Utilisateur beneficiaire;

    /**
     * Initialise une nouvelle instance de la classe {@link Cagnotte}.
     * @author Jean-Loup Naddef
     */
    public Cagnotte() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNombrePoints() {
        return nombrePoints;
    }

    public void setNombrePoints(int nombrePoints) {
        this.nombrePoints = nombrePoints;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public String getActionARealiser() {
        return actionARealiser;
    }

    public void setActionARealiser(String ActionARealiser) {
        this.actionARealiser = ActionARealiser;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Utilisateur getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(Utilisateur beneficiaire) {
        this.beneficiaire = beneficiaire;
    }
    
}
