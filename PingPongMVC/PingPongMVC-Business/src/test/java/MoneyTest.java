import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import business.Business;
import dto.PlayerDTO;

public class MoneyTest {

	Business b;
	@Test
	public void test() throws SQLException, IOException {
		b=new Business();
		b.getTournamentsFromDB();
		b.getPlayersFromDB();
		PlayerDTO player=b.getPlayerByID(6);
		//System.out.println(player.toString());
		float sum=player.getAccount();
		float amount=100 + sum;
		b.depositMoney(player,100);
		System.out.println(player.getAccount());
		System.out.println(amount);
		assertEquals(player.getAccount(),amount);
	}
	
	@Test
	public void test1() throws SQLException, IOException {
		b=new Business();
		b.getTournamentsFromDB();
		b.getPlayersFromDB();
		PlayerDTO player=b.getPlayerByID(6);
		float sum=player.getAccount();
		float amount=sum-100;
		b.withdrawMoney(player, 100);
		System.out.println(player.getAccount());
		System.out.println(amount);
		assertEquals(player.getAccount(),amount);
	}

}