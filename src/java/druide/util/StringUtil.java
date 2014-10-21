//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 08/11/2013
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------
package druide.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Classe utilitaire permettant d'effectuer des fonctions communes sur les chaines de caractères.
 *
 * @author Jean-Loup Naddef
 */
public final class StringUtil {

    /**
     * L'alphabet. Utilisé pour récupérer le caractère à un index précis.
     */
    private static List<Character> alphabet = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    /**
     * Initialise une nouvelle instance de la classe HashUtil.
     *
     * @author Jean-Loup Naddef
     */
    private StringUtil() {
    }

    /**
     * Méthode hashant un string avec l'algorithme SHA 256.
     * La chaine à hacher doit être en UTF-8.
     *
     * @param chaine La chaine à hacher
     * @return le string hashé.
     */
    public static String hache(String chaine) {
        Objects.requireNonNull(chaine, "La chaine à hacher ne doit pas être nulle");
        String valeurHachee;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(chaine.getBytes(Charset.forName("UTF-8")));
            byte[] byteData = messageDigest.digest();

            // On convertit les bytes en hexadécimal.
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            valeurHachee = sb.toString();

        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Impossible de calculer la valeur en HASH SHA 256.", ex);
        }

        return valeurHachee;
    }

    /**
     * Génère un mot de passe aléatoire.
     * Le mot de passe est généré selon les règles suivantes.
     * - 3 minuscules quelconques
     * - Suivies de 3 majuscules quelconques
     * - Suivies de 3 chiffres quelconques
     * - Suivis de 2 caractères spéciaux
     * @return Le mot de passe généré.
     */
    public static String genererMotDePasse() {
        String pass = "";
        Random r = new Random();

        char[] minuscules = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] majuscules = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] caracteresSpeciaux = {'#', '{', '}', '/', '*', '-', '+', '@', '(', ')', '[', ']', '&', '$', '%', '_'};

        // Ajout des minuscules
        for (int i = 0; i < 3; ++i) {
            int range = 25;
            int key = r.nextInt(range);
            pass += minuscules[key];
        }
        // Ajout des majuscules
        for (int i = 0; i < 3; ++i) {
            int range = 25;
            int key = r.nextInt(range);
            pass += majuscules[key];
        }
        // Ajout des chiffres
        for (int i = 0; i < 3; ++i) {
            int range = 10;
            pass += Integer.toString(r.nextInt(range));
        }
        // Ajout des caractères spéciaux
        for (int i = 0; i < 2; ++i) {
            int range = 15;
            int key = r.nextInt(range);
            pass += caracteresSpeciaux[key];
        }

        return pass;
    }

    /**
     * Construit une nouvelle chaine de caractères à partir d'un tableau de mots (string).
     * La chaine produite contient tous les mots séparée avec le séparateur donné.
     *
     * @param separateur Le séparateur.
     * @param mots Les mots (objets quelconques, mais à priori des strings)
     * @return La chaine contenant tous les mots séparée par le séparateur.
     */
    public static String implode(String separateur, Object... mots) {
        StringBuilder sb = new StringBuilder();
        if (mots != null && mots.length > 0) {
            for (int i = 0; i < mots.length - 1; i++) {
                sb.append(mots[i]);
                sb.append(separateur);
            }

            sb.append(mots[mots.length - 1]);
        }
        else {
            sb.append("");
        }

        return sb.toString();
    }

    /**
     * Teste si une chaine est nulle ou vide.
     *
     * @param chaine La chaine à tester.
     * @return Si la chaine est nulle ou vide.
     */
    public static boolean nulleOuVide(String chaine) {
        return chaine == null || chaine.equals("");
    }

    /**
     * Teste si une chaine est nulle, chaine vide, ou uniquement avec des espaces.
     *
     * @param chaine La chaine à tester.
     * @return Si la chaine est nulle, vide, ou uniquement avec des espaces.
     */
    public static boolean nulleOuEspaces(String chaine) {
        return chaine == null || chaine.trim().equals("");
    }


    /**
     * Récupère la lettre à un indice compris entre 0 et 25.
     * @param numeroLettre le numéro de la lettre que l'on souhaite.
     * @return La lettre.
     */
    public static char getLettre(int numeroLettre) {
        if (numeroLettre > 25) {
            throw new IllegalArgumentException("Le nombre entré en paramètre n'est pas dans les bornes de l'alphabet!");
        }

        return alphabet.get(numeroLettre);
    }

    /**
     * Transforme une chaine de caractère en une chaine vide si elle est nulle, sinon renvoie la chaine telle qu'elle est.
     * Permet d'éviter d'avoir des valeurs nulles.
     * @param chaine La chaine qui ne doit pas être nulle.
     * @return La chaine non nulle.
     */
    public static String transformerNonNulle(String chaine) {
        return chaine == null ? "" : chaine;
    }

    /**
     * Convertit un entier, qui peut être nulle, en chaine de caractères.
     * Une valeur nulle est convertie en chaine vide.
     * @param entier L'entier à convertir
     * @return La chaine correspondant à l'entier.
     */
    public static String convertirEnString(Integer entier) {
        return entier == null ? "" : String.valueOf(entier);
    }

    /**
     * Teste si deux chaines de caractères sont égales.
     * L'une ou l'autre des chaines de caractères peuvent être nulles.
     * Une chaine vide est considérée égale à la valeur nulle.
     * @param s1 La première chaine.
     * @param s2 La seconde chaine.
     * @return Si les deux chaines sont égales.
     */
    public static boolean verifierEgalite(String s1, String s2) {
        String s1NonNulle = s1 == null ? "" : s1;
        String s2NonNulle = s2 == null ? "" : s2;
        return s1NonNulle.equals(s2NonNulle);
    }

    /**
     * Remplace toutes les occurrences de balises du texte par les valeurs correspondantes dans la liste de valeurs.
     * @param texte Le texte contenant les balises à remplacer.
     * @param balisesEtValeurs La map ordonnée contenant les balises en clé et valeurs associées en valeur.
     * @return Le texte remplacé.
     */
    public static String remplacerBalisesAvecValeurs(String texte, LinkedHashMap<String, String> balisesEtValeurs) {
        String resultat = texte;
        for (Map.Entry<String, String> correspondance : balisesEtValeurs.entrySet()) {
            resultat = resultat.replace(correspondance.getKey(), correspondance.getValue());
        }

        return resultat;
    }

}
