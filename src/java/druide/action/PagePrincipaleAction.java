//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 16/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.formulaire.InformationsPagePrincipale;
import druide.metier.PagePrincipaleMetier;
import druide.metier.ResultatMetier;


/**
 * Action d'affichage de la page principale.
 * @author Jean-Loup Naddef
 */
public class PagePrincipaleAction extends BaseAction implements ModelDriven<InformationsPagePrincipale> {
    
    /**
     * Les informations à afficher sur la page.
     */
    private InformationsPagePrincipale informationsPagePrincipale;

    /**
     * Initialise une nouvelle instance de la classe {@link PagePrincipaleAction}.
     * @author Jean-Loup Naddef
     */
    public PagePrincipaleAction() {
        super();
    }
    
    @Override
    public String execute() {
        PagePrincipaleMetier pagePrincipaleMetier = new PagePrincipaleMetier(this.getUtilisateur());
        ResultatMetier<InformationsPagePrincipale> resultatMetier = pagePrincipaleMetier.recupererInformations();
        this.informationsPagePrincipale = resultatMetier.getResultat();
        
        return SUCCESS;
    }

    @Override
    public InformationsPagePrincipale getModel() {
        return this.informationsPagePrincipale;
    }
    
}
