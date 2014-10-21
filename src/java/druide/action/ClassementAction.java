//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 24/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.formulaire.ClassementActidruide;
import druide.metier.ClassementMetier;
import druide.metier.ResultatMetier;


/**
 * Action d'affichage du classement.
 * @author Jean-Loup Naddef
 */
public class ClassementAction extends BaseAction implements ModelDriven<ClassementActidruide> {

    /**
     * Le modèle sur lequel on affiche le classement.
     */
    private ClassementActidruide classementActidruide;
    
    /**
     * Initialise une nouvelle instance de la classe {@link ClassementAction}.
     * @author Jean-Loup Naddef
     */
    public ClassementAction() {
        this.classementActidruide = new ClassementActidruide();
    }
    
    @Override
    public String execute() {
        ClassementMetier classementMetier = new ClassementMetier(this.getUtilisateur());
        ResultatMetier<ClassementActidruide> resultatMetier = classementMetier.retournerClassementActidruide();
        
        this.classementActidruide = resultatMetier.getResultat();
        
        ResultatAjax resultatAjax = new ResultatAjax();
        resultatAjax.addFreemarkerTemplate("popup_classement.ftl");
        this.setResultatAjax(resultatAjax);
        return AJAX_JSON;
    }

    @Override
    public ClassementActidruide getModel() {
        return this.classementActidruide;
    }
    
}
