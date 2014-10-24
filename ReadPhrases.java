// ReadPhrases.java
// David Barkman
// david.barkman@cox.net
// May 20, 2009
// Class - CIS263AA
// Section - 12218

// import packages
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class ReadPhrases extends JFrame implements ActionListener {

    // some variables
    String secretPhrase;
    int record = 0, entry;

    // setup some components
    JLabel name = new JLabel("Read Phrases for the Secret Phrase Game");
    JLabel phraseLabel = new JLabel("Phrase: ");
    JTextField secretPhraseField = new JTextField(50);
    JButton read = new JButton("Read");

    // setup the panel
    JPanel panel = new JPanel();

    // setup the file
    RandomAccessFile random;
    final int RECORD_SIZE = 36;
    StringBuffer blankPhrase = new StringBuffer(32);

    public ReadPhrases() {

        // setup new frame
        super("Read Phrases");

        // try to open the file for reading
        try {
            random = new RandomAccessFile("phrases.dat", "rw");
        }
        catch(IOException e1) {
            System.err.println("File not opened");
            System.exit(1);
        }

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the layout and add the buttons
        add(panel);
        panel.add(name);
        panel.add(phraseLabel);
        panel.add(secretPhraseField);
        panel.add(read);

        // setup the action listeners
        read.addActionListener(this);
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        ReadPhrases phrases = new ReadPhrases();
        phrases.setSize(800, 100);
        phrases.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // try to read the file
        try {
            random.seek(record * RECORD_SIZE);
            entry = random.readInt();
            secretPhrase = random.readUTF();
        }
        catch(EOFException e2) {
            closeFile();
        }
        catch(IOException e3) {
            System.err.println("Could not write to file");
            System.exit(1);
        }

        secretPhraseField.setText(secretPhrase);

        record++;
    }

    // method for closing the file
    public void closeFile() {
        try {
            random.close();
            System.exit(0);
        }
        catch(IOException e4) {
            System.err.println("Error closing file");
            System.exit(1);
        }
    }
}