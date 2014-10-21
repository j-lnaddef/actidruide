//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 22/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.exception.UtilisateurNonDefiniException;
import druide.formulaire.ApercuClassement;
import druide.formulaire.InformationsPagePrincipale;
import druide.hibernate.pojo.Cagnotte;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Utilisateur;
import java.util.List;


/**
 * Métier récupérant les infomations de la page principale.
 * @author Jean-Loup Naddef
 */
public class PagePrincipaleMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link PagePrincipaleMetier}.
     * @author Jean-Loup Naddef
     */
    public PagePrincipaleMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
    /**
     * Récupère les informations à afficher sur la page principale.
     * @return les informations à afficher sur la page principale.
     */
    public ResultatMetier<InformationsPagePrincipale> recupererInformations() {
        
        if (this.getUtilisateur() == null) {
            this.lancerException(new UtilisateurNonDefiniException());
        }
        
        InformationsPagePrincipale informationsPagePrincipale = new InformationsPagePrincipale();
        
        informationsPagePrincipale.setNbPointsDetenus(this.getUtilisateur().getNbPoints());
        informationsPagePrincipale.setNbPointsRestants(this.getUtilisateur().getNbPointsRestantsAAttribuer());
        informationsPagePrincipale.setRang(this.getUtilisateur().getRang().getNom());
        
        // Récupération de l'historique des actions.
        HistoriqueActionMetier historiqueActionMetier = new HistoriqueActionMetier(this.getUtilisateur());
        
        this.ouvrirSessionHibernate();
        List<HistoriqueAction> historiqueActions = historiqueActionMetier.recupererDernieresActions();
        this.fermerSessionHibernate();
        
        informationsPagePrincipale.setHistoriqueActions(historiqueActions);
        
        // Récupération de l'aperçu du classement.
        ClassementMetier classementMetier = new ClassementMetier(this.getUtilisateur());
        
        this.ouvrirSessionHibernate();
        ApercuClassement apercuClassement = classementMetier.creationApercuClassement();
        this.fermerSessionHibernate();
        
        informationsPagePrincipale.setApercuClassement(apercuClassement);
        
        // Récupération des cagnottes actives.
        CagnotteMetier cagnotteMetier = new CagnotteMetier(this.getUtilisateur());
        
        this.ouvrirSessionHibernate();
        List<Cagnotte> cagnottes = cagnotteMetier.recupererCagnottesActives();
        this.fermerSessionHibernate();
        
        informationsPagePrincipale.setCagnottesActives(cagnottes);
        
        // Récupération de l'ensemble des utilisateurs pouvant être sélectionnés pour une action.
        
        UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
        this.ouvrirSessionHibernate();
        List<Utilisateur> utilisateurs = utilisateurMetier.recupererUtilisateursPossibles();
        this.fermerSessionHibernate();
        
        informationsPagePrincipale.setUtilisateursPossibles(utilisateurs);
        
        ResultatMetier<InformationsPagePrincipale> resultatMetier = new ResultatMetier<>();
        
        resultatMetier.setResultat(informationsPagePrincipale);
        return resultatMetier;
    }
    
}
