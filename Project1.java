/*
 * Project1.java
 * 01 September 2020
 * Jonathan Mainhart - CMIS 242
 * Compute the minimum, maximum, and average of a collection of weights read 
 * from an input file. The weights will be in pounds and ounces.
 */
package project1;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author jonmainhart
 */
public class Project1 {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        final int MAXOBJECTS = 25;
        ArrayList<Weight> weightsList = new ArrayList<>();
        Weight[] weightsArray;
        int lineCount = 0;

        // JFileChooser block 
        JButton open = new JButton(); // object to connect JFileChooser to
        JFileChooser fc = new JFileChooser(); //uses JFileChooser to select file
        fc.setCurrentDirectory(new File(".")); // set to working directory
        fc.setDialogTitle("Select a File Containing Weights");
        int returnValue = fc.showOpenDialog(open);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            //  GUI file chooser will try to open user selected file
            File selectedfile = fc.getSelectedFile();

            String lineInput;
            BufferedReader inputReader = null;

            try {
                /**
                 * Uses BufferedReader and FileReader to read the file line by
                 * line and parses comma separated values into an array of type
                 * String. The strings are parsed to type Double and used as
                 * arguments to build Weight() objects to fill the weightsList
                 * as long as the size of the ArrayList is less than MAXOBJECTS.
                 */
                inputReader = new BufferedReader(new FileReader(selectedfile));

                while ((lineInput = inputReader.readLine()) != null) {

                    if (lineCount < MAXOBJECTS) {
                        // splits line input into separate elements in an array
                        String[] inputStr = lineInput.split(",");
                        // parses input from String to double
                        double lbs = Double.parseDouble(inputStr[0]);
                        double oz = Double.parseDouble(inputStr[1]);

                        weightsList.add(new Weight(lbs, oz)); // constructs new object and adds to array
                        lineCount++; // increment line counter

                    } else {
                        System.out.println("Input exceeds maximum of "
                                + MAXOBJECTS + " weights. Exiting...");
                        System.exit(0);
                    }

                }
            } catch (FileNotFoundException fnf) {

                System.out.println(fnf + "\nExiting...");
                System.exit(0);

            } catch (IOException iox) {

                System.out.println(iox + "\nExiting...");
                System.exit(0);

            } finally { // closes the file
                try {
                    if (inputReader != null) {
                        inputReader.close();
                    }
                } catch (IOException iox) {
                    System.out.println("Something went wrong while trying to close the file: "
                            + iox.getMessage());
                } // end ioException

            } // end finally  

        } // end JFileChooser

        /*
         * Copy the ArrayList into an Array of Weights. Preserves funtionality of
         * existing methods while preventing any Out Of Bounds errors or null
         * reference pointers.
         */
        weightsArray = weightsList.toArray(new Weight[lineCount]);

        System.out.println("There are " + weightsArray.length + " objects"); // Verify the input

        for (Weight weightsArray1 : weightsArray) {
            System.out.println(weightsArray1.toString());
        }

        Weight min = findMinimum(weightsArray, weightsArray.length);
        System.out.println("Minimum weight: " + min.toString()); // verify .toString

        System.out.println("Maximum Weight: " + findMaximum(weightsArray, weightsArray.length));

        System.out.println("Average Weight: " + findAverage(weightsArray, weightsArray.length));

    } // end main()  

    private static Weight findMinimum(Weight[] arr, int numOfObj) {
        /*
         * Finds the minimum weight and returns an object of type Weight
         */
        Weight returnVal = arr[0];
        for (int i = 0; i < numOfObj; i++) {
            Weight myVal = arr[i];
            if (myVal.lessThan(returnVal)) { // if the value of myVal is less than the returnVal
                returnVal = myVal; // returnVal chages to the lesser value. 
            } // do this until all values are compared
        }
        return returnVal;

    }

    private static Weight findMaximum(Weight[] arr, int numOfObj) {
        Weight returnVal = arr[0];
        for (int i = 0; i < numOfObj; i++) {
            Weight myVal = arr[i];
            if (!myVal.lessThan(returnVal)) { // works exactly like minValue
                returnVal = myVal; // but chooses the larger value
            }
        }
        return returnVal;
    }

    private static Weight findAverage(Weight[] arr, int numOfObj) {
        // create a temp Weight obj
        Weight temp = new Weight(0.0, 0.0);
        // add each array obj value to temp
        for (Weight weight : arr) {
            temp.addTo(weight);
        }
        // divide temp by number of objects in array to get average
        temp.divide(numOfObj);
        // return temp Weight
        return temp;
    }

} // end Project1
