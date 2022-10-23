package com.example.criptoquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuizController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static int currentQuestion=0;
    public static int[] questionList;
    public static int points=0;
    public static String username;

    @FXML
    Label questionLabel;
    @FXML
    Button answerButton1;
    @FXML
    Button answerButton2;
    @FXML
    Button answerButton3;
    @FXML
    Button answerButton4;
    @FXML
    TextArea answerTextArea;
    @FXML
    Label otherQuestionLabel;
    @FXML
    Label leaderboardLabel;
    @FXML
    Label timeLabel;

    public void attemptQuiz(ActionEvent event) throws IOException {
        currentQuestion = 0;
        points = 0;
        Path path = Paths.get("/home/milos/IdeaProjects/CriptoQuiz/src/root/loggedInUser.txt");
        username = Files.readString(path);
        username= username.replace("\n","");

        int timesPlayed=QuizUtilities.getTimesPlayed(username);
        timesPlayed++;
        QuizUtilities.incrementTimesPlayed(username,timesPlayed);

        if(timesPlayed<4)
        {

            QuizUtilities.shuffleQuestions();
            int questionNumber = questionList[currentQuestion];


            String tmp = QuizUtilities.getQuestion(questionNumber);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("quizScene1.fxml"));
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("quizScene2.fxml"));

            if(Utilities.typeOfQuestion(tmp))
            {
                root = loader.load();
                QuizController controller=loader.getController();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                String[] split = tmp.split("\\? ");
                String answers=split[1];

                controller.questionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");

                String[] tokens =answers.split(" ");

                Label label = new Label();
                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);



                StopWatch.getStopWatch(label);



                System.out.println("Correct answer: " + tokens[0]);

                Utilities.shuffle(tokens);
                controller.answerButton1.setText(tokens[0]);
                controller.answerButton2.setText(tokens[1]);
                controller.answerButton3.setText(tokens[2]);
                controller.answerButton4.setText(tokens[3]);

                stage.show();

            }
            else{

                root = loader1.load();
                QuizController controller = loader1.getController();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                Label label = new Label();
                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);
                StopWatch.getStopWatch(label);

                String[] split = tmp.split("\\? ");
                controller.otherQuestionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");
                System.out.println("Correct answer: " + split[1]);
                stage.show();

            }
        }
        else
        {
            System.out.println("IGRALI STE MAKSIMALAN BROJ PUTA!");
            QuizUtilities.revokeCRL(username);
        }
    }

// 4 answers
    public void nextQuestion(ActionEvent event) throws IOException {

        int questionNumber = questionList[currentQuestion];
        String tmp=QuizUtilities.getQuestion(questionNumber);

        String[] split = tmp.split("\\? ");
        String answers=split[1];
        String[] tokens =answers.split(" ");

        String finalCorrectAnswer = tokens[0];                  //provjeri da li je tacno dugme kliknuto

        Object node = event.getSource();
        Button b = (Button) node;

        if(finalCorrectAnswer.equalsIgnoreCase(b.getText()))
            points++;


        System.out.println("Broj bodova nakon obrade: " + points);

        //postavljanje sledeceg pitanja

        if(currentQuestion!=4) {
            currentQuestion++;
            questionNumber = questionList[currentQuestion];

            tmp=QuizUtilities.getQuestion(questionNumber);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quizScene1.fxml"));
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("quizScene2.fxml"));

            if(Utilities.typeOfQuestion(tmp))
            {
                root = loader.load();
                QuizController controller=loader.getController();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                Label label = new Label();
                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);


                StopWatch.getStopWatch(label);

                split = tmp.split("\\? ");
                answers=split[1];

                controller.questionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");

                String[] tokens1 =answers.split(" ");
                System.out.println("Correct answer: " + tokens1[0]);

                Utilities.shuffle(tokens1);

                controller.answerButton1.setText(tokens1[0]);
                controller.answerButton2.setText(tokens1[1]);
                controller.answerButton3.setText(tokens1[2]);
                controller.answerButton4.setText(tokens1[3]);

                stage.show();
            }
            else{

                root = loader1.load();
                QuizController controller = loader1.getController();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                Label label = new Label();

                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);


                StopWatch.getStopWatch(label);

                split = tmp.split("\\? ");


                controller.otherQuestionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");

                stage.show();
            }

        }
        else
        {
            String timePlayed = StopWatch.lastStopWatch.stopAnimation();

            QuizUtilities.unlockLeaderboard();

            System.out.println("Broj bodova nakon kviza: " + points);

            QuizUtilities.writeInLeaderboard(username, points, timePlayed);

            //iscitaj sve iz fajla i sortiraj
            String leaderboard = QuizUtilities.sortLeaderboard();

            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
            root = loader3.load();
            QuizController controller=loader3.getController();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            controller.leaderboardLabel.setText(leaderboard);
            stage.show();

            QuizUtilities.lockLeaderboard();
        }
    }

// 1 answer
    public void submitAnswer(ActionEvent event) throws IOException {

        int questionNumber = questionList[currentQuestion]; //obradi ovo pitanje iz liste
        String tmp=QuizUtilities.getQuestion(questionNumber);

        String[] split = tmp.split("\\? ");
        String answer=split[1];

        if(answer.equalsIgnoreCase(answerTextArea.getText()))
            points++;

        System.out.println("Trenutni broj bodova: " + points);


        //postavljanje sledeceg pitanja
        if(currentQuestion!=4) {
            currentQuestion++;
            questionNumber = questionList[currentQuestion];
            tmp = QuizUtilities.getQuestion(questionNumber);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("quizScene1.fxml"));
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("quizScene2.fxml"));

            if (Utilities.typeOfQuestion(tmp)) {
                root = loader.load();
                QuizController controller = loader.getController();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                Label label = new Label();
                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);


                StopWatch.getStopWatch(label);

                split = tmp.split("\\? ");
                String answers = split[1];


                controller.questionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");
                String[] tokens = answers.split(" ");

                System.out.println("Correct answer: " + tokens[0]);

                Utilities.shuffle(tokens);
                controller.answerButton1.setText(tokens[0]);
                controller.answerButton2.setText(tokens[1]);
                controller.answerButton3.setText(tokens[2]);
                controller.answerButton4.setText(tokens[3]);

                stage.show();
            } else {

                root = loader1.load();
                QuizController controller = loader1.getController();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                Label label = new Label();
                label.setStyle("-fx-text-fill: white");
                ((AnchorPane)root).getChildren().add(label);


                StopWatch.getStopWatch(label);

                split = tmp.split("\\? ");

                controller.otherQuestionLabel.setText(String.valueOf(currentQuestion+1) + ". " + split[0] + "?");

                System.out.println("Correct answer: " + split[1]);

                stage.show();
            }
        }
        else
        {

            String timePlayed = StopWatch.lastStopWatch.stopAnimation();

            QuizUtilities.unlockLeaderboard();

            System.out.println("Broj bodova nakon kviza: " + points);
            QuizUtilities.writeInLeaderboard(username, points, timePlayed);


            String leaderboard = QuizUtilities.sortLeaderboard();
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
            root = loader3.load();
            QuizController controller=loader3.getController();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            controller.leaderboardLabel.setText(leaderboard);
            stage.show();


            QuizUtilities.lockLeaderboard();
        }
    }



    public void switchToLoginMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showLeaderboard(ActionEvent event) throws  IOException
    {
        QuizUtilities.unlockLeaderboard();

        String leaderboard = QuizUtilities.sortLeaderboard();

        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
        root = loader3.load();
        QuizController controller=loader3.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        controller.leaderboardLabel.setText(leaderboard);
        stage.show();

        QuizUtilities.lockLeaderboard();
    }
}
