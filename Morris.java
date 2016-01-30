import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

public class Morris extends Frame implements MouseListener{
	// our board co-ordinates for our game
	private int x1 = 100;
	private int y1 = 100; 
	private int x2 = x1 -10;
	private int y2 = y1 -10;
	private int w = 200;
	
	/* Our Board works on the principle of Magic Squares. The Idea is we fill a square with numbers
	   such that all the columns, rows, and diagonals add up to the same number. Since we only ever
	   have 3 pieces on the board for each player at any time we can assign these pieces the numbers
	   in the square and if they add up to our key number, 15, then we know we have a victory condition*/
	
	private int[] board = {4,9,2,3,5,7,8,1,6}; 
	private int mouseClick = 0; 	//lets us know what has been clicked
	private boolean hasGone;		//Lets us know when a players turn has ended
	private Player p1, p2;			//Our players
	private Ellipse2D.Double[] p1Pieces = new Ellipse2D.Double[3]; //stores the ellipses that represent the balls each player has
	private Ellipse2D.Double[] p2Pieces = new Ellipse2D.Double[3]; 
	private int currentPlayer = 0; 	//lets us know whos turn is 
	private int selected = -1;		//lets us know which of the players peices has been selected
	//shows us the spots on the board a ball can be placed
	private int[][] intersect1 = {{x2,y2},{x2+100,y2},{x2+200,y2},{x2,y2+100},{x2+100,y2+100},{x2+200,y2+100},{x2,y2+200},{x2+100,y2+200},{x2+200,y2+200}};
	private int isOver = 0;			//lets us know if the game has ended
	
	public Morris(){
		//creates the game window
		super("Three Man Morris");
		setSize(400,400);
		setVisible(true);
		//gives us the inital locations of our pieces
		p1Pieces[0] = new Ellipse2D.Double(30,50,20,20);
		p1Pieces[1] = new Ellipse2D.Double(60,50,20,20);
		p1Pieces[2] = new Ellipse2D.Double(90,50,20,20);
		p2Pieces[0] = new Ellipse2D.Double(350,50,20,20);
		p2Pieces[1] = new Ellipse2D.Double(320,50,20,20);
		p2Pieces[2] = new Ellipse2D.Double(290,50,20,20);
		
		//creates our players
		p1 = new Player("Black");
		p2 = new Player("Red");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
		
		addMouseListener(this);
	}
	
	public void paint (Graphics g){
		//starts a new graphics window
		Graphics g2d = (Graphics2D) g; 
		
		//draws a text message to inform the user whos turn it is
		if (currentPlayer == 1) g2d.drawString("Player 1 go", 170, 50);
		else if (currentPlayer == 2) g2d.drawString("Player 2 go", 170, 50);
		else if (isOver == 1) g2d.drawString("Player 1 Wins!", 170, 50);
		else if (isOver == 2) g2d.drawString("Player 2 Wins!", 170, 50);
		else g2d.drawString("Waiting...", 150, 50);
		
		//game board borders 
		g2d.drawLine(x1,y1,x1 + w, y1);
		g2d.drawLine(x1,y1,x1, y1 + w);
		g2d.drawLine(x1 +w,y1,x1 + w, y1+w);
		g2d.drawLine(x1,y1+w,x1 + w, y1+w);
		
		// lines through the game board 
		g2d.drawLine(x1 + 100,y1,x1 + 100, y1+w);
		g2d.drawLine(x1,y1 +100,x1 + w, y1+100);
		
		//draws the game pieces on the board
		g2d.setColor(Color.BLACK);
		((Graphics2D) g2d).draw(p1Pieces[0]);
		((Graphics2D) g2d).fill(p1Pieces[0]);
		((Graphics2D) g2d).draw(p1Pieces[1]);
		((Graphics2D) g2d).fill(p1Pieces[1]);
		((Graphics2D) g2d).draw(p1Pieces[2]);
		((Graphics2D) g2d).fill(p1Pieces[2]);
		
		g2d.setColor(Color.RED);
		((Graphics2D) g2d).draw(p2Pieces[0]);
		((Graphics2D) g2d).fill(p2Pieces[0]);
		((Graphics2D) g2d).draw(p2Pieces[1]);
		((Graphics2D) g2d).fill(p2Pieces[1]);
		((Graphics2D) g2d).draw(p2Pieces[2]);
		((Graphics2D) g2d).fill(p2Pieces[2]);
		
		//to signify a highlighted ball we paint it yellow
		g2d.setColor(Color.YELLOW);
		if (currentPlayer == 1 && selected != -1) ((Graphics2D) g2d).fill(p1Pieces[selected]);
		if (currentPlayer == 2 && selected != -1) ((Graphics2D) g2d).fill(p2Pieces[selected]);
		
		//draws a blue circle in the open locations for a ball to go
		if (mouseClick == 1){
			g2d.setColor(Color.BLUE);
			for(int i = 0; i<9; i++){
				if (board[i] != 15){
					g2d.drawOval(intersect1[i][0], intersect1[i][1], 20, 20);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (hasGone) return; //if a player has already gone, don't run their turn
		// this runs at the start of a players turn, or if no ball is selected
		if (mouseClick == 0){
			//checks the players turn, if it there turn find out if one of their pieces has been clicked
			if( currentPlayer == 1){
				for (int i = 0; i < 3; i++){
					if(p1Pieces[i].contains(e.getPoint())){
						//once we find the selected pieces, store it, adjust the mouseclick and repaint the window
						selected = i;
						mouseClick = 1;
						repaint();
						return;
					}
				}
			}
			//
			else if (currentPlayer == 2){
				for (int i = 0; i < 3; i++){
					if (p2Pieces[i].contains(e.getPoint())){
						selected = i;
						mouseClick = 1;
						repaint();
						return;
					}
				}
			}
		}
		//if a ball has been selected 
		else if (mouseClick == 1){
			//get the x and y of the point that has been clicked 
			Point p = e.getPoint();
			double x = p.getX();
			double y = p.getY();
			
			//check if one of the board locations has been clicked
			for (int i = 0; i < intersect1.length; i++){
				//checks if any of the spots have been clicked 
				if (x > intersect1[i][0] && x < intersect1[i][0] + 20){
					if (y > intersect1[i][1] && y < intersect1[i][1] + 20){
					/*performs the move operation in player. 
					  If the move is successful then we can move the piece and end their turn*/
						if (currentPlayer == 1 && p1.move(i, selected, board)) {
							p1Pieces[selected] = new Ellipse2D.Double(intersect1[i][0],intersect1[i][1],20,20);
							hasGone = true;
							currentPlayer = 0;
						}
						else if (currentPlayer == 2 && p2.move(i,selected,board)){
							p2Pieces[selected] = new Ellipse2D.Double(intersect1[i][0],intersect1[i][1],20,20);
							hasGone = true;
							currentPlayer = 0;
						}
						break;
					}
				}
			}
			//ends the cycle, if no action is taken the game will return to start
			selected = -1;
			mouseClick = 0;
			repaint();
		}
	}
	public boolean checkWin(int i){
		//a method that checks if a player has won
		if (i == 1) {
			if( p1.checkWin()){
				// if a player has won, end the game 
				isOver = 1;
				repaint();
				return true;
			}
		}
		else {
			if( p2.checkWin()){
				isOver = 2;
				repaint();
				return true;
			}
		}
		return false;
	}
	
	//sets a player so the game can start 
	public void setCurrentPlayer(int i){ 
			currentPlayer = i;
			hasGone = false;
			repaint();
		}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// used in Control
	public boolean PlayerHasGone(){
		return hasGone;
	}
}
