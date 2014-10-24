// SecretPhraseUsingFile.java
// David Barkman
// david.barkman@cox.net
// May 20, 2009
// Class - CIS263AA
// Section - 12218

// import packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

public class SecretPhraseUsingFile extends JFrame implements ActionListener {

    // some variables
    int valid = 0, count = 0, entry;
    String phrasePicked;
    StringBuffer phrase;

    // setup the file
    RandomAccessFile random;
    final int RECORD_SIZE = 36;

    // setup some components
    JLabel title = new JLabel("Secrte Phrase Game");
    JLabel label1 = new JLabel("Play our game - guess the secret phrase - Click one letter");
    JLabel label2 = new JLabel("");
    JLabel secret = new JLabel("");
    Font arialB30 = new Font("Arial", Font.BOLD, 30);
    Font arialB20 = new Font("Arial", Font.BOLD, 20);
    JButton playAgain = new JButton("Play Again?");

    // setup the panel
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // put all the letters of the alphabet into a string
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // initialize the array of letter buttons
    JButton[] buttons = new JButton[alpha.length()];

    public SecretPhraseUsingFile() {

        // setup new frame
        super("Secret Phrase Game");

        // try to open the file for reading
        try {
            random = new RandomAccessFile("phrases.dat", "rw");
        }
        catch(IOException e1) {
            System.err.println("File not opened");
            System.exit(1);
        }

        // the following code block is used to find how many records are in the file
        try {
            while (valid == 0) {
                random.seek(count * RECORD_SIZE);
                entry = random.readInt();
                if (entry == 0) {
                    valid = 1;
                } else {
                    count++;
                }
            }
        }
        catch(IOException e2) {
            System.err.println("Could not read file at record " + count + ".");
            System.exit(1);
        }

        // randomly pick one of the phrases to use from the file
        Random r = new Random();
        int num = r.nextInt(count);

        // try to read the picked phrase from the file
        try {
            random.seek(num * RECORD_SIZE);
            entry = random.readInt();
            phrasePicked = random.readUTF();
        }
        catch(IOException e3) {
            System.err.println("Could not write to file");
            System.exit(1);
        }

        // put the phrase in a string buffer called phrase
        phrase = new StringBuffer(phrasePicked);

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup the panel
        add(panel);
        title.setFont(arialB30);
        label1.setFont(arialB20);
        panel.add(title);
        panel.add(label1);

        // turn phrase into all "*", only letters, not spaces
        int phraseLength = phrase.length();
        for (int i = 0; i < phraseLength; i++) {
            char letter = phrase.charAt(i);
            if (Character.isLetter(letter)) {
                phrase.setCharAt(i,'*');
            }
        }

        // phrase the string buffer is copied into phraseSt the String
        // can only set an applet object with a String
        String phraseSt = phrase.toString();
        secret.setText(phraseSt);
        secret.setFont(arialB30);
        panel.add(secret);
        panel.add(label2);

        // add button objects to the "buttons" array and setup the action listeners
        int alphaLength = alpha.length();
        for (int i = 0; i < alphaLength; i++) {
            char letter = alpha.charAt(i);
            String letterStr = Character.toString(letter);
            buttons[i] = new JButton(letterStr);
            panel.add(buttons[i]);
            buttons[i].addActionListener(this);
            playAgain.addActionListener(this);
        }
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        SecretPhraseUsingFile phrases = new SecretPhraseUsingFile();
        phrases.setSize(600, 250);
        phrases.setVisible(true);
    }

    public void actionPerformed(ActionEvent thisEvent) {

        // find out what button was clicked
        Object source = thisEvent.getSource();
        if (source.equals(playAgain)) {
            SecretPhraseUsingFile phrases = new SecretPhraseUsingFile();
            phrases.setSize(600, 250);
            phrases.setVisible(true);
        }
        for (int i = 0; i < buttons.length; i++) {
            if (source.equals(buttons[i])) {
                buttons[i].setEnabled(false);
                String buttonText = buttons[i].getText();

                // find out if the button clicked matches a letter in the phrase
                // use "count" to keep track of how letters matched the letter clicked
                int count = 0;
                int phraseStrLength = phrasePicked.length();
                for (int j = 0; j < phraseStrLength; j++) {
                    char letter = phrasePicked.charAt(j);
                    String letterStr = Character.toString(letter);
                    // if it matches swap out the *(s) for the letter(s)
                    if (letterStr.equalsIgnoreCase(buttonText)) {
                        phrase.setCharAt(j, letter);
                        // turn phrase back into a String and display the updated phrase
                        String phraseSt = phrase.toString();
                        secret.setText(phraseSt);
                        count++;

                        // count the *s in the phrase to see if any are left
                        int letterCount = 0;
                        int secretLength = phraseSt.length();
                        for (int k = 0; k < secretLength; k++) {
                            char letter1 = phraseSt.charAt(k);
                            if (letter1 == '*') letterCount++;
                        }
                        // if all the letters have been converted, disable all the buttons
                        // and display the Congratulations message
                        if (letterCount == 0) {
                            for (int m = 0; m < buttons.length; m++) {
                                buttons[m].setEnabled(false);
                            }
                            label2.setText("Congratulations!");
                            //panel.add(playAgain);
                            break;
                        }
                    }
                    // display a message based on if the letter was matched
                    if (count == 0) {
                        label2.setText("Sorry, " + buttonText + " is not in the phrase.");
                    } else if (count >= 1) {
                        label2.setText("Correct!");
                    }
                }
            }
        }
    }
}