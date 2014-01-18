
package liga.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liga.db.DBData;
import liga.uinterface.UserInterface;


public class Team extends ObjectBase implements IObjectBase {

	public int PlayersCount;
	public String ShortNe;
	
	public Team(DBData xDBData){
		
		super(xDBData);
		
		this.DBData = xDBData;
		
	}
	
	public Team(DBData xDBData, int xIdx, String xNe, int xPlayersCount, String xShortNe){
		
		super(xDBData);
		
		Idx = xIdx;
		Ne = xNe;
		PlayersCount = xPlayersCount;
		ShortNe = xShortNe;
		
	}
		
	public void SaveObjectToDB() {
		//funkcja zapisująca studenta do bazy
	
		PreparedStatement pIdentity;
		PreparedStatement pPrparedStatement;
		ResultSet pResult;
		
		if(this.DBData.IsObjectNeInDataTable("Teams", "Ne", this.Ne)) return;//jeśli drużyna już istnieje
		
		try {
		  pPrparedStatement = DBData.Cnn.prepareStatement("INSERT INTO Teams (Ne, PlayersCount, ShortNe) VALUES (?, ?, ?)");
		  pPrparedStatement.setString(1, this.Ne);
		  pPrparedStatement.setInt(2, this.PlayersCount);
		  pPrparedStatement.setString(3, this.ShortNe);
		  pPrparedStatement.executeUpdate();
	      pIdentity = DBData.Cnn.prepareStatement("CALL IDENTITY()");
	      pResult = pIdentity.executeQuery();
	      pResult.next();
	      this.Idx = pResult.getInt(1);
	      pResult.close();
	      CreatePlayers();									//utworzenie zawodników drużyny
	      System.out.println("Dodano drużynę o indeksie "+ this.Idx);
		} catch (SQLException e) {
			System.err.println("Dodanie dryżynu nie powiodło się: "+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreatePlayers(){
		//funkcja tworząca zawodników dla danej drużyny
		
		int i;
		Player pPlayer;
		
		for(i=1; i <= this.PlayersCount; i++) {				//pętla wykona się tyle razy, ilu mamy zawodników w drużynie
			pPlayer = new Player(this.DBData);
			pPlayer.Ne = this.ShortNe + " zawodnik "+ i;
			pPlayer.IdxTeam = this.Idx;
			pPlayer.SaveObjectToDB();
		}
		
	}
	
	public void CreateByUserInput(UserInterface xUserInterface){
		//funkcja tworząca uzytkownika po wprowadzeniu danych przez użytkownika
		//xUserInterface - obiekt interfeksu użytkownika służący do wprowadzenia danych
		
		System.out.println("Tworzenie nowej drużyny");
		
		System.out.println("Nazwa : ");
		Ne = xUserInterface.GetUserAnswer();
		
		System.out.println("Licza graczy : ");
		PlayersCount = Integer.parseInt(xUserInterface.GetUserAnswer()); 
		
		System.out.println("Nazwa skrócona : ");
		ShortNe = xUserInterface.GetUserAnswer();
		
		SaveObjectToDB();
		
	}
	
	public void ReadDataByResultSet(ResultSet xResultSet){
		//funkcja czytająca dane o druzynie z obiektu resultset
		//xResultSet - czytany obiekt
		
		try {
			this.Ne = xResultSet.getString("ne");
			this.ShortNe = xResultSet.getString("shortne");
			this.PlayersCount = xResultSet.getInt("playerscount");
			this.Idx = xResultSet.getInt("idxteam");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String GetDisplayStr(int xOrderNumber){
		//funkcja zwracajaca tekst wyświetlający informacje o drużynie
		//xOrderNumber - numer porządkowy do wyświetlania 
		
		String pStr;
		
		pStr = xOrderNumber + ". " + this.Ne + "   " + this.ShortNe + "   " + this.PlayersCount;
		
		return pStr;
		
	}

}