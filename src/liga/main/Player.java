package liga.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liga.db.DBData;

public class Player extends ObjectBase implements IObjectBase {

	public int IdxTeam;
	
	public Player(DBData xDBData){
		
		super(xDBData);
		
	}
	
	public void SaveObjectToDB() {
		//funkcja zapisująca zawodnika do bazy
	
		PreparedStatement pIdentity;
		PreparedStatement pPrparedStatement;
		ResultSet pResult;
		
		try {
		  pPrparedStatement = DBData.Cnn.prepareStatement("INSERT INTO Players (Ne, IdxTeam) VALUES (?, ?)");
		  pPrparedStatement.setString(1, this.Ne);
		  pPrparedStatement.setInt(2, this.IdxTeam);
		  pPrparedStatement.executeUpdate();
	      pIdentity = DBData.Cnn.prepareStatement("CALL IDENTITY()");
	      pResult = pIdentity.executeQuery();
	      pResult.next();
	      this.Idx = pResult.getInt(1);
	      pResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void ReadDataByResultSet(ResultSet xResultSet){
		//funkcja czytająca dane o zawodniku z obiektu resultset
		//xResultSet - czytany obiekt
		
		try {
			this.Ne = xResultSet.getString("ne");
			this.Idx = xResultSet.getInt("idxplayer");
			this.IdxTeam = xResultSet.getInt("idxteam");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
