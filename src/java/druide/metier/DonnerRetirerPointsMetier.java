//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 15/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.dao.HistoriqueActionDao;
import java.util.Objects;
import druide.dao.UtilisateurDao;
import druide.exception.UtilisateurNonDefiniException;
import druide.formulaire.FormulaireDonnerRetirerPoints;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Utilisateur;
import java.util.ArrayList;
import java.util.List;


/**
 * Métier du don/retrait de points.
 * @author Jean-Loup Naddef
 */
public class DonnerRetirerPointsMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link DonnerRetirerPointsMetier}.
     * @author Jean-Loup Naddef
     */
    public DonnerRetirerPointsMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
    /**
     * Donne ou retire des points à un utilisateur.
     * @param formulaire le formulaire de don ou de retrait de points.
     * @return si l'opération s'est bien passée.
     */
    public ResultatMetierSimple donnerRetirerPoints(FormulaireDonnerRetirerPoints formulaire) {
        Objects.requireNonNull(formulaire, "Le formulaire ne peut pas être nul");
        Objects.requireNonNull(formulaire.getAction(), "L'action ne peut pas être nulle");
        
        if (this.getUtilisateur() == null) {
            this.lancerException(new UtilisateurNonDefiniException());
        }
        
        // Si l'utilisateur essaye de se donner des points à lui-même...
        if (this.getUtilisateur().getId().equals(formulaire.getDestinataire())) {
            this.ouvrirSessionHibernate();
            // On le punit!
            this.punirTriche();
            this.fermerSessionHibernate();
            return new ResultatMetierSimple();
        }
        
        // Si l'utilisateur n'a pas le droit pour l'action, on lance une exception.
        if (!DroitsUtilisateurs.verifierDroits(this.getUtilisateur(), formulaire.getAction())) {
            throw new IllegalAccessError("Vous n'avez pas les droits pour effectuer cette action");
        }
        
        List<ErreurMetier> erreurMetiers = this.verifierValiditeFormulaire(formulaire);
        
        ResultatMetierSimple resultatMetierSimple = new ResultatMetierSimple();
        
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        
        if (erreurMetiers.isEmpty()) {
            // Si l'utilisateur n'a pas assez de points pour l'action, on crée une erreur métier.
            if (!DroitsUtilisateurs.pointsSuffisants(this.getUtilisateur(), formulaire.getNbPoints())) {
                erreurMetiers.add(new ErreurMetier(CodeMetier.PasAssezDePointsDisponibles));
            }

            // Si le nombre de points à donner/retirer est inférieur à 1, on crée une erreur métier.
            else if (formulaire.getNbPoints() < 1) {
                erreurMetiers.add(new ErreurMetier(CodeMetier.NombreDePointsNonValide));
            }      
        }
        
        if (erreurMetiers.isEmpty()) {
            this.ouvrirSessionHibernate();
                Utilisateur destinataire = utilisateurDao.chargerParId(formulaire.getDestinataire());
            this.fermerSessionHibernate();

            // Si le destinataire n'existe pas on lance une exception.
            if (destinataire == null) {
                throw new IllegalArgumentException("L'utilisateur cible n'a pas été trouvé");
            }
            if (formulaire.getAction().equals("donner_points")) {
                destinataire.setNbPoints(destinataire.getNbPoints() + formulaire.getNbPoints());
            }
            else if (formulaire.getAction().equals("retirer_points")) {
                destinataire.setNbPoints(destinataire.getNbPoints() - formulaire.getNbPoints());
            }
            this.getUtilisateur().setNbPointsRestantsAAttribuer(this.getUtilisateur().getNbPointsRestantsAAttribuer() - formulaire.getNbPoints());
            
            // Et on ajoute l'action dans l'historique des actions.
            HistoriqueAction historiqueAction = new HistoriqueAction();
            historiqueAction.setAction(formulaire.getAction());
            historiqueAction.setCible(destinataire);
            historiqueAction.setNombrePoints(formulaire.getNbPoints());
            historiqueAction.setRaison(formulaire.getRaison());
            historiqueAction.setSource(this.getUtilisateur());
            
            HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
            this.ouvrirSessionHibernate();
            historiqueActionDao.sauvegarder(historiqueAction);
            
            // Avant de sauvegarder l'utilisateur, on vérifie si il ne change pas de rang.
            UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
            utilisateurMetier.verifierChangementRang(destinataire);
            
            utilisateurDao.sauvegarder(destinataire);
            utilisateurDao.sauvegarder(this.getUtilisateur());
            this.fermerSessionHibernate();
            
        }
        
        resultatMetierSimple.setErreurs(erreurMetiers);
        
        return resultatMetierSimple;
    }
    
    /**
     * Vérifie que tous les champs ont bien été remplis.
     * @param formulaire le formulaire à vérifier.
     * @return la liste des erreurs métier.
     */
    private List<ErreurMetier> verifierValiditeFormulaire(FormulaireDonnerRetirerPoints formulaire) {
        List<ErreurMetier> erreurMetiers = new ArrayList<>();
        
        // On crée l'erreur métier concernant les champs manquants, puis on lui ajoute des paramètres selon les champs manquants.
        ErreurMetier erreurChampManquant = new ErreurMetier(CodeMetier.ChampRequisNonRenseigne);
        
        if (formulaire.getDestinataire() == null) {
            erreurChampManquant.ajouterParametre("destinataire");
        }
        
        if (formulaire.getNbPoints()== null) {
            erreurChampManquant.ajouterParametre("nbPoints");
        }
        
        if (!erreurChampManquant.getParametres().isEmpty()) {
            erreurMetiers.add(erreurChampManquant);
        }
        
        return erreurMetiers;
    }
    
    /**
     * Punit un utilisateur qui essaye de s'ajouter des points à lui-même.
     */
    private void punirTriche() {
        this.getUtilisateur().setNbPoints(this.getUtilisateur().getNbPoints() - 5);
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        utilisateurDao.sauvegarder(this.getUtilisateur());
        
        HistoriqueAction historiqueAction = new HistoriqueAction();
        historiqueAction.setAction("triche");
        historiqueAction.setCible(this.getUtilisateur());
        historiqueAction.setNombrePoints(5);
        historiqueAction.setRaison("Avoir essayé de s'auto-attribuer des points");
        
        HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
        historiqueActionDao.sauvegarder(historiqueAction);
    }
    
}
