//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 16/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.intercepteurs;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import druide.action.ActionUtil;
import druide.action.BaseAction;
import druide.exception.UtilisateurNonDefiniException;

/**
 * Intercepteur Struts permettant de gérer toutes les actions se produisant durant l'exécution d'une action.
 * @author Clément VASSEUR
 */
public class ExceptionIntercepteur implements Interceptor {
    
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
     * Gère une exception survenant durant l'exécution d'une action.
     * @param invocation Invocation de l'action Struts.
     * @return Le résultat de l'invocation de la chaine d'invocation Struts.
     * @throws Exception Exception quelconque durant l'exécution.
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String resultat;

        try {
            resultat = invocation.invoke();
        }
        catch (Exception ex) {
            resultat = this.traiterException(ex, (ActionSupport) invocation.getAction());
        }

        return resultat;
    }

    /**
     * Analyse et traite une exception qui est survenue. 
     * @param exception L'exception qui vient de survenir.
     * @param action L'action Struts qui était ou qui allait être exécutée.
     * @return Code d'action.
     */
    public String traiterException(Exception exception, ActionSupport action) {
        String resultatDefaut;
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        HttpServletRequest requete = ServletActionContext.getRequest();
        HttpServletResponse reponse = ServletActionContext.getResponse();
        String message;
        String messageAdditionnel = "";
        boolean afficherPileAppels = false;

        if (exception instanceof UtilisateurNonDefiniException) {
            // L'utilisateur n'est pas connecté ou sa session a expiré
            reponse.setStatus(401); // Unauthorized
            message = action.getText("global.erreur.session_expire");
            resultatDefaut = ActionSupport.LOGIN;
        }
        else {
            // Exception inattendue, comportement par défaut
            reponse.setStatus(500); // Internal Server Error
            message = action.getText("global.erreur.defaut");
            messageAdditionnel = exception.getMessage();
            afficherPileAppels = true;
            resultatDefaut = ActionSupport.ERROR;
            
        }
        
        // Mettre l'exception dans la pile, de manière à ce qu'elle soit accessible dans la vue
        valueStack.set("exception", exception);
        valueStack.set("message", message);
        valueStack.set("messageAdditionnel", messageAdditionnel);
        valueStack.set("afficherPileAppels", afficherPileAppels);
        valueStack.set("pileAppels", this.formaterException(exception));
        
        // Mettre le résultat Ajax dans la pile, de manière à ce qu'il soit accessible dans la vue
        if (ActionUtil.estAjax(requete)) {
            reponse.setHeader("Content-Type", "text/html");
            resultatDefaut = BaseAction.AJAX_ERREUR; // Toujours un résultat ajax, quelle que soit l'erreur
        }
        
        return resultatDefaut;
    }
    
    /**
     * Formate une exception de manière à ce que son contenu soit facilement affichable dans la vue.
     * @param exception L'exception à formater.
     * @return L'exception formatée (une ligne pour chaque information à afficher).
     */
    private List<String> formaterException(Exception exception) {
        List<String> resultat = new LinkedList<String>();
        if (exception != null) {
            Throwable e = exception;
            resultat.add("Exception " + e.getClass().getCanonicalName());
            
            // Parcourir les exceptions, de haut en bas (exception de départ vers la cause première).
            while (e != null) {
                for (StackTraceElement element : e.getStackTrace()) {
                    resultat.add("--- at " + element);
                }

                if (e.getCause() != null) {
                    resultat.add("\nCaused by : " + e.getCause().getClass().getCanonicalName() + " " + e.getCause().getMessage());
                }
                
                e = e.getCause();
            }
        }
        
        return resultat;
    }
}
