//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 20/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

package druide.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import druide.configuration.ConfigurationUtil;
import druide.hibernate.HibernateUtil;

/**
 * Classe écoutant les événements de création/destructiond du ServletContext de l'application.
 * @author Clément VASSEUR
 */
public class ApplicationListener implements ServletContextListener {

    /**
     * Réagit à l'événement de création du ServletContext.
     * Initialise la configuration de l'application.
     * @param sce Paramètre de l'événément.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialisation de la configuration spécifique à ECN&CI
        ConfigurationUtil.chargerConfiguration();
    }

    /**
     * Réagit à l'événement de destruction du ServletContext.
     * @param sce Paramètre de l'événément.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.fermerSessionFactory();
    }
}
