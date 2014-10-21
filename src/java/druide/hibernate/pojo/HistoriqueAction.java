//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 16/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.hibernate.pojo;


/**
 * POJO de l'historique des actions.
 * @author Jean-Loup Naddef
 */
public class HistoriqueAction {
    
    private int id;
    
    private String action;
    
    private Utilisateur source;
    
    private Utilisateur cible;
    
    private int nombrePoints;
    
    private String raison;

    /**
     * Initialise une nouvelle instance de la classe {@link HistoriqueAction}.
     * @author Jean-Loup Naddef
     */
    public HistoriqueAction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Utilisateur getSource() {
        return source;
    }

    public void setSource(Utilisateur source) {
        this.source = source;
    }

    public Utilisateur getCible() {
        return cible;
    }

    public void setCible(Utilisateur cible) {
        this.cible = cible;
    }

    public int getNombrePoints() {
        return nombrePoints;
    }

    public void setNombrePoints(int nombrePoints) {
        this.nombrePoints = nombrePoints;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }
    
}
