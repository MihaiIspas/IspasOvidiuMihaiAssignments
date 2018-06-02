package factory;

import match.MatchDAO;
import match.MatchDAO_Hibernate;
import player.PlayerDAO;
import player.PlayerDAO_Hibernate;
import set.SetDAO;
import set.SetDAO_Hibernate;
import tournament.TournamentDAO;
import tournament.TournamentDAO_Hibernate;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public TournamentDAO getTournamentDAO() {
		return new TournamentDAO_Hibernate();
	}

	@Override
	public MatchDAO getMatchDAO() {
		return new MatchDAO_Hibernate();
	}

	@Override
	public SetDAO getSetDAO() {
		return new SetDAO_Hibernate();
	}

	@Override
	public PlayerDAO getPlayerDAO() {
		return new PlayerDAO_Hibernate();
	}
}
