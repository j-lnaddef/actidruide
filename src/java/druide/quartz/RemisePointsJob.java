//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2014 ACTIMAGE
// 
// Créé le : 17/04/2014
// Auteur  : Jean-Loup Naddef
//-------------------------------------------------------------

package druide.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import druide.metier.UtilisateurMetier;

/**
 * Job s'exécutant régulièrement afin de modifier l'état des épreuves dans l'état "Validée par CS" depuis trop longtemps.
 * @author Jean-Loup Naddef
 */
public class RemisePointsJob implements Job {

    /**
     * Initialise une nouvelle instance de la classe {@link DelaiValidationJob}.
     * @author Jean-Loup Naddef
     */
    public RemisePointsJob() {
    }
    
    /**
     * Exécute une action définie à l'aide d'un CRON.
     * Permet de repasser en expertise des épreuves dans l'état "validée par CS" depuis trop longtemps.
     * @param context Contexte d'exécution de l'action.
     * @throws JobExecutionException Exception levée en cas de problème lors du lancement de l'action.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        UtilisateurMetier utilisateurMetier = new UtilisateurMetier();
        utilisateurMetier.redonnerPoints();
    }
}
