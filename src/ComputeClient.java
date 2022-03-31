

/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :ComputeClient.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.lang.String;
import java.net.*;
import java.io.*;
import java.util.*;

public class ComputeClient {

    private static int portNumber;
    private static String serverName;
    private static PublicKey pubKey;
   
    public static void main(String args[]) throws IOException, Exception {
        Socket s = null;

        try {
            // the port number the server listens at
            int serverPort = 8888;
            // bind the socket with the server name and port number
            s = new Socket("localhost", serverPort);
            //establish inout and output streams
            ObjectInputStream in = null;
            ObjectOutputStream out = null;

            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            String input = "";//variable to hold user input
            //Scanner instance assigned to receive user input
            Scanner sa = new Scanner(System.in);
            //read the public key
            PublicKey pubKey =(PublicKey)in.readObject();

            //client is engaged with the server until he chooses to exit
            while (true) {
                System.out.println("Enter User Name");
                String user = sa.nextLine();//get user name
                System.out.println("Enter User Password");
                String pass = sa.nextLine();//get user password
                String message = user + ":" + pass;//create a message like userID:userPassword
                //write message to the server
                out.writeObject(Cryptography.encrypt(pubKey, message));
                //read message sent by server
                Object obj = in.readObject();
                //print the message from the server
                System.out.println((String)obj);
                String successMsg = "**********Success*********";
                //if user authenticated, print math menu
                if(obj.equals(successMsg)){
                    while(!input.equalsIgnoreCase("exit")){
                        // math menu
                        System.out.println("PLEASE MAKE YOUR SELECTION "+user);
                        System.out.println("*******************");
                        System.out.println("1.Fibonacci");
                        System.out.println("2.Factorial");
                        System.out.println("3.Gcd");
                        System.out.println("4.Exit");
                        //get selection from user
                        int selection = sa.nextInt();
                        if(selection==1){
                            //user select Fibonacci
                            System.out.println("Enter the range limit");
                            int rangeFibon = sa.nextInt(); // get range limit
                            // create a new Task object to do the Fibonacci sequence
                            Task fibonacci = new Fibonacci(rangeFibon);
                            //write object to the server
                            out.writeObject(fibonacci);
                            //receive computed object from the server
                            fibonacci = (Fibonacci)in.readObject();
                            //get the result
                            System.out.println( ((Fibonacci)fibonacci).getResult());
                        }
                        if (selection == 2){
                            //user select Factorial
                            System.out.println("Enter the range limit");
                            //get range limit to do the math
                            int rangeFact = sa.nextInt();
                            //create a new Task object to do the Factorial math
                            Task factorial = new Factorial(rangeFact);
                            // wirte Task object to the server
                            out.writeObject(factorial);
                            //read(receive) obj from the server
                            factorial = (Task)in.readObject();
                            //print the result
                            System.out.println( ((Factorial)factorial).getResult());
                        }
                        if (selection == 3){
                            //user select Gcd
                            System.out.println("Enter number 1");
                            //get num 1 to do the math
                            int gcdNum1 = sa.nextInt();
                            System.out.println("Enter number 2");
                            //get num 2 to do the math
                            int gcdNum2 = sa.nextInt();
                            //create a new Task object to do the Gcd
                            Task gcd = new Gcd(gcdNum1,gcdNum2);
                            //send obj to the server
                            out.writeObject(gcd);
                            //read(receive) obj from the server 
                            gcd = (Task)in.readObject();
                            //print the computed result
                            System.out.println( ((Gcd)gcd).getResult());
                        }
                        if(selection==4){//user want to exit the program
                            //goodbye message
                            System.out.println("Thank you and goodbye "+user);
                            System.exit(0);//end program
                        }
                    }
                }
            }
            //exception handling
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (s != null) try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }

        }
    }
}
