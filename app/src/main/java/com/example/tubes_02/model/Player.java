package com.example.tubes_02.model;

//isinya untuk di leaderboard saja

public class Player {
    public int id;
    public String name;
    public String score;

    public Player(int id, String name, String score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Player(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
