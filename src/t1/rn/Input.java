/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1.rn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1511 IRON
 */
public class Input {

    Map<Integer, Double> inputs = new HashMap<>();

    public Map carregarInput(int i) {

        return inputs;
    }

    public static void writeMatrix(String filename, double[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    bw.write(matrix[i][j] + ((j == matrix[i].length - 1) ? "" : " "));
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
        }
    }

    public int[][] readInputMatrix(String filename) {
        int[][] matrix = new int[10][10];
        try {
            Scanner input = new Scanner(new File(filename));
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j) {
                    if (input.hasNextInt()) {
                        matrix[i][j] = input.nextInt();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matrix;
    }

    public int[] getInputvector(int[][] matrix) {
        int[] inputsA = new int[101];
        int count = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                inputsA[count] = matrix[i][j];
                count++;
            }
        }
        return inputsA;
    }

    public String toString(int[][] matrix) {
        StringBuilder sbResult = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sbResult.append(matrix[i][j]);
                sbResult.append("  ");
            }
            sbResult.append("\n");
        }

        return sbResult.toString();
    }

    public  int[][] toInteger(String matrix) {
        int[][] m = new int[10][10];
        int i = 0;
        for (int k = 0; k < 10; k++) {
            for(int j = 0 ; j < 10; ){
                if(i % 2 == 0){
                    m[k][j] = Character.getNumericValue(matrix.charAt(i));
                    //System.out.print(matrix.charAt(i) + " ");
                    j++;
                }
                i++;
            }
            //System.out.println("");
        }

        return m;
    }
    
    public static double[][] readWeightMatrix(String filename, int sizex, int sizey) {
        double[][] matrix = new double[sizex][sizey];
        try {
            Scanner input = new Scanner(new File(filename));
            for (int i = 0; i < sizex; i++) {
                String aux = input.nextLine();
                String delims = "[ ]";
                String[] tokens = aux.split(delims);
                for (int j = 0; j < tokens.length; j++) {
                        matrix[i][j] = Double.parseDouble(tokens[j]);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("impossible read weights");
        }

        return matrix;
    }

    public int[][] invertMatrix(int[][] m){
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
    
        for (int i = 0, i2 = temp.length-1; i < temp.length; i++, i2--)
            for (int j = 0, j2= temp[0].length-1; j < temp[0].length; j++, j2--)
                m[i][j] = temp[i][j2];
        
    return m;
    
    }
}
