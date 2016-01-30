public class Player {
	private int[] ball = {15,15,15}; //stores the value of the players balls 
	private int[] LocsOf15 = new int[3]; //stores the location of the balls we took from
	private String color; 			//stores the player's color
	
	// initializes the player
	public Player(String x){
		this.color = x;
	}
	
	//checks if the player has a win condition 
	public boolean checkWin(){
		//check the sum of the pieces being 15
		if (ball[0] + ball[1] + ball[2] == 15){
			//check against diagonals since the are not valid
			int[] invalid1 = {4,5,6};
			int[] invalid2 = {8,5,2};
			return (areDiff(ball, invalid1) && areDiff(ball, invalid2));
		}
		return false;
	}
	
	//returns if any two size 3 matrices contain the same elements 
	private boolean areDiff(int[] ball2, int[] invalid) {
		boolean[] a = {true,true,true};
		for(int i = 0; i < ball2.length; i++){
			for (int j = 0; j < invalid.length; j++){
				if (ball2[i] == invalid[j]){
					a[i] = false;
					break;
				}
			}
		}
		return a[0] || a[1] || a[2];
	}
	
	// Moves a piece from one spot to another 
	public boolean move (int spot, int ballNum, int[] board){
		//Checks if a spot is in the board and that is not already filled 
		if (spot > -1 && board[spot] != 15){
			/*if the ball is already on the board we have to be sure that the original value is returned to its
			original spot as to not compromise the structure of the magic square*/
			if (ball[ballNum] != 15){
				board[LocsOf15[ballNum]] = ball[ballNum];
				ball[ballNum] = 15;
			}
			LocsOf15[ballNum] = spot;
			ball[ballNum] = board[spot];
			board[spot] = 15;
			return true;
		}
		//if a move was not made do not change the turn 
		return false;
	}
	
	//gives us information about a player
	public String toString(){
		return "The Player has " + color + " balls at locations " + ball[0] + ", " + ball[1] + ", " + ball[2];
	}
	

}
