package tournament;

import java.sql.SQLException;
import java.util.HashMap;


public class Test {
	
	public static void main(String[] args) throws SQLException{
		TournamentDAO p = new TournamentDAO();
		//String ID=p.insert("Tournament1","Waiting");
		//System.out.println(ID);
		//p.delete("6");
		HashMap<String,String[]> map=p.selectAll();
		p.updateNrOfPlayers("19", "0");
		for(String s:map.keySet()) {
			System.out.println(s+": ");
			//System.out.println(map.get(s)+" ");
			for(int i=0;i<map.get(s).length;i++) {
				System.out.println(map.get(s)[i]);
			}
		}
		/*String[] s=p.selectByID("19");
		for(int i=0;i<s.length;i++) {
			System.out.println(s[i]);
		}*/
	}

}
