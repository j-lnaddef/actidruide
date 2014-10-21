package druide.hibernate.pojo;


import java.util.HashSet;
import java.util.Set;

/**
 * Pojo Rang.
 */
public class Rang  implements java.io.Serializable {


     private Integer id;
     private String nom;
     private int nbPointsJournaliers;
     private int palier;
     private Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>(0);
     private Set<Permission> permissions = new HashSet<Permission>(0);

    public Rang() {
    }


   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }
    
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public int getNbPointsJournaliers() {
        return nbPointsJournaliers;
    }

    public void setNbPointsJournaliers(int nbPointsJournaliers) {
        this.nbPointsJournaliers = nbPointsJournaliers;
    }

    public int getPalier() {
        return palier;
    }

    public void setPalier(int palier) {
        this.palier = palier;
    }

}


