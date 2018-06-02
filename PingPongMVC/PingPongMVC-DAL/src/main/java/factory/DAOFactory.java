package factory;

import java.util.logging.LogManager;

import org.hibernate.engine.internal.Collections;

import match.MatchDAO;
import player.PlayerDAO;
import set.SetDAO;
import tournament.TournamentDAO;

public abstract class DAOFactory {
	
	protected DAOFactory() {
		
	}
	
	public static DAOFactory getInstance(String type) {
		/*List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for ( Logger logger : loggers ) {
		    logger.setLevel(Level.OFF);
		}*/
		if(type.equals("HIBERNATE")) {
			return new HibernateDAOFactory();
		}
		if(type.equals("JDBC")) {
			return new JDBCDAOFactory();
		}
		return null;
	}
	public abstract TournamentDAO getTournamentDAO();
	public abstract MatchDAO getMatchDAO();
	public abstract SetDAO getSetDAO();
	public abstract PlayerDAO getPlayerDAO();

}
