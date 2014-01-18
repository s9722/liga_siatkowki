package liga.db;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;


import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBDataTest {

	private static DBData DBData;
	
	@BeforeClass
	public static void setUp() {
		
		DBData = new DBData();
		
		try {
			DBData.ConnectToDB("liga_siatkowki");
		} catch (SQLException e) {
			fail("Failed");
		}
		
	}
	
	@Test
	public void DBCnnTest() {
		
		assertNotNull(DBData.GetCnn());
		
	}
	
	@Test
	public void TeamsTableExistsTest() {
	
		assertTrue(DBData.CreateTableTeams());
		
	}
	
	@Test
	public void TeamsTableExistsPlayers() {
	
		assertTrue(DBData.CreateTableTeams());
		
	}
	
	@Test
	public void GetResultSetTest() {
	
		assertNotNull(DBData.GetResultSet("SELECT * FROM Teams"));
		
	}
	
	@Test
	public void ExecuteQueryTest() {
	
		assertTrue(DBData.ExecuteQuery("SELECT * FROM Teams"));
		
	}
	
	@Test
	public void IsObjectNeInDataTableTest(){
		
		assertFalse(DBData.IsObjectNeInDataTable("Teams", "Ne", ""));
		
	}
	
}
