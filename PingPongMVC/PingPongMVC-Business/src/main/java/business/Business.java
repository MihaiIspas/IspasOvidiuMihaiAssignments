package business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;

import dto.MatchDTO;
import dto.PlayerDTO;
import dto.SetDTO;
import dto.TournamentDTO;
import factory.DAOFactory;
import factory.HibernateDAOFactory;
import match.MatchDAO_Hibernate;
import match.MatchDAO_JDBC;
import match.MatchObj;
import player.PlayerDAO_Hibernate;
import player.PlayerDAO_JDBC;
import player.PlayerObj;
import set.SetDAO_Hibernate;
import set.SetDAO_JDBC;
import set.SetObj;
import tournament.TournamentDAO_Hibernate;
import tournament.TournamentDAO_JDBC;
import tournament.TournamentObj;

public class Business extends Observable {
	
	static PlayerDAO_JDBC playerDAOJDBC=new PlayerDAO_JDBC();
	static PlayerDAO_Hibernate playerDAOHibernate=new PlayerDAO_Hibernate();
	static TournamentDAO_JDBC tournamentDAOJDBC=new TournamentDAO_JDBC();
	static TournamentDAO_Hibernate tournamentDAOHibernate=new TournamentDAO_Hibernate();
	static MatchDAO_JDBC matchDAOJDBC=new MatchDAO_JDBC();
	static MatchDAO_Hibernate matchDAOHibernate=new MatchDAO_Hibernate();
	static SetDAO_JDBC setDAOJDBC=new SetDAO_JDBC();
	static SetDAO_Hibernate setDAOHibernate=new SetDAO_Hibernate();
	
	private static final String FILENAME = "C:\\Users\\Hp\\eclipse-workspace\\PingPongMVC\\PingPongMVC-Business\\src\\main\\java\\business\\DAConfig";
	
	BufferedReader br = null;
	FileReader fr = null;
	
	DAOFactory DAO;
	
	public LinkedHashMap<TournamentDTO,LinkedHashMap<MatchDTO,ArrayList<SetDTO>>> map;
	public ArrayList<PlayerDTO> playerList;
	
	public Business() throws IOException {
		fr=new FileReader(FILENAME);
		br = new BufferedReader(fr);
		String currentLine;
		String currLine = null;
		while ((currentLine = br.readLine()) != null) {
			currLine=currentLine;
		}
		//System.out.println(currLine);
		if(currLine.equals("HIBERNATE")) {
			DAO=DAOFactory.getInstance(currLine);
		}
		if(currLine.equals("JDBC")) {
			DAO=DAOFactory.getInstance(currLine);
		}
		
	}
	
	public int signIn(String userName,String password) {
		for(PlayerDTO p:playerList) {
			if(p.getUserName().equals(userName) && p.getPassword().equals(password)) {
				if(p.getType().equals("A")) {
					return 1;
				}
				else {
					return p.getID();
				}
			}
		}
		return 0;
	}
	
	public static void copyTournamentObjToDTO(TournamentObj tour,TournamentDTO tourDTO) {
		tourDTO.setID(tour.getID());
		tourDTO.setName(tour.getName());
		tourDTO.setStatus(tour.getStatus());
		tourDTO.setPrice(tour.getPrice());
		tourDTO.setNumberPlayers(tour.getNumberPlayers());
	}
	
	public static void copyPlayerObjToDTO(PlayerObj player,PlayerDTO playerDTO) {
		playerDTO.setID(player.getID());
		playerDTO.setName(player.getName());
		playerDTO.setAge(player.getAge());
		playerDTO.setUserName(player.getUserName());
		playerDTO.setPassword(player.getPassword());
		playerDTO.setAccount(player.getAccount());
		//playerDTO.setAvailable(player.getAvailable());
		if(player.getAvailable()==0) {
			playerDTO.setAvailable("Available");
		}
		if(player.getAvailable()==1) {
			playerDTO.setAvailable("Not available");
		}
		playerDTO.setType(player.getType());
	}
	
	public static void copyMatchObjToDTO(MatchObj match,MatchDTO matchDTO) {
		matchDTO.setID(match.getID());
		matchDTO.setName(match.getName());
		matchDTO.setScore(match.getScore());
		matchDTO.setStatus(match.getStatus());
		matchDTO.setTournamentID(match.getTournamentID());
		matchDTO.setPlayer1ID(Integer.toString(match.getPlayer1ID()));
		matchDTO.setPlayer2ID(Integer.toString(match.getPlayer2ID()));
		matchDTO.setWinnerID(Integer.toString(match.getWinnerID()));
	}
	
	public static void copySetObjToDTO(ArrayList<SetObj> setList,ArrayList<SetDTO> setDTOList) {
		for(SetObj set:setList) {
			SetDTO setDTO=new SetDTO();
			setDTO.setID(set.getID());
			setDTO.setName(set.getName());
			setDTO.setScore(set.getScore());
			setDTO.setStatus(set.getStatus());
			setDTO.setMatchID(set.getMatchID());
			setDTO.setScoreLimit(set.getScoreLimit());
			setDTOList.add(setDTO);
		}
	}
	
	public static void populateTournamentDTO(TournamentDTO tourDTO,int ID,String name,String status,float price,int nrPlayers,LocalDate date) {
		tourDTO.setID(ID);
		tourDTO.setName(name);
		tourDTO.setStatus(status);
		tourDTO.setPrice(price);
		tourDTO.setNumberPlayers(nrPlayers);
		tourDTO.setDate(date);
	}
	
	public static void populatePlayerDTO(PlayerDTO playerDTO,int ID,String name,int age,int available,String userName,String password,float account) {
		playerDTO.setID(ID);
		playerDTO.setName(name);
		playerDTO.setAge(age);
		playerDTO.setUserName(userName);
		playerDTO.setPassword(password);
		playerDTO.setAccount(account);
		//playerDTO.setAvailable(available);
		if(available==0) {
			playerDTO.setAvailable("Available");
		}
		if(available==1) {
			playerDTO.setAvailable("Not available");
		}
		playerDTO.setType("P");
	}
	
	public static void populateMatchDTO(MatchDTO matchDTO,int ID,String name,String score,String status,Integer tourID,String player1ID,String player2ID,String winnerID) {
		matchDTO.setID(ID);
		matchDTO.setName(name);
		matchDTO.setScore(score);
		matchDTO.setStatus(status);
		matchDTO.setTournamentID(tourID);
		matchDTO.setPlayer1ID(player1ID);
		matchDTO.setPlayer2ID(player2ID);
		matchDTO.setWinnerID(winnerID);
	}
	
	public static void populateSetDTO(SetDTO setDTO,int ID,String name,String score,String status,Integer matchID,int scoreLimit) {
		setDTO.setID(ID);
		setDTO.setName(name);
		setDTO.setScore(score);
		setDTO.setStatus(status);
		setDTO.setMatchID(matchID);
		setDTO.setScoreLimit(scoreLimit);
	}
	
	public PlayerDTO getPlayerByID(int ID) {
		for(PlayerDTO p:playerList) {
			if(p.getID()==ID) {
				return p;
			}
		}
		return null;
	}
	
	public TournamentDTO getTournamentByID(int ID) {
		TournamentDTO tour=new TournamentDTO();
		for(TournamentDTO t:map.keySet()) {
			if(t.getID()==ID) {
				tour=t;
			}
		}
		return tour;
	}
	
	public MatchDTO getMatchByID(int ID) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.getID()==ID) {
					return m;
				}
			}
		}
		return null;
	}
	
	public MatchDTO getMatchByPlayerID(int ID) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.getPlayer1ID()!=null) {
					if(m.getPlayer1ID().equals(Integer.toString(ID))) {
						return m;
					}
				}
				if(m.getPlayer2ID()!=null) {
					if(m.getPlayer2ID().equals(Integer.toString(ID))) {
						return m;
					}
				}
			}
		}
		return null;
	}
	
	public SetDTO getSetByID(int ID) {
		SetDTO set=new SetDTO();
		boolean break1=false,break2=false;
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				for(SetDTO s:map.get(t).get(m)) {
					if(s.getID()==ID) {
						set=s;
						break1=true;
						break2=true;
						break;
					}
				}
				if(break2==true) {
					break;
				}
			}
			if(break1==true) {
				break;
			}
		}
		return set;
	}
	
	public int getOpponentID(int playerID) {
		int opID=0;
		MatchDTO match=this.findMatchByPlayerID(playerID);
		//System.out.println(match.toString());
		//opID=Integer.parseInt(match.getPlayer2ID());
		if(match.getPlayer1ID()!=null) {
			if(match.getPlayer1ID().equals(Integer.toString(playerID))) {
				if(match.getPlayer2ID()!=null) {
					opID=Integer.parseInt(match.getPlayer2ID());
				}
			}
		}
		if(match.getPlayer2ID()!=null) {
			if(match.getPlayer2ID().equals(Integer.toString(playerID))) {
				if(match.getPlayer1ID()!=null) {
					opID=Integer.parseInt(match.getPlayer1ID());
				}
			}
		}
		return opID;
	}
	
	public void getPlayersFromDB() throws SQLException {
		playerList=new ArrayList<PlayerDTO>();
		for(PlayerObj p:DAO.getPlayerDAO().selectAll()) {
			PlayerDTO playerDTO=new PlayerDTO();
			copyPlayerObjToDTO(p,playerDTO);
			playerList.add(playerDTO);
		}
	}
	
	public void getTournamentsFromDB() throws SQLException {
		map=new LinkedHashMap<TournamentDTO,LinkedHashMap<MatchDTO,ArrayList<SetDTO>>>();
		for(TournamentObj t:DAO.getTournamentDAO().selectAll()) {
			LinkedHashMap<MatchDTO,ArrayList<SetDTO>> matchMap=new LinkedHashMap<MatchDTO,ArrayList<SetDTO>>();
			TournamentDTO tourDTO=new TournamentDTO();
			//System.out.println(t.toString());
			populateTournamentDTO(tourDTO, t.getID(), t.getName(), t.getStatus(), t.getPrice(), t.getNumberPlayers(),t.getStartDate().toLocalDate());
			for(MatchObj m:DAO.getMatchDAO().selectByTournamentID(t.getID())) {
				//System.out.println("hei");
				MatchDTO matchDTO=new MatchDTO();
				String player1ID=null,player2ID=null,winnerID=null;
				//System.out.println(m.getPlayer1ID());
				if(m.getPlayer1ID()!=null) {
					player1ID=Integer.toString(m.getPlayer1ID());
				}
				if(m.getPlayer2ID()!=null) {
					player2ID=Integer.toString(m.getPlayer2ID());
				}
				if(m.getWinnerID()!=null) {
					winnerID=Integer.toString(m.getWinnerID());
				}
				populateMatchDTO(matchDTO, m.getID(), m.getName(), m.getScore(), m.getStatus(), m.getTournamentID(), player1ID, player2ID, winnerID);
				ArrayList<SetDTO> setDTOList=new ArrayList<SetDTO>();
				copySetObjToDTO(DAO.getSetDAO().selectByMatchID(m.getID()),setDTOList);
				matchMap.put(matchDTO, setDTOList);
			}
			map.put(tourDTO, matchMap);
		}
	}
	
	public void createPaidTournament(String tournamentName,float price,LocalDate startDate) throws SQLException {
		String matchName=null;
		String setName=null;
		Integer tourID=DAO.getTournamentDAO().insert(tournamentName, "Upcoming", 0, price,Date.valueOf(startDate));
		TournamentDTO tourDTO=new TournamentDTO();
		populateTournamentDTO(tourDTO,tourID,tournamentName,"Upcoming",price,0,startDate);
		LinkedHashMap<MatchDTO,ArrayList<SetDTO>> matchList=new LinkedHashMap<MatchDTO,ArrayList<SetDTO>>();
		for(int i=1;i<=4;i++) {
			matchName="Match "+Integer.toString(i)+" - "+tournamentName;
			int matchID=DAO.getMatchDAO().insert(matchName, "0 - 0", "Waiting for players", tourID);
			MatchDTO matchDTO=new MatchDTO();
			populateMatchDTO(matchDTO,matchID,matchName,"0 - 0","Waiting for players",tourID,null,null,null);
			ArrayList<SetDTO> setList=new ArrayList<SetDTO>();
			for(int j=1;j<=5;j++) {
				setName="Set "+Integer.toString(j);
				int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for players", matchID, 11);
				SetDTO setDTO=new SetDTO();
				populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for players",matchID,11);
				setList.add(setDTO);
			}
			matchList.put(matchDTO, setList);
		}
		
		int matchID=DAO.getMatchDAO().insert("Semifinal 1 - "+tournamentName, "0 - 0", "Waiting for players", tourID);
		MatchDTO matchDTO=new MatchDTO();
		populateMatchDTO(matchDTO,matchID,"Semifinal 1 - "+tournamentName,"0 - 0","Waiting for players",tourID,null,null,null);
		ArrayList<SetDTO> setList=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for players", matchID, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for players",matchID,11);
			setList.add(setDTO);
		}
		matchList.put(matchDTO, setList);
		
		int matchID1=DAO.getMatchDAO().insert("Semifinal 2 - "+tournamentName, "0 - 0", "Waiting for players", tourID);
		MatchDTO matchDTO1=new MatchDTO();
		populateMatchDTO(matchDTO1,matchID1,"Semifinal 2 - "+tournamentName,"0 - 0","Waiting for players",tourID,null,null,null);
		ArrayList<SetDTO> setList1=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for players", matchID1, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for players",matchID1,11);
			setList1.add(setDTO);
		}
		matchList.put(matchDTO1, setList1);
		
		int matchID2=DAO.getMatchDAO().insert("Final - "+tournamentName, "0 - 0", "Waiting for players", tourID);
		MatchDTO matchDTO2=new MatchDTO();
		populateMatchDTO(matchDTO2,matchID2,"Final - "+tournamentName,"0 - 0","Waiting for players",tourID,null,null,null);
		ArrayList<SetDTO> setList2=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for players", matchID2, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for players",matchID2,11);
			setList2.add(setDTO);
		}
		matchList.put(matchDTO2, setList2);
		//System.out.println(tourDTO.toString());
		map.put(tourDTO, matchList);
		setChanged();
		notifyObservers();
	}
	
	public void createFreeTournament(String tournamentName,LocalDate startDate) throws SQLException {
		String matchName=null;
		String setName=null;
		int tourID=DAO.getTournamentDAO().insert(tournamentName, "Upcoming", 0, 0,Date.valueOf(startDate));
		TournamentDTO tourDTO=new TournamentDTO();
		populateTournamentDTO(tourDTO,tourID,tournamentName,"Upcoming",0,0,startDate);
		LinkedHashMap<MatchDTO,ArrayList<SetDTO>> matchList=new LinkedHashMap<MatchDTO,ArrayList<SetDTO>>();
		for(int i=1;i<=4;i++) {
			matchName="Match "+Integer.toString(i)+" - "+tournamentName;
			int matchID=DAO.getMatchDAO().insert(matchName, "0 - 0", "Waiting for players", tourID);
			MatchDTO matchDTO=new MatchDTO();
			populateMatchDTO(matchDTO,matchID,matchName,"0 - 0","Waiting for players",tourID,null,null,null);
			ArrayList<SetDTO> setList=new ArrayList<SetDTO>();
			for(int j=1;j<=5;j++) {
				setName="Set "+Integer.toString(j);
				int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for players", matchID, 11);
				SetDTO setDTO=new SetDTO();
				populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for players",matchID,11);
				setList.add(setDTO);
			}
			matchList.put(matchDTO, setList);
		}
		
		int matchID=DAO.getMatchDAO().insert("Semifinal 1 - "+tournamentName, "0 - 0", "Waiting for semifinalists", tourID);
		MatchDTO matchDTO=new MatchDTO();
		populateMatchDTO(matchDTO,matchID,"Semifinal 1 - "+tournamentName,"0 - 0","Waiting for semifinalists",tourID,null,null,null);
		ArrayList<SetDTO> setList=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for semifinalists", matchID, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for semifinalists",matchID,11);
			setList.add(setDTO);
		}
		matchList.put(matchDTO, setList);
		
		int matchID1=DAO.getMatchDAO().insert("Semifinal 2 - "+tournamentName, "0 - 0", "Waiting for semifinalists", tourID);
		MatchDTO matchDTO1=new MatchDTO();
		populateMatchDTO(matchDTO1,matchID1,"Semifinal 2 - "+tournamentName,"0 - 0","Waiting for semifinalists",tourID,null,null,null);
		ArrayList<SetDTO> setList1=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for semifinalists", matchID1, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for semifinalists",matchID1,11);
			setList1.add(setDTO);
		}
		matchList.put(matchDTO1, setList1);
		
		int matchID2=DAO.getMatchDAO().insert("Final - "+tournamentName, "0 - 0", "Waiting for finalists", tourID);
		MatchDTO matchDTO2=new MatchDTO();
		populateMatchDTO(matchDTO2,matchID2,"Final - "+tournamentName,"0 - 0","Waiting for finalists",tourID,null,null,null);
		ArrayList<SetDTO> setList2=new ArrayList<SetDTO>();
		for(int i=1;i<=5;i++) {
			setName="Set "+Integer.toString(i);
			int setID=DAO.getSetDAO().insert(setName, "0 - 0", "Waiting for finalists", matchID2, 11);
			SetDTO setDTO=new SetDTO();
			populateSetDTO(setDTO,setID,setName,"0 - 0","Waiting for finalists",matchID2,11);
			setList2.add(setDTO);
		}
		matchList.put(matchDTO2, setList2);
		map.put(tourDTO, matchList);
			
		setChanged();
		notifyObservers();
	}
	
	public void updateTournament(int tourID,String name,float price) {
		for(TournamentDTO t:map.keySet()) {
			if(t.getID()==tourID) {
				String newName=null;
				float newPrice=0;
				if(name.equals("")) {
					newName=t.getName();
				}
				else {
					newName=name;
				}
				if(price==-1) {
					newPrice=t.getPrice();
				}
				else {
					newPrice=price;
				}
				t.setName(newName);
				t.setPrice(newPrice);
				int[] matchIDs=new int[7];
				int j=0;
				for(MatchDTO m:map.get(t).keySet()) {
					matchIDs[j]=m.getID();
					j++;
				}
				for(MatchDTO m:map.get(t).keySet()) {
					if(m.getID()==matchIDs[0]) {
						m.setName("Match 1 - "+newName);
					}
					if(m.getID()==matchIDs[1]) {
						m.setName("Match 2 - "+newName);
					}
					if(m.getID()==matchIDs[2]) {
						m.setName("Match 3 - "+newName);
					}
					if(m.getID()==matchIDs[3]) {
						m.setName("Match 4 - "+newName);
					}
					if(m.getID()==matchIDs[4]) {
						m.setName("Semifinal 1 - "+newName);
					}
					if(m.getID()==matchIDs[5]) {
						m.setName("Semifinal 2 - "+newName);
					}
					if(m.getID()==matchIDs[6]) {
						m.setName("Final - "+newName);
					}
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void deleteTournament(int tourID) throws SQLException {
		map.remove(getTournamentByID(tourID));
		for(MatchObj m:DAO.getMatchDAO().selectByTournamentID(tourID)) {
			for(SetObj s:DAO.getSetDAO().selectByMatchID(m.getID())) {
				DAO.getSetDAO().delete(s.getID());
			}
			DAO.getMatchDAO().delete(m.getID());
		}
		DAO.getTournamentDAO().delete(tourID);
			
		setChanged();
		notifyObservers();
	}
	
	public void createPlayer(String name,int age,String userName,String password,float account) throws SQLException {
		int playerID=0;
		playerID=DAO.getPlayerDAO().insert(name, age, 0, userName, password, account, "P");
		PlayerDTO playerDTO=new PlayerDTO();
		populatePlayerDTO(playerDTO, playerID, name, age, 0, userName, password, account);
		playerList.add(playerDTO);
		setChanged();
		notifyObservers();
	}
	
	public void updatePlayer(int ID,String name,int age,String userName,String password) {
		String newName=null,newUserName=null,newPassword=null;
		int newAge=0;
		for(PlayerDTO p:playerList) {
			if(p.getID()==ID) {
				if(name.equals("")) {
					newName=p.getName();
				}
				else {
					newName=name;
				}
				if(userName.equals("")) {
					newUserName=p.getUserName();
				}
				else {
					newUserName=userName;
				}
				if(password.equals("")) {
					newPassword=p.getPassword();
				}
				else {
					newPassword=password;
				}
				if(age==-1) {
					newAge=p.getAge();
				}
				else {
					newAge=age;
				}
				p.setName(newName);
				p.setAge(newAge);
				p.setUserName(newUserName);
				p.setPassword(newPassword);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void deletePlayer(int playerID) throws SQLException {
		PlayerDTO player=new PlayerDTO();
		for(PlayerDTO p:playerList) {
			if(p.getID()==playerID) {
				player=p;
			}
		}
		playerList.remove(player);
		DAO.getPlayerDAO().delete(playerID);
			
		setChanged();
		notifyObservers();
	}
	
	public static void changePlayerState(PlayerDTO player) {
		if(player.getAvailable().equals("Available")) {
			player.setAvailable("Not available");
		}
		if(player.getAvailable().equals("Not available")) {
			player.setAvailable("Available");
		}
	}
	
	public void withdrawMoney(PlayerDTO player,float amount) {
		float newAccount=player.getAccount()-amount;
		for(PlayerDTO p:playerList) {
			if(player.getID()==p.getID()) {
				p.setAccount(newAccount);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void depositMoney(PlayerDTO player,float amount) {
		float newAccount=player.getAccount()+amount;
		for(PlayerDTO p:playerList) {
			if(player.getID()==p.getID()) {
				p.setAccount(newAccount);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public int playerEnroll(int matchID,String playerID) {
		MatchDTO match=new MatchDTO();
		match=getMatchByID(matchID);
		int tourID=match.getTournamentID();
		PlayerDTO player=getPlayerByID(Integer.parseInt(playerID));
		for(TournamentDTO t:map.keySet()) {
			if(tourID==t.getID()) {
				if(t.getNumberPlayers()==8) {
					t.setStatus("Enrolled");
				}
				for(MatchDTO m:map.get(t).keySet()) {
					if(m.getID()==matchID && t.getNumberPlayers()<8) {
						t.setNumberPlayers(t.getNumberPlayers()+1);
						//System.out.println(m.getPlayer1ID());
						if(m.getPlayer1ID()==null) {
							m.setPlayer1ID(playerID);
							withdrawMoney(player,t.getPrice());
						}
						else if(m.getPlayer2ID()==null) {
							m.setPlayer2ID(playerID);
							withdrawMoney(player,t.getPrice());
						}
						else {
							return 1;
						}
					}
				}
			}
		}
		for(PlayerDTO p:playerList) {
			if(p.getID()==Integer.parseInt(playerID)) {
				p.setAvailable("Not available");
			}
		}
		setChanged();
		notifyObservers();
		return 0;
	}
	
	public int enrollPlayers(int matchID,int player1ID,int player2ID) {
		MatchDTO match=new MatchDTO();
		PlayerDTO player1=new PlayerDTO();
		PlayerDTO player2=new PlayerDTO();
		TournamentDTO tour=new TournamentDTO();
		match=getMatchByID(matchID);
		player1=getPlayerByID(player1ID);
		player2=getPlayerByID(player2ID);
		//System.out.println(player1.getID());
		int tourID=match.getTournamentID();
		int nrPlayers = 0;
		float sum=0;
		for(TournamentDTO t:map.keySet()) {
			if(t.getID()==tourID) {
				nrPlayers=t.getNumberPlayers();
				tour=t;
				sum=t.getPrice();
				t.setNumberPlayers(t.getNumberPlayers()+2);
				if(t.getNumberPlayers()==8) {
					t.setStatus("Enrolled");
				}
			}
		}
		//System.out.println(nrPlayers);
		if(match.getPlayer1ID()==null && match.getPlayer2ID()==null && nrPlayers<=8) {
			for(MatchDTO m:map.get(tour).keySet()) {
				if(m.getID()==matchID) {
					m.setPlayer1ID(Integer.toString(player1.getID()));
					m.setPlayer2ID(Integer.toString(player2.getID()));
					m.setStatus("Players enrolled");
					for(SetDTO s:map.get(tour).get(m)) {
						s.setStatus("Waiting to start");
					}
				}
			}
			for(PlayerDTO p:playerList) {
				if(p.getID()==player1ID) {
					p.setAvailable("Not available");
					withdrawMoney(p,sum);
				}
				if(p.getID()==player2ID) {
					p.setAvailable("Not available");
					withdrawMoney(p,sum);
				}
			}
			setChanged();
			notifyObservers();
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public void updateDatabase() throws SQLException {
		int available;
		for(PlayerDTO p:playerList) {
			if(p.getAvailable().equals("Available")) {
				available=0;
			}
			else {
				available=1;
			}
			if(!(p.getName().equals(DAO.getPlayerDAO().selectByID(p.getID()).getName()))) {
				DAO.getPlayerDAO().update(p.getID(), p.getName(), -1, -1, "", "", -1);
			}
			if(p.getAge()!=DAO.getPlayerDAO().selectByID(p.getID()).getAge()) {
				DAO.getPlayerDAO().update(p.getID(), "", p.getAge(), -1, "", "", -1);
			}
			if(available!=DAO.getPlayerDAO().selectByID(p.getID()).getAvailable()) {
				DAO.getPlayerDAO().update(p.getID(), "", -1, available, "", "", -1);
			}
			if(!(p.getUserName().equals(DAO.getPlayerDAO().selectByID(p.getID()).getUserName()))) {
				DAO.getPlayerDAO().update(p.getID(), "", -1, -1, p.getUserName(), "", -1);
			}
			if(!(p.getPassword().equals(DAO.getPlayerDAO().selectByID(p.getID()).getPassword()))) {
				DAO.getPlayerDAO().update(p.getID(), "", -1, -1, "", p.getPassword(), -1);
			}
			if(p.getAccount()!=DAO.getPlayerDAO().selectByID(p.getID()).getAccount()) {
				DAO.getPlayerDAO().update(p.getID(), "", -1, -1, "", "", p.getAccount());
			}
		}
		for(TournamentDTO t:map.keySet()) {
			if(!(t.getName().equals(DAO.getTournamentDAO().selectByID(t.getID()).getName()))) {
				DAO.getTournamentDAO().update(t.getID(), t.getName(), "", -1, -1,null);
			}
			if(!(t.getStatus().equals(DAO.getTournamentDAO().selectByID(t.getID()).getStatus()))) {
				DAO.getTournamentDAO().update(t.getID(), "", t.getStatus(), -1, -1,null);
			}
			if(t.getNumberPlayers()!=DAO.getTournamentDAO().selectByID(t.getID()).getNumberPlayers()) {
				DAO.getTournamentDAO().update(t.getID(), "", "", t.getNumberPlayers(), -1,null);
			}
			if(t.getPrice()!=DAO.getTournamentDAO().selectByID(t.getID()).getPrice()) {
				DAO.getTournamentDAO().update(t.getID(), "", "", -1, t.getPrice(),null);
			}
			if(!(Date.valueOf(t.getDate()).equals(DAO.getTournamentDAO().selectByID(t.getID()).getStartDate()))) {
				DAO.getTournamentDAO().update(t.getID(), "", "", -1, -1,Date.valueOf(t.getDate()));
			}
			for(MatchDTO m:map.get(t).keySet()) {
				if(!(m.getName().equals(DAO.getMatchDAO().selectByID(m.getID()).getName()))) {
					DAO.getMatchDAO().update(m.getID(), "", m.getName(), "", -1, -1, -1, -1);
				}
				if(!(m.getScore().equals(DAO.getMatchDAO().selectByID(m.getID()).getScore()))) {
					DAO.getMatchDAO().update(m.getID(), m.getScore(), "", "", -1, -1, -1, -1);
				}
				if(!(m.getStatus().equals(DAO.getMatchDAO().selectByID(m.getID()).getStatus()))) {
					DAO.getMatchDAO().update(m.getID(), "", "", m.getStatus(), -1, -1, -1, -1);
				}
				if(m.getPlayer1ID()!=null) {
					if(!(m.getPlayer1ID().equals(DAO.getMatchDAO().selectByID(m.getID()).getPlayer1ID()))) {
						DAO.getMatchDAO().update(m.getID(), "", "", "", -1, Integer.parseInt(m.getPlayer1ID()), -1, -1);
					}
				}
				if(m.getPlayer2ID()!=null) {
					if(!(m.getPlayer2ID().equals(DAO.getMatchDAO().selectByID(m.getID()).getPlayer2ID()))) {
						DAO.getMatchDAO().update(m.getID(), "", "", "", -1, -1, Integer.parseInt(m.getPlayer2ID()), -1);
					}
				}
				if (m.getWinnerID()!=null) {
					if(!(m.getWinnerID().equals(DAO.getMatchDAO().selectByID(m.getID()).getWinnerID()))) {
						DAO.getMatchDAO().update(m.getID(), "", "", "", -1, -1, -1, Integer.parseInt(m.getWinnerID()));
					}
				}
				if (m.getStatus()!=null) {
					if(!(m.getStatus().equals(DAO.getMatchDAO().selectByID(m.getID()).getStatus()))) {
						DAO.getMatchDAO().update(m.getID(), "", "", m.getStatus(), -1, -1, -1, -1);
					}
				}
				for(SetDTO s:map.get(t).get(m)) {
					if(!(s.getScore().equals(DAO.getSetDAO().selectByID(s.getID()).getScore()))) {
						DAO.getSetDAO().update(s.getID(), s.getScore(), "", "", -1, -1);
					}
					if(!(s.getStatus().equals(DAO.getSetDAO().selectByID(s.getID()).getStatus()))) {
						DAO.getSetDAO().update(s.getID(), "", "", s.getStatus(), -1, -1);
					}
					if(s.getScoreLimit()!=DAO.getSetDAO().selectByID(s.getID()).getScoreLimit()) {
						DAO.getSetDAO().update(s.getID(), "", "", "", -1, s.getScoreLimit());
					}
				}
			}
		}
	}
	
	public boolean isPlaying(int playerID) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.getPlayer1ID()!=null) {
					if(playerID==Integer.parseInt(m.getPlayer1ID())) {
						//System.out.println("is playing");
						return true;
					}
				}
				if(m.getPlayer2ID()!=null) {
					if(playerID==Integer.parseInt(m.getPlayer2ID())) {
						//System.out.println("is playing");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public MatchDTO findMatchByPlayerID(int playerID) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.getPlayer1ID()!=null) {
					if(m.getPlayer1ID().equals(Integer.toString(playerID))) {
						return m;
					}
				}
				if(m.getPlayer2ID()!=null) {
					if(m.getPlayer2ID().equals(Integer.toString(playerID))) {
						return m;
					}
				}
			}
		}
		return null;
	}
	
	public static String[] splitString(String s) {
		return s.split(" - ");
	}
	
	public TournamentDTO getTournamentByMatch(MatchDTO match) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.equals(match)) {
					return t;
				}
			}
		}
		return null;
	}
	
	public MatchDTO getSpecificMatch(MatchDTO match) {
		for(TournamentDTO t:map.keySet()) {
			for(MatchDTO m:map.get(t).keySet()) {
				if(m.equals(match)) {
					return m;
				}
			}
		}
		return null;
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
	
	static boolean notPlayersAvailable;
	//static int limit=11;
	
	public String updateScore(int playerID) throws SQLException {
		//boolean matchOver=false;
		notPlayersAvailable=false;
		String matchScore=null;
		String newScore=null;
		if(isPlaying(playerID)==true) {
			//System.out.println(isPlaying(playerID));
			MatchDTO match=getMatchByPlayerID(playerID);
			//System.out.println(match.toString());
			TournamentDTO tour=getTournamentByMatch(match);
			//System.out.println(tour.toString());
			//System.out.println(tour.getNumberPlayers());
			if(match.getID()!=0 && tour.getNumberPlayers()==8) {
				//System.out.println(match.getScore());
				if(Integer.parseInt(splitString(match.getScore())[0])<=3 && Integer.parseInt(splitString(match.getScore())[1])<=3) {
					//System.out.println(Integer.parseInt(splitString(match.getScore())[0]));
					for(SetDTO s:map.get(tour).get(match)) {
						//System.out.println(setDAO.selectByMatchID(id).get(s)[1]);
						//System.out.println(s.toString());
						//System.out.println(s.getStatus());
						if(s.getStatus().equals("Waiting to start") || s.getStatus().equals("Ongoing")) {
							int limit=s.getScoreLimit();
							//System.out.println(limit);
							int part=2;
							if(match.getPlayer1ID()!=null) {
								if(Integer.parseInt(match.getPlayer1ID())==playerID) {
									part=0;
								}
							}
							if(match.getPlayer2ID()!=null) {
								if(Integer.parseInt(match.getPlayer2ID())==playerID) {
									part=1;
								}
							}
							if(Integer.parseInt(splitString(s.getScore())[part])<=limit && match.getPlayer1ID()!=null && match.getPlayer2ID()!=null) {
								newScore=increaseScore(s.getScore(),part);
								int scorePlayer1=Integer.parseInt(splitString(newScore)[0]);
								int scorePlayer2=Integer.parseInt(splitString(newScore)[1]);
								//System.out.println(newScore);
								for(TournamentDTO t:map.keySet()) {
									if(t.getID()==tour.getID()) {
										t.setStatus("Ongoing");
									}
								}
								s.setStatus("Ongoing");
								s.setScore(newScore);
								//System.out.println(newScore);
								if(Integer.parseInt(splitString(newScore)[0])==limit || Integer.parseInt(splitString(newScore)[1])==limit) {
									if(max(scorePlayer1, scorePlayer2)-min(scorePlayer1, scorePlayer2)<2) {
										//System.out.println(max(scorePlayer1, scorePlayer2));
										limit++;
										s.setScoreLimit(limit);
										//System.out.println(limit);
										
									}
									else{
										s.setStatus("Done");
										limit=11;
										s.setScoreLimit(11);
										matchScore=match.getScore();
										if(Integer.parseInt(splitString(matchScore)[0])<=3 && Integer.parseInt(splitString(matchScore)[1])<=3) {
											String setScore=s.getScore();
											String newMatchScore=null;
											int matchPart=2;
											if(Integer.parseInt(splitString(setScore)[0])>Integer.parseInt(splitString(setScore)[1])) {
												newMatchScore=increaseScore(matchScore,0);
											}
											else {
												newMatchScore=increaseScore(matchScore,1);
											}
											match.setScore(newMatchScore);
											//int semifinal1Counter=0,semifinal2Counter=0,finalCounter=0;
											if(Integer.parseInt(splitString(newMatchScore)[0])==3 || Integer.parseInt(splitString(newMatchScore)[1])==3) {
												//System.out.println("Done");
												match.setStatus("Done");
												Integer winnerID=null;
												if(Integer.parseInt(splitString(newMatchScore)[0])==3) {
													winnerID=Integer.parseInt(match.getPlayer1ID());
													for(PlayerDTO p:playerList) {
														if(p.getID()==Integer.parseInt(match.getPlayer1ID())) {
															p.setAvailable("Available");
														}
														if(p.getID()==Integer.parseInt(match.getPlayer2ID())) {
															p.setAvailable("Available");
														}
													}
													//System.out.println(winnerID);
													match.setWinnerID(Integer.toString(winnerID));
												}
												if(Integer.parseInt(splitString(newMatchScore)[1])==3) {
													winnerID=Integer.parseInt(match.getPlayer2ID());
													for(PlayerDTO p:playerList) {
														if(p.getID()==Integer.parseInt(match.getPlayer1ID())) {
															p.setAvailable("Available");
														}
														if(p.getID()==Integer.parseInt(match.getPlayer2ID())) {
															p.setAvailable("Available");
														}
													}
													//System.out.println(winnerID);
													match.setWinnerID(Integer.toString(winnerID));
												}
												match.setPlayer1ID(null);
												match.setPlayer2ID(null);
												//for()
												if(match.getName().contains("Match 1")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Semifinal 1")) {
															m.setPlayer1ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Match 2")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Semifinal 1")) {
															m.setPlayer2ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Match 3")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Semifinal 2")) {
															m.setPlayer1ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Match 4")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Semifinal 2")) {
															m.setPlayer2ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Semifinal 1")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Final")) {
															m.setPlayer1ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Semifinal 2")) {
													for(MatchDTO m:map.get(tour).keySet()) {
														if(m.getName().contains("Final")) {
															m.setPlayer2ID(Integer.toString(winnerID));
															for(PlayerDTO p:playerList) {
																if(p.getID()==winnerID) {
																	p.setAvailable("Not available");
																}
															}
															if(m.getPlayer1ID()!=null && m.getPlayer2ID()!=null) {
																m.setStatus("Waiting to start");
																for(SetDTO set:map.get(tour).get(m)) {
																	set.setStatus("Waiting to start");
																}
															}
															match.setWinnerID(Integer.toString(winnerID));
														}
													}
												}
												if(match.getName().contains("Final")) {
													for(PlayerDTO pl:playerList) {
														if(pl.getID()==winnerID) {
															pl.setAvailable("Available");
															depositMoney(pl,8*tour.getPrice());
														}
													}
													for(TournamentDTO t:map.keySet()) {
														if(t.getID()==tour.getID()) {
															t.setStatus("Finished");
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
		setChanged();
        notifyObservers();
		return newScore;
	}
	
}
