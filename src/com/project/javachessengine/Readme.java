
 /*
     * piece=white/black
     * pawn=P/p
     * kinght (horse)=K/k
     * bishop=B/b
     * rook=R/r
     * Queen=Q/q
     * King=A/a
     * 
     * The strategy here in the game is to create a tree of possible outcomes
     * and select the best one. For each of the move this engine search uptil the depth of 4.
     * 
     * (1234b represents row1,column2 moves to row3, column4 which captured
     * b (a space represents no capture))
  */
