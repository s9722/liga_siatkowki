package liga.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBData {
	
	public Connection Cnn;
	public Statement mStatement;
	
	public void ConnectToDB(String xDBName) throws SQLException{
		//funkcja odpowiedzialna za połączenie z bazą danych
		//xDBName - nazwa bazy danych
		
		try {
		      Class.forName("org.hsqldb.jdbc.JDBCDriver");
		  } catch (Exception e) {
		      System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		      e.printStackTrace();
		      return;
		  }

		try {
			Cnn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/"+xDBName, "SA", "");
			mStatement = Cnn.createStatement();
			System.out.println("Połączenie z bazą danych powiodło się!");
			CreateTableTeams();								//utworzenie tabeli drużyn
			CreateTablePlayers();							//utworzenie tabeli zawodników
		} catch (SQLException e) {
			System.err.println("ERROR: Nie udalo sie polaczyc do bazy "+xDBName);
			e.printStackTrace();
		}
		
	}

	public boolean CreateTableTeams() {
		//funkcja tworząca tabelę drużyn
		
		String pSQL;
		int pUpdateCount;
		DatabaseMetaData pMetaData;
		ResultSet pResultSet;
		
		try{
			pMetaData = Cnn.getMetaData();
			pResultSet = pMetaData.getTables(null, null, "TEAMS", null);
			if (pResultSet.next()) return true;				//jeśli tabela instnieje wyjście	
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		pSQL= "CREATE TABLE Teams (" +
		"IdxTeam INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY ," +
		"Ne varchar(50)," +
		"PlayersCount int," +
		"ShortNe varchar(50)"+
		")";

		try {
			pUpdateCount = mStatement.executeUpdate(pSQL);
			if( pUpdateCount > 0 )
				System.out.println("Udalo sie utworzyc tabele DRUŻYN");
		} catch (SQLException e) {
			System.err.println("ERROR: Nie udalo sie utworzyc tabeli DRUŻYN");
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public boolean CreateTablePlayers() {
		//funkcja tworząca tabelę zawodników
		
		String pSQL;
		int pUpdateCount;
		DatabaseMetaData pMetaData;
		ResultSet pResultSet;
		
		try{
			pMetaData = Cnn.getMetaData();
			pResultSet = pMetaData.getTables(null, null, "PLAYERS", null);
			if (pResultSet.next()) return true;				//jeśli tabela instnieje wyjście	
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		pSQL= "CREATE TABLE Players (" +
		"IdxPlayer INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY ," +
		"Ne varchar(50)," +
		"IdxTeam Integer REFERENCES Teams(IdxTeam)" +
		")";

		try {
			pUpdateCount = mStatement.executeUpdate(pSQL);
			if( pUpdateCount > 0 )
				System.out.println("Udalo sie utworzyc tabele ZAWODNIKÓW");
		} catch (SQLException e) {
			System.err.println("ERROR: Nie udalo sie utworzyc tabeli ZAWODNIKÓW");
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public ResultSet GetResultSet(String xSQL){
		//funkcja zwracająca rekordy za pomocą obiektu ResultSet
		//xSQL - zapytanie, dla którego zwrócimy obiekt ResultSet
		
		ResultSet pResultSet;
		
		pResultSet= null;
		
		try{
			pResultSet = mStatement.executeQuery(xSQL);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
		return pResultSet;
		
	}

	public boolean ExecuteQuery(String xSQL){
		//funkcja wykonująca zapytanie dodające rekord
		//xSQL - zapytanie SQL do wykonania
		
		try {
			mStatement.execute(xSQL);
		} catch (SQLException e) {
			System.err.println("ERROR: Wykonanie zapytania nie powiodło się: " +xSQL);
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}

	public boolean IsObjectNeInDataTable(String xTableNe, String xFieldNe, String xNe){
		//funkcja sprawdzająca, czyw tabeli istnieje obiekt o zadanej nazwie
		//xTableNe - nazwa tabeli
		//xFieldNe - nazwa pola w bazie
		//xNe - nazwa obiektu
		
		ResultSet pResultSet;

		pResultSet = GetResultSet("SELECT * FROM " + xTableNe + " WHERE " + xFieldNe + " = '" + xNe + "'");
		
		try {
			if(pResultSet.next()) return true;					//jeśli drużyna już istnieje
		} catch (SQLException e) {
		}			
		
		return false;
		
	}

	public Connection GetCnn() {
		
		return Cnn;
		
	}
}

