package Pieces;

import java.awt.Panel;
import javax.swing.*;

@SuppressWarnings("serial")
public class Pieces extends JFrame{
	
	private char piece;
	private char color;
	private int[] pos;
	private boolean atStart;
	private ImageIcon img;
	private JLabel label;
	private String imgPawnWhite = "pieces/Chess_plt60.png";
	private String imgPawnBlack = "pieces/Chess_pdt60.png";
	private String imgQueenWhite = "pieces/Chess_qlt60.png";
	private String imgQueenBlack = "pieces/Chess_qdt60.png";
	private String imgKnightWhite = "pieces/Chess_nlt60.png";
	private String imgKnightBlack = "pieces/Chess_ndt60.png";
	private String imgRookWhite = "pieces/Chess_rlt60.png";
	private String imgRookBlack = "pieces/Chess_rdt60.png";
	private String imgKingWhite = "pieces/Chess_klt60.png";
	private String imgKingBlack = "pieces/Chess_kdt60.png";
	private String imgBishopWhite = "pieces/Chess_blt60.png";
	private String imgBishopBlack = "pieces/Chess_bdt60.png";
	
	public Pieces(char color, char piece, int [] pos){
		
		this.color = color;
		this.piece = piece;
		this.pos = pos;
		this.atStart = true;
		
		generateIcon(color, piece);
		
	}

	public void setPiece(char p){
		this.piece = p;
	}
	
	public char getPiece(){
		return piece;
	}	
	public void setColor(char c){
		this.color = c;
	}
	public char getColor(){
		return color;
	}
	public void setPos(int[] p){
		this.pos[0] = p[0];
		this.pos[1] = p[1];
	}
	public int [] getPos(){
		return pos; 
	}
	public ImageIcon getImage(){
		return img;
	}
	public JLabel getLabel(){
		return label;
	}
	public boolean getAtStart(){
		return atStart;
	}
	private void generateIcon(char color, char piece){
		if(color == 'w'){
			if(piece == 'p'){
				img = new ImageIcon(imgPawnWhite, "Light Pawn");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'q'){
				img = new ImageIcon(imgQueenWhite, "Light Queen");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'n'){
				img = new ImageIcon(imgKnightWhite, "Light Knight");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'b'){
				img = new ImageIcon(imgBishopWhite, "Light Bishop");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'r'){
				img = new ImageIcon(imgRookWhite, "Light Rook");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'k'){
				img = new ImageIcon(imgKingWhite, "Light King");
				label = new JLabel(img, JLabel.CENTER);
			}
		}else if(color == 'b'){
			if(piece == 'p'){
				img = new ImageIcon(imgPawnBlack, "Dark Pawn");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'q'){
				img = new ImageIcon(imgQueenBlack, "Dark Queen");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'n'){
				img = new ImageIcon(imgKnightBlack, "Dark Knight");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'b'){
				img = new ImageIcon(imgBishopBlack, "Dark Bishop");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'r'){
				img = new ImageIcon(imgRookBlack, "Dark Rook");
				label = new JLabel(img, JLabel.CENTER);
			}else if(piece == 'k'){
				img = new ImageIcon(imgKingBlack, "Dark King");
				label = new JLabel(img, JLabel.CENTER);
			}
		}
	}
	
	//returns whether the piece can be moved or not
	public boolean isLegalMove(int [] newPos, Pieces [][] gameState, Panel[][] board){
		
		if(gameState[newPos[0]][newPos[1]] != null){
			if(this.color == gameState[newPos[0]][newPos[1]].getColor()){
				return false;
			}
		}
		
		if (piece == 'p'){
			return this.movePawn(newPos, gameState);
		}else if(piece == 'r'){
			return this.moveRook(newPos, gameState);
		}else if(piece == 'b'){
			return this.moveBishop(newPos, gameState);
		}else if(piece == 'n'){
			return this.moveKnight(newPos, gameState);
		}else if(piece == 'q'){
			return this.moveQueen(newPos, gameState);
		}else if(piece == 'k'){
			return this.moveKing(newPos, gameState, board);
		}		
		return false;
	}
	
	private boolean movePawn(int [] newPos, Pieces [][] gameState){
		
		if(color == 'w'){
			if(atStart){
				if((pos[0] - 1) == newPos[0]  && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null){
						atStart = false;
						return true;
					}
				}
				if((pos[0] - 2 == newPos[0]) && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null && gameState[newPos[0] + 1][newPos[1]] == null){
						atStart = false;
						return true;
					}
				}
				if((pos[0] - 1) == newPos[0]){
					if((pos[1] - 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
					if((pos[1] + 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
				}
			}else{
				if((pos[0] - 1) == newPos[0] && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null){
						return true;
					}
				}
				if((pos[0] - 1) == newPos[0]){
					if((pos[1] - 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
					if((pos[1] + 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
				}
			}
		}else if(color == 'b'){
			if(atStart){
				if((pos[0] + 1) == newPos[0]  && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null){
						atStart = false;
						return true;
					}
				}
				if((pos[0] + 2 == newPos[0]) && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null && gameState[newPos[0] - 1][newPos[1]] == null){
						atStart = false;
						return true;
					}
				}
				if((pos[0] + 1) == newPos[0]){
					if((pos[1] + 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
					if((pos[1] - 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
				}					
			}else{
				if((pos[0] + 1) == newPos[0] && (pos[1] == newPos[1])){
					if(gameState[newPos[0]][newPos[1]] == null){
						return true;
					}
				}
				if((pos[0] + 1) == newPos[0]){
					if((pos[1] + 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
					if((pos[1] - 1) == newPos[1]){
						if((gameState[newPos[0]][newPos[1]] != null)){
							return true;
						}
					}
				}				
			}
		}
		
		return false;
		
	}
	
	private boolean moveRook(int [] newPos, Pieces[][] gameState){
		
		if(pos[0] > newPos[0] && pos[1] == newPos[1]){
			for(int i = pos[0] - 1; i > newPos[0]; i--){
				if(gameState[i][pos[1]] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] < newPos[0] && pos[1] == newPos[1]){
			for(int i = pos[0] + 1; i < newPos[0]; i++){
				if(gameState[i][pos[1]] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[1] > newPos[1] && pos[0] == newPos[0]){
			for(int i = pos[1] - 1; i > newPos[1]; i--){
				if(gameState[pos[0]][i] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[1] < newPos[1] && pos[0] == newPos[0]){
			for(int i = pos[1] + 1; i < newPos[1]; i++){
				if(gameState[pos[0]][i] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}
		
		return false;
	}
	
	private boolean moveBishop(int [] newPos, Pieces[][] gameState){
		
		int x = pos[0];
		int y = pos[1];
		
		if(x > newPos[0] && y > newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x--;
				y--;
				if(x == -1 || y == -1){
					return false;
				}	
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}
		}else if(x > newPos[0] && y < newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x--;
				y++;
				if(x == -1 || y == 8){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}				
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}			
		}else if(x < newPos[0] && y > newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x++;
				y--;
				if(x == 8 || y == -1){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}	
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}			
		}else if(x < newPos[0] && y < newPos[1]){
			while(!((x == newPos[0] && y == newPos[1]))){
				x++;
				y++;
				if(x == 8 || y == 8){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}				
			}	
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}
			
		}
		
		return false;
	}

	private boolean moveKnight(int [] newPos, Pieces[][] gameState){
	
		if((pos[0] - 2) == newPos[0] && (pos[1] - 1) == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[0] - 2) == newPos[0] && (pos[1] + 1) == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[1] - 2) == newPos[1] && (pos[0] + 1) == newPos[0]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[1] - 2) == newPos[1] && (pos[0] - 1) == newPos[0]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[1] + 2) == newPos[1] && (pos[0] - 1) == newPos[0]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[1] + 2) == newPos[1] && (pos[0] + 1) == newPos[0]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[0] + 2) == newPos[0] && (pos[1] + 1) == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if((pos[0] + 2) == newPos[0] && (pos[1] - 1) == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}
	
		return false;
	}
	
	private boolean moveQueen(int [] newPos, Pieces[][] gameState){
		
		if(pos[0] > newPos[0] && pos[1] == newPos[1]){
			for(int i = pos[0] - 1; i > newPos[0]; i--){
				if(gameState[i][pos[1]] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] < newPos[0] && pos[1] == newPos[1]){
			for(int i = pos[0] + 1; i < newPos[0]; i++){
				if(gameState[i][pos[1]] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[1] > newPos[1] && pos[0] == newPos[0]){
			for(int i = pos[1] - 1; i > newPos[1]; i--){
				if(gameState[pos[0]][i] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[1] < newPos[1] && pos[0] == newPos[0]){
			for(int i = pos[1] + 1; i < newPos[1]; i++){
				if(gameState[pos[0]][i] != null){
					return false;
				}
			}
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}
		
		int x = pos[0];
		int y = pos[1];
		
		if(x > newPos[0] && y > newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x--;
				y--;
				if(x == -1 || y == -1){
					return false;
				}	
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}
		}else if(x > newPos[0] && y < newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x--;
				y++;
				if(x == -1 || y == 8){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}				
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}			
		}else if(x < newPos[0] && y > newPos[1]){
			while(!((x == newPos[0]) && (y == newPos[1]))){
				x++;
				y--;
				if(x == 8 || y == -1){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}	
			}
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}			
		}else if(x < newPos[0] && y < newPos[1]){
			while(!((x == newPos[0] && y == newPos[1]))){
				x++;
				y++;
				if(x == 8 || y == 8){
					return false;
				}
				if(x == newPos[0] && y == newPos[1]){
					break;
				}
				if(gameState[x][y] != null){
					return false;
				}				
			}	
			if(x == newPos[0] && y == newPos[1]){
				if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
					atStart = false;
					return true;
				}
			}
			
		}
		
		return false;
	}
	private boolean moveKing(int [] newPos, Pieces[][] gameState, Panel[][] board){
		
		
		if(atStart){
			if(pos[1] > newPos[1] && pos[0] == newPos[0]){
				for(int i = pos[1] - 1; i >= newPos[1]; i--){
					if(gameState[pos[0]][i] != null){
						return false;
					}
				}
				try{
					Pieces temp = gameState[newPos[0]][newPos[1] - 1];
					if(temp.getPiece() == 'r' && temp.getAtStart()){	
						int [] tempPos = {newPos[0], newPos[1] + 1};
						temp.setPos(tempPos);
						gameState[newPos[0]][newPos[1] + 1] = temp;
						gameState[newPos[0]][newPos[1] - 1] = null;
						board[newPos[0]][newPos[1] - 1].remove(temp.getLabel());
						board[newPos[0]][newPos[1] - 1].repaint();
						board[newPos[0]][newPos[1] + 1].add(temp.getLabel());
						board[newPos[0]][newPos[1] + 1].repaint();
						return true;
					}
				}catch(Exception e){
					
				}
			}else if(pos[1] < newPos[1] && pos[0] == newPos[0]){
				for(int i = pos[1] + 1; i <= newPos[1]; i++){
					if(gameState[pos[0]][i] != null){
						return false;
					}
				}
				try{
					Pieces temp = gameState[newPos[0]][newPos[1] + 1];
					if(temp.getPiece() == 'r' && temp.getAtStart()){
						int [] tempPos = {newPos[0], newPos[1] - 1};
						temp.setPos(tempPos);
						gameState[newPos[0]][newPos[1] - 1] = temp;
						gameState[newPos[0]][newPos[1] + 1] = null;
						board[newPos[0]][newPos[1] + 1].remove(temp.getLabel());
						board[newPos[0]][newPos[1] + 1].repaint();
						board[newPos[0]][newPos[1] - 1].add(temp.getLabel());
						board[newPos[0]][newPos[1] - 1].repaint();
						return true;
					}
				}catch(Exception e){
					
				}
			}
		}
		
		
		if(pos[0] + 1 == newPos[0] && pos[1] == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] + 1 == newPos[0] && pos[1] + 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] + 1 == newPos[0] && pos[1] - 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] == newPos[0] && pos[1] - 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] == newPos[0] && pos[1] + 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] - 1 == newPos[0] && pos[1] == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] - 1 == newPos[0] && pos[1] - 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}else if(pos[0] - 1 == newPos[0] && pos[1] + 1 == newPos[1]){
			if(gameState[newPos[0]][newPos[1]] == null || color != gameState[newPos[0]][newPos[1]].getColor()){
				atStart = false;
				return true;
			}
		}
		
		return false;
	}
	
}
