package druide.hibernate.pojo;

import java.util.Set;


/**
 * Pojo Utilisateur.
 */
public class Utilisateur  implements java.io.Serializable {


     private Integer id;
     private String nom;
     private String prenom;
     private String login;
     private String motDePasse;
     private String email;
     private int nbPoints;
     private int nbPointsRestantsAAttribuer;
     private Rang rang;
     private Set<Cagnotte> cagnottes;

    public Utilisateur() {
    }

	
    public Utilisateur(String nom, String login, String motDePasse) {
        this.nom = nom;
        this.login = login;
        this.motDePasse = motDePasse;
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
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    public String getMotDePasse() {
        return this.motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getNbPointsRestantsAAttribuer() {
        return nbPointsRestantsAAttribuer;
    }

    public void setNbPointsRestantsAAttribuer(int nbPointsRestantsAAttribuer) {
        this.nbPointsRestantsAAttribuer = nbPointsRestantsAAttribuer;
    }

    public Rang getRang() {
        return rang;
    }

    public void setRang(Rang rang) {
        this.rang = rang;
    }

    public Set<Cagnotte> getCagnottes() {
        return cagnottes;
    }

    public void setCagnottes(Set<Cagnotte> cagnottes) {
        this.cagnottes = cagnottes;
    }
    
    

}


