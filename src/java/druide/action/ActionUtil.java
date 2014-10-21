//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 16/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.action;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Utilitaires divers pour les actions.
 * @author Clément VASSEUR
 */
public final class ActionUtil {

    /**
     * Empêche l'instanciation de classes ActionUtil : il s'agit d'une classe utilitaire.
     */
    private ActionUtil() {
    }

    /**
     * Crée un nouveau cookie qui sera envoyé au client. Le cookie créé expire lorsque l'utilisateur ferme son navigateur web. Il est
     * accessible sur toutes les pages de l'application.
     * @param nomCookie Le nom du cookie à créer.
     * @param valeur    La valeur du cookie.
     */
    public static void ajouterCookieReponse(String nomCookie, String valeur) {
        HttpServletRequest requete = ServletActionContext.getRequest();
        HttpServletResponse reponse = ServletActionContext.getResponse();
        Cookie cookie = new Cookie(nomCookie, valeur);
        cookie.setPath(requete.getContextPath());
        reponse.addCookie(cookie);
    }

    /**
     * Supprimer un cookie : le client ne renverra plus ce cookie lors de ses prochaines requêtes.
     * @param nomCookie Le nom du cookie à supprimer.
     */
    public static void supprimerCookieReponse(String nomCookie) {
        HttpServletRequest requete = ServletActionContext.getRequest();
        HttpServletResponse reponse = ServletActionContext.getResponse();
        Cookie cookie = new Cookie(nomCookie, null);
        cookie.setPath(requete.getContextPath());
        cookie.setMaxAge(0);
        reponse.addCookie(cookie);
    }

    /**
     * Donne une valeur booléenne d'un paramètre envoyé via la requête HTTP. Si le paramètre envoyée est "true", on renvoie true. Si le
     * paramètre envoyée est "false", on renvoie false Dans tous les autres cas, on renvoie nulle. Struts ne renvoie jamais null pour un
     * paramètre booléen, ce qui pose parfois problème.
     * @param nomParametre Le nom du paramètre dont on veut la valeur booléenne.
     * @return La valeur booléenne du paramètre.
     */
    public static Boolean getBoolean(String nomParametre) {
        Boolean resultat = null;
        HttpServletRequest requete = ServletActionContext.getRequest();
        String valeurParametre = requete.getParameter(nomParametre);
        if (valeurParametre != null) {
            if (valeurParametre.equals("true")) {
                resultat = true;
            }
            else if (valeurParametre.equals("false")) {
                resultat = false;
            }
        }

        return resultat;
    }


    /**
     * Teste si une requête HTTP est envoyée avec un appel Ajax.
     * @param requete La requete à vérifier.
     * @return Vrai si la requête est faite avec Ajax, faux sinon.
     */
    public static boolean estAjax(HttpServletRequest requete) {

        // Test si le header "X-Requested-With" est envoyé avec la valeur "XMLHttpRequest"
        boolean headerAjax = "XMLHttpRequest".equals(requete.getHeader("X-Requested-With"));

        // Test si le paramètre "ajax" (manuel dans ECN&CI) est bien positionné
        boolean paramAjax = requete.getParameter("ajax") != null;

        // L'un ou l'autre indique que la requete est en Ajax
        return headerAjax || paramAjax;
    }

    /**
     * Obtient la version JSON d'un objet quelconque.
     * @param object l'objet à convertir en JSON.
     * @return La chaine de caractères JSON obtenue.
     */
    public static String getJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * Créer une URL vers une action Struts.
     * @param namespace Le nom du namespace de l'action.
     * @param action    Le nom de l'action.
     * @return L'URL obtenue sous forme de chaine de caractères.
     */
    public static String creerStrutsUrl(String namespace, String action) {
        HttpServletRequest requete = ServletActionContext.getRequest();
        String url = requete.getContextPath() + namespace + "/" + action;
        return url;
    }

    /**
     * Créer une URL vers une action Struts avec des paramètres.
     * @param namespace  Le nom du namespace de l'action.
     * @param action     Le nom de l'action.
     * @param parametres Le tableau des paramètres
     * @return L'URL obtenue sous forme de chaine de caractères.
     */
    public static String creerStrutsUrl(String namespace, String action, Map<String, String> parametres) {
        String url = creerStrutsUrl(namespace, action);

        int i = 0;

        for (Map.Entry<String, String> entry : parametres.entrySet()) {
            if (i == 0) {
                url += "?" + entry.getKey() + "=" + entry.getValue();
            }
            else {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
            i++;
        }

        return url;
    }

    /**
     * Retourne une variable en session sous forme de liste. Permet de supprimer le warning de "unchecked cast".
     * @param <T>     Le type des éléments de la liste.
     * @param cle     La clé de l'objet en session.
     * @param session La session HTTP.
     * @return La liste en session.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getVariableListeSession(String cle, HttpSession session) {
        return (List<T>) session.getAttribute(cle);
    }
}
