//-------------------------------------------------------------
// Projet Perso Actidruide
// 
// Créé le : 07/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.dao;

import druide.hibernate.HibernateUtil;
import druide.hibernate.pojo.Utilisateur;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class UtilisateurDao {

    /**
     * Initialise une nouvelle instance de la classe {@link UtilisateurDao}.
     * @author Jean-Loup Naddef
     */
    public UtilisateurDao() {
    }
    
    
    /**
     * Obtient l'utilisatueur correspondant au login.
     * Charge également les GROUPES et ROLES de l'utilisateur.
     * @param login Le login de l'utilisateur à charger.
     * @return L'utilisateur correspondant au login.
     */
    public Utilisateur chargerParLogin(String login) {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT u FROM Utilisateur u " 
                + "LEFT OUTER JOIN FETCH u.rang r "
                + "LEFT OUTER JOIN FETCH r.permissions "
                + "WHERE u.login = :login";
        Query query = session.createQuery(hql).setParameter("login", login);
        Utilisateur utilisateur = (Utilisateur) query.uniqueResult();
        return utilisateur;
    }
    
    /**
     * Obtient l'utilisatueur correspondant à l'id.
     * Charge également les GROUPES et ROLES de l'utilisateur.
     * @param id L'id de l'utilisateur à charger.
     * @return L'utilisateur correspondant à l'id.
     */
    public Utilisateur chargerParId(Integer id) {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT u FROM Utilisateur u " 
                + "LEFT OUTER JOIN FETCH u.rang r "
                + "LEFT OUTER JOIN FETCH r.permissions "
                + "WHERE u.id = :id";
        Query query = session.createQuery(hql).setParameter("id", id);
        Utilisateur utilisateur = (Utilisateur) query.uniqueResult();
        return utilisateur;
    }
    
    /**
     * Sauvegarde un utilisateur.
     * @param utilisateur 
     */
    public void sauvegarder(Utilisateur utilisateur) {
        Session session = HibernateUtil.sessionCourante();
        session.saveOrUpdate(utilisateur);
    }
    
    /**
     * Récupère l'ensemble des utilisateurs, triés par points décroissants.
     * @return l'ensemble des utilisateurs, triés par points décroissants.
     */
    public List<Utilisateur> trouverTous() {
        Session session = HibernateUtil.sessionCourante();
        String hql = "SELECT u FROM Utilisateur u "
                + "order by u.nbPoints desc";
        Query query = session.createQuery(hql);
        List<Utilisateur> utilisateurs = HibernateUtil.lister(query);
        return utilisateurs;
    }
}
