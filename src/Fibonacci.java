/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :Fibonacci.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fibonacci implements Task, Serializable {

    int num;
    String result;
    //constructpr
    public Fibonacci(int num) {
        this.num = num;
    }
    //getter and setter
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    //implement methods from Task interface
    @Override
    public void executeTask() {//method to calculate fibonacci sequence
       int num1= 0;
       int num2 =1;
       int num3;
       if (num==1){
           result =num2+" + ";
       }
       else if (num==2){
           num3 = num1+num2;
           result = num2+" + "+num3+" + ";
       }
       
       else{
           StringBuilder sb = new StringBuilder();
           for(int i =2; i<num;++i){
           num3 = num1+num2;
           String result2 = " + "+num3;
           num1 =num2;
           num2 = num3;
           sb.append(result2);
           }
           result = "0 + 1"+sb.toString();
       }  
    }
    @Override
    public String getResult() {
        return result;
    }
}
