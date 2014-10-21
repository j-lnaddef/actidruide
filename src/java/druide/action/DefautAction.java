//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Classe contenant les actions par défaut de l'application.
 *
 * @author Serge NJIKI
 */
public class DefautAction extends BaseAction {
    
    /**
     * Le nom de l'action vers laquelle on redirige (paramètre de sortie).
     */
    private String nomAction;
    
    /**
     * Le namespace de l'action vers lequel on redirige (paramètre de sortie).
     */
    private String nomNamespace;
    
    /**
     * Le namespace du menu auquel l'utilisateur accède (paramètre d'entrée).
     * Utilisé dans la méthode {@link #redirigeAvecMenu()}.
     */
    private String nomMenu;
    
    /**
     * Redirige l'action en cours une autre action en fonction de la session utilisateur et des permissions.
     * @return Code d'action.
     */
    @Override
    public String execute() {
        String result = ActionSupport.SUCCESS;
        
        if (!this.estUtilisateurConnecte()) {
            // Si la session a expiré, on renvoie l'utilisateur sur la page de login.
            result = LOGIN;
        }
        
        return result;
    }
    
    /**
     * Obtient le nom de l'action.
     * @return Le nom de l'action.
     */
    public String getNomAction() {
        return this.nomAction;
    }

    /**
     * Définit le nom de l'action.
     * @param nomAction Le nom de l'action.
     */
    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    /**
     * Obtient le namesapce.
     * @return Le namespace.
     */
    public String getNomNamespace() {
        return this.nomNamespace;
    }

    /**
     * Définit le namespace.
     * @param nomNamespace Le namespace.
     */
    public void setNomNamespace(String nomNamespace) {
        this.nomNamespace = nomNamespace;
    }

    /**
     * Obtient le nom du menu auquel l'utilisateur accède.
     * @return Le nom du menu auquel l'utilisateur accède.
     */
    public String getNomMenu() {
        return this.nomMenu;
    }

    /**
     * Définit le nom du menu auquel l'utilisateur accède.
     * @param namespaceMenu Le nom du menu auquel l'utilisateur accède.
     */
    public void setNomMenu(String namespaceMenu) {
        this.nomMenu = namespaceMenu;
    }
}