//-------------------------------------------------------------
// Projet perso Actidruide
// 
// Créé le : 07/09/2014
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
package druide.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Opérations courantes avec Hibernate : gestion des sessions.
 *
 * @author Jean-Loup NADDEF
 */
public class HibernateUtil {

    /**
     * SessionFactory d'Hibernate.
     */
    private static SessionFactory sessionFactory;
    /**
     * Session courante d'Hibernate.
     */
    private static ThreadLocal<Session> session;

    static {
        sessionFactory = null;
        session = new ThreadLocal<Session>();
    }

    /**
     * Initialise la session factory d'Hibernate, à partir d'une configuration donnée.
     *
     * @param configuration La configuration Hibernate
     */
    public static void initialiserSessionFactory(Configuration configuration) {
        if (sessionFactory != null) {
            throw new IllegalStateException("La session factory Hibernate est déjà initialisée.");
        }

        if (configuration == null) {
            throw new NullPointerException("La configuration Hibernate est obligatoire");
        }

        try {
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (HibernateException ex) {
            throw new RuntimeException("Erreur lors de l'initialisation de la session factory Hibernate", ex);
        }
    }

    /**
     * Détermine si une session Hibernate a été créée et est ouverte.
     *
     * @return Vrai si la session Hibernate est ouverte, faux sinon.
     */
    public static boolean sessionOuverte() {
        Session s = session.get();
        return s != null && s.isOpen();
    }

    /**
     * Obtient la session courante. Si la session n'a pas encore été créée, elle est créée.
     *
     * @return La session courante
     * @throws HibernateException
     */
    public static Session sessionCourante() {
        Session s = session.get();

        // Ouvre une nouvelle session, si le thread courant n'en a pas déjà une.
        if (s == null) {
            if (!estSessionFactoryInitialise()) {
                throw new IllegalStateException("La session factory doit avoir été initialisée avant de créer une session Hibernate.");
            }

            s = sessionFactory.openSession();
            session.set(s);
        }

        return s;
    }

    /**
     * Ferme la session courante. Si aucune session n'a été créé, l'appel est sans effet.
     *
     * @throws HibernateException
     */
    public static void fermerSessionCourante() {
        Session s = session.get();
        if (s != null && s.isOpen()) {
            try {
                s.close();
            }
            catch (HibernateException exception) {
                throw new RuntimeException("Erreur lors de la fermeture d'une session Hibernate", exception);
            }
        }

        session.set(null);
    }

    /**
     * Teste si la session factory d'Hibernate a déjà été initialisée.
     *
     * @return Vrai si la session factory a déjà été initialisée, faux sinon.
     */
    public static boolean estSessionFactoryInitialise() {
        return sessionFactory != null;
    }

    /**
     * Obtient la session factory d'Hibernate. Si celle-ci n'a pas été initialisée, une exception est lancée.
     *
     * @return La session factory Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        if (!estSessionFactoryInitialise()) {
            throw new RuntimeException("La session factory d'Hibernate n'a pas été initalisée. "
                    + "Appelez d'abord la méthode initialiserSessionFactory");
        }

        return sessionFactory;
    }

    /**
     * Ferme la session factory d'Hibernate. Si celle-ci n'a pas été initialisée, l'appel est sans effet.
     */
    public static void fermerSessionFactory() {
        try {
            if (estSessionFactoryInitialise()) {
                getSessionFactory().close();
            }
        }
        catch (HibernateException exception) {
            throw new RuntimeException("Erreur lors de la fermeture de la session factory Hibernate.", exception);
        }

        sessionFactory = null;
    }

    /**
     * Execute une requête Hibernate "list".
     *
     * @param <T> Le type de retour des objets de la liste.
     * @param q La requête Hibernate.
     * @return La liste des objets
     */
    public static <T> List<T> lister(Query q) {
        @SuppressWarnings("unchecked")
        List<T> list = q.list();
        return list;
    }

    /**
     * Execute une requête Hibernate "list".
     *
     * @param <T> Le type de retour des objets de la liste.
     * @param criteria Les critères Hibernate.
     * @return La liste des objets
     */
    public static <T> List<T> lister(Criteria criteria) {
        @SuppressWarnings("unchecked")
        List<T> list = criteria.list();
        return list;
    }
}
