//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 24/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.hibernate.pojo.Cagnotte;
import druide.metier.CagnotteMetier;
import druide.metier.ResultatMetier;
import druide.metier.ResultatMetierSimple;


/**
 * Action des cagnottes.
 * @author Jean-Loup Naddef
 */
public class CagnotteAction extends BaseAction implements ModelDriven<Cagnotte> {

    /**
     * La cagnotte consultée.
     */
    private Cagnotte cagnotteCourante;
    
    /**
     * Le nombre de points que l'utilisateur souhaite ajouter à la cagnotte.
     */
    private Integer nbPointsAjoutes;
    
    /**
     * Initialise une nouvelle instance de la classe {@link CagnotteAction}.
     * @author Jean-Loup Naddef
     */
    public CagnotteAction() {
        this.cagnotteCourante = new Cagnotte();
    }
    
    @Override
    public String execute() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        ResultatMetier<Cagnotte> resultatMetier = cagnotteMetier.recupererCagnotte(this.cagnotteCourante.getId());
        this.cagnotteCourante = resultatMetier.getResultat();
        
        ResultatAjax resultatAjax = new ResultatAjax();
        resultatAjax.addFreemarkerTemplate("popup_cagnotte.ftl");
        this.setResultatAjax(resultatAjax);
        
        return AJAX_JSON;
    }
    
    /**
     * Permet de rajouter des points dans une cagnotte.
     * @return le code de résultat. 
     */
    public String ajouterPoints() {
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        ResultatMetierSimple resultatMetierSimple = cagnotteMetier.ajouterPointsCagnotte(this.cagnotteCourante.getId(), this.nbPointsAjoutes);
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

    public Integer getNbPointsAjoutes() {
        return nbPointsAjoutes;
    }

    public void setNbPointsAjoutes(Integer nbPointsAjoutes) {
        this.nbPointsAjoutes = nbPointsAjoutes;
    }
    
}
