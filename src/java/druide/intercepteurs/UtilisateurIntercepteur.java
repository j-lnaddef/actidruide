//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.intercepteurs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import druide.action.BaseAction;
import druide.action.SessionUtilisateurAction;
import druide.hibernate.pojo.Utilisateur;
import druide.metier.UtilisateurMetier;

/**
 * Intercepteur Struts chargé de définir l'utilisateur actuellement connecté dans l'action qui va être exécutée.
 * Si l'utilisateur n'est pas connecté, l'intercepteur est sans effet.
 *
 * @author Clément VASSEUR
 */
public class UtilisateurIntercepteur implements Interceptor {

    /**
     * Initialise l'intercepteur.
     */
    @Override
    public void init() {
    }

    /**
     * Détruit l'intercepteur.
     */
    @Override
    public void destroy() {
    }

    /**
     * Tente de définir l'utilisateur actuellement connecté dans l'action Struts.
     *
     * @param invocation Invocation de l'action Struts.
     * @return Le résultat de l'invocation de la chaine d'invocation Struts.
     * @throws Exception Exception quelconque durant l'exécution.
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        // Récupération de l'action Struts qui va être invoquée
        Object action = invocation.getAction();

        // Recupération de l'id de l'utilisateur, ainsi que son groupe actif, en session
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) {
            Integer utilisateurId = (Integer) session.getAttribute(SessionUtilisateurAction.CLE_SESSION_UTILISATEUR_ID);
            
            // Si l'utilisateur existe bien en session
            if (utilisateurId != null) {

                // Si l'action est bien une action ECNCI (devrait toujours être vrai).
                if (action instanceof BaseAction) {
                    BaseAction actionActiDruide = (BaseAction) action;

                    // On charge l'utilisateur avec tous ses droits
                    UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
                    Utilisateur utilisateur;
                    
                    utilisateur = utilisateurMetier.recupererUtilisateur(utilisateurId);

                    // On le définit dans l'action Struts
                    actionActiDruide.setUtilisateur(utilisateur);
                }
            }
        }
        
        // Continuation de l'exécution de la chaine (action Struts ou prochain intercepteur).
        String result = invocation.invoke();

        return result;
    }
    
    /**
     * Obtient l'adresse IP du client effectuant la requête HTTP vers le serveur.
     * @return L'adresse IP du client.
     */
    private String getAdresseIpClient() {
        HttpServletRequest requete = ServletActionContext.getRequest();
        String adresseIp = requete.getHeader("X-FORWARDED-FOR");  
        if (adresseIp == null) {  
            adresseIp = requete.getRemoteAddr();  
        }
        
        return adresseIp;
    }
}
