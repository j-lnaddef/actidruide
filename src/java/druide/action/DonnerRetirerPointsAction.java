//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 15/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import com.opensymphony.xwork2.ModelDriven;
import druide.formulaire.FormulaireDonnerRetirerPoints;
import druide.metier.DonnerRetirerPointsMetier;
import druide.metier.ErreurMetier;
import druide.metier.ResultatMetierSimple;
import java.util.List;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class DonnerRetirerPointsAction extends BaseAction implements ModelDriven<FormulaireDonnerRetirerPoints> {

    /**
     * Le formulaire permettant de donner et retirer des points.
     */
    private FormulaireDonnerRetirerPoints formulaireDonnerRetirerPoints;
    
    /**
     * Initialise une nouvelle instance de la classe {@link DonnerRetirerPointsAction}.
     * @author Jean-Loup Naddef
     */
    public DonnerRetirerPointsAction() {
        formulaireDonnerRetirerPoints = new FormulaireDonnerRetirerPoints();
    }
    
    @Override
    public String execute() {
        
        String resultat = SUCCESS;
        
        DonnerRetirerPointsMetier donnerRetirerPointsMetier = new DonnerRetirerPointsMetier(this.getUtilisateur());
        ResultatMetierSimple resultatMetierSimple = donnerRetirerPointsMetier.donnerRetirerPoints(this.formulaireDonnerRetirerPoints);
        
        if (resultatMetierSimple.getErreurs().isEmpty()) {
            this.formulaireDonnerRetirerPoints.remettreAZero();
        }
        
        else {
            String message;
            List<ErreurMetier> erreurMetiers = resultatMetierSimple.getErreurs();
            for (ErreurMetier erreurMetier : erreurMetiers) {
                switch (erreurMetier.getCodeErreur()) {
                    case NombreDePointsNonValide :
                        message = "Le nombre de points n'est pas valide";
                        this.addFieldError("model.nbPoints", message);
                        this.addActionError(message);
                        break;
                        
                    case PasAssezDePointsDisponibles :  
                        message = "Vous n'avez pas assez assez de points pour effectuer cette action";
                        this.addFieldError("model.nbPoints", message);
                        this.addActionError(message);
                        break;
                    
                    case ChampRequisNonRenseigne : 
                        List<Object> parametres = erreurMetier.getParametres();
                        for (Object param : parametres) {
                            message = this.getText("Champ requis non renseigné : " + param.toString());
                            this.addFieldError("model." + param.toString(), message);
                            this.addActionError(message);
                        }
                        break;
                }
            }
        }
        
        return resultat;
    }
    
    @Override
    public FormulaireDonnerRetirerPoints getModel() {
        return this.formulaireDonnerRetirerPoints;
    }
}
