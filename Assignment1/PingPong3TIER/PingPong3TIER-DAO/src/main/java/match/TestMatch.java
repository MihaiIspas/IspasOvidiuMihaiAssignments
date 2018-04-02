package match;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TestMatch {

	public static void main(String[] args) throws SQLException {
		MatchDAO match = new MatchDAO();
		//match.insert("0 - 0", "Match1", "waiting", "2",null,null);
		//match.delete("2");
		LinkedHashMap<String,String[]> map=match.selectByTournamentID("19");
		//match.selectAll();
		//System.out.println("Hello");
		for(String s:map.keySet()) {
			String[] str=new String[5];
			str=map.get(s);
			System.out.println(s+" : ");
			for(int i=0;i<str.length;i++) {
				System.out.print(str[i]+" ");
			}
			System.out.print("\n");
		}
		//match.selectByTournamentID("15");
	}

}
