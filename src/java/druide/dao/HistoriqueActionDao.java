//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 22/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.dao;

import druide.hibernate.HibernateUtil;
import druide.hibernate.pojo.HistoriqueAction;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Dao des historiques d'action.
 * @author Jean-Loup Naddef
 */
public class HistoriqueActionDao {

    /**
     * Initialise une nouvelle instance de la classe {@link HistoriqueActionDao}.
     * @author Jean-Loup Naddef
     */
    public HistoriqueActionDao() {
    }
    
    /**
     * Trouve les 10 dernier historiques d'action.
     * @return la liste des 10 derniers historiques d'action.
     */
    public List<HistoriqueAction> trouverDernieresActions() {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT h FROM HistoriqueAction h " 
                + "LEFT OUTER JOIN FETCH h.source "
                + "LEFT OUTER JOIN FETCH h.cible "
                + "order by h.id desc";
        Query query = session.createQuery(hql);
        query.setMaxResults(10);
        
        List<HistoriqueAction> historiqueAction = HibernateUtil.lister(query);
        return historiqueAction;
    }
    
    /**
     * Sauvegarde d'un historique d'action.
     * @param historiqueAction 
     */
    public void sauvegarder (HistoriqueAction historiqueAction) {
        Session session = HibernateUtil.sessionCourante();
        session.saveOrUpdate(historiqueAction);
    }
}
