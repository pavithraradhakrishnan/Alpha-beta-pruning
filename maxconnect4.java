import java.io.*;
import java.util.Scanner;

/**
 * 
 * @author James Spargo
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 *
 * the usage to run the program is as follows:
 * ( again, from the maxConnectFour directory )
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 * 
 *   
 */

public class maxconnect4
{
  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException 
  {
	  //int flag;
	  //int flag =0;
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();
    // the game mode
    String input = args[1].toString();	
    GameBoard currentGame;
    String first_player = args[2].toString();
    // the input game file
    if(input == null)
    { 
    	int [][]playBoard = null;
    	for(int i = 0;i<6;i++)
    	{
    		for(int j=0;j<7;j++)
    		{
    			playBoard[i][j] = 0;
    		}
    	}
    	currentGame = new GameBoard(playBoard);
    	
    }
    else
    {
    	currentGame = new GameBoard(input);
    }
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
		
    // create and initialize the game board
    
    // create the Ai Player
    AiPlayer calculon = new AiPlayer();
		
    //  variables to keep up with the game
    int playColumn = 99;				//  the players choice of column to play
    boolean playMade = false;			//  set to true once a play has been made


			
     if( !game_mode.equalsIgnoreCase( "one-move" ) && !game_mode.equalsIgnoreCase("interactive")) 
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      return;
    }

    /////////////   one-move mode ///////////
    
    String output = args[2].toString();				// the output game file
  
    System.out.print("game state before move:\n");
    
    //print the current game board
    currentGame.printGameBoard();
    // print the current scores
    System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
			", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
    	String player = args[2].toString().toLowerCase();
        System.out.println(player);
    	
		if(player.equalsIgnoreCase("computer-next"))
    	{  	
			 //flag = 1;
			while(currentGame.getPieceCount() < 42)
			{int current_player = 1;
				System.out.println("The current player now is" + current_player + "\n");
			   
			   //int flag =1;
				playColumn = calculon.findBestPlay(currentGame,depthLevel);
				//System.out.println("column that the player is gonna play is" + playColumn);
				
			        
					
				if(currentGame.isValidPlay(playColumn))
				{
				currentGame.playPiece( playColumn);
				}System.out.println(playColumn);    	
				
			        // display the current game board
			        System.out.println("move " + currentGame.getPieceCount() 
			                           + ": Player " + current_player
			                           + ", column " + playColumn);
			        System.out.print("game state after move  \n");
			        currentGame.printGameBoard();
			        System.out.println("Your turn! Please enter  your move(0-6)");
			        current_player = 2;
			        Scanner reader = new Scanner(System.in); 
			        int n = reader.nextInt();
			        
			        
			         if(n <0 || n >6 )
			        {do {
			        	System.out.println("Please enter column no from 0 to 6");
			        	 reader = new Scanner(System.in); 
					      n = reader.nextInt();
			        }while(n<0 || n > 6);
			        }
			         while(!currentGame.isValidPlay(n))
			         {
			        		System.out.println("Invalid move! Please try again");
							 reader = new Scanner(System.in); 
						      n = reader.nextInt();
						      
			        	}
			if(currentGame.isValidPlay(n))
	        {
	        currentGame.playPiece(n);
	        }
	
			        	System.out.println("move " + currentGame.getPieceCount() 
                    + ": Player " + current_player
                    + ", column " + playColumn);
			        currentGame.printGameBoard();
				
			}
			if(currentGame.getPieceCount() ==42)
	         {
	        	 System.out.println("Player 1 score:   ");
	        	 System.out.println(currentGame.getScore(1));
	        	 System.out.print("Player 2 score   ");
	        	 System.out.print(currentGame.getScore(2));
	        	 if(currentGame.getScore(1) > currentGame.getScore(2))
	        	 {
	        		 System.out.println("Player 1 wins!!!!");
	        	 }
 	        	 else if(currentGame.getScore(1)==currentGame.getScore(2))
 	        	 {
 	        		 System.out.println("It is a tie !!!Well Played" );
 	        	 }

	        	 else
	        	 {
	        		 System.out.println("Player 2 wins!!!!");
	        	 }
	         }
	         if(currentGame.getPieceCount() > 42)
	         {
	        	 System.out.println("\n I can't play.\nThe Board is Full\n\nGame Over"); 
	         }
			
    	
           
    		
    	}
		if(player.equalsIgnoreCase("human-next"))
		{
			//flag =0;
         while(currentGame.getPieceCount() < 42)
				
			{  
        	 //System.out.println("inside while");
        	 //int current_player = 2;
        	 //int flag =1;
        	 int current_player = currentGame.getCurrentTurn();
        	 System.out.println("The current player now is" + current_player +"\n");
			
        	 System.out.println("Your turn! Please enter  your move(0-6)");
		        //current_player = 2;
		        Scanner reader = new Scanner(System.in); 
		        int n = reader.nextInt();
		      
		        
		        if(!currentGame.isValidPlay(n))
		        {
		        	do
		        	{
		        		System.out.println("Invalid move! Please try again");
						 reader = new Scanner(System.in); 
					      n = reader.nextInt();
					     
		        	}while(!currentGame.isValidPlay(n));
		        }
		        
		        if(currentGame.isValidPlay(n))
				{
		        currentGame.playPiece(n);
				}
		        System.out.println("move " + currentGame.getPieceCount() 
             + ": Player " + "1"
             + ", column " + playColumn);
		        currentGame.printGameBoard();
			      current_player = 1;
			
			      if(currentGame.getPieceCount() == 42)
			      {
			    	  System.out.println("Game over");
			    	  System.out.println("Player 1 score:   ");
			        	 System.out.println(currentGame.getScore(1));
			        	 System.out.print("Player 2 score:   ");
			        	 System.out.print(currentGame.getScore(2));
			        	 if(currentGame.getScore(1) > currentGame.getScore(2))
			        	 {
			        		 System.out.println("    Player 1 wins!!!!");
			        	 }
		 	        	 else if(currentGame.getScore(1)==currentGame.getScore(2))
		 	        	 {
		 	        		 System.out.println("It is a tie !!!Well Played" );
		 	        	 }

			        	 else
			        	 {
			        		 System.out.println("Player 2 wins!!!!");
			        	 }
			        	 
			        	 return;
			         }
			    	  
			      
			     // flag = 1;
				playColumn = calculon.findBestPlay( currentGame,depthLevel);
				//System.out.println("column that the player is gonna play is");
				//System.out.println(playColumn);
				current_player = currentGame.getCurrentTurn();
				 if(currentGame.isValidPlay(n))
					{
				currentGame.playPiece( playColumn );
					}
				//System.out.println(playColumn);    	
				
			        // display the current game board
			        System.out.println("move " + currentGame.getPieceCount() 
			                           + ": Player " + current_player 
			                           + ", column " + playColumn);
			        System.out.print("game state after move  \n");
			        currentGame.printGameBoard();
			        
				
			}
         if(currentGame.getPieceCount() ==42)
         {
        	 System.out.print("Player one score:");
        	 System.out.print(currentGame.getScore(1) + ',');
        	 System.out.println("Player 2 score");
        	 System.out.print(currentGame.getScore(2));
        	 if(currentGame.getScore(1) > currentGame.getScore(2))
        	 {
        		 System.out.println("Player 1 wins!!!!");
        	 }
	        	 else if(currentGame.getScore(1)==currentGame.getScore(2))
	        	 {
	        		 System.out.println("It is a tie !!!Well Played" );
	        	 }

        	 else
        	 {
        		 System.out.println("Player 2 wins!!!!");
        	 }
         }
         if(currentGame.getPieceCount() > 42)
         {
        	 System.out.println("\n I can't play.\nThe Board is Full\n\nGame Over"); 
         }
		}
    	
	
	return;
    } 
    
    // ****************** this chunk of code makes the computer play
    if(game_mode.equalsIgnoreCase("one-move"))
    {
    if( currentGame.getPieceCount() < 42 ) 
    {
    	//flag =1;
    	int current_player =1;
        //int current_player = currentGame.getCurrentTurn();
        System.out.println("The current player now is" + "\n");
        System.out.println(currentGame.getCurrentTurn());
	// AI play - random play
	playColumn = calculon.findBestPlay( currentGame,depthLevel);
	System.out.println("column that the player is gonna play is");
	// play the piece
	currentGame.playPiece( playColumn );
	System.out.println(playColumn);    	
	
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() 
                           + ": Player " + current_player
                           + ", column " + playColumn);
        System.out.print("game state after move  \n");
        currentGame.printGameBoard();
    
        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                            ", Player2 = " + currentGame.getScore( 2 ) + "\n " )
        ;
        
        currentGame.printGameBoardToFile( output );
    } 
    else 
    {
	System.out.println("\n I can't play.\nThe Board is Full\n\nGame Over");
    }
    }
    //************************** end computer play
			
    
    return;
        
} // end of main()
	
  /**
   * This method is used when to exit the program prematurly.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
} // end of class connectFour 	