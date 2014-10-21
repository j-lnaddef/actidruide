//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 15/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionSupport;
import druide.hibernate.pojo.Utilisateur;
import druide.util.StringUtil;

/**
 * Classe d'actions Struts de base pour toutes les autres actions Struts Actidruide.
 *
 * @author Clément VASSEUR
 */
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    /**
     * Le numéro de version actuel de l'application Actidruide.
     */
    public static final String VERSION_APPLICATION = "0.1";

    /**
     * Code de retour indiquant qu'une donnée n'a pas été trouvée.
     */
    public static final String NON_TROUVE = "non_trouve";

    /**
     * Code de retour indiquant que l'utilisateur n'a pas la permission pour effectuer l'action demandée.
     */
    public static final String PERMISSION = "permission";

    /**
     * Code de retour utilisé pour afficher un résultat Ajax en JSON.
     */
    public static final String AJAX_JSON = "ajax_json";

    /**
     * Code de retour utilisé pour les appels Ajax ayant provoqué une exception.
     */
    public static final String AJAX_ERREUR = "ajax_erreur";

    /**
     * Code de retour utilisé pour les erreurs métiers.
     */
    public static final String METIER_ERREUR = "metier_erreur";

    /**
     * Code de retour utilisé pour les CSV.
     */
    public static final String EXPORT_CSV = "export_csv";

    /**
     * Code de retour utilisé pour télécharger un PDF.
     */
    public static final String EXPORT_PDF = "export_pdf";

    /**
     * Code de retour utilisé pour télécharger un ZIP.
     */
    public static final String EXPORT_ZIP = "export_zip";
    
    /**
     * Code de retour utilisé pour prévisualiser un PDF (téléchargement et affichage dans le navigateur).
     */
    public static final String PREVISUALISATION_PDF = "previsualisation_pdf";

    /**
     * Code de retour utilisé pour afficher un texte brut en sortie.
     */
    public static final String TEXTE = "texte";

    /**
     * Code de retour utilisé pour afficher une image en sortie.
     */
    public static final String IMAGE = "image";

    /**
     * L'ensemble des noms des rôles pour lesquels on ouvre automatiquement la popup des participants.
     */
    public static final Set<String> NOM_ROLES_PARTICIPANTS_AUTO = new HashSet<>(Arrays.asList(
            "Expert CS",
            "Membre CS",
            "Membre CS restreint"
    ));
    
    /**
     * La requête HTTP utilisée dans l'action.
     */
    private HttpServletRequest requete;

    /**
     * La réponse HTTP utilisée dans l'action.
     */
    private HttpServletResponse reponse;

    /**
     * L'ensemble des cookies de la requête.
     */
    private Map<String, String> cookies;

    /**
     * Utilisateur courant, connecté à l'application.
     */
    private Utilisateur utilisateur;

    /**
     * Résultat Ajax d'une action.
     */
    private ResultatAjax resultatAjax;

    /**
     * Flag envoyé dans la requête HTTP indiquant si la requête est faite via Ajax ou non.
     */
    private boolean ajax;

    /**
     * Valeur indiquant si un formulaire a été soumis ou non.
     */
    private boolean formulaireSoumis;
    
    /**
     * Flux pour le fichier de téléchargement.
     */
    private InputStream fluxFichierTelechargement;

    /**
     * Nom du fichier à télécharger.
     */
    private String nomFichierTelechargement;
    
    /**
     * Obtient l'utilisateur courant, connecté à l'application.
     *
     * @return l'utilisateur courant.
     */
    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    /**
     * Définit l'utilisateur courant, connecté à l'application.
     *
     * @param utilisateur l'utilisateur courant.
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Définit la requête HTTP utilisée dans l'action.
     *
     * @param requete La requête HTTP.
     */
    @Override
    public void setServletRequest(HttpServletRequest requete) {
        this.requete = requete;
        if (requete != null && requete.getCookies() != null) {
            Map<String, String> cookiesMap = new HashMap<>();
            for (Cookie c : requete.getCookies()) {
                cookiesMap.put(c.getName(), c.getValue());
            }

            this.setCookiesMap(cookiesMap);
        }
    }

    /**
     * Obtient la requête HTTP utilisée dans l'action.
     *
     * @return La requête HTTP utilisée dans l'action.
     */
    public HttpServletRequest getServletRequest() {
        return this.requete;
    }

    /**
     * Définit la réponse HTTP utilisée dans l'action.
     *
     * @param reponse La réponse HTTP utilisée dans l'action..
     */
    @Override
    public void setServletResponse(HttpServletResponse reponse) {
        this.reponse = reponse;
    }

    /**
     * Obtient la réponse HTTP utilisée dans l'action.
     *
     * @return La réponse HTTP utilisée dans l'action..
     */
    public HttpServletResponse getServletResponse() {
        return this.reponse;
    }

    /**
     * Teste si l'utilisateur est connecté.
     *
     * @return true si l'utilisateur est connecté, false sinon.
     */
    public boolean estUtilisateurConnecte() {
        return this.getUtilisateur() != null;
    }

    /**
     * Définit l'ensemble des cookies de la requête HTTP utilisée dans l'action.
     *
     * @param cookies L'ensemble des cookies.
     */
    public void setCookiesMap(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    /**
     * Obtient la valeur d'un cookie à partir de son nom.
     *
     * @param nomCookie Le nom du cookie.
     * @return La valeur du cookie.
     */
    public String getCookie(String nomCookie) {
        if (this.cookies == null) {
            return null;
        }
        else {
            return this.cookies.get(nomCookie);
        }
    }

    /**
     * Obtient la valeur entière d'un cookie à partir de son nom.
     *
     * @param nomCookie Le nom du cookie.
     * @return La valeur entière du cookie.
     */
    public Integer getCookieInteger(String nomCookie) {
        String valeurString = this.getCookie(nomCookie);
        Integer valeur = null;
        if (!StringUtil.nulleOuVide(valeurString)) {
            try {
                valeur = Integer.parseInt(valeurString);
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Cookie de valeur entière invalide : " + valeurString, ex);
            }
        }
        
        return valeur;
    }

    /**
     * Ajoute un cookie.
     *
     * @param nom    Le nom du cookie.
     * @param valeur La valeur du cookie.
     */
    public void ajouteCookie(String nom, String valeur) {
        this.cookies.put(nom, valeur);
    }

    /**
     * Obtient les cookies.
     *
     * @return Les cookies.
     */
    public Map<String, String> getCookies() {
        return this.cookies;
    }

    /**
     * Obtient le résultat Ajax d'une action.
     *
     * @return Le résultat Ajax.
     */
    public ResultatAjax getResultatAjax() {
        return this.resultatAjax;
    }

    /**
     * Définit le résultat Ajax d'une action.
     *
     * @param resultatAjax Le résultat Ajax.
     */
    public void setResultatAjax(ResultatAjax resultatAjax) {
        if (resultatAjax != null) {
            // Changer le content type de la réponse automatiquement
            this.getServletResponse().setHeader("Content-Type", "application/json");
        }
        this.resultatAjax = resultatAjax;
    }

    /**
     * Obtient une valeur indiquant si la requête HTTP est effectuée via Ajax. 
     * (paramètre "ajax" envoyé manuellement dans le formulaire).
     * @return Si la requête est effectuée via Ajax.
     */
    public boolean isAjax() {
        return this.ajax;
    }

    /**
     * Définit une valeur indiquant si la requête HTTP est effectuée via Ajax.
     *
     * @param ajax Si la requête est effectuée via Ajax.
     */
    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

    /**
     * Permet de récupérer une variable gardée en session.
     *
     * @param cle la clé permettant de récupérer la variable.
     * @return la variable.
     */
    public Object getVariableSession(String cle) {
        if (this.getServletRequest().getSession(false) != null) {
            return this.getServletRequest().getSession().getAttribute(cle);
        }
        else {
            return null;
        }
    }

    /**
     * Permet de définir une variable stockée en session et référencée par un clé.
     *
     * @param cle    la clé référençant la valeur.
     * @param valeur la valeur gardée en session.
     */
    public void setVariableSession(String cle, Object valeur) {
        this.getServletRequest().getSession().setAttribute(cle, valeur);
    }

    /**
     * Permet de savoir si une variable en session existe pour la clé entrée en paramètre.
     *
     * @param cle la clé référençant la valeur.
     * @return si une valeur existe pour la clé donnée.
     */
    public boolean variableSessionExiste(String cle) {
        if (this.getServletRequest().getSession(false) != null) {
            return this.getServletRequest().getSession().getAttribute(cle) != null;
        }
        else {
            return false;
        }
    }

    /**
     * Permet de savoir si le formulaire a été soumis.
     *
     * @return si oui ou non le formulaire a été soumis.
     */
    public boolean isFormulaireSoumis() {
        return this.formulaireSoumis;
    }

    /**
     * Définit si le formulaire a été soumis ou non.
     *
     * @param submit si le formulaire a été soumis.
     */
    public void setFormulaireSoumis(boolean submit) {
        this.formulaireSoumis = submit;
    }

    /**
     * Obtient le numéro de version de l'application.
     * @return Le numéro de version de l'application.
     */
    public String getVersionApplication() {
        return BaseAction.VERSION_APPLICATION;
    }
    
    /**
     * Ecrit un texte sur la sortie standard de la Servlet.
     * Util avec le code de retour Struts {@link BaseAction#TEXTE}.
     * @param texte Le texte à écrire.
     */
    public void ecrireTexteSortie(String texte) {
        try {
            this.getServletResponse().getWriter().write(texte);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Impossible d'écrire sur la sotie de la réponse de la Servlet", ex);
        }
    }
}
