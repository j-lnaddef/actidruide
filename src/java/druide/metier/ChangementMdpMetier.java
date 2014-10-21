//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 12/10/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.dao.UtilisateurDao;
import druide.exception.UtilisateurNonDefiniException;
import druide.hibernate.pojo.Utilisateur;
import druide.util.StringUtil;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class ChangementMdpMetier extends BaseMetier {

    /**
     * Initialise une nouvelle instance de la classe {@link ChangementMdpMetier}.
     * @author Jean-Loup Naddef
     */
    public ChangementMdpMetier(Utilisateur utilisateur) {
        super(utilisateur);
    }
    
        
    /**
     * Modifie le mot de passe d'un utilisateur.
     * @param ancienMdp l'ancien mot de passe.
     * @param nouveauMdp le nouveau mot de passe.
     * @return la liste des erreurs survenues.
     */
    public ResultatMetierSimple changerMdp(String ancienMdp, String nouveauMdp) {
        
        if (this.getUtilisateur() == null) {
            this.lancerException(new UtilisateurNonDefiniException());
        }
        
        ResultatMetierSimple resultatMetierSimple = new ResultatMetierSimple();
        
        // On vérifie tout d'abord que l'ancien mdp correspond bien au mot de passe de l'utilisateur.
        if (!StringUtil.hache(ancienMdp).equals(this.getUtilisateur().getMotDePasse())) {
            resultatMetierSimple.ajouterErreur(new ErreurMetier(CodeMetier.MauvaisMotDePasse));
        }
        
        else {
            this.getUtilisateur().setMotDePasse(StringUtil.hache(nouveauMdp));
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            
            this.ouvrirSessionHibernate();
            utilisateurDao.sauvegarder(this.getUtilisateur());
            this.fermerSessionHibernate();
        }
        
        return resultatMetierSimple;
    }
}
