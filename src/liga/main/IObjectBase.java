package liga.main;

import java.sql.ResultSet;

public interface IObjectBase {

	public void SaveObjectToDB();
	
	public void ReadDataByResultSet(ResultSet xResultSet);

}
