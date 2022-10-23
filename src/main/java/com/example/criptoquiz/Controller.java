package com.example.criptoquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String alg, algkey;
    @FXML
    private Label wrongInputLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField countryNameTextField;
    @FXML
    private TextField stateOrProvinceNameTextField;
    @FXML
    private TextField organizationNameTextField;
    @FXML
    private TextField organizationalUnitNameTextField;
    @FXML
    private TextField commonNameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField logInUsername;
    @FXML
    private PasswordField logInPassword;
    @FXML
    private Label loginLabel;

    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void signUpNext(ActionEvent event) throws IOException {
            String rootCA, username, password, countryName, stateOrProvinceName, organizationName, organizationalUnitName, commonName, emailAddress, hashPassword, salt;

            if(Math.random()<0.5)
                rootCA="rootca1";
            else
                rootCA = "rootca2";

            username=usernameTextField.getText();
            password=passwordPasswordField.getText();
            salt=Utilities.generateRandomSalt();
            countryName=countryNameTextField.getText();
            stateOrProvinceName=stateOrProvinceNameTextField.getText();
            organizationName=organizationNameTextField.getText();
            organizationalUnitName=organizationalUnitNameTextField.getText();
            commonName=commonNameTextField.getText();
            emailAddress=emailAddressTextField.getText();
            System.out.println("Using: " + rootCA);

            if(!Utilities.usernameAlreadyExists(username) && !username.contains(" ") && !username.isEmpty())
                if(!(password.length() < 4) && !password.contains(" ") && !commonName.contains(" ") && !emailAddress.contains(" ") && !emailAddress.isEmpty() && !commonName.isEmpty() && "BA".contentEquals(countryName) && "RS".contentEquals(stateOrProvinceName) && "Elektrotehnicki Fakultet".contentEquals(organizationName) && "ETF".contentEquals(organizationalUnitName))
                    {
                        hashPassword=Utilities.processBuilderHashPassword("src/root/scripts/PasswordHash.sh", password, salt);
                        Utilities.processBuilderGeneratePrivateKey("src/root/" + rootCA + "/GeneratingPrivateKey.sh", username, rootCA);
                        Utilities.processBuilderGenerateCertificate("src/root/" + rootCA + "/GeneratingCertificate.sh", username, commonName, emailAddress, rootCA);
                        Utilities.processBuilderGenerateUserTxt("src/root/" + rootCA + "/GeneratingUserTxt.sh", username, hashPassword, password, salt, rootCA);
                        switchToMenu(event);
                    }
                else
                    wrongInputLabel.setText("Check your input!");
            else
                wrongInputLabel.setText("Username already exists or is invalid!");

    }

    public void logInNext(ActionEvent event) throws IOException {
        String username, password, content, salt;
        username = logInUsername.getText();
        password = logInPassword.getText();
        if(!Utilities.usernameAlreadyExists(username))
        {
            loginLabel.setText("Username doesn't exist!");
        }
        else {
                Path path = Paths.get("/home/milos/IdeaProjects/CriptoQuiz/src/root/users/" + username + "/" + username + ".txt");
                content = Files.readString(path);
                String[] tokens = content.split(" ");
                salt = tokens[2];
                String rootca = tokens[3];
                rootca=rootca.replace("\n","");
                if (tokens[0].equals(username))
                    if (!password.isEmpty() && tokens[1].equals(Utilities.processBuilderHashPassword("src/root/scripts/PasswordHash.sh", password, salt))) {
                        Utilities.verifyLogIn("src/root/" + rootca + "/VerifyLogIn.sh", username, rootca, password);
                        String tmp = Utilities.getAlgorithm(username,password);
                        String[] split = tmp.split(" ");
                        alg=split[0];
                        algkey=split[1];
                        root = FXMLLoader.load(getClass().getResource("loginMenu.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        loginLabel.setText("Wrong Password!");
                        logInPassword.setText("");
                    }
                else {
                    loginLabel.setText("Wrong Username!");
                    logInUsername.setText("");
                }
        }
    }
}