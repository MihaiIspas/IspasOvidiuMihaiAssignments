package business;

import java.io.IOException;
import java.sql.SQLException;

import dto.MatchDTO;
import dto.PlayerDTO;
import dto.SetDTO;
import dto.TournamentDTO;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {
		Business b=new Business();
		
		b.getPlayersFromDB();
		for(PlayerDTO p:b.playerList) {
			System.out.println(p.toString());
		}
		// TODO Auto-generated method stub
		//Business.getTournamentsFromDB("J");
		//Business.getPlayersFromDB("J");
		
		//Business.createPaidTournament("Ping", 30.8f, "H");
		
		/*for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
			for(MatchDTO m:Business.map.get(t).keySet()) {
				System.out.println(m.toString());
			}
		}
		
		Business.updateTournament(50, "PingPong", -1);
		System.out.println("-------------------------------------------------------------------");
		for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
			for(MatchDTO m:Business.map.get(t).keySet()) {
				System.out.println(m.toString());
			}
		}*/
		
		//Business.createFreeTournament("Cluj", "J");
		
		/*for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
		}
		
		Business.deleteTournament(57, "J");
		System.out.println("--------------------------------------------");
		for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
		}*/
		
		//Business.updatePlayer(30,"Paul Bogdan", -1, "paulb", "paulb");
		
		/*for(PlayerDTO p:Business.playerList) {
			System.out.println(p.toString());
		}
		
		Business.deletePlayer(30, "J");
		
		for(PlayerDTO p:Business.playerList) {
			System.out.println(p.toString());
		}*/
		
		/*for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
			for(MatchDTO m:Business.map.get(t).keySet()) {
				System.out.println(m.toString());
			}
		}
		Business.enrollPlayers(191, 6, 7);
		System.out.println("--------------------------------------------");
		for(TournamentDTO t:Business.map.keySet()) {
			System.out.println(t.toString());
			for(MatchDTO m:Business.map.get(t).keySet()) {
				System.out.println(m.toString());
			}
		}
		Business.updateDatabase("J");*/
		
		//Business.enrollPlayers(211, 6, 7);
		
		//String score=Business.updateScore(6);
		//System.out.println(score);
		/*for(TournamentDTO t:Business.map.keySet()) {
			for(MatchDTO m:Business.map.get(t).keySet()) {
				for(SetDTO s:Business.map.get(t).get(m)) {
					System.out.println(s.toString());
				}
			}
		}*/
		
		//Business.updateDatabase("J");
		
		//Business.deleteTournament(52, "J");
	}

}
