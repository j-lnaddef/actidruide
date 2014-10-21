//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 16/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;

import java.util.Date;


/**
 * Champs du formulaire de création d'une cagnotte.
 * @author Jean-Loup Naddef
 */
public class FormulaireCreationCagnotte {
    
    /**
     * Le titre de la cagnotte.
     */
    private String titre;
    
    /**
     * Le montant initial mis dans la cagnotte.
     */
    private int montant;
    
    /**
     * L'action à réaliser pour gagner le contenu de la cagnotte.
     */
    private String actionARealiser;
    
    /**
     * La date d'échéance de la cagnotte.
     */
    private Date dateEcheance;

    /**
     * Initialise une nouvelle instance de la classe {@link FormulaireCreationCagnotte}.
     * @author Jean-Loup Naddef
     */
    public FormulaireCreationCagnotte() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getActionARealiser() {
        return actionARealiser;
    }

    public void setActionARealiser(String actionARealiser) {
        this.actionARealiser = actionARealiser;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }
    
}
