package com.example.insy4308.mavblaster.mavUtilities;

import android.util.Log;

import java.util.Comparator;

public class Scores implements Comparator<Scores>, Comparable<Scores>{
    private String name;
    private int score;

    public Scores()
    {
        this.name = "";
        this.score = 0;
    }

    public Scores (String info)
    {
        Log.d("info passed: ", info);
        String [] splitInfo = info.split(";");
        if (splitInfo.length < 2)
        {
            score = Integer.parseInt(splitInfo[0]);
            name = "N/A";
        }
        else {
            score = Integer.parseInt(splitInfo[0]);
            name = splitInfo[1];
        }
    }

    public Scores(int score, String name)
    {
        this.score = score;
        this.name = name;
    }

    public int getScore()
    {
        return this.score;
    }

    public String getName()
    {
        return this.name;
    }

    public int compareTo(Scores s)
    {
        return (this.name).compareTo(s.name);
    }

    public int compare(Scores s, Scores s1)
    {
        return s.score - s1.score;
    }
}
