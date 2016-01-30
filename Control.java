public class Control implements Runnable{
	//----------------------------------------------------------------------------------
	//RUN THIS CLASS' MAIN METHOD FOR THE GAME!!!!
	//----------------------------------------------------------------------------------

	//controls what actions happen
	private int action = 1;
	private int state = 0;
	Morris game = new Morris();
	
	@Override
	public void run() {
		//is the end state of our game 
		while(action != -1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//runs player 1's turn
			if (action == 1){
				if (state ==0){
					game.setCurrentPlayer(1);
					state = 1;
				}
				if (game.PlayerHasGone()){
					action = 2;
				}
			}
			//does the checkwin for each player depending on whos turn it was
			else if (action == 2){
				if (state == 1)
					//lets us know if player 1 has won in the output window
					if(game.checkWin(1)) action = -1;
					else{
						state = 2;
						action = 3;
					}
				else if (state == 3){
					//lets us know if player 2 has won in the output window
					if(game.checkWin(2)) action = -1; 
					else{
						action = 1;
						state = 0;
					}
				}
			}
			//runs the turn for player 3
			else if (action == 3){
					if (state ==2){
						game.setCurrentPlayer(2);
						state = 3;
					}
					if (game.PlayerHasGone()){
						action = 2;
					}
				}
			}
	}
	

	
	public static void main(String args[]) {
		Control GameControl = new Control();

		Thread myThread = new Thread(GameControl);
		myThread.start();

		try {
			myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
