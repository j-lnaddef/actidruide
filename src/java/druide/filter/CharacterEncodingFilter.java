//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 10/01/2014
// Auteur  : Clément VASSEUR
//-------------------------------------------------------------
package druide.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filtre interceptant chacune des requête HTTP chargé de force un encodage par défaut pour la requête et la réponse HTTP.
 * @author Clément VASSEUR
 */
public class CharacterEncodingFilter implements Filter {

    /**
     * Encodage par défaut des requêtes/réponses HTTP.
     */
    public static final String ENCODAGE = "UTF-8";
    
    @Override
    public void init(FilterConfig filterConfig)
        throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        // Forcer l'encodage de la requête et de la réponse.
        servletRequest.setCharacterEncoding(ENCODAGE);
        servletResponse.setCharacterEncoding(ENCODAGE);
        
        // Poursuivre l'exécution de la requête
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}