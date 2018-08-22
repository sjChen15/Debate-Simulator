import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

public class Main extends JFrame implements ActionListener{
	
	private javax.swing.Timer myTimer;
	private JPanel cards;
	private CardLayout cLayout = new CardLayout();

	//JButtons
	private JButton[][] buttons = {{new JButton("Start"),new JButton("Members")},{new JButton("Add Ballot"), new JButton("Shake Tin"), new JButton("Menu")},
			{new JButton("Skip"),new JButton("Confirm")},
			{new JButton("Add Member"),new JButton("Menu")},{new JButton("Confirm"), new JButton("Back")},
			{new JButton("Confirm"), new JButton("Back")},{new JButton("Next")},
			{new JButton("Menu")}};

	private String[] panelKey = {"menu","start","date","members","addBallot","addMember","shakeTin","debates"};
	//JPanels
	private JPanel[] panels = {new MenuPanel(buttons[0]),new StartPanel(buttons[1]), new DatePanel(buttons[2]), new MembersPanel(buttons[3]), new AddBallotPanel(buttons[4]), new AddMemberPanel(buttons[5]),
			new ShakeTinPanel(buttons[6]), new DebatesPanel(buttons[7])}; //construct panels with buttons

	public Main() {
		super("Debate Simulator");
		setSize(new Dimension(1200,800)); //constant size
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add buttons to corresponding panels
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].addActionListener(this);
			}
		}

		//CardLayout
		cards = new JPanel(cLayout);
		for (int i = 0; i < panels.length; i++) {
			panels[i].setSize(new Dimension(1200,800)); //constant size
			cards.add(panels[i],panelKey[i]);
		}
		getContentPane().add(cards);
	
		setVisible(true);

		myTimer = new javax.swing.Timer(100,this);
		myTimer.start();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint(); //graphics
		Object source = e.getSource();
		
		//MenuPanel
		if (source == buttons[0][0]) { //Start button
			((StartPanel)panels[1]).makeMemberTree();
			cLayout.show(cards, "date");
		}
		else if (source == buttons[0][1]) { //Members button
			cLayout.show(cards, "members");
		}
		//StartPanel
		else if (source == buttons[1][0]) { //Add Ballot button
            ((AddBallotPanel)panels[4]).setMemberTree(((StartPanel)panels[1]).getMemberTree());
            ((AddBallotPanel)panels[4]).updateBoxes();
			cLayout.show(cards, "addBallot");
		}
		else if (source == buttons[1][1]) { //Shake Tin button
			cLayout.show(cards, "shakeTin");
		}
		else if (source == buttons[1][2]) { //Menu button
			cLayout.show(cards,"menu");
		}
		//DatePanel
		if(source == buttons[2][0]){//Confirm button
			if(((DatePanel)panels[2]).canConfirm()){ //confirm all parameters have been filled in
				((DatePanel)panels[2]).writeToFile(); //write the date to the file
				cLayout.show(cards,"start");
			}
		}
		else if(source == buttons[2][1]){ //Skip button
			cLayout.show(cards,"start");
		}
		//MembersPanel
		else if (source == buttons[3][0]) { //Add Member button
			cLayout.show(cards, "addMember");
		}
		else if (source == buttons[3][1]) { //Menu button
			cLayout.show(cards,"menu");
		}
		//AddBallotPanel
		else if (source == buttons[4][0]) { //Confirm button
			cLayout.show(cards, "start");				
			if(((AddBallotPanel)panels[4]).canConfirm()){
				((AddBallotPanel)panels[4]).confirmNames();					
				((AddBallotPanel)panels[4]).confirmNames();
				((StartPanel)panels[1]).addToTin(((AddBallotPanel)panels[4]).getBallot());
				cLayout.show(cards, "start");
			}
		}			
		else if (source == buttons[4][1]) { //Back button
			cLayout.show(cards, "start");
		}
		//AddMemberPanel
		else if (source == buttons[5][0]) { //Confirm button
			if(((AddMemberPanel)panels[5]).canConfirm()) {
				cLayout.show(cards, "members");
			}
		}
		else if (source == buttons[5][1]) { //Back button
			cLayout.show(cards, "members");
		}
		//ShakeTinPanel
		else if (source == buttons[6][0]) { //Next button
			cLayout.show(cards, "debates");
		}
		//DebatesPanel
		else if (source == buttons[7][0]) { //Menu button
			cLayout.show(cards,"menu");
		}
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
}


class MenuPanel extends JPanel implements MouseListener{

	private Image podium;
	private Image logo;
	private Color underline1 = Color.WHITE;
	private Color underline2 = Color.WHITE;
	private JLabel[] labels = {new JLabel("Vincent Massey Secondary School",SwingConstants.CENTER),new JLabel("Debate Club",SwingConstants.CENTER)};
	private JButton[] buttons = {new JButton("Start"),new JButton("Members")}; //initially avoid null pointer
	
	public MenuPanel(JButton[] buttons) {
		
		this.buttons = buttons;
		
		//Formatting
		setLayout(null);
		//load files
		try {
			podium = ImageIO.read(new File("MenuFiles/podium.png")).getScaledInstance(1200,400,Image.SCALE_DEFAULT); 
			logo = ImageIO.read(new File("MenuFiles/DebateLogo.png")).getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		} catch (IOException e) {	e.printStackTrace();}
		
		//background
		setBackground(new Color(128,0,0)); //marroon
		
		//JLabels
		for (JLabel l : labels) {
			l.addMouseListener(this);
			l.setForeground(Color.WHITE);
		}
		//Vincent Massey Secondary School
		labels[0].setForeground(new Color(163,99,43)); //podium's brown
		labels[0].setFont(new Font("Times New Roman", Font.PLAIN, 30));
		labels[0].setSize(new Dimension(1200,40));
		labels[0].setLocation(new Point(0,70));
		add(labels[0]);
		//Debate Club
		labels[1].setFont(new Font("Times New Roman", Font.BOLD, 100));
		labels[1].setSize(new Dimension(1200,180));
		labels[1].setLocation(new Point(0,90));
		add(labels[1]);
		
		//JButtons
		for (JButton b  : buttons) {
			b.addMouseListener(this);
			b.setSize(new Dimension(300,100));
			b.setFont(new Font("Times New Roman", Font.BOLD, 48));
			
			b.setBackground(new Color(0,0,0,0));
			b.setForeground(Color.WHITE);
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
		}
		//Start
		buttons[0].setLocation(new Point(50,400));
		add(buttons[0]);
		//Members
		buttons[1].setLocation(new Point(850,400));
		add(buttons[1]);
	}

	//graphics
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo,350, 0, null);
		g.drawImage(podium,0,400,null);
		
		//button underlines
		g.setColor(underline1);
		g.fillRect(100, 480, 200, 3); //Start Button
		g.setColor(underline2);
		g.fillRect(900, 480, 200, 3); //Members Button
	}

	//mouse listener methods
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		//Formatting
		if (source == buttons[0]) {
			buttons[0].setForeground(new Color(163,99,43)); //podium's brown
			underline1 = new Color(163,99,43);
		}
		else if (source == buttons[1]) {
			buttons[1].setForeground(new Color(163,99,43)); //podium's brown
			underline2 = new Color(163,99,43);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		//Formatting
		if (source == buttons[0]) {
			buttons[0].setForeground(Color.WHITE);
			underline1 = Color.WHITE;
		}
		else if (source == buttons[1]) {
			buttons[1].setForeground(Color.WHITE);
			underline2 = Color.WHITE;
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}

class StartPanel extends JPanel implements MouseListener{
	private BTree memberTree;
	private int numOfMembers; //the number of members in the tree
	private boolean needNewTree = true; //is true if user has just start program or has went back to menu from the start page

	private ArrayList<Ballot> tin = new ArrayList<Ballot>(); //to store ballots
	
	private JButton[] buttons;

	public StartPanel(JButton[] buttons) {

		//buttons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}

	}

	public void addToTin(Ballot b) {
		tin.add(b);
	}
	
    public void makeMemberTree(){
        if(needNewTree){
            memberTree = new BTree();
            try{
                File file = new File("Debators.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String st;
                numOfMembers=0;
                while ((st = br.readLine()) != null){
                    Member m = new Member(st);
                    memberTree.add(m);
                    numOfMembers++;
                    //System.out.println(m.getName());
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        needNewTree = false;
        System.out.println(memberTree.countLeaves());
        System.out.println(memberTree.display(1));
    }

    public BTree getMemberTree(){
	    return memberTree;
    }
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
}

class DatePanel extends JPanel implements MouseListener{
	
	private JButton[] buttons = {new JButton("Skip"),new JButton("Confirm")}; //initially avoid null pointer
	private JLabel[] directions = {new JLabel("Enter the date if it is an official debate meeting and if it has not been entered today.",SwingConstants.CENTER),
			new JLabel("Matchmaking and member deletion are dependant on the accuracy of dates entered so please be careful.",SwingConstants.CENTER)};
	private JLabel[] labels = {new JLabel("Day"), new JLabel("Month"), new JLabel("Year")};

	//to select the date of the meeting
	private String[][] ranges = {numberArrayString(1,31),numberArrayString(1,12),numberArrayString(2018,2050)};
	private JComboBox[] dropdowns = {new JComboBox(ranges[0]),new JComboBox(ranges[1]),new JComboBox(ranges[2])};
	
	//method creates string array given first and last integer by an increment of 1 
	public String[] numberArrayString(int firstInclusive, int secondInclusive) {
		String[] stringArray = new String[secondInclusive - firstInclusive +1];
		int counter = 0; //to iterate through array
		for (int i = firstInclusive; i <= secondInclusive; i++) {
			stringArray[counter] = Integer.toString(i);
			counter++;
		}
		return stringArray;
	}
	
	//Constructor
	public DatePanel(JButton[] buttons){
		
		//Formatting
		setLayout(null);
		
		//JButtons
		this.buttons = buttons;
		//Skip
		buttons[0].setLocation(new Point(50,650));
		buttons[0].setHorizontalAlignment(SwingConstants.LEFT);
		//Confirm
		buttons[1].setLocation(new Point(850,650));	
		buttons[1].setHorizontalAlignment(SwingConstants.RIGHT);
		//background
		setBackground(new Color(128,0,0)); //marroon
		for (JButton b  : buttons) {
			b.addMouseListener(this);
			b.setSize(new Dimension(300,100));
			b.setFont(new Font("Times New Roman", Font.BOLD, 48));
			
			b.setBackground(new Color(0,0,0,0));
			b.setForeground(Color.WHITE);
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
			
			add(b);
		}
		
		
		//JLabels for Directions
		directions[0].setLocation(new Point(0,50));
		directions[1].setLocation(new Point(0,100));
		
		for (JLabel l : directions) {
			l.setSize(new Dimension(1200,50));
			l.setFont(new Font("Times New Roman",Font.PLAIN,24));
			
			l.setForeground(Color.WHITE);
			
			add(l);
		}
		
		
		//JLabels for Date
		for (int i = 0; i < labels.length; i++) {
			labels[i].setLocation(new Point(400,300+i*100));
		}
		for (JLabel l : labels) {
			l.setSize(new Dimension(100,25));
			l.setFont(new Font("Times New Roman",Font.PLAIN,24));
			
			l.setForeground(Color.WHITE);
			
			add(l);
		}
		
		//JComboBoxes
		for (int i = 0; i < dropdowns.length; i++) {
			dropdowns[i].setLocation(new Point(600,300+i*100));
		}
		for (JComboBox b : dropdowns) {
			b.addMouseListener(this);
			
			b.setSize(new Dimension(100,25));
			b.setFont(new Font("Times New Roman",Font.PLAIN,24));
		
			b.setBackground(Color.WHITE);
			b.setForeground(Color.BLACK);
			b.setEditable(true); //optional
			
			add(b);
		}
		
	}

	//checks if all boxes have been filled in
	public boolean canConfirm(){
		if(dropdowns[0].getItemCount()!=0 && dropdowns[1].getItemCount()!=0 && dropdowns[2].getItemCount()!=0){
			return true;
		}
		return false;
	}

	//writes the date to the "Weeks.txt" file
	//stores dates in form "day month year"
	//e.g. 17 10 2018
	public void writeToFile(){
		String txtFileInfo = dropdowns[0].getSelectedItem()+" "+dropdowns[1].getSelectedItem()+" "+dropdowns[2].getSelectedItem(); //the string that will be written into the txt file

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Weeks.txt",true));
			writer.write(txtFileInfo);
			writer.newLine();
			writer.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			buttons[0].setForeground(Color.BLACK);
		}
		else if (source == buttons[1]) {
			buttons[1].setForeground(Color.BLACK);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			buttons[0].setForeground(Color.WHITE);
		}
		else if (source == buttons[1]) {
			buttons[1].setForeground(Color.WHITE);
		}
	}
}

class AddBallotPanel extends JPanel implements MouseListener{
	private JButton[] buttons;
	private BTree memberTree;
	private JComboBox member1;
	private JComboBox member2;
	private String[] sample_names = {"Rahma","Jenny","Adam","Vinh","Albert","Zak","Poonam","Georgia"}; //replace with list of member names
    private String[] allMembers;

    private String member1Name,member2Name;

    private Ballot b;
    
    /*
	private JLabel member1 = new JLabel("Member 1:");
	private JLabel member2 = new JLabel("Member 2:");*/


	public AddBallotPanel(JButton[] buttons) {
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
		
	}
	
	//getter
	public Ballot getBallot() {
		return b;
	}
	
	//methods
	public boolean canConfirm() {
		if (member1.getItemCount() != 0 && member2.getItemCount() != 0) {
			return true;
		}
		return false;
	}

    public void setMemberTree(BTree m){
        memberTree = m;
    }

    public void updateBoxes(){
		System.out.println("Hey there!");
		//make String[] of all names in alphabetical order
		allMembers = memberTree.toStringArray();
        //JComboBoxes to enter names
        member1 = new JComboBox(allMembers);
		member1.setEditable(true);
		// get the combo box' editor component
		JTextComponent editor1 = (JTextComponent) member1.getEditor().getEditorComponent();
		// change the editor's document to our BadDocument
		editor1.setDocument(new JComboBoxEditor(member1));
		member1.addMouseListener(this);
		add(member1);

        member2 = new JComboBox(allMembers);
		member2.setEditable(true);
		// get the combo box' editor component
		JTextComponent editor2 = (JTextComponent) member2.getEditor().getEditorComponent();
		// change the editor's document to our BadDocument
		editor2.setDocument(new JComboBoxEditor(member2));
		member2.addMouseListener(this);
		add(member2);
    }

    public void confirmNames(){
		member1Name = member1.getSelectedItem().toString();
		member2Name = member2.getSelectedItem().toString();
		Ballot b = new Ballot(memberTree.findName(member1Name).getMember(),memberTree.findName(member2Name).getMember());
		//TODO: now store them in a tin or something...
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
}

class ShakeTinPanel extends JPanel implements MouseListener {
	
	private JButton[] buttons;
	
	public ShakeTinPanel(JButton[] buttons) {
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
	}
	
	//getters
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}

class DebatesPanel extends JPanel implements MouseListener {
	private JButton[] buttons;

	public DebatesPanel(JButton[] buttons) {
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}

class MembersPanel extends JPanel implements MouseListener{
	
	private JButton[] buttons;

	public MembersPanel(JButton[] buttons) {

		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
	}
	
	//getters
		@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}

class AddMemberPanel extends JPanel implements MouseListener,ActionListener{

	//buttons[0] = "confirm" buttons[1] = "back"
	private JButton[] buttons;
	
	private Avatar newAvatar; //change to member after - temporary to test physical appearance things
	private JButton[] gender_buttons = {new JButton("Male"),new JButton("Female")};
	private JButton[] skin_buttons = {new JButton(), new JButton(), new JButton(), new JButton(), new JButton()};
	private String[] skins = {"light","tanned","tanned2","dark","dark2"}; //related list for skin buttons
	private int skin_int = 0; //keep track of current skin
	private int gender_int = 0; //keep track of current gender
	//gender (male,female) x skin colors (light,tanned,tanned2,dark,dark2) x direction(up,down,right,left) x number of pictures
	private Image[][][][] sprites = new Image[2][5][4][10];

	private JTextField nameField; //textbox
	private JButton[] grade_buttons = {new JButton("9"),new JButton("10"),new JButton("11"),new JButton("12")}; //choose grade buttons
	private JButton[] exp_buttons = {new JButton("Senior"),new JButton("Junior")}; //choose exp buttons
	private String name;
	private int grade; //grade
	private boolean senior; //exp
	private JLabel name_label;
	private JLabel grade_label;
	private JLabel exp_label;

	
	public AddMemberPanel(JButton[] buttons) {

		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}

		//Member making buttons
		for (JButton b : gender_buttons) {
			b.addMouseListener(this);
			add(b);
		}
		//skin buttons
		skin_buttons[0].setBackground(new Color(253,213,183));
		skin_buttons[1].setBackground(new Color(253,208,130));
		skin_buttons[2].setBackground(new Color(236,196,121));
		skin_buttons[3].setBackground(new Color(186,132,84));
		skin_buttons[4].setBackground(new Color(156,102,62));

		for (JButton b : skin_buttons) {
			b.addMouseListener(this);
			add(b);
		}

		//load all sprite images
		String g,s; // temporary variables gender, skin color
		for (int i = 0; i < 2; i++) { //gender loop
			for (int j = 0; j < 5; j++) { //skin color loop
				for (int k = 0; k < 4; k++) { //direction loop
					for (int l = 0; l < 9; l++) { //image

						if (i == 0)
							g = "male";
						else
							g = "female";

						s = skins[j]; //use existing list

						try {
							if (s.charAt(s.length()-1) == '2')
								sprites[i][j][k][l] = ImageIO.read(new File("Avatar/body/"+g+"/"+s+"/"+s+"_"+((l+1)+(k*9))+".png"));
							else
								sprites[i][j][k][l] = ImageIO.read(new File("Avatar/body/"+g+"/"+s+"/"+s+((l+1)+(k*9))+".png"));
						} catch (IOException e) {e.printStackTrace();}
					}
				}
			}
		}
		//male and light skin
		newAvatar = new Avatar(sprites[gender_int][skin_int]); //default start

		//JTextField
		nameField = new JTextField(20);
		nameField.addActionListener(this);
		add(nameField);

		//grade buttons
		for(JButton b: grade_buttons){
			b.addMouseListener(this);
			add(b);
		}

		//experience buttons
		for(JButton b: exp_buttons){
			b.addMouseListener(this);
			add(b);
		}

		//informative labels
		name_label = new JLabel("Name: ");
		add(name_label);

		grade_label = new JLabel("Grade: ");
		add(grade_label);

		exp_label = new JLabel("Experience: ");
		add(exp_label);

	}

	//format the name in the textbox so it is in the correct format for the txt file
	//also the name is formatted and displayed
	public void fixName(){
		if(name.indexOf(" ")!=-1 && name.indexOf(" ")!= name.length()-1){ //make sure the name has a space before formmating
			name = name.toLowerCase();
			char[] nameChars = name.toCharArray();

			//change first letters of first and last name to uppercase
			nameChars[0] = Character.toUpperCase(nameChars[0]);
			int spaceIndex = name.indexOf(" ");
			nameChars[spaceIndex+1] = Character.toUpperCase(nameChars[spaceIndex+1]);
			String u = "_";
			nameChars[spaceIndex] = u.charAt(0); //set the space to an underscore for the constructor
			name = new String(nameChars);

			//display name
			String[] firstNLast = name.split("_");
			if(!name.equals("")){
				name_label.setText("Name: "+firstNLast[0]+ " "+firstNLast[1]);
			}
		}
		else{
			name_label.setText("Name: ");
		}

	}

	//resets the variables
	public void resetVariables(){
		skin_int = 0; //skin
		gender_int = 0; //gender
		newAvatar = new Avatar(sprites[gender_int][skin_int]); //return to default display
		nameField.setText("");
		name = new String();
		name_label.setText("Name: ");
		grade_label.setText("Grade: ");
		exp_label.setText("Experience: ");
	}

	//returns true if all information has been entered
	public boolean canConfirm(){
		if(name!=null && grade!=0){ //make sure these have been filled in
				writeToFile();
				return true;
		}
		return false;
	}

	public void writeToFile(){
		Member m = new Member(name,grade,senior,gender_int,skin_int);
		String txtFileInfo; //the string that will be written into the txt file
		if(senior){
			txtFileInfo = name+" "+grade+" true 0 0 0 0 "+gender_int+" "+skin_int;
		}
		else{
			txtFileInfo = name+" "+grade+" false 0 0 0 0 "+gender_int+" "+skin_int;
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Debators.txt",true));
			writer.write(txtFileInfo);
			writer.newLine();
			writer.close();
		}
		catch(IOException ex){
		ex.printStackTrace();
		}
		resetVariables(); //reset the variables
	}

	//graphics
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		newAvatar.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		//for Member creation
		//gender
		if (source == gender_buttons[0]) { //male
			gender_int = 0;
			newAvatar = new Avatar(sprites[gender_int][skin_int]);
		}
		else if (source == gender_buttons[1]) { //female
			gender_int = 1;
			newAvatar = new Avatar(sprites[gender_int][skin_int]);
		}
		//skin
		for (int i = 0; i < skin_buttons.length; i++) {
			if (source == skin_buttons[i]) {
				skin_int = i;
				newAvatar = new Avatar(sprites[gender_int][skin_int]);
			}
		}

		//grade
		for(JButton b: grade_buttons){
			if(source == b){
				grade = Integer.parseInt(b.getText());
				grade_label.setText("Grade: "+grade);
			}
		}

		//experience
		if(source == exp_buttons[0]){
			senior = true;
			exp_label.setText("Experience: Senior");
		}
		else if(source == exp_buttons[1]){
			senior = false;
			exp_label.setText("Experience: Junior");
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent e) {	
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("hey there beautiful");
		name = nameField.getText();
		fixName(); //make the name correct for txt and display the name

	}

	
}
