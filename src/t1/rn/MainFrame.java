/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1.rn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author 1511 IRON
 */
public class MainFrame extends JPanel{

    JFrame j;
    JTextArea image;
    JLabel error;
    JLabel currentNumber;
    JLabel aux;
    JButton button;

    public void showMainFrame() {
        j = new JFrame("ICMC-USP - T1 - Neural Networks - Adriller Ferreira");
        j.setSize(450, 250);
        j.setLayout(new BorderLayout());
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        image = new JTextArea("matrix goes here");
        image.setSize(10, 10);
        j.add(image, BorderLayout.CENTER);

        error = new JLabel("Error rate: ");
        currentNumber = new JLabel();
        j.add(error, BorderLayout.PAGE_END);
        j.add(currentNumber, BorderLayout.PAGE_START);
        

        aux = new JLabel();
        j.add(aux, BorderLayout.LINE_START);
        
        button = new JButton("Guess number!");
        j.add(button, BorderLayout.LINE_END);
        int[] inputs; //represents the vector input
        int errors = 0, count = 0, hits = 0; //total errors
        j.setVisible(true);

        //sizes of vectors
        int sizeI = 101;
        int sizeH = 501;
        int sizeO = 10;

        NeuralNetwork NN = new NeuralNetwork(sizeI, sizeH, sizeO);
        Input in = new Input();
        int number = 0;
        NN.readWeights();

        button.addActionListener(testNN(currentNumber, error, image, in, NN));

        /*
        for (int time = 0; time < 1000; time++) {
            for (int i = 0; i < 10; i++) {
                int[][] matrix = in.readInputMatrix("number" + Integer.toString(i) + ".txt");
                inputs = in.getInputvector(matrix);
                currentNumber.setText("Current Number = " + Integer.toString(i));
                image.setText(in.toString(matrix));
                boolean keepInput = true;
                count++;
                NN.setInputs(inputs);
                NN.setDesired(i);
                NN.calcHiddenLayer();
                NN.calcOutputLayer();
                int awnser = NN.awnser();
                if (awnser != i) {
                    errors++;
                    NN.rectifyWeigths2();
                    NN.rectifyWeigths1();
                } else {
                    hits++;
                    System.out.println("correct for " + i);
                }
                currentNumber.setText("   Current Number = " + Integer.toString(i));
                error.setText("      Errors: " + Double.toString(errors) + "      Hits: " + Double.toString(hits) + "        Awnser for currently input: : " + Integer.toString(awnser));
            }
        }
        error.setText("    Errors: " + Double.toString(errors) + "   Hits: " + Double.toString(hits) + "    Error Rate: " + (double)errors/count);
        //NN.saveWeights();
         */
    }

    public ActionListener testNN(JLabel currentNumber, JLabel error, JTextArea image, Input in, NeuralNetwork NN) {
        ActionListener temp = null;
        temp = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("text = " + image.getText() + "size " + image.getText().length());
                int[][] matrix = in.toInteger(image.getText());
                int[] inputs = in.getInputvector(matrix);
                transformInImage(in.invertMatrix(matrix));
                NN.setInputs(inputs);
                NN.calcHiddenLayer();
                NN.calcOutputLayer();
                int awnser = NN.awnser();
                currentNumber.setText("");
                error.setText("                 Awnser for currently input: : " + Integer.toString(awnser));
            }
        };
        return temp;
    }

    public void transformInImage(int[][] matrix) {
        try {
            String path = "test.jpg";
            BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    int value;
                    if (matrix[x][y] == 0) {
                        value = new Color(255, 255, 255).getRGB();
                    } else {
                        value = new Color(0, 0, 0).getRGB();
                    }
                    image.setRGB(x, 9-y, value);
                }
            }
            ImageIcon x = new ImageIcon(image);
            Image image2 = x.getImage(); // transform it 
            Image newimg = image2.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            x = new ImageIcon(newimg);  // transform it back
            aux.setIcon(x);
            
            File ImageFile = new File(path);
            try {
                ImageIO.write(image, "jpg", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("impossible turn into a image");
        }
    }

    
}
