import java.io.FileNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * The constructor essentially does nothing. 
 * 
 * @author james spargo
 *
 */

public class AiPlayer
{
	/**
	 * The constructor essentially does nothing except instantiate an
	 * AiPlayer object.
	 *
	 */
	public AiPlayer() 
	{
		// nothing to do here
	}

	/**
	 * This method plays a piece randomly on the board
	 * @param currentGame The GameBoard object that is currently being used to
	 * play the game.
	 * @return an integer indicating which column the AiPlayer would like
	 * to play in.
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	
	public int findBestPlay( GameBoard currentGame,int depthlevel)  
	{
		
		
		

		int depth =depthlevel;

		int alpha =-100000;

		int beta = 100000;

		int playChoice = 100000  ;
		
		int v = 100000;
		
		if(currentGame.getCurrentTurn() ==1 || currentGame.getCurrentTurn() == 2)
	     {
			//System.out.println("playing as max player");
			//if(flag ==1)
			//{
		    int[] playChoices = new int[7];
			for(int i= 0; i<7;i++)
			{

				GameBoard successorGameBoard_1 = new GameBoard(currentGame.getGameBoard());
				if(currentGame.getPieceCount()<42 && currentGame.isValidPlay(i))
				{
				successorGameBoard_1.playPiece(i);
				//System.out.println("successor game board");
				//successorGameBoard_1.printGameBoard();
				
				playChoices[i] = FindMaxValue(successorGameBoard_1, alpha,beta,depth);
				successorGameBoard_1.removePiece(i);
				//System.out.println("play choice in max"+ playChoices[i]);
				//System.out.println("depth is" + depth);
				//System.out.println("find best play values" +playChoices[i]);
				if(  playChoices[i] < v)
				{
					//System.out.println(playChoices[i]);
					v = playChoices[i];
					//System.out.println("the value of v" +v);
					playChoice = i;
				}
				}
				//System.out.println("Play choice in max is " + playChoices[i]);
			}
		

	     }
		

		return playChoice;	
}
		
		
		
		
		
		

/*This function is used to find the max value.
 *  It calculates the score by playing the piece at each level and recursively calling FindMinValue function */


		public  int FindMaxValue(GameBoard currentGame, int alpha, int beta,int depth)
		{ int v =-100000;
		//int v = (int) Double.MIN_VALUE;
		GameBoard successorGameBoard = new GameBoard(currentGame.getGameBoard());
		//System.out.println("printing the depth in find max value " +  depth + "\n");
		
			//System.out.println("printing the column no in find max value " + i  + "\n");
		if(currentGame.getPieceCount()<42 &&(depth >0 ))
		{

			for(int j =0;j<7;j++)
			{ 
				
                if(successorGameBoard.isValidPlay(j))
                {
				successorGameBoard.playPiece(j);
				//System.out.println("successor game board");
				//successorGameBoard.printGameBoard();
				int value = FindMinValue(successorGameBoard,alpha,beta,depth-1);
				successorGameBoard.removePiece(j);
				//System.out.println("Min value is" + v);
                //System.out.println("valuein find max " + v);
				if(value > v)
				{
					v = value;
				}

				if(v >= beta)
				{
					return v;
				}
				if(alpha < v)
				{
					alpha = v; 
				}
			} 
				}
		}
		
		else 
		{   
			int player1_score =Evaluate_heuristics(successorGameBoard,1) + successorGameBoard.getScore(1);
	         int player2_score=Evaluate_heuristics(successorGameBoard,2) + successorGameBoard.getScore(2);
	            //evaluate_heuristics(successorGameBoard);
				//v = currentGame.getScore(2) - currentGame.getScore(1);
				v = player2_score - player1_score;
			
		}


			
			
			

		return v;
		}

		/*This function is used to find the min value of the corresponding node . 
		 * It calculates the score by playing the piece at each level and recursively calling FindMaxValue function */


		public int FindMinValue(GameBoard currentGame, int alpha, int beta,int depth)
		{

			int v = 10000;
			GameBoard successorGameBoard = new GameBoard(currentGame.getGameBoard());
			//System.out.println("printing the column no in find min value " + i  + "depth" + depth + "\n");
			if(currentGame.getPieceCount()<42 && depth > 0)
			{
				for(int j =0;j<7;j++)
				{ 
					
					//System.out.println("printing the depth in find min value " +  depth + "\n");
					   
                        if(successorGameBoard.isValidPlay(j))
                        {
						successorGameBoard.playPiece(j);


						   int value = FindMaxValue(successorGameBoard,alpha,beta,depth-1);
						   successorGameBoard.removePiece(j);
						    //System.out.println("Max value is" + v);
						   //System.out.println("valuein find  " + v);
						         if(value < v)
						          {
							        v = value;
							
						          }

						          if(v <=  alpha)
						          {
							        return v;
							//continue;
						           }
						           if(v < beta)
						           {

							        beta = v;
						            }


                        }
					}
			        
			}	

			       else 
			       {
				         int player1_score =Evaluate_heuristics(successorGameBoard,1) + successorGameBoard.getScore(1);
				         int player2_score=Evaluate_heuristics(successorGameBoard,2) + successorGameBoard.getScore(2);
				            //evaluate_heuristics(successorGameBoard);
							//v = currentGame.getScore(2) - currentGame.getScore(1);
							v = player2_score - player1_score;
			          }

			
		
				
		
		return v;
	}
/*Evaluate heuristics is used calculate the heuristic value according to the position of the current board state. The Evaluate heuristic function is designed to play well againt random
 * It looks for the winning streak like 4 in a row and 3 in a row
 */
public int Evaluate_heuristics(GameBoard currentGame,int playerid)
{
	int player;

	player = playerid;
	int playerscore = 0;
	for( int i = 0; i < 6; i++ ) 
    {
    for( int j = 0; j < 3; j++ ) 
    {
	if( ( currentGame.playBoard[ i ][j] == player ) &&
	    ( currentGame.playBoard[ i ][ j+1 ] == player ) &&
	    ( currentGame.playBoard[ i ][ j+2 ] == player ) &&
	    ( currentGame.playBoard[ i ][ j+3 ] == player ) ) 
	{
	    playerscore = playerscore + 5;
	}
    }
    }
	
	for( int i = 0; i < 6; i++ ) 
    {
    for( int j = 0; j < 3; j++ ) 
    {
	if( ( currentGame.playBoard[ i ][j] == player ) &&
	    ( currentGame.playBoard[ i ][ j+1 ] == player ) &&
	    ( currentGame.playBoard[ i ][ j+2 ] == player ))
	     
	{
	    playerscore = playerscore + 2;
	}
    }
    }
 // end horizontal

//check vertically
for( int i = 0; i < 3; i++ ) {
    for( int j = 0; j < 7; j++ ) {
	if( ( currentGame.playBoard[ i ][ j ] == player ) &&
	    ( currentGame.playBoard[ i+1 ][ j ] == player ) &&
	    ( currentGame.playBoard[ i+2 ][ j ] == player ) &&
	    ( currentGame.playBoard[ i+3 ][ j ] == player ) ) {
	    playerscore = playerscore +5;
	}
    }
} // end verticle

for( int i = 0; i < 3; i++ ) {
    for( int j = 0; j < 7; j++ ) {
	if( ( currentGame.playBoard[ i ][ j ] == player ) &&
	    ( currentGame.playBoard[ i+1 ][ j ] == player ) &&
	    ( currentGame.playBoard[ i+2 ][ j ] == player ))
	 {
	    playerscore = playerscore +2;
	}
    }
} 

//check diagonally - backs lash ->	\
    for( int i = 0; i < 3; i++ ){
	for( int j = 0; j < 4; j++ ) {
	    if( ( currentGame.playBoard[ i ][ j ] == player ) &&
		( currentGame.playBoard[ i+1 ][ j+1 ] == player ) &&
		( currentGame.playBoard[ i+2 ][ j+2 ] == player ) &&
		( currentGame.playBoard[ i+3 ][ j+3 ] == player ) ) {
		playerscore = playerscore +5;
	    }
	}
    }
    
    for( int i = 0; i < 3; i++ ){
    	for( int j = 0; j < 4; j++ ) {
    	    if( ( currentGame.playBoard[ i ][ j ] == player ) &&
    		( currentGame.playBoard[ i+1 ][ j+1 ] == player ) &&
    		( currentGame.playBoard[ i+2 ][ j+2 ] == player ))
    	 {
    		playerscore = playerscore +2;
    	    }
    	}
        }
    
    
    //check diagonally - forward slash -> /
    for( int i = 0; i < 3; i++ ){
	for( int j = 0; j < 4; j++ ) {
	    if( ( currentGame.playBoard[ i+3 ][ j ] == player ) &&
		( currentGame.playBoard[ i+2 ][ j+1 ] == player ) &&
		( currentGame.playBoard[ i+1 ][ j+2 ] == player ) &&
		( currentGame.playBoard[ i ][ j+3 ] == player ) ) {
		playerscore =playerscore +5;
	    }
	}
    }// end player score check
    for( int i = 0; i < 3; i++ ){
    	for( int j = 0; j < 4; j++ ) {
    	    if( ( currentGame.playBoard[ i+3 ][ j ] == player ) &&
    		( currentGame.playBoard[ i+2 ][ j+1 ] == player ) &&
    		( currentGame.playBoard[ i+1 ][ j+2 ] == player )) 
    	{
    		playerscore =playerscore +2;
    	    }
    	}
        }
return playerscore;
}
}