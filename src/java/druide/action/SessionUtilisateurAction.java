//-------------------------------------------------------------
// Projet perso Actidruide
// 
// Créé le : 07/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.action;

import java.util.List;
import druide.hibernate.pojo.Utilisateur;
import druide.metier.CodeMetier;
import druide.metier.ErreurMetier;
import druide.metier.ResultatMetier;
import druide.metier.UtilisateurMetier;


/**
 * Actions liées à l'utilisateur.
 * @author Jean-Loup Naddef
 */
public class SessionUtilisateurAction extends BaseAction {

    /**
     * Nom de la clé de la variable de session permettant de récupérer l'id de l'utilisateur courant.
     */
    public static final String CLE_SESSION_UTILISATEUR_ID = "utilisateur_id";
    
    /**
     * Le login de l'utilisateur.
     */
    private String login;
    
    /**
     * Le mot de passe de l'utilisateur.
     */
    private String motDePasse;
    
    /**
     * Initialise une nouvelle instance de la classe {@link SessionUtilisateurAction}.
     * @author Jean-Loup Naddef
     */
    public SessionUtilisateurAction() {
    }
    
    public String execute() {
        return SUCCESS;
    }
    
    /**
     * Authentification de l'utilisateur.
     * @return Si l'authentification s'est passée sans erreur ou non.
     */
    public String authentifier() {
        String result = SUCCESS;
        
        UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
        ResultatMetier<Utilisateur> resultatMetier = utilisateurMetier.authentifierUtilisateur(login, motDePasse);
        
        List<ErreurMetier> erreurMetiers = resultatMetier.getErreurs();
        
        if (!erreurMetiers.isEmpty()) {
            result = ERROR;
            if (erreurMetiers.get(0).getCodeErreur().equals(CodeMetier.LoginInexistant)) {
                this.addFieldError("login", "Login inexistant");
                this.addActionError("Login inexistant");
            }
            else {
                this.addFieldError("motDePasse", "Mauvais mot de passe");
                this.addActionError("Mauvais mot de passe");
            }
        }
        
        else {
            this.setVariableSession(SessionUtilisateurAction.CLE_SESSION_UTILISATEUR_ID, resultatMetier.getResultat().getId());
        }
        
        return result;
    }

    /**
     * Déconnecte l'utilisateur.
     * @return Code d'action.
     */
    public String deconnecter() {
        // Supprimer la session
        this.getServletRequest().getSession().invalidate();
        return BaseAction.SUCCESS;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
