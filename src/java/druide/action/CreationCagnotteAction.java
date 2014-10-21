//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 16/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.formulaire.FormulaireCreationCagnotte;
import druide.metier.CagnotteMetier;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class CreationCagnotteAction extends BaseAction implements ModelDriven<FormulaireCreationCagnotte> {

    /**
     * Le formulaire de création de la cagnotte.
     */
    private FormulaireCreationCagnotte formulaireCreationCagnotte;
    
    /**
     * Initialise une nouvelle instance de la classe {@link CreationCagnotteAction}.
     * @author Jean-Loup Naddef
     */
    public CreationCagnotteAction() {
        this.formulaireCreationCagnotte = new FormulaireCreationCagnotte();
    }
    
    @Override
    public String execute() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        cagnotteMetier.creerCagnotte(formulaireCreationCagnotte);
        return SUCCESS;
    }

    @Override
    public FormulaireCreationCagnotte getModel() {
        return this.formulaireCreationCagnotte;
    }
}
