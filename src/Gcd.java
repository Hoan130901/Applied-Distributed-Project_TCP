/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :Gcd.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */

import java.io.Serializable;

public class Gcd implements Task, Serializable {
    int n1;
    int n2;
    String result;
    //constructor
    public Gcd(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }
    //getter and setter
    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }
    //implement methods from Task interface
    

    @Override
    public void executeTask() {//method to calculate gcd sequence
        int gcd = 1;
        for (int i =1; i <= n1 && i <= n2; i++){
            if(n1 % i == 0 && n2 % i ==0){
                gcd =i;
            }
        }
        //format the result
        result = String.format("GCD of given numbers is: %d", gcd);
    }
    @Override
    public String getResult() {//return result method
        return result;
    }
    
}
