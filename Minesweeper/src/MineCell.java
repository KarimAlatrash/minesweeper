import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/*
TODO center and style everything properly
TODO make game over frame, about me frame
 */

public class MineCell extends JButton {
    int surroundingMines = 0;
    int row, col;
    public boolean revealed = false;
    public boolean flagged = false;
    private static LinkedList<MineCell> mines = new LinkedList<>();

    //sets the cell to being a bomb if in bottom 10%
    //sets text to #of surrounding bombs if in 90%
    MineCell (int r, int c) {

        //records coordinate of cell
        row = r;
        col = c;

        //would set the background to alternating colours
        //too afraid to change it now
        if(c % 2 == 0 && r % 2 != 0)
            setBackground(new Color(205, 205, 205));
        else
            setBackground(new Color(199, 199, 199));

        setForeground(Color.WHITE);
        setFocusable(false);

    }

    //returns if the cell is revlealed or not
    public boolean isRevealed() {
        return revealed;
    }

    //sets the state of the cell to revealed
    public void setRevealed() {
        revealed = true;
    }

    //returns the row of the cell
    public int getRow () {
        return row;
    }

    //returns the column of the cell
    public int getCol () {
        return col;
    }

    //adds the cell passed as argument to list of mines
    public static void addNewMine (MineCell mine) {
        mines.add(mine);
    }

    //adds one to the # of surrounding mines and then returns that number
    public void addMineNum () {
        surroundingMines++;
    }

    //returns # of mines surrounding
    public int getMineNum () {
        return surroundingMines;
    }

    //checks if there is a mine at this cell
    public boolean hasMine () {
        return mines.contains(this);
    }

    //adds a number to each cell for each bomb in the cells radius
    public static void setMineNum(){

        //iterates through all mines
        for (MineCell m : mines) {

            //sets row and col to the row and col of the mine in the loop
            int row = m.getRow();
            int col = m.getCol();

            /*
            checks every possibility no matter what but
            breaks out of individual try block if the
            cell does not exist
            */
            try {
                MinePanel.mineField[row][col + 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row][col - 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row + 1][col + 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row - 1][col + 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row + 1][col - 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row - 1][col - 1].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row - 1][col].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e) { }

            try {
                MinePanel.mineField[row + 1][col].addMineNum();
            }
            catch (ArrayIndexOutOfBoundsException e){ }

        }
    }

    //returns the total number of mines
    public int getTotalMines() {
        return mines.size()-1;
    }

    //clears the mine list from the previous game
    public void clearMineList () {
        try {
            //clears the mine list
            mines.clear();
        }
        catch (Exception e) {
            System.out.println("Whoops, something went wrong with the getTotalMines() method!");
        }
    }


    //sets the colour of the cell if it is flagged or not
    public void setFlag(boolean flagged) {

        //if the cell is supposed to be flagged, set the colour to orange
        if(flagged)
        setBackground(new Color(255, 146, 72));

        //if it is supposed to be unflagged, set it to white
        //was in checkered pattern, not anymore
        else {
            if(getRow()*getCol() % 2 == 0)
                setBackground(new Color(205, 205, 205));
            else
                setBackground(new Color(205, 205, 205));
        }

        this.flagged = flagged;
    }

    //returns if the cell is flagged or not
    public boolean getFlag () {
        return flagged;
    }



 }
