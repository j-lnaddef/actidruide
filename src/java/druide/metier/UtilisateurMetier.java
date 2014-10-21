//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 07/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import druide.dao.HistoriqueActionDao;
import druide.dao.RangDao;
import druide.dao.UtilisateurDao;
import druide.hibernate.pojo.HistoriqueAction;
import druide.hibernate.pojo.Rang;
import druide.hibernate.pojo.Utilisateur;
import druide.util.StringUtil;


/**
 * Le métier de ce qui concerne l'utilisateur.
 * @author Jean-Loup Naddef
 */
public class UtilisateurMetier extends BaseMetier {

    /**
     * L'utilisateur système.
     * Il s'agit d'un super administrateur. Seule l'application doit l'utiliser (ex: tâches planifiées).
     */
    private static Utilisateur utilisateurSysteme;
    
    static {
        utilisateurSysteme = new Utilisateur();
    }

    /**
     * Obtient l'utilisateur système.
     * @return  L'utilisateur système.
     */
    public static Utilisateur getUtilisateurSysteme() {
        return utilisateurSysteme;
    }
    
    /**
     * Initialise une nouvelle instance de la classe {@link UtilisateurMetier}.
     * @author Jean-Loup Naddef
     */
    public UtilisateurMetier() {
        super();
    }
    
    public Utilisateur recupererUtilisateur(Integer utilisateurId) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        this.ouvrirSessionHibernate();
        Utilisateur utilisateur = utilisateurDao.chargerParId(utilisateurId);
        this.fermerSessionHibernate();
        return utilisateur;
    }
    
    /**
     * Authentifie un utilisateur.
     * @param login le login de l'utilisateur.
     * @param motDePasse le mot de passe de l'utilisateur.
     * @return un résultat métier contenant l'utilisateur identifié, ou les erreurs.
     */
    public ResultatMetier<Utilisateur> authentifierUtilisateur(String login, String motDePasse) {
        Objects.requireNonNull(login, "Le login ne peut pas être nul");
        Objects.requireNonNull(motDePasse, "Le mot de passe ne peut pas être nul");
        
        this.ouvrirSessionHibernate();
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = utilisateurDao.chargerParLogin(login);
        this.fermerSessionHibernate();
        
        List<ErreurMetier> erreurMetiers = new ArrayList<>();
        ResultatMetier<Utilisateur> resultatMetier = new ResultatMetier<>();
        
        // On vérifie que le login existe bien, et que le mot de passe correspond.
        if (utilisateur == null) {
            erreurMetiers.add(new ErreurMetier(CodeMetier.LoginInexistant));
        }
        
        else if (!utilisateur.getMotDePasse().equals(StringUtil.hache(motDePasse))) {
            erreurMetiers.add(new ErreurMetier(CodeMetier.MauvaisMotDePasse));
        }
        
        else {
            resultatMetier.setResultat(utilisateur);
        }
        
        resultatMetier.setErreurs(erreurMetiers);
        
        return resultatMetier;
    }
    
    /**
     * Récupère l'ensemble des utilisateurs utilisables pour une action.
     * @return l'ensemble des utilisateurs utilisables pour une action.
     */
    public List<Utilisateur> recupererUtilisateursPossibles() {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        List<Utilisateur> utilisateurs = utilisateurDao.trouverTous();
        List<Utilisateur> utilisateursPossibles = new ArrayList<>(utilisateurs);
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getRang().getNom().equals("administrateur")) {
                utilisateursPossibles.remove(utilisateur);
            }
        }
        
        return utilisateursPossibles;
    }
    
    /**
     * Redonne les points à l'ensemble des utilisateurs.
     */
    public void redonnerPoints() {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        this.ouvrirSessionHibernate();
        List<Utilisateur> utilisateurs = utilisateurDao.trouverTous();
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateur.setNbPointsRestantsAAttribuer(utilisateur.getRang().getNbPointsJournaliers());
            utilisateurDao.sauvegarder(utilisateur);
        }
        this.fermerSessionHibernate();
    }
    
    /**
     * On vérifie si un utilisateur change de rang suite à une action où il gagne ou perd des points.
     * @param utilisateur l'utilisateur pour lequel on vérifie le changement de rang.
     */
    public void verifierChangementRang(Utilisateur utilisateur) {
        RangDao rangDao = new RangDao();
        List<Rang> rangs = rangDao.trouverTous();
            
        int nbPointsUtilisateur = utilisateur.getNbPoints();
        
        // On détermine le rang qui correspond au nombre de points de l'utilisateur.
        Rang rang = null;
        for (Rang r : rangs) {
            if (nbPointsUtilisateur >= r.getPalier()) {
                rang = r;
            }
        }
        
        // On regarde si le rang est le même que celui de l'utilisateur.
        if (rang != null && !utilisateur.getRang().getId().equals(rang.getId())) {
            utilisateur.setRang(rang);
            
            // Et on crée un évènement dans l'historique des actions
            HistoriqueAction historiqueAction = new HistoriqueAction();
            historiqueAction.setAction("changement_rang");
            historiqueAction.setCible(utilisateur);
            historiqueAction.setRaison(rang.getNom());
            
            HistoriqueActionDao historiqueActionDao = new HistoriqueActionDao();
            historiqueActionDao.sauvegarder(historiqueAction);
        }
    }

}
