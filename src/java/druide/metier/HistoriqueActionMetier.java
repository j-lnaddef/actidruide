//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 23/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.dao.HistoriqueActionDao;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Utilisateur;
import java.util.List;


/**
 * Le métier des historiques d'action.
 * @author Jean-Loup Naddef
 */
public class HistoriqueActionMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link HistoriqueActionMetier}.
     * @author Jean-Loup Naddef
     */
    public HistoriqueActionMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
    /**
     * Récupération des dernières actions.
     * @return les dernières actions.
     */
    public List<HistoriqueAction> recupererDernieresActions() {
        HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
        List<HistoriqueAction> historiqueActions = historiqueActionDao.trouverDernieresActions();
        return historiqueActions;
    }
}
