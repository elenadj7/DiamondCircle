package application.validations;


import application.exceptions.*;

public class DataValidation {
	
	public DataValidation(){
		
	}
	
	public void validate(String[] config) throws InvalidConfigFileException, InvalidNumberOfPlayersException, InvalidMatrixDimensionException, InvalidUsernameException {
		
		if(config.length != 4) {
			
			throw new InvalidConfigFileException();
		}
		if(Integer.parseInt(config[0]) < 7 || Integer.parseInt(config[0]) > 10) {
			
			throw new InvalidMatrixDimensionException();
		}
		if(Integer.parseInt(config[1]) < 2 || Integer.parseInt(config[1]) > 4) {
			
			throw new InvalidNumberOfPlayersException();
		}
		
		String[] players = config[2].split(",");
		if(players.length != Integer.parseInt(config[1])) {
			throw new InvalidUsernameException("Number of players and usernames are not compatible");
		}
		for(int i = 0; i < players.length - 1; ++i) {
			
			
			for(int j = i + 1; j < players.length; ++j) {
				
				if(players[i].toLowerCase().equals(players[j].toLowerCase())) {
					
					throw new InvalidUsernameException();
					
				}
			}
		}
		
	}
}
