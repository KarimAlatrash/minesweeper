import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinePanel extends JPanel {

    //declare class variables
    public static MineCell mineField[][];
    private int flagsUsed;
    public int boardRows;
    public int boardCols;
    int minesFlagged;
    MineFrame.Header header;
    private MineFrame mf;



    public MinePanel (boolean diff, MineFrame.Header header, MineFrame mf) {

        //sets the board rows and cols based off difficulty
        if (diff){
            boardCols = 20;
            boardRows = 20;

        }
        else {
            boardCols = 10;
            boardRows = 10;
        }


        //sets layout for the panel, col is +1 because of the header
        setLayout(new GridLayout(boardRows, boardCols+1));

        //initialises and creates field
        mineField = new MineCell[boardRows][boardCols];
        buildButtonField(mineField);
        this.header = header;
        this.mf = mf;
    }

    //builds the button field using 2d array parameter
    public void buildButtonField(MineCell Minefield[][]) {

        //declares mousehandler to be used
        MouseHandler handler;

        //iterates through each cell in the minefield
        for(int r = 0; r < Minefield.length; r++)
        {
            for(int c = 0; c < Minefield[0].length; c++)
            {

                //creates a new minecell and adds a mouselistener
                Minefield[r][c] = new MineCell(r, c);
                Minefield[r][c].setText("");

                handler = new MouseHandler(r, c);
                Minefield[r][c].addMouseListener(handler);

                add(Minefield[r][c]);
            }
        }

        //clears minelist before new mines are added
        //the mine list is static so this call should suffice
        Minefield[1][1].clearMineList();

        //to be used in the next for loop
        int x, y;

        //sets up mines on board, 10 mines for easy, 40 for hard
        for (int i = 0; i <= Math.pow(boardRows, 2)/10; i++) {

            //picks two random numbers to use as coordinates f+or a possible mine
            x = (int)(Math.random()*boardRows);
            y = (int)(Math.random()*boardCols);

            //if there is no mine at these coordinates, add one
            if (!mineField[x][y].hasMine()) {
                MineCell.addNewMine(mineField[x][y]);

            }

            else {
                i--;
            }
        }

        //sets the mine number
        MineCell.setMineNum();
    }

    //runs the code when there is a game over
    public void gameOver () {

        //removes all mouse listeners from each cell
        for (int i = 0; i < boardRows; i++) {

            for (int j = 0; j < boardCols; j++) {
                try {
                    for (int q = 0; q < mineField[i][j].getMouseListeners().length; q++) {
                        mineField[i][j].removeMouseListener(mineField[i][j].getMouseListeners()[q]);
                    }
                }
                catch (Exception e) {
                }
            }
        }

        //creates the JOption pane to pop up after a loss
        Object[] options = { "Play Again", "Quit" };
        switch (JOptionPane.showOptionDialog(null,
                //prints the # of mines left after the loss
                "There were " + (mineField[1][1].getTotalMines()-flagsUsed)+" mines left when you lost :(",
                "Game Over!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0])){
            //if the select play again, close the current frame and open the starting frame
            case 0:
                mf.frame.dispose();
                new MineFrame();
                break;
            //if the select quit, close the program
            case 1:
                System.exit(0);
                break;


        }

    }

    //code to run after a win
    public void gameWin () {

        //removes all mouse listeners from each cell
        for (int i = 0; i < boardRows; i++) {

            for (int j = 0; j < boardCols; j++) {
                try {
                    for (int q = 0; q < mineField[i][j].getMouseListeners().length; q++) {
                        mineField[i][j].removeMouseListener(mineField[i][j].getMouseListeners()[q]);
                    }
                }
                catch (Exception e) {
                }
            }
        }

        //creates the JOption pane to pop up after a win
        Object[] options = { "Play Again", "Quit" };
        switch (JOptionPane.showOptionDialog(null,
                "Congratulations! Do something to celebrate",
                "YOU WON!!!!!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0])){
            case 0:
                mf.frame.dispose();
                new MineFrame();
                break;

            //if the select quit, close the program
            case 1:
                System.exit(0);
                break;


        }

    }

    //recursively reveals the zero values
    public void revealZeros (int r, int c) {
        try {

            //checks if the mine clicked is a zero mine
            if (mineField[r][c].getMineNum() == 0 && !mineField[r][c].revealed) {

                //removes the mouse listener
                mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);

                //sets the mine to revealed
                mineField[r][c].setRevealed();


                //removes the mouseListeners again as a workaround so it actually gets removed
                if(c % 2 == 0 && r % 2 != 0) {
                    mineField[r][c].setBackground(new Color(255, 255, 255));
                    mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);
                }
                else {
                    mineField[r][c].setBackground(new Color(255, 255, 255));
                    mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);
                }

                //recursively reveals the cells of all
                //zeros or cells around this one
                revealZeros(r + 1, c);
                revealZeros(r - 1, c);
                revealZeros(r, c - 1);
                revealZeros(r, c + 1);


            }

            //reveals non zero cells
            else if(mineField[r][c].getMineNum() != 0){

                //sets the text to # of mines surrounding them
                mineField[r][c].setText(mineField[r][c].getMineNum() + "");

                mineField[r][c].setBackground(new Color(60, 63, 65));

                //removes their mouse listener
                mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);

                mineField[r][c].setRevealed();

            }

        }

        catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    //mousehandler for all minecells
    class MouseHandler extends MouseAdapter
    {
        //parameters are to make sure the mousehandler is for the
        //correct minecell
        int r, c;
        public MouseHandler(int r, int c)
        {
            this.r = r;
            this.c = c;
        }

        public void mouseReleased(MouseEvent e)
        {

            //in the event of a left mouse click...
            if(e.getButton() == 1) {

                //sets background of cells in a checkered pattern
                if(c % 2 == 0 && r % 2 != 0)
                    mineField[r][c].setBackground(new Color(60, 63, 65));
                else
                    mineField[r][c].setBackground(new Color(60, 63, 65));

                //if the cell has a mine make it game over
                if (mineField[r][c].hasMine()) {

                    mineField[r][c].setBackground(new Color(214, 48, 58));

                    mineField[r][c].setText("B");

                    //removes the mouselistener so it cannot be clicked again
                    mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);

                    //runs the game over code
                    gameOver();
                }

                //if the cell wasnt a mine...
                else {

                    //checks if there are any mines around it
                    if (mineField[r][c].getMineNum() > 0) {

                        //sets the text to the integer # of bombs surrounding it
                        mineField[r][c].setText(Integer.toString(mineField[r][c].getMineNum()));

                        //removes the mouse listener so the player cant mess with anything
                        mineField[r][c].removeMouseListener(mineField[r][c].getMouseListeners()[0]);


                        //removes the first mouse listener associated with this object (should be only one anyways)
                        mineField[r][c].revealed = true;
                    }

                    //checks if the cell is 0 valued
                    else if (mineField[r][c].getMineNum() == 0){
                        //recursively reveals the zero-cells that are touching eachother
                        revealZeros(r, c);


                    }

                }
            }

            //in the event of a right click....
            else if(e.getButton() == 3) {

                //if the cell isnt flagged and you have enough flags, and the cell hasnt already been revealed flag it
                if (flagsUsed < mineField[r][c].getTotalMines() && !mineField[r][c].getFlag() && !mineField[r][c].isRevealed()) {
                    mineField[r][c].setText("F");
                    mineField[r][c].setFlag(true);
                    flagsUsed++;

                    //refreshes the header with the new # of flags
                    header.flags.setText("<html><body><center>Flags Used: "+(mineField[r][c].getTotalMines()-flagsUsed)+"<center></body></html>");

                    //adds another mine flagged if the cell flagged was a mine
                    if (mineField[r][c].hasMine()) {
                        minesFlagged++;
                    }
                }

                //if the cell is flagged, unflag it
                else if (mineField[r][c].getFlag()) {

                    //resets the cells values
                    mineField[r][c].setText("");
                    mineField[r][c].setFlag(false);
                    flagsUsed--;

                    //refreshes header
                    header.flags.setText("<html><body><center>Flags Used: "+(mineField[r][c].getTotalMines()-flagsUsed)+"<center></body></html>");

                    //takes away a flagged mine if the cell was a mine
                    if (mineField[r][c].hasMine()) {
                        minesFlagged--;
                    }

                }

                //if you dont have enough flags left, do nothing
                else {
                    System.out.println("Too many flags used!!! Not enough flags");
                }

                //if all the mines have been flagged, the game has been won
                if (minesFlagged == mineField[r][c].getTotalMines()) {
                    gameWin();

                }

            }
        }
    }


}

