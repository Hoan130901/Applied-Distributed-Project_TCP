/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :Factorial.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */
import java.io.Serializable;
import static java.time.Clock.system;

public class Factorial implements Task, Serializable {

    int n;
    String result;

    //constructor
    public Factorial(int n) {
        this.n = n;
    }

    //getter and setter
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void main(String args[]) {

    }

    //implement methods from Task interface
    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void executeTask() {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        result = String.format("Factorial of %d = %d", n, fact);
    }
}
