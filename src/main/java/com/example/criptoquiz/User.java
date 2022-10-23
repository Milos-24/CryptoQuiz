package com.example.criptoquiz;

public class User {
    public  String username;

    public  int points;
    String timePlayed;
    public User(String username1, String points1, String timePlayed)
    {
        username=username1;
        points= Integer.parseInt(points1);
        this.timePlayed = timePlayed;
    }
    @Override
    public String toString(){
        String pointsString = Integer.toString(this.points);
        StringBuilder result = new StringBuilder(this.username.length()+pointsString.length()+this.timePlayed.length()+5);

        result.append(this.username+" ");
        result.append(pointsString+" ");
        result.append(timePlayed);
        result.append("\n");

        return result.toString();
    }

    public int getPoints()
    {
        return this.points;
    }
}
