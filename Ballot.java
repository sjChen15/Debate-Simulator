//Ballot.java

//TODO: when one ballot wins, click on the ballot that won, edit in the wins and losses

public class Ballot {
    private Member debator1, debator2; //the debators on the ballot

    //constructor
    public Ballot(Member debator1, Member debator2) {
        this.debator1 = debator1;
        this.debator2 = debator2;
    }

    //displays the names
    public void display(){
        String d1Name = debator1.getName();
        String d2Name = debator2.getName();
    }

    //returns the average ranking of the ballot
    public double getRank(){
        return (debator1.getRank()+debator2.getRank())/2;
    }
}
