package factory;

import match.MatchDAO;
import match.MatchDAO_JDBC;
import player.PlayerDAO;
import player.PlayerDAO_JDBC;
import set.SetDAO;
import set.SetDAO_JDBC;
import tournament.TournamentDAO;
import tournament.TournamentDAO_JDBC;

public class JDBCDAOFactory extends DAOFactory {

	@Override
	public TournamentDAO getTournamentDAO() {
		return new TournamentDAO_JDBC();
	}

	@Override
	public MatchDAO getMatchDAO() {
		return new MatchDAO_JDBC();
	}

	@Override
	public SetDAO getSetDAO() {
		return new SetDAO_JDBC();
	}

	@Override
	public PlayerDAO getPlayerDAO() {
		return new PlayerDAO_JDBC();
	}

}
