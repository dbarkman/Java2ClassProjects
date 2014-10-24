// Positions.java
// David Barkman
// david.barkman@cox.net
// Feb 20, 2009
// Class - CIS163AA
// Section - 27602

// positions class used for keeping track of the 64 positions on the board
public class Positions {

    private int number;
    private String color;
    private int taken = 0;

    // constructor used to create the 64 positions on the board
    // fields are number (0-63) and taken (if the position has been played)
    // taken is set to 0 to show not played
    public Positions(int number, int taken) {
        this.number = number;
        this.taken = taken;
    }

    // get the number on the board of the position
    public int getNumber() {
        return number;
    }

    // set a color on the position based on the play
    public void setColor(String color) {
        this.color = color;
        taken = 1;
    }

    // get which color was set on the position
    public String getColor() {
        return color;
    }

    // find out if the position has been played yet
    public int isTaken() {
        return taken;
    }
}
