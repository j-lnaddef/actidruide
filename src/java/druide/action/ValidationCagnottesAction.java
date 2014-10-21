//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 13/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.hibernate.pojo.Cagnotte;
import druide.metier.CagnotteMetier;
import java.util.List;


/**
 * Action de la validation des cagnottes.
 * @author Jean-Loup Naddef
 */
public class ValidationCagnottesAction extends BaseAction implements ModelDriven<List<Cagnotte>>{

    /**
     * La liste des cagnottes sans bénéficiaire;
     */
    private List<Cagnotte> cagnottes;
    
    /**
     * Initialise une nouvelle instance de la classe {@link ValidationCagnottesAction}.
     * @author Jean-Loup Naddef
     */
    public ValidationCagnottesAction() {
    }
    
    @Override
    public String execute() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        cagnottes = cagnotteMetier.recupererCagnottesSansBeneficiaire().getResultat();
        return SUCCESS;
    }

    @Override
    public List<Cagnotte> getModel() {
        return this.cagnottes;
    }

    public List<Cagnotte> getCagnottes() {
        return cagnottes;
    }

    public void setCagnottes(List<Cagnotte> cagnottes) {
        this.cagnottes = cagnottes;
    }
}
