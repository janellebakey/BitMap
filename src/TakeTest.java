import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/*
    Southern Oregon University - CS258 Computer Science III - Lab 3

    Author: Janelle Bakey
    Date: 05/05/2017
    Class: TakeTest.java
    Desc: This program administers a test to the user and saves the user’s
    answers into a BitMap object. It then reads the key file (using an ObjectInputStream)
    and compares the answers the user gave to the answers in the key. It then displays
    the score earned by the test taker.

 */
public class TakeTest {
    public static void main(String args[]) {
        boolean[] userInput;
        BitMap key;
        BitMap userAnswers;
        String fileName = "Lab3Questions.txt";
        try {
            BufferedReader question= new BufferedReader(new FileReader(fileName)); //open file containing questions
            ArrayList<String> list = new ArrayList<>();
            do{list.add(question.readLine());
            }while(question.ready());
            question.close();
            BufferedReader answer = new BufferedReader(new InputStreamReader(System.in));
            userInput = new boolean[list.size()];
            char a;
            int i = 0;
            while(i<list.size()){ //while there are more questions
                System.out.println(list.get(i) + " (t)rue or (f)alse: ");
                try {a = answer.readLine().charAt(0); //check user answers and store in array list
                    userInput[i] = checkAnswer(a);
                    i++;
                } catch (IOException e) {System.out.println("Illegal answer. Please answer (t)rue or (f)alse.");}
            }
            userAnswers = new BitMap(userInput); //new Bitmap object with user answers
            answer.close();
            fileName = fileName.replace("txt", "bin");
            File keyFile = new File(fileName); //Binary file with answers
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(keyFile)));
            key = (BitMap)in.readObject();
            int score = 0;
            if(userAnswers.equals(key)){ //if Bitmap objects return perfect score
                score = list.size();
            }
            else{ //otherwise add 1 to score for every bit that is matched in both objects
                for(int j=0; j <list.size(); j++) {
                    if(userAnswers.checkBit(j) && key.checkBit(j)) {score = score +1;}
                    else if(!userAnswers.checkBit(j) && !key.checkBit(j)){score = score +1;}
                }
            }
            System.out.println("\nYour test is now complete.\n Your score is " + score + "/" + list.size());
            in.close();
        }
        catch (FileNotFoundException e){System.out.println("File Not Found Exception"); e.printStackTrace();}
        catch (IOException e){System.out.println("I/O Exception"); e.printStackTrace();}
        catch (ClassNotFoundException e){System.out.println("Class Not Found Exception"); e.printStackTrace();}
    }
    private static boolean checkAnswer(char c) throws IOException{
        if(c == 'f' || c == 'F'){return false;}
        else if(c == 't' || c == 'T'){return true;}
        else{ throw new IOException();}
    }
}
