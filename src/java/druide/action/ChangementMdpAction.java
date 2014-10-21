//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 11/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.formulaire.FormulaireChangementMdp;
import druide.metier.ChangementMdpMetier;
import druide.metier.ResultatMetierSimple;


/**
 * Action de changement du mot de passe de l'utilisateur.
 * @author Jean-Loup Naddef
 */
public class ChangementMdpAction extends BaseAction implements ModelDriven<FormulaireChangementMdp> {

    
    /**
     * Le formulaire de changement de mot de passe.
     */
    private FormulaireChangementMdp formulaire;
    
    /**
     * Initialise une nouvelle instance de la classe {@link ChangementMdpAction}.
     * @author Jean-Loup Naddef
     */
    public ChangementMdpAction() {
        formulaire = new FormulaireChangementMdp();
    }
    
    @Override 
    public String execute() {
        ResultatAjax resultatAjax = new ResultatAjax();
        resultatAjax.addFreemarkerTemplate("popup_changement_mdp.ftl");

        this.setResultatAjax(resultatAjax);
        return AJAX_JSON;    
    }
    
    /**
     * Modifie le mot de passe de l'utilisateur.
     * @return 
     */
    public String modifierMdp() {
        ChangementMdpMetier changementMdpMetier = new ChangementMdpMetier(this.getUtilisateur());
        ResultatMetierSimple resultatMetierSimple = changementMdpMetier.changerMdp(formulaire.getAncienMdp(), formulaire.getNouveauMdp());
        ResultatAjax resultatAjax = new ResultatAjax();
        resultatAjax.addFreemarkerTemplate("popup_changement_mdp.ftl");
        
        if (!resultatMetierSimple.getErreurs().isEmpty()) {
            this.addActionError("Le mot de passe est incorrect");
            this.addActionMessage("Le mot de passe est incorrect");
            this.addFieldError("model.ancienMdp", "Le mot de passe est incorrect");
            resultatAjax.addErreur(resultatMetierSimple.getErreurs().get(0).getCodeErreur().name());
        }
        
        this.setResultatAjax(resultatAjax);
        
        return AJAX_JSON;
    }

    @Override
    public FormulaireChangementMdp getModel() {
        return formulaire;
    }

    public FormulaireChangementMdp getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(FormulaireChangementMdp formulaire) {
        this.formulaire = formulaire;
    }
}
