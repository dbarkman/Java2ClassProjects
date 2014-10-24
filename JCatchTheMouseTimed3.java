// JCatchTheMouseTimed3.java
// David Barkman
// david.barkman@cox.net
// May 31, 2009
// Class - CIS263AA
// Section - 12218

// import packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class JCatchTheMouseTimed3 extends JFrame implements ActionListener {

    // some variables
    int num, time, pastTime, start, stop;
    double score, score1, clicks, hits;
    String pattern, scoreStr;
    DecimalFormat percentFormat;
    GregorianCalendar begin, end;

    // some components
    JPanel panel = new JPanel(new BorderLayout());
    JPanel top = new JPanel(new GridLayout(2,1));
    JPanel grid = new JPanel(new GridLayout(6,8));
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton[] buttons = new JButton[48];
    JLabel title = new JLabel("Click the X!");
    JLabel result = new JLabel();

    // setup the streams
    DataOutputStream ostream;
    DataInputStream istream;

    public JCatchTheMouseTimed3() {

        // setup new frame
        super("Click the X");

        // instantiate the a calendar object to get the starting time
        begin = new GregorianCalendar();
        start = (int) begin.getTimeInMillis();

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup the panel
        add(panel);
        panel.add(top, BorderLayout.NORTH);
        top.add(titlePanel);
        titlePanel.add(title);
        top.add(resultPanel);
        resultPanel.add(result);
        panel.add(grid, BorderLayout.CENTER);

        // instantiate the 48 buttons
        for (int i = 0; i < 48; i++) {
            buttons[i] = new JButton();
            grid.add(buttons[i]);
            buttons[i].addActionListener(this);
        }

        // randomly pick one of the buttons to display the first X
        Random r = new Random();
        num = r.nextInt(48);
        buttons[num].setText("X");

        // instantiate a new percentFormat object to use for formatting percents
        pattern = "###";
        percentFormat = new DecimalFormat(pattern);
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        JCatchTheMouseTimed3 mouse = new JCatchTheMouseTimed3();
        mouse.setSize(500, 500);
        mouse.setVisible(true);
    }

    public void actionPerformed(ActionEvent thisEvent) {

        // keep track of total clicks for the score
        clicks++;

        // find out what button was clicked
        Object source = thisEvent.getSource();
        if (source == buttons[num]) {
            buttons[num].setText("");
            Random r = new Random();
            num = r.nextInt(48);
            buttons[num].setText("X");
            hits++;
        }

        // end the game once the player has 10 sucessful hits
        if (hits == 10) {
            // figure out how long the user took to complete the exercise
            end = new GregorianCalendar();
            stop = (int) end.getTimeInMillis();
            time = stop - start;
            time = time / 1000;
            
            // clean up the board and display the score
            buttons[num].setText("");
            score = hits/clicks * 100;
            scoreStr = percentFormat.format(score);
            for (int i = 0; i < 48; i++) {
                buttons[i].setEnabled(false);
            }
            title.setText("Congratulations!  Your score is " + scoreStr + "%!  The exercise took you " + time + " seconds.");

            // try to open the file for reading
            try {
                istream = new DataInputStream(new FileInputStream("bestTime.dat"));
            }
            catch(IOException e1) {
                try {
                    ostream = new DataOutputStream(new FileOutputStream("bestTime.dat"));
                    istream = new DataInputStream(new FileInputStream("bestTime.dat"));
                }
                catch(IOException e2) {
                System.err.println("File not opened");
                System.exit(1);
                }
            }

            // try to read from the file
            try {
                pastTime = istream.readInt();
            }
            catch(IOException e3) {
                pastTime = 999;
            }

            if (pastTime < time) {
                // not best time
                time = pastTime;
                result.setText("This is not your best time, the best time is " + time + ".");
            } else {
                // best time
                result.setText("This is your best time ever!");
            }

            // try to write to the file
            try {
                ostream = new DataOutputStream(new FileOutputStream("bestTime.dat"));
                ostream.writeInt(time);
            }
            catch(IOException e4) {
                System.err.println("Could not write to file");
                System.exit(1);
            }
        }
    }
}