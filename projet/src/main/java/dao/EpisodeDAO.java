/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import modele.Aventure;
import modele.Biographie;
import modele.Episode;
import modele.Joueur;

/**
 *
 * @author Jules-Eugène Demets, Léo Gouttefarde, Salim Aboubacar, Simon Rey
 */
public final class EpisodeDAO extends AbstractEpisodeDAO {

    static private EpisodeDAO instance;
    
    private EpisodeDAO(DataSource ds) {
        super(ds);
    }
    
    public static EpisodeDAO Create(DataSource ds) {
        if (instance == null) {
            instance = new EpisodeDAO(ds);
        }

        return instance;
    }

    public static EpisodeDAO Get() {
        return instance;
    }
    
    @Override
    public List<Episode> getEpisodesEnEdition(Biographie b) throws DAOException {
        Connection c = null;
        try{
            c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from Episode e where e.biographie_id = ? and e.valide = 0 order by e.eDate");
        ps.setInt(1, b.getID());
            ResultSet rs = ps.executeQuery();
            LinkedList<Episode> epi = new LinkedList<Episode>();
            while(rs.next()){
                if(rs.getObject("aventure_id") !=null)
                    epi.add(new Episode(rs.getInt("id"), rs.getInt("eDate"), false, new Aventure(rs.getInt("aventure_id")), new Joueur(rs.getInt("mj_id")), b));
                else
                  epi.add(new Episode(rs.getInt("id"), rs.getInt("eDate"), false, null, new Joueur(rs.getInt("mj_id")), b));  
            }
            closeConnection(c);
            return epi;
        }catch(Exception e){
            throw new DAOException(null, e);
        }finally{
            closeConnection(c);
        }
    }

    @Override
    public List<Episode> getEpisodes(Biographie b) throws DAOException {
        Connection c = null;
        try{
            c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from Episode e where e.biographie_id = ? and e.valide = 1 order by e.eDate");
        ps.setInt(1, b.getID());
            ResultSet rs = ps.executeQuery();
            LinkedList<Episode> epi = new LinkedList<Episode>();
            while(rs.next()){
                if(rs.getObject("aventure_id") !=null)
                    epi.add(new Episode(rs.getInt("id"), rs.getInt("eDate"), true, new Aventure(rs.getInt("aventure_id")), new Joueur(rs.getInt("mj_id")), b));
                else
                  epi.add(new Episode(rs.getInt("id"), rs.getInt("eDate"), true, null, new Joueur(rs.getInt("mj_id")), b));  
            }
            closeConnection(c);
            return epi;
        }catch(Exception e){
            throw new DAOException(null, e);
        }finally{
            closeConnection(c);
        }
    }

    @Override
    public Collection<Episode> getEpisodesAValider(Joueur mj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Episode getEpisode(int id) throws DAOException {
        Connection c = null;
        try {
            c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement("select * from Episode e where e.id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Episode e;
            if (rs.getObject("aventure_id") != null) {
                e = new Episode(rs.getInt("id"), rs.getInt("eDate"), true, new Aventure(rs.getInt("aventure_id")), new Joueur(rs.getInt("mj_id")), null);
            } else {
                e = new Episode(rs.getInt("id"), rs.getInt("eDate"), true, null, new Joueur(rs.getInt("mj_id")), null);
            }
            closeConnection(c);
            return e;
        } catch (Exception e) {
            throw new DAOException(null, e);
        } finally {
            closeConnection(c);
        }
    }

    @Override
    public void suppressEpisode(int pid) throws DAOException {
        Connection c = null;
        try {
            c = dataSource.getConnection(); 
            PreparedStatement ps = c.prepareStatement("delete from paragraphe where episode_id =?");
            ps.setInt(1, pid);
            ps.executeUpdate();
            ps = c.prepareStatement("delete from episode where id = ?");
            ps.setInt(1, pid);
            ps.executeUpdate();          
            closeConnection(c);
           
        } catch (Exception e) {
            throw new DAOException(null, e);
        } finally {
            closeConnection(c);
        }
    }
    
}
