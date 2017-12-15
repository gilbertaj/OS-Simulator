import java.io.*;
import java.util.*;

public class ReadIn{
    public static Scanner reader;
    public static ArrayList<String> testArray;


    public static void openFile(String job){


        try{
            reader = new Scanner(new File("Jobs\\" + job + ".txt"));
        }
        catch(Exception exc){
            System.out.println("why");}

    }


    public static void readFile(String job){
        testArray = new ArrayList<String>();
        while(reader.hasNext()){
            String a = reader.next();
            testArray.add(a);


        }
        for(int i=0; i < testArray.size(); i++){
            String value = testArray.get(i);
            System.out.println(value);

        }

    }

    public static void closeFile(){
        reader.close();
    }




}