//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 07/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.dao;

import druide.hibernate.HibernateUtil;
import druide.hibernate.pojo.Rang;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class RangDao {

    /**
     * Initialise une nouvelle instance de la classe {@link RangDao}.
     * @author Jean-Loup Naddef
     */
    public RangDao() {
    }
    
    /**
     * Retourne l'ensemble des rangs en base.
     * @return 
     */
    public List<Rang> trouverTous() {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT r FROM Rang r "
                + "order by r.palier asc";
        Query query = session.createQuery(hql);
        List<Rang> rangs = HibernateUtil.lister(query);
        return rangs;
    }
}
