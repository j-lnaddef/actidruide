//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 20/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------

package druide.configuration;

import java.io.File;
import org.hibernate.cfg.Configuration;
import druide.hibernate.HibernateUtil;

/**
 * Utilitaire de configuration : facilite le chargement de toutes les configurations de l'application.
 * @author Clément VASSEUR
 */
public final class ConfigurationUtil {

    /**
     * Le nom de la variablement d'environnement utilisé pour obtenir le répertoire où se situe les configurations de l'application.
     */
    public static final String REPERTOIRE_CONFIGURATION = "ecnci_config_dir";
    
    /**
     * Le nom de la variablement d'environnement utilisé pour obtenir le nom de l'environnement applicatif (test, dev, prod...).
     */
    public static final String ENVIRONNEMENT = "ecnci_env";
    
    /**
     * Valeur indiquant si le chargement des configurations a déjà été effectué ou non.
     */
    private static boolean chargementEffectue;
       
    static {
        // Marquer le chargement comme pas encore effectué.
        chargementEffectue = false;
    }
    
    /**
     * Initialise une nouvelle instance de la classe ConfigurationUtil.
     */
    private ConfigurationUtil() {
    }
    
    /**
     * Obtient une valeur indiquant si le chargement des configurations a déjà été effectué ou non.
     * @return Vrai si le chargement a été effectué, faux sinon.
     */
    public static boolean chargementEffectue() {
        return chargementEffectue;
    }
    
    /**
     * Effectue le chargement de toutes les configurations utiles pour le bon déroulement de l'application.
     * Le chargement est effectué à partir des variables d'environnement 
     */
    public static void chargerConfiguration() {
        String repertoire = System.getProperty(REPERTOIRE_CONFIGURATION);
        String environnement = System.getProperty(ENVIRONNEMENT);
        
        if (repertoire == null || repertoire.equals("")) {
            String message = "Répertoire de configuration non fourni : "
                    + "la JVM doit être lancé avec le paramètre '" + REPERTOIRE_CONFIGURATION + "' "
                    + "Exemple: java -D" + REPERTOIRE_CONFIGURATION + "=/var/config ";
            throw new IllegalArgumentException(message);
        }
        
        if (environnement == null || environnement.equals("")) {
            String message = "Environnement non fourni : "
                    + "la JVM doit être lancé avec le paramètre '" + ENVIRONNEMENT + "' "
                    + "Exemple: java -D" + ENVIRONNEMENT + "=dev";
            throw new IllegalArgumentException(message);
        }
        
        chargerConfiguration(repertoire, environnement);
    }
    
    /**
     * Charge la configuration de l'application à partir d'un répertoire donné et un environnement donné.
     * @param repertoire Le répertoire de configuration de l'application.
     * @param environnement L'environnement de l'application.
     */
    public static void chargerConfiguration(String repertoire, String environnement) {

        if (chargementEffectue) {
            throw new IllegalStateException("La configuration ECN&CI ne peut être chargée, elle l'a déjà été");
        }

                
        // Initialisation de la configuration Hibernate
        Configuration configurationHibernate = new Configuration();

        // Acces JDBC
        configurationHibernate.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/actidruide");
        configurationHibernate.setProperty("hibernate.connection.username", "root");
        configurationHibernate.setProperty("hibernate.connection.password", "root");
  

        // Chargement des autres informations de configuration, non variables (ex: mapping)
        configurationHibernate.configure("/druide/hibernate/hibernate.cfg.xml");
        HibernateUtil.initialiserSessionFactory(configurationHibernate);
        
        // Chargement de la configuration log4j
        
        chargementEffectue = true;
    }
}
