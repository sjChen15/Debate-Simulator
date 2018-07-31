//Member.java

import sun.audio.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
sample line
Jenny_Chen 12 true 0 0 0 0 1 3
*/
public class Member {
    private String name; //name of member
    private int grade; //grade of the member
    private boolean senior = false; //true if senior false if junior
    private int debatesWon; //number of deabtes won
    private int debatesLost; //number of debates lost
    private int timesGov; //number of government debates
    private int timesOp; //number of opposition debates
    private int rank; //rank of each individual member
    private int gender, skin;
    //TODO: make an int of total number of members so we know how many times to loop through
    //TODO: make flag for alternating debates: one week of senior v junior next week junior v junior
    //TODO: average the partner ranks sort the teams debating and then do the half and half thing
    //TODO: delete member after 8 weeks inactive, make a warning like "these people bout to be deleted"
    //TODO: everytime make debates you add a mark or something counting number of weeks

    //constructor
    public Member(String line) {
        String[] info = line.split(" ");
        name = info[0]; //will be in the form "First.Last"
        grade = Integer.parseInt(info[1]);
        if(info[2].equals("true")){
            senior = true;
        }
        debatesWon = Integer.parseInt(info[3]);
        debatesLost = Integer.parseInt(info[4]);
        timesGov = Integer.parseInt(info[5]);
        timesOp = Integer.parseInt(info[6]);
        gender = Integer.parseInt(info[7]);
        skin = Integer.parseInt(info[8]);
    }

    public Member(String name, int grade, boolean senior, int gender, int skin){
        this.name = name;
        this.grade = grade;
        this.senior = senior;
        this.gender = gender;
        this.skin = skin;

        debatesWon = 0;
        debatesLost = 0;
        timesGov = 0;
        timesOp = 0;

    }

    //returns the name with a space inbetween the first and last name
    public String getName(){
        String[] fullName = name.split("_");
        String splitName = fullName[0]+" "+fullName[1];
        return splitName;
    }

    public int getRank(){
        return rank;
    }
}
