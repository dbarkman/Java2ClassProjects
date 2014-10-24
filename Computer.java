// Computer.java
// David Barkman
// david.barkman@cox.net
// Feb 20, 2009
// Class - CIS163AA
// Section - 27602

import java.util.ArrayList;
import java.util.Random;


// Computer class used for the computer player
public class Computer {
    
    // when the computer player is initialized, it will initialize the 64 positions
    // this is the virtual board the computer uses to keep track of the game
    Positions[] position = new Positions[64];

    // constructor for the computer object, immediatly initializes the 64 positions
    // sets the posistion number (0-63) and taken to 0 (0 means the position is available for play)
    public Computer() {
        for (int i = 0; i < 64; i++) {
            position[i] = new Positions(i, 0);
        }
    }

    // method used to see if a horizontal play is available
    public int checkHorizontalPlay(int num) {
        int answer, position1, position2;
        position1 = position[num].isTaken();
        position2 = position[num + 1].isTaken();
        if (position1 == 0 && position2 == 0) {
            answer = 0;
        } else {
            answer = 1;
        }
        return answer;
    }

    // method used to make a horizontal play
    public void horizontalPlay(int num) {
        String color = "black";
        position[num].setColor(color);
        position[num + 1].setColor(color);
    }

    // method used to see if a vertical play is available
    public int checkVerticalPlay(int num) {
        int answer, position1, position2;
        position1 = position[num].isTaken();
        position2 = position[num + 8].isTaken();
        if (position1 == 0 && position2 == 0) {
            answer = 0;
        } else {
            answer = 1;
        }
        return answer;
    }

    // method used to make a vertical play
    public void verticalPlay(int num) {
        String color = "white";
        position[num].setColor(color);
        position[num + 8].setColor(color);
    }

    // the main method used for computer moves
    public int nextMove() {
        ArrayList<Integer> open = new ArrayList<Integer>();
        int play = 56, check;
        for (int i = 0; i < 56; i++) {
            check = checkVerticalPlay(i);
            if (check == 0) open.add(i);
        }
        // all moves possible moves are recorded and one is picked from random
        if (open.size() > 0) {
            Random r = new Random();
            int num = r.nextInt(open.size());
            play = open.get(num);
            verticalPlay(play);
        }
        return play;
    }

    // method used to check to see if computer won
    // returns 1 if computer won or 2 if there was a draw
    public int checkWinner() {
        int winner, check;
        ArrayList<Integer> open = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 8; i < 15; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 16; i < 23; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 24; i < 31; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 32; i < 39; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 40; i < 47; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 48; i < 55; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        for (int i = 56; i < 63; i++) {
            check = checkHorizontalPlay(i);
            if (check == 0) open.add(i);
        }
        if (open.size() > 0) {
            // no winner yet
            winner = 0;
        } else {
            // computer may have won, check for draw
            winner = 1;
        }

        // if human is out of moves, check to see if computer is also out of moves
        if (winner == 1) {
            ArrayList<Integer> openVert = new ArrayList<Integer>();
            for (int i = 0; i < 56; i++) {
                check = checkVerticalPlay(i);
                if (check == 0) openVert.add(i);
            }
            if (openVert.size() == 0) winner = 2;
        }

        return winner;
    }
}