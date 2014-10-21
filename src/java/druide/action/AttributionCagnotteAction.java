//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 13/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ModelDriven;
import static druide.action.BaseAction.AJAX_JSON;
import druide.hibernate.pojo.Cagnotte;
import druide.metier.CagnotteMetier;
import druide.metier.ResultatMetier;
import druide.metier.ResultatMetierSimple;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class AttributionCagnotteAction extends BaseAction implements ModelDriven<Cagnotte>{
    
    /**
     * La cagnotte consultée.
     */
    private Cagnotte cagnotteCourante;
    
    /**
     * Le bénéficiaire de la cagnotte.
     */
    private Integer destinataire;
    

    /**
     * Initialise une nouvelle instance de la classe {@link AttributionCagnotteAction}.
     * @author Jean-Loup Naddef
     */
    public AttributionCagnotteAction() {
        this.cagnotteCourante = new Cagnotte();
    }
    
    @Override
    public String execute() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        ResultatMetier<Cagnotte> resultatMetier = cagnotteMetier.recupererCagnotte(this.cagnotteCourante.getId());
        this.cagnotteCourante = resultatMetier.getResultat();
        
        ResultatAjax resultatAjax = new ResultatAjax();
        resultatAjax.addFreemarkerTemplate("administration/popup_attribution_cagnotte.ftl");
        this.setResultatAjax(resultatAjax);
        
        return AJAX_JSON;
    }
    
    /**
     * Permet de rajouter des points dans une cagnotte.
     * @return le code de résultat. 
     */
    public String attribuerCagnotte() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        try {
            ResultatMetierSimple resultatMetierSimple = cagnotteMetier.attribuerPointsCagnottes(this.cagnotteCourante.getId(), this.destinataire);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(AttributionCagnotteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SUCCESS;
    }

    @Override
    public Cagnotte getModel() {
        return this.cagnotteCourante;
    }

    public Cagnotte getCagnotteCourante() {
        return cagnotteCourante;
    }

    public void setCagnotteCourante(Cagnotte cagnotteCourante) {
        this.cagnotteCourante = cagnotteCourante;
    }

    public Integer getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Integer beneficiaire) {
        this.destinataire = beneficiaire;
    }

}
