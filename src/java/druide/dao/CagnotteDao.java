//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 23/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.dao;

import druide.hibernate.HibernateUtil;
import druide.hibernate.pojo.Cagnotte;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Dao des cagnottes.
 * @author Jean-Loup Naddef
 */
public class CagnotteDao {

    /**
     * Initialise une nouvelle instance de la classe {@link CagnotteDao}.
     * @author Jean-Loup Naddef
     */
    public CagnotteDao() {
    }
    
    /**
     * Retourne l'ensemble des cagnottes, ordonnées par date d'échéance décroissante.
     * @return 
     */
    public List<Cagnotte> trouverToutes() {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT c FROM Cagnotte c "
                + "order by c.dateEcheance desc";
        Query query = session.createQuery(hql);
        List<Cagnotte> cagnottes = HibernateUtil.lister(query);
        return cagnottes;
    }
    
    /**
     * Retourne l'ensemble des cagnottes actives, ordonnées par date d'échéance croissante.
     * @return 
     */
    public List<Cagnotte> trouverActives() {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT c FROM Cagnotte c "
                + "order by c.dateEcheance asc";
        Query query = session.createQuery(hql);
        List<Cagnotte> cagnottes = HibernateUtil.lister(query);
        return cagnottes;
    }
    
    /**
     * Récupère une cagnotte à partir de son identifiant.
     * @param id l'identifiant de la cagnotte à récupérer.
     * @return la cagnotte récupérée.
     */
    public Cagnotte trouverParId(Integer id) {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT c FROM Cagnotte c "
                + "WHERE c.id = :id ";
        Query query = session.createQuery(hql).setParameter("id", id);
        Cagnotte cagnotte = (Cagnotte) query.uniqueResult();
        return cagnotte;
    }
    
    public void save(Cagnotte cagnotte) {
        Session session = HibernateUtil.sessionCourante();
        session.saveOrUpdate(cagnotte);
    }
}
