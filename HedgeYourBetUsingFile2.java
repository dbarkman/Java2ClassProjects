// HedgeYourBetUsingFile2.java
// David Barkman
// david.barkman@cox.net
// May 29, 2009
// Class - CIS263AA
// Section - 12218

// import packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HedgeYourBetUsingFile2 extends JFrame implements ActionListener {

    // some variables
    int questionCount = 0;
    int points = 0;
    int answerCount = 0;
    int possiblePoints = 0;
    String message = "";
    int pastScore = 0;

    // setup some components
    JLabel title = new JLabel("Desert Trivia");
    JLabel lastScore = new JLabel();
    JLabel question = new JLabel("The Saguaro cactus lives only in which desert?");
    JCheckBox sonoran = new JCheckBox("Sonoran Desert");
    JCheckBox mojave = new JCheckBox("Mojave Desert");
    JCheckBox chihua = new JCheckBox("Chihuahuan Desert");
    JButton submit = new JButton("Submit");
    JLabel score = new JLabel();
    Font arialB25 = new Font("Arial", Font.BOLD, 25);
    GridLayout layout = new GridLayout(3,1);

    // setup the panel
    JPanel panel = new JPanel(layout);
    JPanel top = new JPanel(new GridLayout(2,1));
    JPanel answers = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel button = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel answerGrid = new JPanel(new GridLayout(4,1));
    JPanel bottom = new JPanel(new GridLayout(2,1));

    // setup the streams
    DataOutputStream ostream;
    DataInputStream istream;

    public HedgeYourBetUsingFile2() {

        // setup new frame
        super("Desert Trivia");

        // try to open the file for reading
        try {
            istream = new DataInputStream(new FileInputStream("score.dat"));
        }
        catch(IOException e1) {
            try {
                ostream = new DataOutputStream(new FileOutputStream("score.dat"));
                istream = new DataInputStream(new FileInputStream("score.dat"));
            }
            catch(IOException e2) {
            System.err.println("File not opened");
            System.exit(1);
            }
        }

        // try to read from the file
        try {
            pastScore = istream.readInt();
        }
        catch(IOException e3) {
            pastScore = 0;
        }

        // display the highest score yet
        lastScore.setText("High score: " + Integer.toString(pastScore));

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the layout and add the components
        add(panel);
        panel.add(top);
        title.setFont(arialB25);
        top.add(title);
        top.add(lastScore);
        panel.add(answers);
        answers.add(answerGrid);
        answerGrid.add(question);
        answerGrid.add(sonoran);
        answerGrid.add(mojave);
        answerGrid.add(chihua);
        panel.add(bottom);
        bottom.add(button);
        button.add(submit);
        bottom.add(score);

        // setup the action listeners
        submit.addActionListener(this);
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        HedgeYourBetUsingFile2 bet = new HedgeYourBetUsingFile2();
        bet.setSize(350, 350);
        bet.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // process one question at a time
        switch (questionCount) {
            case 0:
                // see how many answers the user chose
                answerCount = 0;
                if (sonoran.isSelected()) answerCount++;
                if (mojave.isSelected()) answerCount++;
                if (chihua.isSelected()) answerCount++;
                // set possible points based on how many answers were picked
                if (answerCount == 1) possiblePoints = 5;
                if (answerCount == 2) possiblePoints = 2;
                if (answerCount == 3) possiblePoints = 1;
                // check for the correct answer and award the points, if any
                if (sonoran.isSelected()) {
                    points = points + possiblePoints;
                }
                // clean up the board and present the next question
                sonoran.setSelected(false);
                mojave.setSelected(false);
                chihua.setSelected(false);
                question.setText("The Joshua tree is native to which desert?");
                break;
            case 1:
                // see how many answers the user chose
                answerCount = 0;
                if (sonoran.isSelected()) answerCount++;
                if (mojave.isSelected()) answerCount++;
                if (chihua.isSelected()) answerCount++;
                // set possible points based on how many answers were picked
                if (answerCount == 1) possiblePoints = 5;
                if (answerCount == 2) possiblePoints = 2;
                if (answerCount == 3) possiblePoints = 1;
                // check for the correct answer and award the points, if any
                if (mojave.isSelected()) {
                    points = points + possiblePoints;
                }
                // clean up the board and present the next question
                sonoran.setSelected(false);
                mojave.setSelected(false);
                chihua.setSelected(false);
                question.setText("Which desert extends into Texas?");
                break;
            case 2:
                // see how many answers the user chose
                answerCount = 0;
                if (sonoran.isSelected()) answerCount++;
                if (mojave.isSelected()) answerCount++;
                if (chihua.isSelected()) answerCount++;
                // set possible points based on how many answers were picked
                if (answerCount == 1) possiblePoints = 5;
                if (answerCount == 2) possiblePoints = 2;
                if (answerCount == 3) possiblePoints = 1;
                // check for the correct answer and award the points, if any
                if (chihua.isSelected()) {
                    points = points + possiblePoints;
                }
                // clean up the board and present the next question
                sonoran.setSelected(false);
                mojave.setSelected(false);
                chihua.setSelected(false);
                question.setText("Which desert covers Las Vegas?");
                break;
            case 3:
                // see how many answers the user chose
                answerCount = 0;
                if (sonoran.isSelected()) answerCount++;
                if (mojave.isSelected()) answerCount++;
                if (chihua.isSelected()) answerCount++;
                // set possible points based on how many answers were picked
                if (answerCount == 1) possiblePoints = 5;
                if (answerCount == 2) possiblePoints = 2;
                if (answerCount == 3) possiblePoints = 1;
                // check for the correct answer and award the points, if any
                if (mojave.isSelected()) {
                    points = points + possiblePoints;
                }
                // clean up the board and present the next question
                sonoran.setSelected(false);
                mojave.setSelected(false);
                chihua.setSelected(false);
                question.setText("Which desert covers Phoenix?");
                break;
            default:
                // see how many answers the user chose
                answerCount = 0;
                if (sonoran.isSelected()) answerCount++;
                if (mojave.isSelected()) answerCount++;
                if (chihua.isSelected()) answerCount++;
                // set possible points based on how many answers were picked
                if (answerCount == 1) possiblePoints = 5;
                if (answerCount == 2) possiblePoints = 2;
                if (answerCount == 3) possiblePoints = 1;
                // check for the correct answer and award the points, if any
                if (sonoran.isSelected()) {
                    points = points + possiblePoints;
                }
                // clean up the board and present the next question
                sonoran.setSelected(false);
                mojave.setSelected(false);
                chihua.setSelected(false);

                // decide on the response based on points
                if (points > 21) {
                    message = "Fantastic!";
                } else if (points > 15 && points <= 21) {
                    message = "Very good";
                } else {
                    message = "OK";
                }

                // check if the current score is higher than the last
                // points is always written to the file regardless of the outcome,
                // so if the last score was higher, write to the file again
                score.setText("Your total score is: " + points + " - " + message);
                if (pastScore > points) {
                    points = pastScore;
                }

                // try to write to the file
                try {
                    ostream = new DataOutputStream(new FileOutputStream("score.dat"));
                    ostream.writeInt(points);
                }
                catch(IOException e4) {
                    System.err.println("Could not write to file");
                    System.exit(1);
                }
        }
        questionCount++;
    }
}