//Member.java


public class Member {
    private String name; //name of member
    private int grade; //grade of the member
    private boolean senior; //true if senior false if junior
    private int debatesWon; //number of deabtes won
    private int debatesLost; //number of debates lost
    private int timesGov; //number of government debates
    private int timesOp; //number of opposition debates
    private String partner; //partner of this member, 'no partner' if no partner
    private int rank; //rank of each individual member
    //TODO: make an int of total number of members so we know how many times to loop through
    //TODO: make flag for alternating debates: one week of senior v junior next week junior v junior
    //TODO: average the partner ranks sort the teams debating and then do the half and half thing
    //TODO: delete member after 8 weeks inactive


    public Member(String name, int grade, boolean senior, String partner) {
        this.name = name;
        this.grade = grade;
        this.senior = senior;
        this.debatesWon = 0;
        this.debatesLost = 0;
        this.timesGov = 0;
        this.timesOp = 0;
        this.partner = partner;
    }
}
