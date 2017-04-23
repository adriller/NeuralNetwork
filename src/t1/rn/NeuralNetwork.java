/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1.rn;

import static java.lang.Math.exp;
import java.util.Random;

/**
 *
 * @author 1511 IRON
 */
public class NeuralNetwork {

    //the maps bellow represents the tree vectors: input layer, hidden layer and output layer
    int[] inputs;
    double[] hidden;
    double[] output;

    //sizes of vectors
    int sizeI;
    int sizeH;
    int sizeO;

    //weights matrixes. weights1 is the weights between I->H and weights2 is the weights between H->O
    double[][] weights1;
    double[][] weights2;

    int desired;
    double eta = 0.35;

    public NeuralNetwork(int sI, int sH, int sO) {
        sizeI = sI;
        sizeH = sH;
        sizeO = sO;
        hidden = new double[sH];
        hidden[0] = 1;
        output = new double[sO];
        weights1 = initWeights(sizeI, sizeH);
        weights2 = initWeights(sizeH, sizeO);
    }

    public void setInputs(int[] i) {
        this.inputs = i;
        inputs[0] = 1;
    }
    
    public void setDesired(int d){
        this.desired = d;
    }

    private double[][] initWeights(int sizex, int sizey) {
        double[][] weights = new double[sizex][sizey];
        for (int i = 0; i < sizex; i++) {
            for (int j = 1; j < sizey; j++) {
                Random generator = new Random();
                double number = generator.nextDouble()/5 - 0.1; // [0,2[
                weights[i][j] = number;
            }
        }
        
        return weights;
    }

    //calculate values of hidden[0] ... hidden[h] that is sum(weights1[i][h] * Ii)
    public void calcHiddenLayer() {
        //for each hidden h
        for (int h = 1; h < sizeH; h++) {
            //for each input i
            double sum = 0;
            for (int i = 0; i < sizeI; i++) {
                sum += weights1[i][h] * inputs[i];
            }
            hidden[h] = sum;
        }
        hidden[0]=1;
        ActivationFunction();
    }

    //normalize values of hidden to become 0 or 1
    public void ActivationFunction() {
        for (int h = 1; h < sizeH; h++) {
            hidden[h] = 1 / (1 + exp(-1*hidden[h]));
            /*if (hidden[h] >= 0) {
                hidden[h] = 1;
            } else {
                hidden[h] = 0;
            }*/
        }
    }
    
    public void OutputActivationFunction() {
        for (int o = 0; o < sizeO; o++) {
            output[o] = 1 / (1 + exp(-1*output[o]));
            /*if (output[o] >= 0) {
                output[o] = 1;
            } else {
                output[o] = 0;
            }*/
        }
    }

    //calculate values of output[0] ... hidden[o] that is sum(weights2[h][o] * Hh)
    public void calcOutputLayer() {
        //for each output o
        for (int o = 0; o < sizeO; o++) {
            //for each hidden h
            double sum = 0;
            for (int h = 0; h < sizeH; h++) {
                sum += weights2[h][o] * hidden[h];
            }
            output[o] = sum;
        }
        OutputActivationFunction();
    }

    public void rectifyWeigths2() {
        //for each output o
        for (int o = 0; o < sizeO; o++) {
            //for each hidden h
            double error = calcError2(o);
            for (int h = 0; h < sizeH; h++) {
                weights2[h][o] += eta * error * hidden[h];
            }
        }
    }

    public double calcError2(int index) {
        double error = 0;
        double d = index == desired ? 1 : 0;
        error = output[index] * (1 - output[index]) * (d - output[index]);
        return error;
    }

    public void rectifyWeigths1() {
        for (int h = 1; h < sizeH; h++) {
            double error = calcError1(h);
            for (int i = 0; i < sizeI; i++) {
                weights1[i][h] += eta * error * inputs[i];
            }
        }
    }

    public double calcError1(int index) {
        double error = 0;
        double sum = 0;
        for(int o = 0 ; o < sizeO ; o++){
            sum += calcError2(o) * weights2[index][o];
        }
        error = hidden[index] * (1 - hidden[index]) * sum;
        return error;
    }
    
    public int awnser(){
        double greater = Double.MIN_VALUE;
        int indexGreater = 0;
        for(int o = 0 ; o < sizeO ; o++){
            if(output[o] > greater){
                greater = output[o];
                indexGreater = o;
            }
        }
        return indexGreater;
    }

    public void saveWeights(){
        Input.writeMatrix("weights1.txt", weights1);
        Input.writeMatrix("weights2.txt", weights2);
        double[][] test = new double[][]{
            {1, 2, 4},
            {5, 5, 6},
            {8, 9, 10}
        };
        Input.writeMatrix("teste.txt", test);
    }
    
    public void readWeights(){
        double[][] test = Input.readWeightMatrix("teste.txt", 3, 3);
        weights1 = Input.readWeightMatrix("weights1.txt", sizeI, sizeH);
        weights2 = Input.readWeightMatrix("weights2.txt", sizeH, sizeO);
        
        /*for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                System.out.print(test[i][j] + " ");
            }
            System.out.println("");
        }*/
        
    }
    
    public void printWeight1(){
        for (int h = 1; h < sizeH; h++) {
            for (int i = 0; i < sizeI; i++) {
                System.out.println(weights1[i][h] + " ");
            }
            System.out.println("\n");
        }
    }
}
