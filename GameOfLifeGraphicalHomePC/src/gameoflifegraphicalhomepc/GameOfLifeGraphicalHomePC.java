/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflifegraphicalhomepc;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.w3c.dom.events.MouseEvent;

/**
 *
 * @author Elias
 */
public class GameOfLifeGraphicalHomePC {
static JLabel board[][] = new JLabel[50][50]; //public 2d array of jlabels
    static boolean boardBooleans[][] = new boolean[50][50]; //public 2d array of booleans holding the current state of each jlabel 
    static boolean boardBooleanTransfer[][] = new boolean[50][50]; //public 2d array of booleans to hold the next state of each jlabel
    static int cellCount = 0; //public int to hold the number of surrounding cells 
    static boolean startCondition = false; //public boolean to hold the startcondition that sets of the thread 
    static JButton start = new JButton("Start"); //public manual start jbutton, needs to be public so name can be dynamically changed 
    static boolean dontDoIt = false; //boolean for an easter egg
    static int value = 1; //Static into for the values of the jspinner 
    static int min = 1;
    static int max = 10;
    static int step = 1;
    static JSpinner delayValueModifier = new JSpinner(new SpinnerNumberModel(value, min, max, step)); //static jspinner
    static String[] customOption = {"Fill Board", "Clear Board", "Random Fill"}; //String array for all the options in the combo box
    static JComboBox customOptions = new JComboBox(customOption); //Static jcombobox

    public static void main(String[] args) {
        initializeBooleans(); //Calls certain method to start up the program
        createFrameAndLabels();
        createControlPanel();
    } //Starts the whole program

    public static boolean initializeBooleans() {
        for (int rowNum = 0; rowNum < boardBooleans.length; rowNum++) { //for every row in the boolean array
            for (int columnNum = 0; columnNum < boardBooleans[rowNum].length; columnNum++) { //for every column in the boolean array
                boardBooleans[rowNum][columnNum] = false; //sets the boolean in the index to false
            }
        }
        return (true); //return back to the main method 
    } //boolean method to set all the booleans of the board to false

    public static boolean createFrameAndLabels() {
        JFrame boardFrame = new JFrame("Game of life"); //Creates the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the monitor
        int width = (int) screenSize.getWidth(); //sets int width to the width of the screen
        int height = (int) screenSize.getHeight(); //sets int height to the height of the screen
        boardFrame.setSize(width, height); //sets the size of the frame  
        boardFrame.setResizable(false); //makes it so that the frame isnt resizable
        boardFrame.setVisible(true); //sets the frame to make it visible
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets the frame so the program stops when its closed 
        boardFrame.setLayout(new GridLayout(50, 50)); //Creates a grid layout the same size as the grid for the game of life 
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for lop for the length of the board 
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) { //nested for loop for the length of each row of the boar 
                board[rowNum][columnNum] = new JLabel(); //Initailizes the jlabel
                Border blackline = BorderFactory.createLineBorder(Color.black); //creates a black border with border factory
                board[rowNum][columnNum].setBorder(blackline); //adds the black border to each label
                boardFrame.add(board[rowNum][columnNum]); //Adds each jlabel to the frame, each jlabel is added in sequence of the grid layout so it's already organized 
                board[rowNum][columnNum].setOpaque(true); //sets the opaqueness of each jlabel to true, this is so the color of the jlabel can be seen to be changed 
                final int tmp1 = rowNum; //Creates a final int of the row and column index 
                final int tmp2 = columnNum; //This needs to be done to change the background color of the jlabel with the mouse listener 
                board[rowNum][columnNum].addMouseListener(new MouseAdapter() { //Adds mouselistener to each jlabel
                    public void mousePressed(MouseEvent e) { //When the certain jlabel is pressed
                        if (boardBooleans[tmp1][tmp2] == false) { //If it previously wasn't checked
                            board[tmp1][tmp2].setBackground(Color.BLUE); //Changes the background color
                            boardBooleans[tmp1][tmp2] = true; //sets it to checked 
                        } else if (boardBooleans[tmp1][tmp2] == true) {//if it previously was checked 
                            board[tmp1][tmp2].setBackground(Color.WHITE); //Changes the background color back to white 
                            boardBooleans[tmp1][tmp2] = false; //leaves it as false 
                        }
                    }
                });
            }
        }
        boardFrame.validate(); //validates the board so the grid properly loads 
        return (true); //returns the method 

    } //boolean method to set up the jframe and jlabels with mouselistener 

    public static void createControlPanel() {
        JFrame controlFrame = new JFrame("Control Panel"); //Creates jframe
        JButton exit = new JButton("Exit"); //Creates exit button
        exit.setBounds(10, 63, 100, 50); //coordinates and size of exit button
        exit.addActionListener(new exitAction()); //Action listener for exit button
        JButton nextGen = new JButton("Next Gen"); //creates next gen button 
        nextGen.setBounds(130, 63, 100, 50); //coordinates and size of next gen button
        nextGen.addActionListener(new nextGenAction()); //Action listener for next gen button
        //The start button is already declared in public
        start.addActionListener(new manualStartAction());//Action listener for manual start button
        start.setBounds(250, 63, 100, 50); //coordinates and dimensions of manual start button
        delayValueModifier.setBounds(320, 20, 30, 30); //Sets the bounds of the modifier to change the speed of the manual start option 
        JLabel info = new JLabel("<html>Scroll to modify speed <br/>of manual simulation</html>", SwingConstants.CENTER); //Jlabel with html to label the jspiner 
        info.setBounds(185, 18, info.getPreferredSize().width, info.getPreferredSize().height); //Sets the bounds of the info jlabel 
        JLabel info2 = new JLabel("Custom Board Construction"); //jlabel for info for the combobox
        info2.setBounds(10, 0, info2.getPreferredSize().width, info2.getPreferredSize().height); //sets the bounds of the info2 jlabel
        customOptions.addActionListener(new optionBoxChoices()); //Adds the action listener for the combobox options
        customOptions.setBounds(10, 20, 100, 20); //sets the bounds of the jcombo box
        controlFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Makes it unable to be closed 
        controlFrame.setSize(365, 150); //sets the size
        controlFrame.setAlwaysOnTop(true); //keeps the control panel always on top 
        controlFrame.setResizable(false); //makes it unable to be resized \
        controlFrame.setLayout(null); //Sets a null frame layout
        controlFrame.add(exit); //Adds all the buttons, jlabels, jspinner and jcomboboxes to the control panel
        controlFrame.add(nextGen);
        controlFrame.add(start);
        controlFrame.add(delayValueModifier);
        controlFrame.add(info);
        controlFrame.add(customOptions);
        controlFrame.add(info2);
        controlFrame.setVisible(true); //sets it visible to true
    } //method to create the control panel

    static class exitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0); //If the button is clicked, then the program exits 
        }
    } //Action listener class for the exit button

    static class nextGenAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dontDoIt = false; //Declares this easter egg boolean as false 
            for (int rowNum = 0; rowNum < board.length; rowNum++) {
                for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                    if ((columnNum - 1) < 0) { //Check for the area to the left of the current cell
                        //An initial check is done to see of the spot exists and is a part of the array
                        //If it doesn't then nothing happens, and the next check takes place
                    } else { //If the spot does exist
                        if (boardBooleans[rowNum][columnNum - 1] == true) { //Then it checks for another cell in that certain area 
                            cellCount++; //If the spot exists and is occupied by a cell, 1 is added to the cell count 
                            //at the end of the stage the cell count is used to determine the future of the area that was checked
                        }
                    }//This process is repeated for every spot in radius = 1 from the current cell
                    if ((columnNum - 1) < 0 || (rowNum - 1) < 0) { //Check for the area to the top left of the current cell
                    } else {
                        if (boardBooleans[rowNum - 1][columnNum - 1] == true) {
                            cellCount++;
                        }
                    }
                    if ((rowNum - 1) < 0) {//Check for the area above the current cell
                    } else {
                        if (boardBooleans[rowNum - 1][columnNum] == true) {
                            cellCount++;
                        }
                    }
                    if ((rowNum - 1) < 0 || (columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the top right of the current cell
                        //Do nothing   
                    } else {
                        if (boardBooleans[rowNum - 1][columnNum + 1] == true) {
                            cellCount++;
                        }
                    }
                    if ((columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the right of the current cell
                        //Do nothing   
                    } else {
                        if (boardBooleans[rowNum][columnNum + 1] == true) {
                            cellCount++;
                        }
                    }
                    if ((rowNum + 1) > board.length - 1 || (columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the bottom right of the current cell
                        //Do nothing    
                    } else {
                        if (boardBooleans[rowNum + 1][columnNum + 1] == true) {
                            cellCount++;
                        }
                    }
                    if ((rowNum + 1) > board.length - 1) { //Check for the area below the current cell
                        //Do nothing 
                    } else {
                        if (boardBooleans[rowNum + 1][columnNum] == true) {
                            cellCount++;
                        }
                    }
                    if ((rowNum + 1) > board.length - 1 || (columnNum - 1) < 0) { //Check for the area to the bottom left of the current cell
                        //Do nothing   
                    } else {
                        if (boardBooleans[rowNum + 1][columnNum - 1] == true) {
                            cellCount++;
                        }
                    }

                    if (boardBooleans[rowNum][columnNum] == false) { //Checks if the cell was previously unoccupied
                        if (cellCount == 3) { //If it was and it was surrounded by 3 living cells
                            boardBooleanTransfer[rowNum][columnNum] = true; //sets its spot in the transfer boolean array to true
                        } else if (cellCount != 3) { //If it wasn't surrounded by 3 living cells
                            boardBooleanTransfer[rowNum][columnNum] = false; //sets its spot in the transfer boolean array to false 
                        }
                    } else if (boardBooleans[rowNum][columnNum] == true) { //If the area was occupied by a living cell 
                        if (cellCount == 0 || cellCount == 1) { //Checks if it was surrounded by 1 or no cells 
                            boardBooleanTransfer[rowNum][columnNum] = false; //if so, sets its spot in the transfer boolean array to false
                        } else if (cellCount >= 4) { //Check if it was surrounded by 4 or more cells
                            boardBooleanTransfer[rowNum][columnNum] = false; //If so sets its spot in the transfer boolean array to false
                        } else if (cellCount == 2 || cellCount == 3) { //Check if it was surrounded by exactly 2 or 3 cells 
                            boardBooleanTransfer[rowNum][columnNum] = true; //If so sets its spot in the transfer boolean array to true
                        }
                    }
                    cellCount = 0; //rests cell count for each area
                }
            }
            booleanArrayTransfer(); //Calls the array transfer method 
            changeCellStatus(); //Calls the change cell status 

            for (int rowNum = 0; rowNum < board.length; rowNum++) { //A check for an easter egg
                for (int columnNum = 0; columnNum < board.length; columnNum++) {
                    if (boardBooleans[rowNum][columnNum] == true) {
                        dontDoIt = true;
                    } else {
                        //Do nothing
                    }
                }
            }
            if (dontDoIt == false) { //the product of that easter egg
                int random = (int) (Math.random() * ((2 - 1) + 1) + 1);
                if (random == 1) {
                    try {
                        Desktop.getDesktop().browse(new URL("https://www.math.princeton.edu/sites/default/files/styles/portrait/public/2017-03/conway.jpg?itok=SIuqZXe2").toURI()); //Opens a url
                    } catch (Exception f) { //catch exception to open url
                        f.printStackTrace();
                    }
                } else if (random == 2) {
                    try {
                        Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI()); //Opens a url
                    } catch (Exception f) { //catch exception to open url
                        f.printStackTrace();
                    }
                }

            }

        }
    } //Action listener class for the next gen button

    static class manualStartAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (startCondition == false) { //If the start conditon is false 
                startCondition = true; //Set it to true 
                start.setText("Pause"); //Set the text of the button as "Pause"
            } else if (startCondition == true) { //If the start condition is false
                startCondition = false; //sets the condition back to false 
                start.setText("Start"); //Sets the text of the button back to start
            }
            Thread constantThread = new Thread() {//Creates a new thread 
                public void run() { //When the thread is running 
                    for (;;) { //For infinity 
                        if (startCondition == true) { //If the start condition is true 
                            try { //try condition for thread.sleep
                                int delay = (int) delayValueModifier.getValue();
                                sleep(1000 / delay); //Delays the process for 1 second 
                                for (int rowNum = 0; rowNum < board.length; rowNum++) { //This is the same code as the one found in the next gen action class 
                                    for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                                        if ((columnNum - 1) < 0) { //Check for the area to the left of the current cell
                                            //An initial check is done to see of the spot exists and is a part of the array
                                            //If it doesn't then nothing happens, and the next check takes place
                                        } else { //If the spot does exist
                                            if (boardBooleans[rowNum][columnNum - 1] == true) { //Then it checks for another cell in that certain area 
                                                cellCount++; //If the spot exists and is occupied by a cell, 1 is added to the cell count 
                                                //at the end of the stage the cell count is used to determine the future of the area that was checked
                                            }
                                        }//This process is repeated for every spot in radius = 1 from the current cell
                                        if ((columnNum - 1) < 0 || (rowNum - 1) < 0) { //Check for the area to the top left of the current cell
                                        } else {
                                            if (boardBooleans[rowNum - 1][columnNum - 1] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((rowNum - 1) < 0) {//Check for the area above the current cell
                                        } else {
                                            if (boardBooleans[rowNum - 1][columnNum] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((rowNum - 1) < 0 || (columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the top right of the current cell
                                            //Do nothing   
                                        } else {
                                            if (boardBooleans[rowNum - 1][columnNum + 1] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the right of the current cell
                                            //Do nothing   
                                        } else {
                                            if (boardBooleans[rowNum][columnNum + 1] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((rowNum + 1) > board.length - 1 || (columnNum + 1) > board[rowNum].length - 1) { //Check for the area to the bottom right of the current cell
                                            //Do nothing    
                                        } else {
                                            if (boardBooleans[rowNum + 1][columnNum + 1] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((rowNum + 1) > board.length - 1) { //Check for the area below the current cell
                                            //Do nothing 
                                        } else {
                                            if (boardBooleans[rowNum + 1][columnNum] == true) {
                                                cellCount++;
                                            }
                                        }
                                        if ((rowNum + 1) > board.length - 1 || (columnNum - 1) < 0) { //Check for the area to the bottom left of the current cell
                                            //Do nothing   
                                        } else {
                                            if (boardBooleans[rowNum + 1][columnNum - 1] == true) {
                                                cellCount++;
                                            }
                                        }

                                        if (boardBooleans[rowNum][columnNum] == false) { //Checks if the cell was previously unoccupied
                                            if (cellCount == 3) { //If it was and it was surrounded by 3 living cells
                                                boardBooleanTransfer[rowNum][columnNum] = true; //sets its spot in the transfer boolean array to true
                                            } else if (cellCount != 3) { //If it wasn't surrounded by 3 living cells
                                                boardBooleanTransfer[rowNum][columnNum] = false; //sets its spot in the transfer boolean array to false 
                                            }
                                        } else if (boardBooleans[rowNum][columnNum] == true) { //If the area was occupied by a living cell 
                                            if (cellCount == 0 || cellCount == 1) { //Checks if it was surrounded by 1 or no cells 
                                                boardBooleanTransfer[rowNum][columnNum] = false; //if so, sets its spot in the transfer boolean array to false
                                            } else if (cellCount >= 4) { //Check if it was surrounded by 4 or more cells
                                                boardBooleanTransfer[rowNum][columnNum] = false; //If so sets its spot in the transfer boolean array to false
                                            } else if (cellCount == 2 || cellCount == 3) { //Check if it was surrounded by exactly 2 or 3 cells 
                                                boardBooleanTransfer[rowNum][columnNum] = true; //If so sets its spot in the transfer boolean array to true
                                            }
                                        }
                                        cellCount = 0; //rests cell count for each area
                                    }
                                }
                                booleanArrayTransfer(); //Calls the array transfer method 
                                changeCellStatus(); //Calls the change cell status method
                            } catch (InterruptedException ex) { // catch for thread.sleep
                                
                            }
                        }
                    }
                }
            };
            constantThread.start(); //Starts the thread
        }
    } //Action listener class for the manual start button

    static class optionBoxChoices implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = customOptions.getSelectedIndex();
            if (choice == 0) { //This choice fills the whole board 
                for (int rowNum = 0; rowNum < board.length; rowNum++) { //For loops
                    for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                        board[rowNum][columnNum].setBackground(Color.BLUE); //Change all the colors
                        boardBooleans[rowNum][columnNum] = true; //change the board booleans 
                    }
                }
            } else if (choice == 1) { //This choice clears the array
                for (int rowNum = 0; rowNum < board.length; rowNum++) {
                    for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                        board[rowNum][columnNum].setBackground(Color.WHITE); //change all the colors to white  
                        boardBooleans[rowNum][columnNum] = false; //changes all the board booleans
                    }
                }
            } else if (choice == 2) { //This choice randomly fills the array 
                int randomSelection = 0; //int made as the random selection number
                for (int rowNum = 0; rowNum < board.length; rowNum++) {// for every row in the board 
                    for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) { //For every column in every row of the board 
                        randomSelection = (int) (Math.random() * ((2 - 1) + 1) + 1); //pick a random int from 1 to 2
                        if (randomSelection == 1) { //if the random int is 1
                            board[rowNum][columnNum].setBackground(Color.BLUE); //sets its color to blue 
                            boardBooleans[rowNum][columnNum] = true; //sets its corresponding boardboolean to true 
                        } else if (randomSelection == 2) { //else if the random int is 2
                            board[rowNum][columnNum].setBackground(Color.WHITE); //sets its color to white 
                            boardBooleans[rowNum][columnNum] = false;//sets it board boolean to false
                        }
                    }
                }
            }
        }
    }

    public static boolean booleanArrayTransfer() {
        for (int rowNum = 0; rowNum < boardBooleans.length; rowNum++) { //for every row in the board boolean array 
            for (int columnNum = 0; columnNum < boardBooleans[rowNum].length; columnNum++) { //for the length of each row in the board boolean array 
                boardBooleans[rowNum][columnNum] = boardBooleanTransfer[rowNum][columnNum]; //Change the status of that index to the stauts of the transfer boolean  
            }
        }
        return (true); //Returns the code to where it was called in the action listener
    } //A boolean method to transfer the data from the boolean holding array to the actual boolean array that is used for the board 

    public static boolean changeCellStatus() {
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for each row in the board
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) { //For each row in the column in the board 
                if (boardBooleans[rowNum][columnNum] == true) { //If the boolean array is true 
                    board[rowNum][columnNum].setBackground(Color.BLUE); //Change the color of the corresponding jlabel to blue 
                } else if (boardBooleans[rowNum][columnNum] == false) { //If the boolean array is true 
                    board[rowNum][columnNum].setBackground(Color.WHITE); //Change the color of the corresponding jlabel to white 
                }
            }                                                     //nice
        }
        return (true); //returns the method
    } //Method for the actions to change a cell based on the status stored in the corresponding array of the boardboolean transfer array 

}
