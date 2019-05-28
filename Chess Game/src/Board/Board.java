package Board;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Pieces.*;


@SuppressWarnings("serial")
public class Board extends JFrame implements MouseListener{	
	
	private Panel [][] board; 
	private Pieces [][] gameState;
	private boolean pieceSelected = false;
	private Pieces selectedPiece = null;
	private Pieces whiteKing = null;
	private Pieces blackKing = null;
	private boolean blackInCheck = false;
	private boolean whiteInCheck = false;
	private Color prevColor;
	private int [] tempPos;
	private char turn = 'w';
	
	public Board(){		
		
	}
	
	public void generateBoard(){
		
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(8,8,1,1));
		gameState = new Pieces[8][8];
		board = new Panel[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				board[i][j] = new Panel();
				String name = "" + i;
				name = name + "," + j;
				board[i][j].setName(name);
				if((i+j)%2 == 0){
					board[i][j].setBackground(Color.red);
				}else{
					board[i][j].setBackground(Color.gray);
				}
				c.add(board[i][j]);
				setBeginningPiece(i,j);
				board[i][j].addMouseListener(this);
				
			}
		}
		
		setSize(800,800);
		setVisible(true);
		setResizable(false);
		
		
	}
	
	public Pieces getPiece(int i, int j){
		
		if (gameState[i][j] == null){
			return null;
		}else{
			return gameState[i][j];
		}
		
	}
	
	private void movePiece(int [] currPos, int [] newPos){
		
		Pieces temp = null;
		boolean pieceTaken = false;
		
		if(!(selectedPiece.isLegalMove(newPos, gameState, board))){
			selectedPiece = null;
			pieceSelected = false;
			board[currPos[0]][currPos[1]].setBackground(prevColor);
			board[currPos[0]][currPos[1]].repaint();
			return;
		}
		if(gameState[newPos[0]][newPos[1]] != null){
			temp = gameState[newPos[0]][newPos[1]];			
			board[newPos[0]][newPos[1]].remove(temp.getLabel());			
			gameState[newPos[0]][newPos[1]] = null;
			pieceTaken = true;
		}
		
		gameState[newPos[0]][newPos[1]] = gameState[currPos[0]][currPos[1]];
		gameState[currPos[0]][currPos[1]] = null;
		board[currPos[0]][currPos[1]].remove(selectedPiece.getLabel());
		board[currPos[0]][currPos[1]].setBackground(prevColor);
		board[currPos[0]][currPos[1]].repaint();
		board[newPos[0]][newPos[1]].add(selectedPiece.getLabel());
		board[newPos[0]][newPos[1]].repaint();
		int [] tempPos = {currPos[0], currPos[1]};
		selectedPiece.setPos(newPos);
		
		kingInCheck();
		
		if(blackInCheck || whiteInCheck){
			String userInput = JOptionPane.showInputDialog("King is in Check is this a Checkmate? (type yes to end game)");			
			if(userInput != null){
				userInput = userInput.trim();
				userInput = userInput.toUpperCase();
				if(userInput.equals("YES")){
					if(whiteInCheck){
						JOptionPane.showMessageDialog(null,"Black Wins");
						gameState = null;
						board = null;
						this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
					}else if(blackInCheck){
						JOptionPane.showMessageDialog(null,"White Wins");
						gameState = null;
						board = null;
						this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
					}
				}
			}
			gameState[tempPos[0]][tempPos[1]] = gameState[newPos[0]][newPos[1]];
			gameState[newPos[0]][newPos[1]] = null;
			board[newPos[0]][newPos[1]].remove(selectedPiece.getLabel());
			board[newPos[0]][newPos[1]].repaint();
			board[tempPos[0]][tempPos[1]].add(selectedPiece.getLabel());
			board[tempPos[0]][tempPos[1]].repaint();
			selectedPiece.setPos(tempPos);
			if(pieceTaken){
				gameState[newPos[0]][newPos[1]] = temp;
				board[newPos[0]][newPos[1]].add(temp.getLabel());
				board[newPos[0]][newPos[1]].repaint();
			}
			pieceSelected = false;
			selectedPiece = null;
			return;
			
		}
		
		
		
		if(turn == 'w'){
			turn = 'b';
		}else if(turn == 'b'){
			turn = 'w';
		}
		
		pieceSelected = false;
		selectedPiece = null;
		
	}
	
	private void setBeginningPiece(int i, int j){
		
		int [] pos = {i, j};
		//set chess pieces based on original generation of the board
		if (i == 1){			
			Pawn bPawn = new Pawn('b', pos);
			gameState[i][j] = bPawn;
			board[i][j].add(bPawn.getLabel());
		}else if (i == 6){
			Pawn wPawn = new Pawn('w', pos);
			gameState[i][j] = wPawn;
			board[i][j].add(wPawn.getLabel());
		}else if(i == 0 && (j == 0 || j == 7)){
			Rook bRook = new Rook('b', pos);
			gameState[i][j] = bRook;
			board[i][j].add(bRook.getLabel());
		}else if(i == 7 && (j == 0 || j == 7)){
			Rook wRook = new Rook('w', pos);
			gameState[i][j] = wRook;
			board[i][j].add(wRook.getLabel());
		}else if(i == 0 && (j == 1 || j == 6)){
			Knight bKnight = new Knight('b', pos);
			gameState[i][j] = bKnight;
			board[i][j].add(bKnight.getLabel());
		}else if(i == 7 && (j == 1 || j == 6)){
			Knight wKnight = new Knight('w', pos);
			gameState[i][j] = wKnight;
			board[i][j].add(wKnight.getLabel());
		}else if(i == 0 && (j == 2 || j == 5)){
			Bishop bBishop = new Bishop('b', pos);
			gameState[i][j] = bBishop;
			board[i][j].add(bBishop.getLabel());
		}else if(i == 7 && (j == 2 || j == 5)){
			Bishop wBishop = new Bishop('w', pos);
			gameState[i][j] = wBishop;
			board[i][j].add(wBishop.getLabel());
		}else if(i == 0 && j == 3){
			Queen bQueen = new Queen('b', pos);
			gameState[i][j] = bQueen;
			board[i][j].add(bQueen.getLabel());
		}else if(i == 7 && j == 3){
			Queen wQueen = new Queen('w', pos);
			gameState[i][j] = wQueen;
			board[i][j].add(wQueen.getLabel());
		}else if(i == 0 && j == 4){
			King bKing = new King('b', pos);
			blackKing = bKing;
			gameState[i][j] = bKing;
			board[i][j].add(bKing.getLabel());
		}else if(i == 7 && j == 4){
			King wKing = new King('w', pos);
			whiteKing = wKing;
			gameState[i][j] = wKing;
			board[i][j].add(wKing.getLabel());
		}else {
			gameState[i][j] = null;
		}
	}
	
	/*public void checkForKing(){
		
		boolean blackKing = true;
		boolean whiteKing = true;
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(gameState[i][j] != null){
					if(gameState[i][j].getPiece() == 'k' && gameState[i][j].getColor() == 'w'){
						whiteKing = false;
					}else if(gameState[i][j].getPiece() == 'k' && gameState[i][j].getColor() == 'b'){
						blackKing = false;
					}
				}
			}
		}
		
		
		
	}*/
	
	private void kingInCheck(){
		
		if(turn == 'b'){
			int [] currPos = blackKing.getPos();			
			int x = currPos[0] - 1;
			int y = currPos[1] - 1;
			while(true){
				if(x == -1 || y == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'b'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
				y--;
			}
			
			x = currPos[0] - 1;
			y = currPos[1];
			while(true){
				if(x == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
			}
			
			x = currPos[0] - 1;
			y = currPos[1] + 1;
			while(true){
				if(x == -1 || y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'b'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
				y++;
			}
				
			x = currPos[0];
			y = currPos[1] + 1;
			while(true){
				if(y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				y++;
			}
			
			x = currPos[0];
			y = currPos[1] - 1;
			while(true){
				if(y == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				y--;
			}
			
			x = currPos[0] + 1;
			y = currPos[1] + 1;
			while(true){
				if(x == 8 || y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'p' && y - currPos[1] == 1){
							blackInCheck = true;
							return;
						}
						if(piece == 'q' || piece == 'b'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				y++;
				x++;
			}
			
			x = currPos[0] + 1;
			y = currPos[1] - 1;
			while(true){
				if(y == -1 || x == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'p' && currPos[1] - y == 1){
							blackInCheck = true;
							return;
						}
						if(piece == 'q' || piece == 'b'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				y--;
				x++;
			}
			
			x = currPos[0] + 1;
			y = currPos[1];
			while(true){
				if(x == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						break;
					}
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'b'){
							blackInCheck = true;
							return;
						}
						break;
					}
				}
				x++;
			}	
			
			//check for white knight in position to take king
			x = currPos[0] - 1;
			y = currPos[1] - 2;
			if(x < 0 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 2;
			y = currPos[1] - 1;
			if(x < 0 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 1;
			y = currPos[1] - 2;
			if(x > 7 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 2;
			y = currPos[1] - 1;
			if(x > 7 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 1;
			y = currPos[1] + 2;
			if(x > 7 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 2;
			y = currPos[1] + 1;
			if(x > 7 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 1;
			y = currPos[1] + 2;
			if(x < 0 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 2;
			y = currPos[1] + 1;
			if(x < 0 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							blackInCheck = true;
							return;
						}
					}
				}
			}
			
			blackInCheck = false;			
		}else if(turn == 'w'){
			int [] currPos = whiteKing.getPos();			
			int x = currPos[0] - 1;
			int y = currPos[1] - 1;
			while(true){
				if(x == -1 || y == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'p' && currPos[1] - y  == 1){
							whiteInCheck = true;
							return;
						}
						if(piece == 'q' || piece == 'b'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
				y--;
			}
			
			x = currPos[0] - 1;
			y = currPos[1];
			while(true){
				if(x == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
			}
			
			x = currPos[0] - 1;
			y = currPos[1] + 1;
			while(true){
				if(x == -1 || y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'p' && y - currPos[1]  == 1){
							whiteInCheck = true;
							return;
						}
						if(piece == 'q' || piece == 'b'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				x--;
				y++;
			}
				
			x = currPos[0];
			y = currPos[1] + 1;
			while(true){
				if(y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				y++;
			}
			
			x = currPos[0];
			y = currPos[1] - 1;
			while(true){
				if(y == -1){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				y--;
			}
			
			x = currPos[0] + 1;
			y = currPos[1] + 1;
			while(true){
				if(x == 8 || y == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'b'){
							whiteInCheck = true;
							return;
						}
						break;
					}
				}
				y++;
				x++;
			}
			
			x = currPos[0] + 1;
			y = currPos[1] - 1;
			while(true){
				if(y == -1 || x == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'b'){
							whiteInCheck = true;
							return;
						}
					}
				}
				y--;
				x++;
			}
			
			x = currPos[0] + 1;
			y = currPos[1];
			while(true){
				if(x == 8){
					break;
				}
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'w'){
						break;
					}
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'q' || piece == 'r'){
							whiteInCheck = true;
							return;
						}
					}
				}
				x++;
			}
			
			//check for black knight in position to take king
			x = currPos[0] - 1;
			y = currPos[1] - 2;
			if(x < 0 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 2;
			y = currPos[1] - 1;
			if(x < 0 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 1;
			y = currPos[1] - 2;
			if(x > 7 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 2;
			y = currPos[1] - 1;
			if(x > 7 || y < 0){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 1;
			y = currPos[1] + 2;
			if(x > 7 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] + 2;
			y = currPos[1] + 1;
			if(x > 7 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 1;
			y = currPos[1] + 2;
			if(x < 0 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			x = currPos[0] - 2;
			y = currPos[1] + 1;
			if(x < 0 || y > 7){
				//do nothing because we are outside the array boundaries
			}else{
				if(gameState[x][y] != null){
					if(gameState[x][y].getColor() == 'b'){
						char piece = gameState[x][y].getPiece();
						if(piece == 'n'){
							whiteInCheck = true;
							return;
						}
					}
				}
			}
			
			whiteInCheck = false;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Panel temp = (Panel)e.getSource();
		String [] tempArr = temp.getName().split(",");
		
		if(pieceSelected == true){
			int [] currPos = selectedPiece.getPos();
			int [] newPos = {Integer.parseInt(tempArr[0]), Integer.parseInt(tempArr[1])};
			movePiece(currPos, newPos);
			
			
		}else{
			selectedPiece = getPiece(Integer.parseInt(tempArr[0]), Integer.parseInt(tempArr[1]));
			//reset variables if illegal piece was selected
			if(selectedPiece == null || selectedPiece.getColor() != turn){
				pieceSelected = false;
				selectedPiece = null;
				return;
			}
			pieceSelected = true;
			tempPos = selectedPiece.getPos();
			prevColor = board[tempPos[0]][tempPos[1]].getBackground();
			board[tempPos[0]][tempPos[1]].setBackground(Color.GREEN);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
