/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :ComputeServer.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class ComputeServer {

    private static int serverPort;

    public static void main(String args[]) throws NoSuchAlgorithmException {
        try {
            int serverPort = 8888;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Compute Server running...");
            //build keypair
            KeyPair keyPair = Cryptography.buildKeyPair();
            PublicKey pubKey = keyPair.getPublic();
            PrivateKey prKey = keyPair.getPrivate();
            while (true) {
                Socket clientSocket = listenSocket.accept();
                // creates a new thread to deal with each client
                //multithreading
                Connection c = new Connection(clientSocket, pubKey, prKey);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

class Connection extends Thread {

    ObjectOutputStream out;
    ObjectInputStream in;
    Socket clientSocket;
    PublicKey pubKey;
    PrivateKey privateKey;
    DatabaseConnection db;

    public Connection(Socket aclientSocket, PublicKey pbKey, PrivateKey prKey) {
        try {
            clientSocket = aclientSocket;
            //ready to recieve input from client in the stream
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            pubKey = pbKey;
            privateKey = prKey;
            db = new DatabaseConnection("jdbc:mysql://localhost:3306/mysql");//database connection
            this.start();
            //write public key
            out.writeObject(pubKey);
        } catch (Exception ex) {
            System.out.println("Connection:" + ex.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                //read object from the database
                Object obj = in.readObject();
                PrintWriter replyMessage = new PrintWriter(out, true);
                //recieve userid and password from the client
                if (obj instanceof byte[]) {
                    //decrypt the string message
                    String message = new String(Cryptography.decrypt(privateKey, (byte[]) obj));
                    //split the String message with :
                    String[] w = message.split(":");
                    String user = w[0]; //store user in w[0]
                    String password = w[1];//store user password in w[1]
                    //check if user correct
                    if (db.checkUser(user)) {
                        if (db.checkPassword(user, password)) {
                            out.writeObject("**********Success*********");
                        } else {
                            out.writeObject("**********Invalid Password*********");
                            //invalid password
                        }
                    } else {
                        //invalid user
                        out.writeObject("**********Invalid User*********");
                    }
                }
                //receive request from the user/client
                if (obj instanceof Fibonacci) {//user want to execute Fibonacci task
                    Task fibonacciTask = (Task) obj;//call the method from the interface
                    fibonacciTask.executeTask();
                    System.out.println("Fibonacci task received and Computed");
                    out.writeObject(fibonacciTask);
                }
                if (obj instanceof Factorial) {
                    Task factorialTask = (Task) obj;
                    factorialTask.executeTask();
                    System.out.println("Factorial task received and Computed");
                    out.writeObject(factorialTask);
                }
                if (obj instanceof Gcd) {
                    Task GcdTask = (Task) obj;
                    GcdTask.executeTask();
                    System.out.println("Gcd task received and Computed");
                    out.writeObject(GcdTask);
                }
            }
        } catch (Exception e) {
            System.out.println("Exeption:" + e.getMessage());
        }
    }

}
