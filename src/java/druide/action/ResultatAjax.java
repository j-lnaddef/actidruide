//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 16/10/2013
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Resultat Ajax générique, pour toutes les requêtes Ajax.
 * @author Clément VASSEUR
 */
public class ResultatAjax {

    /**
     * Le résultat de l'appel Ajax, qui peut être un objet de type quelconque.
     */
    private Object resultat;
    
    /**
     * Les chemins des templates Freemarker.
     * Les chemins sont relatifs au fichier ajax_json.ftl.
     */
    private List<String> freemarkerTemplates;

    /**
     * Drapeau pour indiquer si une erreur est survenue.
     */
    private boolean enErreur;

    /**
     * Les erreurs métiers qui sont survenues, indexées par champs.
     * Un champ est associé à plusieurs messages erreurs.
     */
    private Map<String, List<String>> champsEnErreur;
    
    /**
     * Ensemble des erreurs métiers, non associées à un champ en particulier, qui sont survenues.
     * Ensemble de messages d'erreurs.
     */
    private List<String> erreurs;
    
    /**
     * Un message (erreur/succès) réutilisable à divers fin.
     */
    private String message;

    /**
     * Initialise une nouvelle instance de la classe ResultatAjax.
     * @author Clément VASSEUR
     */
    public ResultatAjax() {
        this.freemarkerTemplates = new LinkedList<>();
        this.champsEnErreur = new HashMap<>();
        this.erreurs = new LinkedList<>();
    }

    /**
     * Obtient le résultat de l'appel Ajax, qui peut être un objet de type quelconque.
     * @return L'objet résultat de l'appel Ajax.
     */
    public Object getResultat() {
        return this.resultat;
    }

    /**
     * Définit le résultat de l'appel Ajax, qui peut être un objet de type quelconque.
     * @param resultat L'objet résultat de l'appel Ajax.
     */
    public void setResultat(Object resultat) {
        this.resultat = resultat;
    }
    
    /**
     * Obtient le résultat de l'appel Ajax au format JSON.
     * @return Le résultat de l'appel Ajax.
     */
    public String getResultatEnJson() {
        return ActionUtil.getJson(this.resultat);
    }

    /**
     * Obtient l'ensemble des chemins des templates Freemarker.
     * @return L'ensemble des chemins des templates Freemarker.
     */
    public List<String> getFreemarkerTemplates() {
        return this.freemarkerTemplates;
    }

    /**
     * Ajoute un chemin de template Freemarker.
     * @param freemarkerTemplate Un chemin de template Freemarker, relatif au fichier ajax_json.ftl.
     */
    public void addFreemarkerTemplate(String freemarkerTemplate) {
        this.freemarkerTemplates.add(freemarkerTemplate);
    }   

    /**
     * Obtient une valeur indiquant si l'appel a renvoyé une erreur.
     * @return Vrai si l'appel est en erreur, faux sinon.
     */
    public boolean isEnErreur() {
        return this.enErreur;
    }

    /**
     * Définit une valeur indiquant si l'appel a renvoyé une erreur.
     * @param enErreur Vrai si l'appel est en erreur, faux sinon.
     */
    public void setEnErreur(boolean enErreur) {
        this.enErreur = enErreur;
    }

    /**
     * Obtient la liste des messages d'erreurs, indexés par champs.
     * @return La liste des messages d'erreurs, indexés par champs.
     */
    public Map<String, List<String>> getChampsEnErreur() {
        return this.champsEnErreur;
    }

    /**
     * Définit la liste des messages d'erreurs métiers, indexés par champs.
     * @param champsEnErreur La liste des messages d'erreurs, indexés par champs.
     */
    public void setChampsEnErreur(Map<String, List<String>> champsEnErreur) {
        this.champsEnErreur = champsEnErreur;
    }
    
    /**
     * Obtient la liste des messages d'erreurs, indexés par champs, au format JSON.
     * @return La liste des messages d'erreurs, indexés par champs, au format JSON.
     */
    public String getChampsEnErreurJson() {
        return ActionUtil.getJson(this.getChampsEnErreur());
    }

    /**
     * Obtient la liste des erreurs métiers globales, non associées à un champ.
     * @return La liste des erreurs métiers globales, non associées à un champ.
     */
    public List<String> getErreurs() {
        return this.erreurs;
    }
    
    /**
     * Ajoute une message d'erreur global.
     * @param erreur Le message d'erreur à ajouter.
     */
    public void addErreur(String erreur) {
        this.erreurs.add(erreur);
    }

    /**
     * Définit la liste des erreurs métiers globales, non associées à un champ.
     * @param erreurs La liste des erreurs métiers globales, non associées à un champ.
     */
    public void setErreurs(List<String> erreurs) {
        if (erreurs == null) {
            // Eviter de mettre une valeur null : toujours avoir une liste.
            this.erreurs = new LinkedList<>();
        }
        
        this.erreurs = erreurs;
    }
    
    /**
     * Obtient la liste des erreurs métiers globales, non associées à un champ, au format JSON.
     * @return la liste des erreurs métiers globales, non associées à un champ, au format JSON.
     */
    public String getErreursJson() {
        return ActionUtil.getJson(this.erreurs);
    }

    /**
     * Obtient un message (erreur/succès) associé au résultat de la méthode.
     * @return Le message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Définit un message (erreur/succès) associé au résultat de la méthode.
     * @param message Le message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
