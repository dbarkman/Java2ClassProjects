// WritePhrases.java
// David Barkman
// david.barkman@cox.net
// May 20, 2009
// Class - CIS263AA
// Section - 12218

// import packages
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class WritePhrases extends JFrame implements ActionListener {

    // setup some variables
    int valid, count, entry;

    // setup some components
    JLabel name = new JLabel("Enter Phrases for the Secret Phrase Game");
    JLabel phraseLabel = new JLabel("Phrase: ");
    JTextField secretPhraseField = new JTextField(50);
    JButton save = new JButton("Save");

    // setup the panel
    JPanel panel = new JPanel();

    // setup the file
    RandomAccessFile random;
    final int RECORD_SIZE = 36;
    final int NUM_RECORDS = 1000;
    StringBuffer blankPhrase = new StringBuffer(32);

    public WritePhrases() {

        // setup new frame
        super("Enter Phrases");

        // try to open the file for writing
        try {
            File randomFile = new File("phrases.dat");
            random = new RandomAccessFile("phrases.dat", "rw");
            if (randomFile.length() == 0) {
                for (int i = 0; i < NUM_RECORDS; ++i) {
                    random.writeInt(0);
                    random.writeUTF(blankPhrase.toString());
                }
            }
        }
        catch(IOException e1) {
            System.err.println("File not opened");
            System.exit(1);
        }

        // setup the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the layout and add the components
        add(panel);
        panel.add(name);
        panel.add(phraseLabel);
        panel.add(secretPhraseField);
        panel.add(save);

        // setup the action listeners
        save.addActionListener(this);
    }

    public static void main(String[] args) {

        // instantiate the new frame object
        WritePhrases phrase = new WritePhrases();
        phrase.setSize(800, 150);
        phrase.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // the following code block is used to find the first empty record in the file
        valid = 0;
        count = 0;
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

        // try to write to the file
        try {
            random.seek(count * RECORD_SIZE);
            random.writeInt(count+1);
            random.writeUTF(secretPhraseField.getText());
        }
        catch(IOException e3) {
            System.err.println("Could not write to file");
            System.exit(1);
        }

        secretPhraseField.setText("");
    }
}