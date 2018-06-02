package match;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws SQLException {
		MatchDAO_JDBC matchJDBC=new MatchDAO_JDBC();
		MatchDAO_Hibernate matchHibernate=new MatchDAO_Hibernate();
		
		//matchHibernate.update(122, "4 - 0", "", "", -1, -1, -1, -1);
		/*ArrayList<MatchObj> list=new ArrayList<MatchObj>();
		list=matchHibernate.selectByTournamentID(34);
		for(MatchObj m:list) {
			System.out.println(m.toString());
		}*/
		
		//matchJDBC.insert("", score, status, tournamentID, player1ID, player2ID)

	}

}
