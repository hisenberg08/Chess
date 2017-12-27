package com.project.javachessengine;

import java.util.Arrays;

import javax.swing.*;

public class AlphaBetaChess {
		
	static int kingPositionC,kingPositionL;	//monitors the positions of king lowercase and capital 
	 static int globalDepth=4;
	static String chessBoard[][]={
			 
		//Capitals is white team and lowercase is black team.
			 
	    {"r","k","b","q","a","b","k","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","K","B","Q","A","B","K","R"}
	 };
	
	public static void main(String[] args) {
		
		 while (!"A".equals(chessBoard[kingPositionC/8][kingPositionC%8])) {kingPositionC++;}//get King's location
	     while (!"a".equals(chessBoard[kingPositionL/8][kingPositionL%8])) {kingPositionL++;}//get king's location
		
		UserInterface ui = new UserInterface();
		JFrame frame = new JFrame("Chess 1.1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(ui);
		frame.setSize(500, 500);
		frame.setVisible(true);
	     System.out.println(possibleMoves());
	        makeMove(alphaBeta(globalDepth, 1000000, -1000000, "", 0));
	        for (int i=0;i<8;i++) {
	            System.out.println(Arrays.toString(chessBoard[i]));
	        }
		
	}
	
	/*this is the recursive method that search the play tree uptil the depth of 4.
	 * It returns a move + rating*/
	
	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        String list=possibleMoves();
        if (depth==0 || list.length()==0) {
        	return move+(rating()*(player*2-1));
        	}
        
        //sort later
        
        player=1-player;//either 1 or 0
        
        /*we iterate over all the possible moves and try to find out the best move based on the rating*/
        
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i,i+5));
            flipBoard();
            String returnString=alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) return move+beta;
                else return move+alpha;
            }
        }
        if (player==0) return move+beta; 
        else return move+alpha;
    }
    private static int rating() {
		
    	
    	return 0;
	}

    //after every move we basically flip the board and make the opponent move as if its ours.
	public static void flipBoard() {
		  String temp;
	        for (int i=0;i<32;i++) {
	            int r=i/8, c=i%8;
	            if (Character.isUpperCase(chessBoard[r][c].charAt(0))) {
	                temp=chessBoard[r][c].toLowerCase();
	            } else {
	                temp=chessBoard[r][c].toUpperCase();
	            }
	            if (Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))) {
	                chessBoard[r][c]=chessBoard[7-r][7-c].toLowerCase();
	            } else {
	                chessBoard[r][c]=chessBoard[7-r][7-c].toUpperCase();
	            }
	            chessBoard[7-r][7-c]=temp;
	        }
	        int kingTemp=kingPositionC;
	        kingPositionC=63-kingPositionL;
	        kingPositionL=63-kingTemp;
    }
	
	//this method will actually make a move. 
	public static void makeMove(String move) {
	        
		 if (move.charAt(4)!='P') {			//x1,y1,x2,y2,captured piece	regular move type
	            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
	            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
	            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
	                kingPositionC=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
	                }
	            
	        } else {
	            //if pawn promotion			//column1,column2,captured-piece,new-piece,P	pawn promotion move type
	            chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
	            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
	        }
	    }
	//Basically we are puting everything back in place, this is what gets executed when we want to undo a move.
	  public static void undoMove(String move) {
	        
		  	//check if move we made was not a pawn promotion.
		  	if (move.charAt(4)!='P') {	
	            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
	            
	            /*in this statement RHS of equation is not set to " " because there could be any element in that location before we moved.
	             * it could or could not be empty
	            */
	            
	            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
	            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
	                kingPositionC=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
	            }
	        } else {
	            //if pawn promotion
	            chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
	            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
	        }
	    }
	 
	public static String possibleMoves(){
		
		String list = "";
		for(int i=0; i<64;i++){
			switch (chessBoard[i/8][i%8]){
				
			case "P": list+=possibleP(i);
				break;
			case "R": list+=possibleR(i);
				break;
			case "K": list+=possibleK(i);
				break;
			case "B": list+=possibleB(i);
				break;
			case "Q": list+=possibleQ(i);
				break;
			case "A": list+=possibleA(i);
				break;
			}
		}
		
		return list; //x1,y1,x2,y2, captured piece
	}
	
	public static String possibleP(int i){
		String list ="", oldPiece;
		int r =i/8, c=i%8;
		for(int j=-1; j<=1;j++){
			try{																//check diagonally in front direction if something could be captured.
				if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i>=16) {
					oldPiece =chessBoard[r-1][c+j];
					chessBoard[r][c] = " ";
					chessBoard[r-1][c+j] = "P";
					if(kingSafe()){
						list = list+ r+c +(r-1)+(c+j)+oldPiece;		
					}
					
					chessBoard[r][c] = "P";
					chessBoard[r-1][c+j] = oldPiece;
					
				}
			}catch(Exception e){}
			
			 try {//promotion && capture						
	                if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i<16) {
	                    String[] temp={"Q","R","B","K"};
	                    for (int k=0; k<4; k++) {
	                        oldPiece=chessBoard[r-1][c+j];
	                        chessBoard[r][c]=" ";
	                        chessBoard[r-1][c+j]=temp[k];
	                        if (kingSafe()) {
	                            //column1,column2,captured-piece,new-piece,P
	                            list=list+c+(c+j)+oldPiece+temp[k]+"P";
	                        }
	                        chessBoard[r][c]="P";
	                        chessBoard[r-1][c+j]=oldPiece;
	                    }
	                }
	            } catch (Exception e) {}
		}
		
		 try {//move one up
	            if (" ".equals(chessBoard[r-1][c]) && i>=16) {
	                oldPiece=chessBoard[r-1][c];
	                chessBoard[r][c]=" ";
	                chessBoard[r-1][c]="P";
	                if (kingSafe()) {
	                    list=list+r+c+(r-1)+c+oldPiece;
	                }
	                chessBoard[r][c]="P";
	                chessBoard[r-1][c]=oldPiece;
	            }
	        } catch (Exception e) {}
		
		 try {//promotion && no capture
	            if (" ".equals(chessBoard[r-1][c]) && i<16) {
	                String[] temp={"Q","R","B","K"};
	                for (int k=0; k<4; k++) {
	                    oldPiece=chessBoard[r-1][c];
	                    chessBoard[r][c]=" ";
	                    chessBoard[r-1][c]=temp[k];
	                    if (kingSafe()) {
	                        //column1,column2,captured-piece,new-piece,P
	                        list=list+c+c+oldPiece+temp[k]+"P";
	                    }
	                    chessBoard[r][c]="P";
	                    chessBoard[r-1][c]=oldPiece;
	                }
	            }
	        } catch (Exception e) {}
		 
		 try {//move two up
	            if (" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i>=48) {
	                oldPiece=chessBoard[r-2][c];
	                chessBoard[r][c]=" ";
	                chessBoard[r-2][c]="P";
	                if (kingSafe()) {
	                    list=list+r+c+(r-2)+c+oldPiece;
	                }
	                chessBoard[r][c]="P";
	                chessBoard[r-2][c]=oldPiece;
	            }
	        } catch (Exception e) {}
		 
		return list;
	}
	
	public static String possibleR(int i){
		String list ="", oldPiece;
		int temp = 1;
		int r =i/8, c=i%8;
		for(int j=-1; j<=1;j+=2){
			try{
				while(" ".equals(chessBoard[r][c+temp*j])){			//traverse along the line horizontally.
					oldPiece =chessBoard[r][c+temp*j];
					chessBoard[r][c] = " ";
					chessBoard[r][c+temp*j] = "R";
					
					if(kingSafe()){
						list = list+ r+c +r+(c+temp*j)+oldPiece;		
					}
					
					chessBoard[r][c] = "R";
					chessBoard[r][c+temp*j] = oldPiece;
					temp++;
				}
				if(Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))){
					oldPiece =chessBoard[r][c+temp*j];
					chessBoard[r][c] = " ";
					chessBoard[r][c+temp*j] = "R";
					
					if(kingSafe()){
						list = list+ r+c +r+(c+temp*j)+oldPiece;		
					}
					
					chessBoard[r][c] = "R";
					chessBoard[r][c+temp*j] = oldPiece;
				}
			}catch(Exception e){}
			
			temp=1;
			
			try{
				while(" ".equals(chessBoard[r+temp*j][c])){					//traverse along the vertical.
					oldPiece =chessBoard[r+temp*j][c];
					chessBoard[r][c] = " ";
					chessBoard[r+temp*j][c] = "R";
					
					if(kingSafe()){
						list = list+ r+c +(r+temp*j)+c+oldPiece;		
					}
					
					chessBoard[r][c] = "R";
					chessBoard[r+temp*j][c] = oldPiece;
					temp++;
				}
				if(Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))){
					oldPiece =chessBoard[r+temp*j][c];
					chessBoard[r][c] = " ";
					chessBoard[r+temp*j][c] = "R";
					
					if(kingSafe()){
						list = list+ r+c +(r+temp*j)+c+oldPiece;		
					}
					
					chessBoard[r][c] = "R";
					chessBoard[r+temp*j][c] = oldPiece;
				}
			}catch(Exception e){}
			temp=1;
		}
		
		return list;
	}
	
	//we will move the piece 1 step in one direction and 2 steps in another direction. we will have 2 try blocks each taking care of pieces at alternate loction.
	public static String possibleK(int i){
		String list ="", oldPiece;
		int r =i/8, c=i%8;
		for(int j=-1; j<=1;j+=2){				//skips j and k =0
			for(int k=-1; k<=1 ;k+=2){
				try{
					if(Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) ||" ".equals(chessBoard[r+j][c+k*2])){
						oldPiece = chessBoard[r+j][c+k*2];
						chessBoard[r][c] = " ";
						chessBoard[r+j][c+k*2] = "K";
						
						if(kingSafe()){
							list = list+ r+c +(r+j)+(c+k*2)+oldPiece;		
						}
						
						chessBoard[r][c] = "K";
						chessBoard[r+j][c+k*2] = oldPiece;
					}
				}catch(Exception e){}
				
				try{
					if(Character.isLowerCase(chessBoard[r+j*2][c+k].charAt(0)) ||" ".equals(chessBoard[r+j*2][c+k])){
						oldPiece = chessBoard[r+j*2][c+k];
						chessBoard[r][c] = " ";
						chessBoard[r+j*2][c+k] = "K";
						
						if(kingSafe()){
							list = list+ r+c +(r+j*2)+(c+k)+oldPiece;		
						}
						
						chessBoard[r][c] = "K";
						chessBoard[r+j*2][c+k] = oldPiece;
					}
				}catch(Exception e){}
		}
	}
		return list;
}
	
	public static String possibleB(int i){

		String list ="",oldPiece;
		int r =i/8, c=i%8;
		int temp =1;
		
		for(int j=-1; j<=1;j+=2){			//j+=2 because Bishop won't be travelling on 0 as it can only walk diagonally. Rest everything is same as queen.
			for(int k=-1; k<=1 ;k+=2){
				try{
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
						
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "B";
						
						if(kingSafe()){
							list = list+ r+c +(r+temp*j)+(c+temp*k)+oldPiece;		
						}
						chessBoard[r][c] = "B";
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
						temp++;
					}
					
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
						
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "B";
						
						if(kingSafe()){
							list = list+ r+c +(r+temp*j)+(c+temp*k)+oldPiece;		
						}
						chessBoard[r][c] = "B";
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
					}
				}catch(Exception e){}
				temp =1;
			}
		}
		return list;
	}
	
	public static String possibleS(int i){
		String list ="";
		
		return list;
	}
	
	public static String possibleQ(int i){
		String list ="",oldPiece;
		int r =i/8, c=i%8;
		int temp =1;
		
		for(int j=-1; j<=1;j++){
			for(int k=-1; k<=1 ;k++){
				if(j!=0 || k!=0){													//condition where we do not want to check, i.e. when j==0 and k==0
					try{
						while(" ".equals(chessBoard[r+temp*j][c+temp*k])){			//move unitl the there is an empty column
							
							oldPiece = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c] = " ";
							chessBoard[r+temp*j][c+temp*k] = "Q";
							
							if(kingSafe()){
								list = list+ r+c +(r+temp*j)+(c+temp*k)+oldPiece;		
							}
							chessBoard[r][c] = "Q";
							chessBoard[r+temp*j][c+temp*k] = oldPiece;
							temp++;
						}
						
						if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){		//check whether to capture the piece or leave if its our own.
							
							oldPiece = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c] = " ";
							chessBoard[r+temp*j][c+temp*k] = "Q";
							
							if(kingSafe()){
								list = list+ r+c +(r+temp*j)+(c+temp*k)+oldPiece;		
							}
							chessBoard[r][c] = "Q";
							chessBoard[r+temp*j][c+temp*k] = oldPiece;
						}
					}catch(Exception e){}
					temp =1;
				}
			}
		}
		
		
		return list;
	}
	
	public static String possibleA(int i){
		String list ="",oldPiece;
		int r =i/8, c=i%8;
		for(int j =0;j<9;j++){
			if(j!=4){	//j=4 is the location where the king is at present.
				//this checks if the point where we are moving has a blank or other opponent.
				try{
				if (Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) {
					oldPiece = chessBoard[r-1+j/3][c-1+j%3];
					chessBoard[r][c] = " ";					//empty the current position
					chessBoard[r-1+j/3][c-1+j%3] = "A";		//move king to new pos
					kingPositionC = i+(j/3)*8+j%3-9;		//abs value of king (out of 64)
					int kingTemp = kingPositionC;
					if(kingSafe()){
						list = list+ r+c +(r-1+j/3)+(c-1+j%3)+oldPiece;		//if king is safe we need to record the move.
					}
					chessBoard[r][c] = "A";			//keeping the king back to its position (All the above code was just to check the moves.)
					chessBoard[r-1+j/3][c-1+j%3] = oldPiece; //keeping everything back in its place.
					kingPositionC = kingTemp;
				}
			}catch(Exception e){
				//will write something later.
			}
		}
	}
		//need to add castling
		return list;
}

	/*After a move is made this method checks if the king is safe.
	 * For this we check all the directions and opponent's piece that could threathen king's safety.*/
	
	private static boolean kingSafe() {
		
		//bishop and queen
		int temp =1;
		
		for(int i=-1; i<=1;i+=2){									//checking diagonals 			
			for(int j=-1; j<=1 ;j+=2){
				try{
					while(" ".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8 + temp*j])) temp++;
					if("b".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8 + temp*j]) 
							|| "q".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8 + temp*j])){
							
						return false;
					}
				}catch(Exception e){}
			temp =1;
			}
		}
		
		//rook and queen
				//int temp =1;
				
				for(int i=-1; i<=1;i+=2){										//check horizontal and vertical
						try{
							while(" ".equals(chessBoard[kingPositionC/8][kingPositionC%8 + temp*i])) temp++;
							if("r".equals(chessBoard[kingPositionC/8][kingPositionC%8 + temp*i]) 
									|| "q".equals(chessBoard[kingPositionC/8][kingPositionC%8 + temp*i])){
									
								return false;
							}
						}catch(Exception e){}
						temp =1;
						
						try{
							while(" ".equals(chessBoard[kingPositionC/8+ temp*i][kingPositionC%8])) temp++;
							if("r".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8]) 
									|| "q".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8])){
									
								return false;
							}
						}catch(Exception e){}
					temp =1;
				}

		//knight
				
				for(int i=-1; i<=1;i+=2){			
					for(int j=-1; j<=1 ;j+=2){
						try{
							if("k".equals(chessBoard[kingPositionC/8+i][kingPositionC%8 + j*2])){
								return false;
							}
						}catch(Exception e){}
						
						try{
							if("k".equals(chessBoard[kingPositionC/8+i*2][kingPositionC%8 + j])){
								return false;
							}
						}catch(Exception e){}
					}
				}

				//pawns
				
				if(kingPositionC>=16)
					try{
						if("p".equals(chessBoard[kingPositionC/8-1][kingPositionC%8-1])){
							return false;
						}
					}catch(Exception e){}
				
				if(kingPositionC>=16)
					try{
						if("p".equals(chessBoard[kingPositionC/8-1][kingPositionC%8+1])){
							return false;
						}
					}catch(Exception e){}
				
				//king
				
				for(int i=-1; i<=1;i++){			
					for(int j=-1; j<=1 ;j++){
						if(i!=0 || j!=0){
							try{
								if("a".equals(chessBoard[kingPositionC/8+i][kingPositionC%8+j])){
									return false;
								}
							}catch(Exception e){}	
						}
					}
				}

				
		return true;
	}
}
