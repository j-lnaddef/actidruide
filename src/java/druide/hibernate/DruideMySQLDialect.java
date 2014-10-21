//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 08/11/2013
// Auteur  : Olga ROTACH
//-------------------------------------------------------------
package druide.hibernate;

import java.sql.Types;
import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Dialect MySQL pour communiquer avec la base de donnée, utilisé par Hibernate.
 * @author Olga ROTACH
 */
public class DruideMySQLDialect extends MySQL5InnoDBDialect {

    /**
     * Initialise une nouvelle instance de la classe {@link DruideMySQLDialect}.
     */
    public DruideMySQLDialect() {
        super();
        // Correspondance entre SQL et Hibernate pour le type de champ "TEXT"
        registerColumnType(Types.VARCHAR, 65535, "text");
    }
}