import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Test {

	public static void main(String[] args) throws SQLException {
		//LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>> map=Business.select();
		//Business.createTournament("BucurestiTour");
		//System.out.println(map.isEmpty());
		/*for(TournamentObj t:map.keySet()) {
			System.out.println(t.getID()+" "+t.getName()+" "+t.getStatus());
			LinkedHashMap<MatchObj,ArrayList<SetObj>> matchMap=	map.get(t);
			//System.out.println(matchMap.isEmpty());
			for(MatchObj m:matchMap.keySet()) {
				System.out.println(m.getID()+" "+m.getName()+" "+m.getScore()+" "+m.getStatus()+" "+m.getTournamentID()+" "+m.getPlayer1ID()+" "+m.getPlayer2ID());
				//ArrayList<SetObj> list=matchMap.get(m);
				/*for(SetObj s:list) {
					System.out.println(s.getID()+" "+s.getScore()+" "+s.getStatus()+" "+s.getMatchID());
				}
			}
			//System.out.println("///////////////////////////////////");
		}*/
		//Business.updateTournament(19, "Tournament2");
		//Business.deleteTournament(20);
		//Business.createPlayer("Muresan Ana", 24, "muresana", "muresana");
		//Business.createPlayer("Pop Andreea", 26, "andreeap", "andreeap");
		/*ArrayList<PlayerObj> list=Business.selectPlayers();
		for(PlayerObj p:list) {
			System.out.println(p.getName()+" "+p.getAge()+" "+p.getUserName()+" "+p.getPassword());
		}*/
		//Business.updatePlayer("6", "Pop Raul", "", "raulpop", "raulpop");
		//Business.deletePlayer("8");
		//Business.enrollPlayers("21", "11", "12");
		//System.out.println(Business.findByPlayerID("12"));
		//System.out.println(Business.increaseScore("0 - 0", 1));
		//Business.updateScore("11");
	}

}
