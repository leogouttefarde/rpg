package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * Classe de DAO générique
 * 
 * @author Jules-Eugène Demets, Léo Gouttefarde, Salim Aboubacar, Simon Rey
 */
public abstract class AbstractDAO {
    
    protected final DataSource dataSource;
    protected Connection curLink;

    protected AbstractDAO(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Méthode permettant de récupérer la connection sans autocommit.
     * 
     * @return La connection
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        curLink = dataSource.getConnection();
        curLink.setAutoCommit(false);

        return curLink;
    }

    /**
     * Méthode permettant d'initialiser une transaction avec SERIALIZABLE.
     * 
     * @return La connection
     * @throws SQLException
     */
    public Connection initConnection() throws SQLException {
        getConnection();
        curLink.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        
        return curLink;
    }

    /**
     * Fermeture d'une connexion
     *
     * @param link La connexion à fermer
     * @throws DAOException si problème lors de la fermeture de la connexion
     */
    protected void closeConnection(Connection link) throws DAOException {
        if (link != null) {
            try {
                link.close();
                
            } catch (SQLException sqle) {
                throw new DAOException("Erreur à la "
                        + "fermeture de connexion", sqle);
            }
        }
    }

    /**
     * Fermeture de la connexion
     *
     * @throws DAOException si problème lors de la fermeture de la connexion
     */
    protected void closeConnection() throws DAOException {
        if (curLink != null) {
            try {
                curLink.close();
                curLink = null;
                
            } catch (SQLException sqle) {
                throw new DAOException("Erreur à la "
                        + "fermeture de connexion", sqle);
            }
        }
    }

    /**
     * Annulation de transaction
     *
     * @param link La connexion
     * @throws DAOException si problème lors du rollback
     */
    protected void rollback(Connection link) throws DAOException {
        if (link != null) {
            try {
                link.rollback();
                
            } catch (SQLException sqle) {
                throw new DAOException("Erreur de rollback", sqle);
            }
        }
    }
    
    /**
     * Annulation de transaction
     *
     * @throws DAOException si problème lors du rollback
     */
    protected void rollback() throws DAOException {
        if (curLink != null) {
            try {
                curLink.rollback();
                
            } catch (SQLException sqle) {
                throw new DAOException("Erreur de rollback", sqle);
            }
        }
    }
    
    /**
     * Permet de fermer une requete facilement.
     * 
     * @param statement La requete
     */
    public static void CloseStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {}
        }
    }
    
    /**
     * Effectue un commit sur la connection courante.
     *
     * @throws DAOException
     */
    public void commit() throws DAOException {
        if (curLink != null) {
            try {
                curLink.commit();
                curLink = null;
                
            } catch (SQLException sqle) {
                throw new DAOException("Erreur de commit", sqle);
            }
        }
    }
}
