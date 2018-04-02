import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

public class ScoreTest {

	@Test
	public void test() throws SQLException {
		String playerID=Integer.toString(12);
		String matchID=Business.findByPlayerID(playerID);
		assertEquals(Business.updateScore(playerID),"6 - 5");
		Business.decreaseScore(matchID);
	}

}