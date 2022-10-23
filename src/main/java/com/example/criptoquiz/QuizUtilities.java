package com.example.criptoquiz;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class QuizUtilities {

    public static int getTimesPlayed(String username) throws FileNotFoundException {
        Path path1 = Paths.get("/home/milos/IdeaProjects/CriptoQuiz/src/root/users/" + username + "/timesPlayed.txt");
        Scanner scanner = new Scanner(new File(String.valueOf(path1)));
        int timesPlayed = 0;

        while(scanner.hasNextInt())
            timesPlayed=scanner.nextInt();

        return  timesPlayed;
    }

    public static void incrementTimesPlayed(String username, int timesPlayed)
    {
        File f = new File("/home/milos/IdeaProjects/CriptoQuiz/src/root/users/" + username + "/timesPlayed.txt");
        try {
            FileWriter fw = new FileWriter(f, false);
            fw.write(String.valueOf(timesPlayed));
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void shuffleQuestions()
    {
        ArrayList<Integer> listOfQuestions = new ArrayList<>();

        for(int i = 1 ; i <= 20 ; i++)
            listOfQuestions.add(i);

        Collections.shuffle(listOfQuestions);
        QuizController.questionList= new int[5];
        for(int i = 0 ; i < 5 ; i++)
        {
            QuizController.questionList[i]=listOfQuestions.get(i);
        }
    }

    public static String getQuestion(int questionNumber)
    {
        String tmp=null;
        String[] cmd = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/scripts/GetQuestion.sh", String.valueOf(questionNumber)};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try{
            Process p = pb.start();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = reader.readLine()) != null){
                tmp=s;
            }
        }catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return tmp;
    }

    public static void revokeCRL(String username) throws IOException {
        Path path2 = Paths.get("/home/milos/IdeaProjects/CriptoQuiz/src/root/users/" + username + "/" + username + ".txt");
        String tmp = Files.readString(path2);
        String[] arr = tmp.split(" ");
        String[] cmd1 = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/" + arr[3].replace("\n", "") + "/RevokeCRL.sh", username};

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
    }

    public static void unlockLeaderboard()
    {
        String[] cmd1 = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/scripts/UnlockLeaderboard.sh", Controller.alg, Controller.algkey};
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
    }

    public static void writeInLeaderboard(String username, int points, String timePlayed)
    {
        try{
            FileWriter writer = new FileWriter("/home/milos/IdeaProjects/CriptoQuiz/src/root/leaderboard.txt", true);
            writer.write(username + " " + points + " " + timePlayed + "\n");
            writer.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<User> parseUsers(String filename) throws  IOException{
        //long numOfUsers=Utilities.countLines(filename);
        List<User> users = new ArrayList<>(15);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        User user;
        String line;

        while((line=br.readLine())!=null)
        {
            String[] token = line.split(" ");
            user = new User(token[0], token[1], token[2]);

            users.add(user);
        }

        return users;
    }

    public static String sortLeaderboard() throws IOException {
        List<User> users = parseUsers("/home/milos/IdeaProjects/CriptoQuiz/src/root/leaderboard.txt");
        Comparator<User> usersByPoints = (o1, o2) -> Integer.compare(o2.getPoints(),o1.getPoints());
        users.sort(usersByPoints);

        FileWriter writer = new FileWriter("/home/milos/IdeaProjects/CriptoQuiz/src/root/leaderboard.txt", false);
        String leaderboard = "";
        for(User user : users){
            writer.write(user.toString());
            leaderboard+=user.toString();
        }

        writer.close();
        return leaderboard;
    }

    public static void lockLeaderboard()
    {
        String[] cmd2 = new String[]{"/home/milos/IdeaProjects/CriptoQuiz/src/root/scripts/LockLeaderboard.sh", Controller.alg, Controller.algkey};
        ProcessBuilder pb3 = new ProcessBuilder(cmd2);
        try{
            Process p = pb3.start();
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

}
