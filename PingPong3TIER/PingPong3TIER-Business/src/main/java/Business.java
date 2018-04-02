import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import match.MatchDAO;
import player.PlayerDAO;
import set.SetDAO;
import tournament.TournamentDAO;

public class Business {

	public static PlayerDAO playerDAO = new PlayerDAO();
	public static TournamentDAO tournamentDAO = new TournamentDAO();
	public static MatchDAO matchDAO = new MatchDAO();
	public static SetDAO setDAO = new SetDAO();
	public static boolean notPlayersAvailable=false;

	public static void createTournament(String tournamentName) throws SQLException {
		String matchName=null;
		String matchID=null;
		String ID=tournamentDAO.insert(tournamentName, "Waiting for players");
		tournamentDAO.updateNrOfPlayers(ID, Integer.toString(0));
		for(int i=1;i<=4;i++) {
			matchName = "Match "+Integer.toString(i)+" - "+tournamentName;
			matchID=matchDAO.insert("0 - 0", matchName, "Waiting for players", ID, null, null);
			for(int j=0;j<5;j++) {
				setDAO.insert("0 - 0", "Waiting to start", matchID,"11","Set "+Integer.toString(j+1));
				
				//matchDAO.updateCurrentSet(matchID, );
			}
		}
		matchName="Semifinal 1 - "+tournamentName;
		matchID=matchDAO.insert("0 - 0", matchName, "Waiting for semifinalists", ID, null, null);
		for(int j=0;j<5;j++) {
			setDAO.insert("0 - 0", "Waiting to start", matchID,"11","Set "+Integer.toString(j+1));
		}
		matchName="Semifinal 2 - "+tournamentName;
		matchID=matchDAO.insert("0 - 0", matchName, "Waiting for semifinalists", ID, null, null);
		for(int j=0;j<5;j++) {
			setDAO.insert("0 - 0", "Waiting to start", matchID,"11","Set "+Integer.toString(j+1));
		}
		matchName="Final - "+tournamentName;
		matchID=matchDAO.insert("0 - 0", matchName, "Waiting for finalists", ID, null, null);
		for(int j=0;j<5;j++) {
			setDAO.insert("0 - 0", "Waiting to start", matchID,"11","Set "+Integer.toString(j+1));
		}
	}
	
	public static String getOpponentID(String id) throws SQLException {
		String opponentID=null;
		String matchID=findByPlayerID(id);
		//System.out.println(matchID);
		if(matchDAO.selectByID(matchID)[5].equals(id)) {
			//System.out.println(matchDAO.selectByID(matchID)[5].equals(id));
			//System.out.println(matchDAO.selectByID(id)[6]);
			if(matchDAO.selectByID(matchID)[6]!=null) {
				opponentID=matchDAO.selectByID(matchID)[6];
				//System.out.println(opponentID);
			}
		}
		if(matchDAO.selectByID(matchID)[6].equals(id)) {
			if(matchDAO.selectByID(matchID)[5]!=null) {
				opponentID=matchDAO.selectByID(matchID)[5];
			}
		}
		return opponentID;
	}
	
	public static void copyStringArray(String[] str1,String[] str2) {
		for(int i=0;i<str1.length;i++) {
			str2[i]=str1[i];
		}
	}
	
	public static LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>> selectTournaments() throws SQLException{
		LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>> map = new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
		LinkedHashMap<MatchObj,ArrayList<SetObj>> matchMap = new LinkedHashMap<MatchObj,ArrayList<SetObj>>();
		ArrayList<SetObj> list = new ArrayList<SetObj>();
		for(String t:tournamentDAO.selectAll().keySet()) {
			String[] tournamentFields=new String[3];
			copyStringArray(tournamentDAO.selectByID(t),tournamentFields);
			TournamentObj tour=new TournamentObj(Integer.parseInt(tournamentFields[0]),tournamentFields[1],tournamentFields[2]);
			map.put(tour, null);
			for(String m:matchDAO.selectByTournamentID(t).keySet()) {
				String[] matchFields=new String[6];
				copyStringArray(matchDAO.selectByTournamentID(t).get(m),matchFields);
				MatchObj match=new MatchObj(Integer.parseInt(m),matchFields[0],matchFields[1],matchFields[2],Integer.parseInt(matchFields[3]));
				if(matchFields[4]!=null) {
					String name=playerDAO.selectByID(matchFields[4])[1];
					//System.out.println(name);
					match.setPlayer1Name(name);
				}
				if(matchFields[5]!=null) {
					String name=playerDAO.selectByID(matchFields[5])[1];
					//System.out.println(name);
					match.setPlayer2Name(name);
				}
				matchMap.put(match, null);
				map.put(tour, matchMap);
				if(setDAO.selectByMatchID(m).keySet()!=null) {
					//System.out.println("here");
					for(String s:setDAO.selectByMatchID(m).keySet()) {
						String[] setFields=new String[4];
						copyStringArray(setDAO.selectByMatchID(m).get(s),setFields);
						//System.out.println(setFields[3]);
						SetObj set=new SetObj(Integer.parseInt(s),setFields[3],setFields[0],setFields[1],Integer.parseInt(setFields[2]));
						list.add(set);
						matchMap.put(match, list);
						map.put(tour, matchMap);
					}
					list = new ArrayList<SetObj>();
				}
			}
			matchMap = new LinkedHashMap<MatchObj,ArrayList<SetObj>>();
		}
		return map;
	}
	
	public static void updateTournament(String id,String name) throws SQLException {
		tournamentDAO.update(id, name, "");
		String matchName=null;
		String[] str=new String[7];
		int j=0;
		for(String s:matchDAO.selectByTournamentID(id).keySet()) {
			str[j]=s;
			j++;
		}
		for(int i=1;i<=4;i++) {
			matchName = "Match "+Integer.toString(i)+" - "+name;
			matchDAO.update(str[i-1], "", matchName, "", "", "", "");
		}
		matchName="Semifinal 1 - "+name;
		matchDAO.update(str[4], "", matchName, "", "", "", "");
		matchName="Semifinal 2 - "+name;
		matchDAO.update(str[5], "", matchName, "", "", "", "");
		matchName="Final - "+name;
		matchDAO.update(str[6], "", matchName, "", "", "", "");
	}
	
	public static void deleteTournament(String id) throws SQLException {
		tournamentDAO.delete(id);
		for(String m:matchDAO.selectByTournamentID(id).keySet()) {
			matchDAO.delete(m);
			for(String s:setDAO.selectByMatchID(m).keySet()) {
				setDAO.delete(s);
			}
		}
	}
	
	public static void createPlayer(String name,int age,String userName,String password) throws SQLException {
		playerDAO.insert(name, Integer.toString(age), Integer.toString(0), userName, password,"P");
	}
	
	public static ArrayList<PlayerObj> selectPlayers() throws SQLException{
		ArrayList<PlayerObj> list=new ArrayList<PlayerObj>();
		for(String p:playerDAO.selectAll().keySet()) {
			String[] s=new String[5];
			copyStringArray(playerDAO.selectAll().get(p),s);
			PlayerObj player=new PlayerObj(Integer.parseInt(p),s[0],Integer.parseInt(s[1]),s[2],s[3],s[4]);
			//System.out.println(player.getAvailability());
			list.add(player);
		}
		return list;
	}
	
	public static void updatePlayer(String id,String name,String age,String userName,String password) throws SQLException {
		playerDAO.update(id, name, age, "", userName, password);
	}
	
	public static void deletePlayer(String id) throws SQLException {
		playerDAO.delete(id);
	}
	
	public static void enrollPlayers(String tournamentID,String player1ID,String player2ID) throws NumberFormatException, SQLException {
		String number=tournamentDAO.selectNrOfPlayers(tournamentID);
		if(Integer.parseInt(number)<=8) {
			System.out.println(player1ID);
			for(String m:matchDAO.selectByTournamentID(tournamentID).keySet()) {
				if(matchDAO.selectByTournamentID(tournamentID).get(m)[2].equals("Waiting for players")) {
					//System.out.println(player1ID);
					if(playerDAO.selectByID(player1ID)[3].equals("Available") && playerDAO.selectByID(player2ID)[3].equals("Available")) {
						matchDAO.update(m, "", "", "Players enrolled", "", player1ID, player2ID);
						int nr=Integer.parseInt(number);
						nr=nr+2;
						tournamentDAO.updateNrOfPlayers(tournamentID, Integer.toString(nr));
						playerDAO.update(player1ID, "", "", "1", "", "");
						playerDAO.update(player2ID, "", "", "1", "", "");
					}
				}
				else {
					continue;
				}
			}
		}
		else {
			tournamentDAO.update(tournamentID, "", "Ongoing");
		}
	}
	
	public static boolean isPlaying(String playerID) throws SQLException {
		boolean isPlaying=false;
		//System.out.println(playerDAO.selectByID(playerID)[3]);
		if(playerDAO.selectByID(playerID)[3].equals("Not available")) {
			isPlaying=true;
		}
		//System.out.println("------------------");
		return isPlaying;
	}
	
	public static String findByPlayerID(String id) throws SQLException {
		String matchID=null;
		//System.out.println(id);
		if(isPlaying(id)==true) {
			//System.out.println(id);
			for(String t:tournamentDAO.selectAll().keySet()) {
				for(String m:matchDAO.selectByTournamentID(t).keySet()) {
					if(matchDAO.selectByTournamentID(t).get(m)[4]!=null) {
						if(matchDAO.selectByTournamentID(t).get(m)[4].equals(id)) {
							matchID=m;
							//System.out.println(m);
						}
					}
					if(matchDAO.selectByTournamentID(t).get(m)[5]!=null) {
						if(matchDAO.selectByTournamentID(t).get(m)[5].equals(id)) {
							matchID=m;
							//System.out.println(m);
						}
					}
				}
			}
		}
		return matchID;
	}
	
	
	
	public static String selectMatchNameByID(String ID) throws SQLException{
		return matchDAO.selectByID(ID)[2];
	}
	
	public static LinkedHashMap<String,String[]> selectByMatchID(String matchID) throws SQLException{
		return setDAO.selectByMatchID(matchID);
	}
	
	public static TournamentObj selectTournamentByMatchID(String ID) throws SQLException{
		TournamentObj tournament = null;
		for(String t:tournamentDAO.selectAll().keySet()) {
			String name=tournamentDAO.selectAll().get(t)[0];
			if(matchDAO.selectByID(ID)[2].contains(name)) {
				tournament=new TournamentObj(Integer.parseInt(t),name,tournamentDAO.selectAll().get(t)[1]);
			}
		}
		return tournament;
	}
	public static String increaseScore(String score,int part) {
		String[] parts=score.split(" - ");
		String newScore=null;
		if(part==0) {
			parts[0]=Integer.toString(Integer.parseInt(parts[0])+1);
			newScore=parts[0]+" - "+parts[1];
		}
		if(part==1) {
			parts[1]=Integer.toString(Integer.parseInt(parts[1])+1);
			newScore=parts[0]+" - "+parts[1];
		}
		return newScore;
	}
	
	public static void decreaseScore(String matchID) throws SQLException {
		for(String s:setDAO.selectByMatchID(matchID).keySet()) {
			if(setDAO.selectByMatchID(matchID).get(s)[1].equals("Ongoing")) {
				String[] parts=setDAO.selectByMatchID(matchID).get(s)[0].split(" - ");
				parts[0]=Integer.toString(Integer.parseInt(parts[0])-1);
				String newScore=parts[0]+" - "+parts[1];
				setDAO.update(s, newScore, "", "", "", "");
				break;
			}
			else {
				continue;
			}
		}
	}
	
	public static String[] splitString(String s) {
		return s.split(" - ");
	}
	
	public static int min(int a,int b) {
		int min=0;
		if(a<=b) {
			min=a;
		}
		else {
			min=b;
		}
		return min;
	}
	
	public static int max(int a,int b) {
		int max=0;
		if(a>=b) {
			max=a;
		}
		else {
			max=b;
		}
		return max;
	}
	
	public static void cancelEnrollment(String id) throws SQLException {
		String player1=matchDAO.selectByID(id)[5];
		String player2=matchDAO.selectByID(id)[6];
		String tourID=Integer.toString(selectTournamentByMatchID(id).getID());
		int nr=Integer.parseInt(tournamentDAO.selectNrOfPlayers(tourID));
		nr=nr-2;
		tournamentDAO.updateNrOfPlayers(tourID, Integer.toString(nr));
		playerDAO.update(player1, "", "", "0", "", "");
		playerDAO.update(player2, "", "", "0", "", "");
		matchDAO.update(id, "", "", "Waiting for players", "", "", "");
		matchDAO.resetPlayerIDs(id);
	}
	
	public static String updateScore(String playerID) throws SQLException {
		//boolean matchOver=false;
		notPlayersAvailable=false;
		String matchScore=null;
		String newScore=null;
		if(isPlaying(playerID)==true) {
			String id=findByPlayerID(playerID);
			if(id!=null) {
				if(Integer.parseInt(splitString(matchDAO.selectByID(id)[1])[0])<=3 && Integer.parseInt(splitString(matchDAO.selectByID(id)[1])[1])<=3) {
					for(String s:setDAO.selectByMatchID(id).keySet()) {
						//System.out.println(setDAO.selectByMatchID(id).get(s)[1]);
						int limit=Integer.parseInt(setDAO.selectScoreLimit(s));
						if(setDAO.selectByMatchID(id).get(s)[1].equals("Waiting to start") || setDAO.selectByMatchID(id).get(s)[1].equals("Ongoing")) {
							int part=2;
							if(matchDAO.selectByID(id)[5]!=null) {
								if(matchDAO.selectByID(id)[5].equals(playerID)) {
									part=0;
								}
							}
							if(matchDAO.selectByID(id)[6]!=null) {
								if(matchDAO.selectByID(id)[6].equals(playerID)) {
									part=1;
								}
							}
							if(Integer.parseInt(splitString(setDAO.selectByMatchID(id).get(s)[0])[part])<=limit && matchDAO.selectByID(id)[5]!=null && matchDAO.selectByID(id)[6]!=null) {
								newScore=increaseScore(setDAO.selectByMatchID(id).get(s)[0],part);
								int scorePlayer1=Integer.parseInt(splitString(newScore)[0]);
								int scorePlayer2=Integer.parseInt(splitString(newScore)[1]);
								//System.out.println(newScore);
								setDAO.update(s, newScore, "Ongoing", "","","");
								if(Integer.parseInt(splitString(newScore)[0])==limit || Integer.parseInt(splitString(newScore)[1])==limit) {
									if(max(scorePlayer1, scorePlayer2)-min(scorePlayer1, scorePlayer2)<2) {
										System.out.println(max(scorePlayer1, scorePlayer2));
										limit++;
										setDAO.update(s, "", "", "", Integer.toString(limit),"");
									}
									else{
										setDAO.update(s, "", "Done", "", "","");
										setDAO.update(s, "", "", "", "11","");
										matchScore=matchDAO.selectByID(id)[1];
										if(Integer.parseInt(splitString(matchScore)[0])<=3 && Integer.parseInt(splitString(matchScore)[1])<=3) {
											String setScore=setDAO.selectByID(s)[1];
											String newMatchScore=null;
											int matchPart=2;
											if(Integer.parseInt(splitString(setScore)[0])>Integer.parseInt(splitString(setScore)[1])) {
												newMatchScore=increaseScore(matchScore,0);
											}
											else {
												newMatchScore=increaseScore(matchScore,1);
											}
											matchDAO.update(id, newMatchScore, "", "", "", "", "");
											//int semifinal1Counter=0,semifinal2Counter=0,finalCounter=0;
											if(Integer.parseInt(splitString(newMatchScore)[0])==3 || Integer.parseInt(splitString(newMatchScore)[1])==3) {
												//System.out.println("Done");
												matchDAO.update(id, "", "", "Done", "", "", "");
												String winnerID=null;
												if(Integer.parseInt(splitString(newMatchScore)[0])==3) {
													winnerID=matchDAO.selectByID(id)[5];
													playerDAO.update(matchDAO.selectByID(id)[5], "", "", "0", "", "");
													playerDAO.update(matchDAO.selectByID(id)[6], "", "", "0", "", "");
													//System.out.println(winnerID);
													matchDAO.updateWinner(id, winnerID);
												}
												if(Integer.parseInt(splitString(newMatchScore)[1])==3) {
													winnerID=matchDAO.selectByID(id)[6];
													playerDAO.update(matchDAO.selectByID(id)[5], "", "", "0", "", "");
													playerDAO.update(matchDAO.selectByID(id)[6], "", "", "0", "", "");
													//System.out.println(winnerID);
													matchDAO.updateWinner(id, winnerID);
												}
												matchDAO.resetPlayerIDs(id);
												//for()
												if(matchDAO.selectByID(id)[2].contains("Match 1")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Semifinal 1")) {
															matchDAO.update(p, "", "", "", "", winnerID, "");
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
												if(matchDAO.selectByID(id)[2].contains("Match 2")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Semifinal 1")) {
															matchDAO.update(p, "", "", "", "", "", winnerID);
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
												if(matchDAO.selectByID(id)[2].contains("Match 3")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Semifinal 2")) {
															matchDAO.update(p, "", "", "", "", winnerID, "");
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
												if(matchDAO.selectByID(id)[2].contains("Match 4")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Semifinal 2")) {
															matchDAO.update(p, "", "", "", "", "", winnerID);
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
												if(matchDAO.selectByID(id)[2].contains("Semifinal 1")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Final")) {
															matchDAO.update(p, "", "", "", "", winnerID, "");
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
												if(matchDAO.selectByID(id)[2].contains("Semifinal 2")) {
													for(String p:matchDAO.selectAll().keySet()) {
														if(matchDAO.selectByID(p)[2].contains("Final")) {
															matchDAO.update(p, "", "", "", "", "", winnerID);
															playerDAO.update(winnerID, "", "", "1", "", "");
														}
													}
												}
											}
										}
									}
								}
							}
							else {
								//setDAO.update(s, "", "Done", "", "","");
								notPlayersAvailable=true;
							}
							
							break;
						}
						else {
							continue;
						}
					}
				}
			}
		}
		return newScore;
	}
	
	public static ArrayList<String[]> selectAllPrerequisites() throws SQLException{
		ArrayList<String[]> list=new ArrayList<String[]>();
		for(String[] s:playerDAO.selectAllPrerequisites()) {
			list.add(s);
		}
		return list;
	}
	
}
