/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Collection;
import javax.sql.DataSource;
import modele.Biographie;
import modele.Episode;
import modele.Joueur;

/**
 *
 * @author plouviej
 */
public abstract class AbstractEpisodeDAO extends AbstractDAO{

    public AbstractEpisodeDAO(DataSource ds) {
        super(ds);
    }
    
    public abstract Collection<Episode> getEpisodesEnEdition(Biographie b) 
            throws DAOException;
    
    public abstract Collection<Episode> getEpisodes(Biographie b) 
            throws DAOException; 
    
    public abstract Collection<Episode> getEpisodesAValider(Joueur mj) 
            throws DAOException; 
    
}
