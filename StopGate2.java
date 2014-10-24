// StopGate2.java
// David Barkman
// david.barkman@cox.net
// May 31, 2009
// Class - CIS263AA
// Section - 12218

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;

public class StopGate2 extends JFrame implements MouseListener {

    // some variables
    int check, play, ready, winner, draw;
    String pattern, percentStr;
    DecimalFormat percentFormat;

    // some components
    JPanel panel = new JPanel(new GridLayout(8,8));
    JPanel[] grid = new JPanel[64];
    Color color1 = Color.blue;
    Color color2 = Color.yellow;
    Color switchColor;
    Color black = Color.black;
    Color white = Color.white;

    // initialize a new computer player
    Computer comp = new Computer();

        // setup the streams
        DataOutputStream ostream;
        DataInputStream istream;

    public StopGate2() {

        // setup new frame
        super("Stop Gate");

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup the panel
        add(panel);
        for (int i = 0; i < 64; i++) {
            grid[i] = new JPanel();
            panel.add(grid[i]);
            if (i % 8 == 0) {
                switchColor = color1;
                color1 = color2;
                color2 = switchColor;
            }
            if (i % 2 == 0) {
                grid[i].setBackground(color1);
            } else {
                grid[i].setBackground(color2);
            }
            grid[i].addMouseListener(this);
        }
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        StopGate2 gate = new StopGate2();
        gate.setSize(500, 500);
        gate.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        
        // ready tells the computer if he can play yet, 1 means he can play
        ready = 1;
        Object source = e.getComponent();
        if (source == grid[7] || source == grid[15] || source == grid[23] || source == grid[31] || source == grid[39] || source == grid[47] || source == grid[55] || source == grid[63]) {
            JOptionPane.showMessageDialog(null, "You cannot select the far right row, try further to the left.");
            ready = 0;
        } else {
            for (int i = 0; i < 64; i++) {
                if (source == grid[i]) {
                    check = comp.checkHorizontalPlay(i);
                    if (check == 0) {
                        comp.horizontalPlay(i);
                        grid[i].setBackground(black);
                        grid[i + 1].setBackground(black);
                    } else {
                        JOptionPane.showMessageDialog(null, "That play is not available, one or both positions are already played.");
                        ready = 0;
                    }
                }
            }
        }
        if (ready == 1) {
            play = comp.nextMove();
            if (play < 56) {
                grid[play].setBackground(white);
                grid[play + 8].setBackground(white);
            } else {
                JOptionPane.showMessageDialog(null, "Human wins! You most likely cheated!");
                updateStats(1);
            }
            winner = comp.checkWinner();
            if (winner == 1) {
                JOptionPane.showMessageDialog(null, "Computer wins! My intelect is far greater than yours!");
                updateStats(0);
            } else if (winner == 2) {
                JOptionPane.showMessageDialog(null, "Draw game. You most likely cheated!");
                updateStats(0);
            }
        }
    }

    public void updateStats(int won) {

        // some variables
        int totalGames, humanWon;
        double totalGamesDbl, humanWonDbl, percent;

        // try to open the file for reading
        try {
            istream = new DataInputStream(new FileInputStream("gameStats.dat"));
        }
        catch(IOException e1) {
            try {
                ostream = new DataOutputStream(new FileOutputStream("gameStats.dat"));
                istream = new DataInputStream(new FileInputStream("gameStats.dat"));
            }
            catch(IOException e2) {
            System.err.println("File not opened");
            System.exit(1);
            }
        }

        // try to read from the file
        try {
            totalGames = istream.readInt();
            humanWon = istream.readInt();
        }
        catch(IOException e3) {
            totalGames = 0;
            humanWon = 0;
        }

        if (won == 1) {
            // human won
            totalGames++;
            humanWon++;
        } else {
            // computer won or draw
            totalGames++;
        }

        // change the stats into double data type for proper division
        totalGamesDbl = totalGames;
        humanWonDbl = humanWon;
        percent = humanWonDbl / totalGamesDbl * 100;

        // instantiate a new percentFormat object to use for formatting percents
        pattern = "###";
        percentFormat = new DecimalFormat(pattern);
        percentStr = percentFormat.format(percent);

        // display the human's record
        JOptionPane.showMessageDialog(null, "You have won " + percentStr + "% of all games played.");

        // try to write to the file
        try {
            ostream = new DataOutputStream(new FileOutputStream("gameStats.dat"));
            ostream.writeInt(totalGames);
            ostream.writeInt(humanWon);
        }
        catch(IOException e4) {
            System.err.println("Could not write to file");
            System.exit(1);
        }
        System.exit(0);
    }

    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}