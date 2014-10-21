//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.metier;

import java.util.Objects;
import java.util.ResourceBundle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import druide.hibernate.HibernateUtil;
import druide.hibernate.pojo.Utilisateur;

/**
 * Classe métier de base pour tous les autres métiers.
 * @author Clément VASSEUR
 */
public abstract class BaseMetier {

    /**
     * Les textes utilisés pour obtenir des valeurs en i18n.
     */
    private ResourceBundle textes;
    
    /**
     * Utilisateur effectuant l'action métier.
     */
    private Utilisateur utilisateur;

    /**
     * Initialise une nouvelle instance de la classe BaseMetier.
     * @param utilisateur Utilisateur effectuant l'action métier.
     */
    public BaseMetier(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
    }
    
    /**
     * Initialise une nouvelle instance de la classe BaseMetier.
     */
    public BaseMetier() {
        this(null);
    }

    /**
     * Vérifie que l'utilisateur est bien défini.
     * @throws UtilisateurNonDefiniException Si l'utilisateur n'est pas défini dans la classe métier.
     */
    /*
    public void verifierUtilisateur() throws UtilisateurNonDefiniException {
        if (this.getUtilisateur() == null) {
            this.lancerException(new UtilisateurNonDefiniException());
        }
    }
    */
    
    /**
     * Vérifie que l'utilisateur est bien défini, et qu'il possède la permission nécessaire pour effectuer l'action. Si tel n'est pas le
     * cas, une exception est lancée.
     * @param permission La permission à vérifier.
     * @throws UtilisateurNonDefiniException Si l'utilisateur n'est pas défini dans la classe métier.
     * @throws PermissionException           Si l'utilisateur n'a pas la permission requise
     */
    
    /*
    public void verifierUtilisateur(String permission) throws UtilisateurNonDefiniException, PermissionException {
        this.verifierUtilisateur();
        
        if ((permission != null) && (!this.getUtilisateur().aPermission(permission))) {
            this.lancerException(new PermissionException(permission));
        }
    }
    */
    
    
    
    /**
     * Obtient l'utilisateur effectuant l'action métier.
     * @return l'utilisateur effectuant l'action métier.
     */
    public final Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    /**
     * Définit l'utilisateur effectuant l'action métier.
     * @param utilisateur l'utilisateur effectuant l'action métier.
     */
    public final void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    /**
     * Lance une exception quelconque (généralement une {@link mesr.ecnci.exception.MetierException}). 
     * Si la session Hibernate est ouverte, elle est fermée préalablement.
     * Si une transaction est en cours, elle est rollback.
     * @param exceptionMetier L'exception à lancer.
     * @throws RuntimeException L'exception que l'on veut lancer.
     */
    public void lancerException(RuntimeException exceptionMetier) throws RuntimeException {
        if (HibernateUtil.sessionOuverte()) {
            Transaction transaction = HibernateUtil.sessionCourante().getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            
            HibernateUtil.fermerSessionCourante();
        }
        
        throw exceptionMetier;
    }
    
    /**
     * Ouvre une nouvelle session Hibernate. Crée automatiquement une transaction.
     * @return La nouvelle Session Hibernate.
     */
    public Session ouvrirSessionHibernate() {
        if (HibernateUtil.sessionOuverte()) {
            throw new IllegalStateException("La session Hibernate ne peut pas être ouverte, elle l'est déjà.");
        }
        
        // Démarrer automatiquement une transaction
        HibernateUtil.sessionCourante().beginTransaction();
        return HibernateUtil.sessionCourante();
    }
    
    /**
     * Ferme la session Hibernate actuelle. Commit automatiquement la transaction courante.
     */
    public void fermerSessionHibernate() {
        if (!HibernateUtil.sessionOuverte()) {
            throw new IllegalStateException("La session Hibernate ne peut pas être fermée, elle n'est pas ouverte.");
        }
        
        // Commit la transaction courante
        Transaction transaction = HibernateUtil.sessionCourante().getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
        
        HibernateUtil.fermerSessionCourante();
    }
    
    /**
     * Obtient un texte en i18n.
     * Les textes doivent être chargés avant d'appeler cette méthode : {@link chargerTextesExport}.
     * @param cle La clé du texte dans le fichier properties.
     * @return Le texte en i18n.
     */
    public String getTexte(String cle) {
        Objects.requireNonNull(this.textes, "Les textes ne sont pas chargés et sont nuls");
        return this.textes.getString(cle);
    }
    
    /**
     * Obtient un texte en i18n et remplace les arguments par des valeurs des paramètres.
     * Les textes doivent être chargés avant d'appeler cette méthode : {@link chargerTextesExport}.
     * @param cle La clé du texte dans le fichier properties.
     * @param parametres Les paramètres associés aux arguments.
     * @return Le texte en i18n.
     */
    public String getTexte(String cle, String[] parametres) {
        Objects.requireNonNull(this.textes, "Les textes ne sont pas chargés et sont nuls");
        String texteFinal = this.textes.getString(cle);
        for (int i = 0; i < parametres.length; i++) {
            texteFinal = texteFinal.replace("{" + i + "}", parametres[i]);
        }
        
        return texteFinal;
    }
    
    /**
     * Charge les textes affichés dans les exports (CSV, PDF, etc.).
     */
    public void chargerTextesExport() {
        this.chargerTextes("export");
    }
    
    /**
     * Charge un fichier de propriétés quelconque, étant donné son nom.
     * @param nomFichierProperties Le nom du fichier properties, sans le ".properties", et sans éventuelle locale (fr_FR).
     */
    private void chargerTextes(String nomFichierProperties) {
        this.textes = ResourceBundle.getBundle("mesr/ecnci/texte/" + nomFichierProperties);
    }
}
