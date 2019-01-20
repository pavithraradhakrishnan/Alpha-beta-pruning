I have used Minimax using alpha-beta pruning for the game Connect4.
The computer plays as a max player and plays the best possible move at each step.Here the Max player tries to maximize his score at each step while the Min player tries to reduce the opponent's score in each step.
I have used to the alpha beta apporach to prune the branches that need not be visited. The Max player at each step updates the alpha value and prunes the branch using the beta value. While the Min player updates the beta value and prunes the branches using the alpha value.
FindMinValue and FindMaxValue are the functions I have used for implementing minimax .While the values are being sent to the findbestplay method to compute the optimum move. I have also used Evaluate_heuristics function. It's implementation details are mentioned in eval_explanation.pdf
How to compile and run the code - javac GameBoard.java
                                  javac AiPlayer.java
                                  javac maxconnect4.java
                                  time java -cp . maxconnect4 interactive input1.txt computer-first 6
(where 6 is the depthlevel.input1.txt is the input file)
or for one-move
                                 time java -cp . maxconnect4 one-move input1.txt out.txt 6
(out.txt can be any output file)

Files included - GameBoard.java,AiPlayer.java,maxconnect4.java