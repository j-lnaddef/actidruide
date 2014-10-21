//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 23/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.dao.CagnotteDao;
import druide.dao.HistoriqueActionDao;
import druide.dao.UtilisateurDao;
import druide.formulaire.FormulaireCreationCagnotte;
import druide.hibernate.pojo.Cagnotte;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Utilisateur;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Métier des cagnottes.
 * @author Jean-Loup Naddef
 */
public class CagnotteMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link CagnotteMetier}.
     * @author Jean-Loup Naddef
     */
    public CagnotteMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
    /**
     * Crée une cagnotte à partir du formulaire passé en paramètre.
     * @param formulaire le formulaire de création d'une cagnotte.
     * @return les éventuelles erreurs métier.
     */
    public ResultatMetierSimple creerCagnotte(FormulaireCreationCagnotte formulaire) {
        ResultatMetierSimple resultatMetierSimple = new ResultatMetierSimple();
        List<ErreurMetier> erreurMetiers = this.verifierValiditeFormulaire(formulaire);
        
        // Si l'utilisateur n'a pas le droit pour l'action, on lance une exception.
        if (!DroitsUtilisateurs.verifierDroits(this.getUtilisateur(), "creer_cagnotte")) {
            throw new IllegalAccessError("Vous n'avez pas les droits pour effectuer cette action");
        }
        
        // Si l'utilisateur n'a pas assez de points pour l'action, on crée une erreur métier.
        if (!DroitsUtilisateurs.pointsSuffisants(this.getUtilisateur(), formulaire.getMontant())) {
            erreurMetiers.add(new ErreurMetier(CodeMetier.PasAssezDePointsDisponibles));
        }
        
        // Si le nombre de points à donner/retirer est inférieur à 1, on crée une erreur métier.
        else if (formulaire.getMontant() < 1) {
            erreurMetiers.add(new ErreurMetier(CodeMetier.NombreDePointsNonValide));
        }      
        
        if (erreurMetiers.isEmpty()) {
            this.getUtilisateur().setNbPointsRestantsAAttribuer(this.getUtilisateur().getNbPointsRestantsAAttribuer() - formulaire.getMontant());
            
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            this.ouvrirSessionHibernate();
            utilisateurDao.sauvegarder(this.getUtilisateur());
            this.fermerSessionHibernate();
            
            Cagnotte cagnotte = new Cagnotte();
            cagnotte.setActionARealiser(formulaire.getActionARealiser());
            if (formulaire.getDateEcheance() != null) {
                cagnotte.setDateEcheance(formulaire.getDateEcheance());
            }
            cagnotte.setCreateur(this.getUtilisateur());
            cagnotte.setNombrePoints(formulaire.getMontant());
            cagnotte.setTitre(formulaire.getTitre());
            
            CagnotteDao cagnotteDao = new CagnotteDao();
            this.ouvrirSessionHibernate();
            cagnotteDao.save(cagnotte);
            this.fermerSessionHibernate();
            
            // Et on ajoute l'action dans l'historique des actions.
            HistoriqueAction historiqueAction = new HistoriqueAction();
            historiqueAction.setAction("creation_cagnotte");
            historiqueAction.setNombrePoints(formulaire.getMontant());
            historiqueAction.setRaison(formulaire.getActionARealiser());
            historiqueAction.setSource(this.getUtilisateur());
            
            HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
            this.ouvrirSessionHibernate();
            historiqueActionDao.sauvegarder(historiqueAction);
            this.fermerSessionHibernate();
        }
        
        resultatMetierSimple.setErreurs(erreurMetiers);
        
        return resultatMetierSimple;
    }
    
    /**
     * Récupère la liste des 5 cagnottes actives ayant la date d'échéance la plus proche.
     * @return la liste des 5 cagnottes actives ayant la date d'échéance la plus proche.
     */
    public List<Cagnotte> recupererCagnottesActives() {
        CagnotteDao cagnotteDao = new CagnotteDao();
        List<Cagnotte> cagnottes = cagnotteDao.trouverActives();
        List<Cagnotte> cagnottesActives = new ArrayList<>(cagnottes);
        Date dateCourante = new Date();
        // On retire les cagnottes dont la date d'échéance est passée.
        for (Cagnotte cagnotte : cagnottes) {
            if (cagnotte.getDateEcheance() != null && cagnotte.getDateEcheance().before(dateCourante) || cagnotte.getBeneficiaire() != null) {
                cagnottesActives.remove(cagnotte);
            }
        }
        
        if (cagnottesActives.size() > 5) {
            List<Cagnotte> premieresCagnottes = cagnottesActives.subList(0, 4);
            return premieresCagnottes;
        }
        return cagnottesActives;
    }
    
    /**
     * Récupère la liste des cagnottes sans bénéficiaire.
     * @return la liste des cagnottes sans bénéficiaire. 
     */
    public ResultatMetier<List<Cagnotte>> recupererCagnottesSansBeneficiaire() {
        CagnotteDao cagnotteDao = new CagnotteDao();
        this.ouvrirSessionHibernate();
        List<Cagnotte> cagnottes = cagnotteDao.trouverActives();
        this.fermerSessionHibernate();
        List<Cagnotte> cagnottesActives = new ArrayList<>(cagnottes);
        // On retire les cagnottes dont la date d'échéance est passée.
        for (Cagnotte cagnotte : cagnottes) {
            if (cagnotte.getBeneficiaire() != null) {
                cagnottesActives.remove(cagnotte);
            }
        }
        
        ResultatMetier<List<Cagnotte>> resultatMetier = new ResultatMetier<>();
        
        if (cagnottesActives.size() > 5) {
            List<Cagnotte> premieresCagnottes = cagnottesActives.subList(0, 4);
            resultatMetier.setResultat(premieresCagnottes);
        }
        resultatMetier.setResultat(cagnottesActives);
        
        return resultatMetier;
    }
    
    /**
     * Retourne une cagnotte à partir de son identifiant.
     * @param cagnotteId
     * @return 
     */
    public ResultatMetier<Cagnotte> recupererCagnotte(Integer cagnotteId) {
        Objects.requireNonNull(cagnotteId, "L'identifiant de la cagnotte ne devrait pas être nul");
        CagnotteDao cagnotteDao = new CagnotteDao();
        
        this.ouvrirSessionHibernate();
        Cagnotte cagnotte = cagnotteDao.trouverParId(cagnotteId);
        this.fermerSessionHibernate();
        
        ResultatMetier<Cagnotte> resultatMetier = new ResultatMetier<>();
        resultatMetier.setResultat(cagnotte);
        return resultatMetier;
    }
    
    /**
     * Ajoute des points dans une cagnotte si possible.
     * @param cagnotteId l'identifiant de la cagnotte à laquelle on ajoute des points.
     * @param nbPoints le nombre de points à ajouter à la cagnotte.
     * @return si l'ajout de points s'est bien passé ou non.
     */
    public ResultatMetierSimple ajouterPointsCagnotte(Integer cagnotteId, Integer nbPoints) {

        ResultatMetierSimple resultatMetierSimple = new ResultatMetierSimple();
        
        // On commence par vérifier que l'utilisateur a suffisamment de points à donner.
        if (!DroitsUtilisateurs.pointsSuffisants(this.getUtilisateur(), nbPoints)) {
            resultatMetierSimple.ajouterErreur(new ErreurMetier(CodeMetier.PasAssezDePointsDisponibles));
        }
        else {
            CagnotteDao cagnotteDao = new CagnotteDao();

            this.ouvrirSessionHibernate();
            Cagnotte cagnotte = cagnotteDao.trouverParId(cagnotteId);
            cagnotte.setNombrePoints(cagnotte.getNombrePoints() + nbPoints);
            cagnotteDao.save(cagnotte);
            this.getUtilisateur().setNbPointsRestantsAAttribuer(this.getUtilisateur().getNbPointsRestantsAAttribuer() - nbPoints);
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            utilisateurDao.sauvegarder(this.getUtilisateur());
            this.fermerSessionHibernate();
        }
        
        return resultatMetierSimple;
    }
    
    /**
     * Attribue les points d'une cagnotte à un utilisateur.
     * @param cagnotte
     * @param beneficiaire
     * @return 
     */
    public ResultatMetierSimple attribuerPointsCagnottes(Integer cagnotteId, Integer beneficiaireId) throws IllegalAccessException {
        ResultatMetierSimple resultatMetierSimple = new ResultatMetierSimple();
        
        if (!this.getUtilisateur().getRang().getNom().equals("administrateur")) {
            throw (new IllegalAccessException("Seul l'administrateur peut valider une cagnotte"));
        }
        
        CagnotteDao cagnotteDao = new CagnotteDao();
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        
        this.ouvrirSessionHibernate();
        Cagnotte cagnotte = cagnotteDao.trouverParId(cagnotteId);
        Utilisateur beneficiaire = utilisateurDao.chargerParId(beneficiaireId);
        this.fermerSessionHibernate();
        
        if (cagnotte == null) {
            throw new NoSuchFieldError("La cagnotte cherchée n'existe pas");
        }
        
        if (beneficiaire == null) {
            throw new NoSuchFieldError("L'utilisateur cherché n'existe pas");
        }
        
        beneficiaire.setNbPoints(beneficiaire.getNbPoints() + cagnotte.getNombrePoints());
        cagnotte.setBeneficiaire(beneficiaire);
        
        
        // Ajout de l'action dans l'historique des actions.
        
        HistoriqueAction historiqueAction = new HistoriqueAction();
        historiqueAction.setAction("gain_cagnotte");
        historiqueAction.setCible(beneficiaire);
        historiqueAction.setNombrePoints(cagnotte.getNombrePoints());
        historiqueAction.setRaison(cagnotte.getActionARealiser());
        historiqueAction.setSource(cagnotte.getCreateur());
        
        HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
        
        this.ouvrirSessionHibernate();
        cagnotteDao.save(cagnotte);
        historiqueActionDao.sauvegarder(historiqueAction);
        
        // Avant de sauvegarder l'utilisateur, on vérifie si il ne change pas de rang.
        UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
        utilisateurMetier.verifierChangementRang(beneficiaire);
        
        utilisateurDao.sauvegarder(beneficiaire);
        this.fermerSessionHibernate();
        
        
        return resultatMetierSimple;
    }
    
        /**
     * Vérifie que tous les champs ont bien été remplis.
     * @param formulaire le formulaire à vérifier.
     * @return la liste des erreurs métier.
     */
    private List<ErreurMetier> verifierValiditeFormulaire(FormulaireCreationCagnotte formulaire) {
        List<ErreurMetier> erreurMetiers = new ArrayList<>();
        
        // On crée l'erreur métier concernant les champs manquants, puis on lui ajoute des paramètres selon les champs manquants.
        ErreurMetier erreurChampManquant = new ErreurMetier(CodeMetier.ChampRequisNonRenseigne);
        
        if (formulaire.getActionARealiser() == null) {
            erreurChampManquant.ajouterParametre("actionARealiser");
        }
        
        if (formulaire.getMontant() == 0) {
            erreurChampManquant.ajouterParametre("montant");
        }
        
        if (formulaire.getTitre() == null) {
            erreurChampManquant.ajouterParametre("titre");
        }
        
        if (!erreurChampManquant.getParametres().isEmpty()) {
            erreurMetiers.add(erreurChampManquant);
        }
        
        return erreurMetiers;
    }
}
