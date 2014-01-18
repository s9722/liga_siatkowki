package liga.uinterface;

public enum UserAnswerEnum {
	
	AddTeam,
	EditTeam,
	DelTeam,
	ShowTeams,
	ShowTeamPlayers,
	Quit,
	InvalidInput ;
	public static UserAnswerEnum ConvertStrToUserAnswer(String str) {
		try {
			
			int pVal = Integer.valueOf(str);
			
			switch(pVal) {
				case 1: return AddTeam;
				case 2: return EditTeam;
				case 3: return DelTeam;
				case 4: return ShowTeams;
				case 5: return ShowTeamPlayers;
				case 6: return Quit;
				default: return InvalidInput;
			}
		} catch (NumberFormatException e) {
			return InvalidInput;
		}
	}
	
}
