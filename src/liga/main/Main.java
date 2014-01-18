package liga.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import liga.db.DBData;
import liga.uinterface.UserAnswerEnum;
import liga.uinterface.UserInterface;

public class Main {

	private UserInterface mUserInterface = new UserInterface();
	private DBData mDBData;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main().StartApplication();					//wystartowanie aplikacji
	    
	}
	
	private void StartApplication(){
		//funkcja startujaca aplikację
		
		mUserInterface = new UserInterface();
		mDBData = new DBData();
		
		mUserInterface.DrawHeader();		
		
		try {
			mDBData.ConnectToDB("liga_siatkowki");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while ( CommunicateWithUser() ) {}
		
	}

	private boolean CommunicateWithUser(){
		//funkcja służąca do komunikacji z użytkownikiem
		
		String pUserAnswer;
		UserAnswerEnum pUserAnswerEnum;
		
		mUserInterface.ShowMenu();							//pokzazanie menu
		
		pUserAnswer = mUserInterface.GetUserAnswer();		//pobranie odpowiedzi od użytkownika
		
		pUserAnswerEnum = UserAnswerEnum.ConvertStrToUserAnswer(pUserAnswer);//przekonwertownie odpowiedzi użytkownika na numerator odpowiedzi
		
		switch(pUserAnswerEnum){
			case AddTeam:									//jeśli wybraliśmy tworzenie użytkownika
				CreateTeam();
				break;
			case EditTeam:									//jeśli wybrano edycję drużyny
				EditTeam();
				break;
			case DelTeam:									//jeśli wybrano usunięcie drużyny
				DeleteTeam();
				break;
			case ShowTeams:									//jeśli wybrano pokazanie wszystkich drużyn
				ShowTeams();
				break;
			case ShowTeamPlayers:							//jeśli wybrano pokazanie zawodników drużyny
				ShowTeamPlayers();
				break;
			case Quit:										//jeśli wybrano zakończenie programu
				return false;
			case InvalidInput:								//jeśli wprowadzono niepoprawne dane
				System.out.println("Wprowadzono nieprawidłową opcję!");
				break;
		}
		
		return true;
		
	}

	private void CreateTeam(){
		//funkcja tworząca nową drużynę
		
		Team pTeam;
		
		pTeam = new Team(mDBData);
		
		pTeam.CreateByUserInput(mUserInterface);			//utworzenie drużyny przez wprowadzenie danych użytkownika
		
	}
	
	private void ShowTeams(){
		//funkcja pokazująca drużyny 
		
		ResultSet pResultSet;
		Team pTeam;
		int i;
		
		pResultSet = mDBData.GetResultSet("SELECT * FROM Teams");
	
		i = 0;
		
		System.out.println("\nLP.   Nazwa   Nazwa skrócona   Liczba zawodników");
		
		try {
			while(pResultSet.next()){
				i++;
				pTeam = new Team(mDBData);
				pTeam.ReadDataByResultSet(pResultSet);
				System.out.println(pTeam.GetDisplayStr(i));	
			}
		} catch (SQLException e) {
			System.err.println("Nie udało się pobrać drużyn");
			e.printStackTrace();
		}
		
	}
	
	private void DeleteTeam(){
		//funkcja usuwajaca drużynę
		
		String pNe, pSQL;
		
		System.out.println("Nazwa drużyny, którą usuwamy: ");
		
		pNe = mUserInterface.GetUserAnswer();				//pobranie nazwy drużyny
		
		if(!mDBData.IsObjectNeInDataTable("Teams", "Ne", pNe)) {
			System.out.println("Drużyna nie istnieje!");
			return;
		}

		pSQL = "DELETE FROM Teams WHERE Ne = '" + pNe + "'";
		
		if(!mDBData.ExecuteQuery(pSQL))return;
		
		System.out.println("Usunięto drużynę " + pNe);
		
	}
	
	private void EditTeam(){
		//funkcja zmieniająca nazwę drużyny
		
		String pNe, pSQL, pNe_New;
		
		System.out.println("Nazwa drużyny, którą edytujemy: ");
		
		pNe = mUserInterface.GetUserAnswer();				//pobranie nazwy drużyny
		
		if(!mDBData.IsObjectNeInDataTable("Teams", "Ne", pNe)) {
			System.out.println("Drużyna nie istnieje!");
			return;
		}

		System.out.println("Nowa nazwa drużyny: ");
		
		pNe_New = mUserInterface.GetUserAnswer();				//pobranie nazwy drużyny
		
		pSQL = "UPDATE Teams SET Ne = '" + pNe_New + "' WHERE Ne = '" + pNe + "'";
		
		mDBData.ExecuteQuery(pSQL);
	
	}
	
	private void ShowTeamPlayers(){
		//funkcja pokazująca drużyny 
		
		String pNe, pSQL;
		ResultSet pResultSet;
		Player pPlayer;
		int i;
		
		System.out.println("Nazwa drużyny: ");
		
		pNe = mUserInterface.GetUserAnswer();				//pobranie nazwy drużyny
		
		if(!mDBData.IsObjectNeInDataTable("Teams", "Ne", pNe)) {
			System.out.println("Drużyna nie istnieje!");
			return;
		}
		
		pSQL = "SELECT * FROM Players LEFT JOIN Teams ON Teams.IdxTeam = Players.IdxTeam "+
			"WHERE Teams.Ne = '" + pNe + "'";
		
		pResultSet = mDBData.GetResultSet(pSQL);			//pobranie zawodników z danej drużyny
	
		i = 0;
		
		System.out.println("\nZawodnicy:");
		
		try {
			while(pResultSet.next()){
				i++;
				pPlayer = new Player(mDBData);
				pPlayer.ReadDataByResultSet(pResultSet);
				System.out.println(pPlayer.Ne);	
			}
		} catch (SQLException e) {
			System.err.println("Nie udało się pobrać zawodników");
			e.printStackTrace();
		}
		
	}
}
