//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 15/09/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.metier;

import druide.hibernate.pojo.Permission;
import druide.hibernate.pojo.Utilisateur;
import java.util.Set;


/**
 * TODO : Description de la classe.
 * @author Jean-Loup Naddef
 */
public class DroitsUtilisateurs {

    /**
     * Vérifie que l'utilisateur courant a bien le droit d'effectuer l'action demandée.
     * @param utilisateur l'utilisateur courant.
     * @param action l'action demandée.
     * @return si l'utilisateur a les droits.
     */
    public static boolean verifierDroits(Utilisateur utilisateur, String action) {
        Set<Permission> permissions = utilisateur.getRang().getPermissions();
        for (Permission permission : permissions) {
            if (permission.getNom().equals(action)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Vérifie que l'utilisateur courant a assez de points pour effectuer l'action demandée.
     * @param utilisateur l'utilisateur courant.
     * @param nbPoints le nombre de points qu'il essaye d'attribuer.
     * @return si l'utilisateur a suffisamment de points.
     */
    public static boolean pointsSuffisants(Utilisateur utilisateur, Integer nbPoints) {
        return nbPoints <= utilisateur.getNbPointsRestantsAAttribuer();
    }
}
