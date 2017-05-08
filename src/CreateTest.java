/*
    Southern Oregon University - CS258 Computer Science III - Lab 3

    Author: Janelle Bakey
    Date: 05/05/2017
    Class: CreateTest.java
    Desc: This program constructs the answer key for a true or false test. It
    asks the user which file to open which should contain the true or false
    questions and then saves the users input as a key and writes this key to a
    sequential binary file with the same file name chosen by the user.

 */

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CreateTest {
    public static void main(String args[]) {
        boolean[] answerKey;
        BitMap key;
        String str = System.getProperty ("user.dir");
        JFileChooser chooser = new JFileChooser(str);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fileName = file.getName();
            System.out.println("You selected " + fileName);
            if (!new File(fileName).exists()) {
                System.out.println("File does not exist");
                System.exit(1);
            }
            try {
                //String s;
                BufferedReader question= new BufferedReader(new FileReader(fileName));
                ArrayList<String> list = new ArrayList<>();
                do{
                    list.add(question.readLine());
                }while(question.ready());
                question.close();
                BufferedReader answer = new BufferedReader(new InputStreamReader(System.in));
                answerKey = new boolean[list.size()];
                char a;
                int i = 0;
                while(i<list.size()){ //while there are more questions
                    System.out.println(list.get(i) + " (t)rue or (f)alse: ");
                        try {a = answer.readLine().charAt(0);
                            answerKey[i] = checkAnswer(a);
                            i++;
                        } catch (IOException e) {System.out.println("Illegal answer. Please answer (t)rue or (f)alse:.");}
                }
                answer.close();
                key = new BitMap(answerKey);
                fileName = fileName.replace("txt", "bin");
                File keyFile = new File(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(keyFile)));
                oos.writeObject(key);
                oos.close();
                System.out.println("\nTest key has been created.");
            }
            catch (FileNotFoundException e){System.out.println("File Not Found Exception");}
            catch (IOException e){System.out.println("I/O Exception");}
        } else {System.out.println("You cancelled the file dialog");}
    }
    private static boolean checkAnswer(char c) throws IOException{
        if(c == 'f' || c == 'F'){return false;}
        else if(c == 't' || c == 'T'){return true;}
        else{ throw new IOException();}
    }
}
