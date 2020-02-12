import java.io.Serializable;

public class GameInfo implements Serializable {

    String message;
    int difficulty;     //1=easy, 2=medium, 3=expert
    String[] board = {"b", "b", "b", "b", "b", "b", "b", "b", "b"};
    int p1, p2, p3;
    int p1s, p2s, p3s;



    GameInfo(){
        message = "";
        difficulty = 1;
        p1 = -1;
        p2 = -1;
        p3 = -1;
        p1s = 0;
        p2s = 0;
        p3s = 0;
    }

    //return true if two X's or two O's are adjacent
    private boolean checkAdjacent(int a, int b, String x)
    {
        if ( board[a].equals(x) && board[b].equals(x) )
            return true;
        else
            return false;
    }

    /*    Board Visual:
     *      0 | 1 | 2
     *      3 | 4 | 5
     *      6 | 7 | 8
     */

    //return true if STRING passed ("X" or "O") has three in a row
    boolean checkWinner(String x){

        //check horizontally
        if( checkAdjacent(0,1, x) && checkAdjacent(1,2, x) )
            return true;
        else if( checkAdjacent(3,4, x) && checkAdjacent(4,5, x) )
            return true;
        else if ( checkAdjacent(6,7, x) && checkAdjacent(7,8, x))
            return true;

            //check vertically
        else if ( checkAdjacent(0,3, x) && checkAdjacent(3,6, x))
            return true;
        else if ( checkAdjacent(1,4, x) && checkAdjacent(4,7, x))
            return true;
        else if ( checkAdjacent(2,5, x) && checkAdjacent(5,8, x))
            return true;

            //check diagonally
        else if ( checkAdjacent(0,4, x) && checkAdjacent(4,8, x))
            return true;
        else if ( checkAdjacent(2,4, x) && checkAdjacent(4,6, x))
            return true;
        else
            return false;
    }

    boolean isTie(){
        for (String s : board) {
            if (s.equals("b"))
                return false;
        }
        return true;
    }
}