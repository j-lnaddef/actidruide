//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 11/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.formulaire;


/**
 * Formulaire de changement de mot de passe.
 * @author Jean-Loup Naddef
 */
public class FormulaireChangementMdp {
    
    /**
     * L'ancien mot de passe.
     */
    private String ancienMdp;
    
    /**
     * Le nouveau mot de passe.
     */
    private String nouveauMdp;
    
    /**
     * Initialise une nouvelle instance de la classe {@link FormulaireChangementMdp}.
     * @author Jean-Loup Naddef
     */
    public FormulaireChangementMdp() {
    }

    public String getAncienMdp() {
        return ancienMdp;
    }

    public void setAncienMdp(String ancienMdp) {
        this.ancienMdp = ancienMdp;
    }

    public String getNouveauMdp() {
        return nouveauMdp;
    }

    public void setNouveauMdp(String nouveauMdp) {
        this.nouveauMdp = nouveauMdp;
    }
}
