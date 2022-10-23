package com.example.criptoquiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Utilities {
    public static boolean usernameAlreadyExists(String user){
        File directoryPath = new File("/home/milos/IdeaProjects/CriptoQuiz/src/root/users");

        for(File file : directoryPath.listFiles()) {
            String tmp = file.getName();
            if(user.equals(tmp))
                return true;
        }

        return false;
    }

    public static String generateRandomSalt(){
        int leftLimit = 48, rightLimit = 122, length = 8;
        Random rnd = new Random();

        return rnd.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }


    public static String processBuilderHashPassword(String path, String password, String salt)
    {
        String[] cmd = new String[]{path, password, salt};
        String hash=null;
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                hash=s;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return hash;
    }

    public static void processBuilderGeneratePrivateKey(String path, String username, String rootCA)
    {

        String[] cmd = new String[]{path, username, rootCA};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                // s cita liniju po liniju ispisa shella, ovdje cemo preuzeti hash
                System.out.println(s);
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void processBuilderGenerateCertificate(String path, String username, String commonName, String emailAddress, String rootCA)
    {
        String[] cmd = new String[]{path, username, commonName, emailAddress, rootCA};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        }catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void processBuilderGenerateUserTxt(String path, String username, String hashPassword, String password, String salt, String rootCA)
    {
        String[] cmd = new String[]{path, username, hashPassword, password, salt, rootCA};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        }catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void verifyLogIn(String path, String username, String rootca, String password)
    {
        String[] cmd1 = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/scripts/ExtractUserCert.sh", username, rootca, password};
        ProcessBuilder pb1 = new ProcessBuilder(cmd1);
        try{
            Process p = pb1.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        }catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

        String[] cmd = new String[]{path, username};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        }catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public static String getAlgorithm(String username, String password) {
        String[] cmd1 = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/scripts/GetAlgorithm.sh", username, password};
        ProcessBuilder pb1 = new ProcessBuilder(cmd1);
        String tmp = "";
        try {
            Process p = pb1.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                tmp += s;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public static boolean typeOfQuestion(String str)
    {
        System.out.println(str);
        String[] split = str.split("\\?");
        String answers=split[1];
        int counter=0;
        String[] tokens =answers.split(" ");
        for(String ignored : tokens)
        {
            counter++;
        }

        return counter > 4;
    }
    public static void swap_elements(String[] words, int i, int j){
        String temp=words[i];
        words[i]=words[j];
        words[j]=temp;
    }
    public static void shuffle(String[] words)
    {
        for(int i = words.length -1 ; i >= 1 ; i--)
        {
            Random rand = new Random();
            int j = rand.nextInt(i+1);
            swap_elements(words,i,j);
        }
    }



}
