import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JFrame implements ActionListener{
	
	private javax.swing.Timer myTimer;
	private JPanel cards;
	private CardLayout cLayout = new CardLayout();
	
	private MenuPanel menu = new MenuPanel();
	private StartPanel start = new StartPanel();
	private MembersPanel members = new MembersPanel();
	private AddBallotPanel addBallot = new AddBallotPanel();
	private AddMemberPanel addMember = new AddMemberPanel();
	private ShakeTinPanel shakeTin = new ShakeTinPanel();
	private DebatesPanel debates = new DebatesPanel();
	private BTree memberTree;
	
	public Main() {
		super("Debate Simulator");
		setSize(Toolkit.getDefaultToolkit().getScreenSize()); //set the size of the screen = to the monitor size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		cards = new JPanel(cLayout);
		cards.add(menu,"menu");
		cards.add(start,"start");
		cards.add(members,"members");
		cards.add(addBallot,"addBallot");
		cards.add(addMember,"addMember");
		cards.add(shakeTin,"shakeTin");
		cards.add(debates,"debates");
		getContentPane().add(cards);
	
		setVisible(true);

		myTimer = new javax.swing.Timer(100,this);
		myTimer.start();
	}

	public void makeMemberTree(){
		memberTree = new BTree();
		try{
			File file = new File("Debators.txt");

			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			while ((st = br.readLine()) != null){
				Member m = new Member(st);
				memberTree.add(m);
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (menu.getStart()) {
			cLayout.show(cards, "start");
			//going back to menu
			if (start.getMenu()) {
				menu.setStart(false); //reset flags
				start.setMenu(false);
				cLayout.first(cards);
			}
			//going to addBallot screen
			else if (start.get_addBallot()) {
				cLayout.show(cards, "addBallot");
				if (addBallot.getBack()) {
					start.set_addBallot(false);
					addBallot.setBack(false);
					cLayout.show(cards, "start");
				}
				else if (addBallot.getConfirm()) {
					start.set_addBallot(false);
					addBallot.setConfirm(false);
					cLayout.show(cards,"start");
				}
			}
			//go to shake tin screen
			else if (start.get_shakeTin()) {
				cLayout.show(cards, "shakeTin");
				//go to debates screen -- remember to reset all these flags eventually
				if (shakeTin.getNext()) {
					cLayout.show(cards, "debates");
				}
			}
		}
		else if (menu.getMembers()) {
			cLayout.show(cards, "members");
			//go back to menu
			if (members.getMenu()) {
				menu.setMembers(false); //reset flags
				members.setMenu(false);
				cLayout.first(cards);
			}
			//go to addMember screen
			else if (members.get_addMember()) {
				cLayout.show(cards, "addMember");

				addMember.actionPerformed(e);
				
				if (addMember.getBack()) {
					members.set_addMember(false);
					addMember.setBack(false);
					cLayout.show(cards, "members");
				}
				else if (addMember.getConfirm()) {
					members.set_addMember(false);
					addMember.setConfirm(false);
					cLayout.show(cards,"members");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
}

class MenuPanel extends JPanel implements MouseListener{
	
	private JButton[] buttons = {new JButton("Start"),new JButton("Members")};
	private boolean start, members;
	
	public MenuPanel() {
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}
	}
	
	//getters
	public boolean getStart() {return start;}
	public boolean getMembers() {return members;};
	//setters
	public void setStart(boolean b) {start = b;}
	public void setMembers(boolean b) {members = b;}
	
	//key listener methods
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			start = true;
		}
		else if (source == buttons[1]) {
			members = true;
		}
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

class StartPanel extends JPanel implements MouseListener{
	//TODO: make a new tree everytime start is pressed
	private JButton[] buttons = {new JButton("Add Ballot"), new JButton("Shake Tin"), new JButton("Menu")};
	private boolean addBallot,shakeTin,menu;
	
	public StartPanel() {
		
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}
	}
	
	//getters
	public boolean get_addBallot() {return addBallot;}
	public boolean get_shakeTin() {return shakeTin;}
	public boolean getMenu() {return menu;}

	//setters
	public void setMenu(boolean b) {menu = b;}
	public void set_addBallot(boolean b) {addBallot = b;}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			addBallot = true;
		}
		else if (source == buttons[1]) {
			shakeTin = true;
		}
		else if (source == buttons[2]) {
			menu = true;
		}
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

class AddBallotPanel extends JPanel implements MouseListener{
	
	private JButton[] buttons = {new JButton("Confirm"), new JButton("Back")};
	private boolean confirm,back;
	
	public AddBallotPanel() {
		
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}
	}
	
	//getters
	public boolean getConfirm() {return confirm;}
	public boolean getBack() {return back;}

	//setters
	public void setConfirm(boolean b) {confirm = b;}
	public void setBack(boolean b) {back = b;}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			confirm = true;
		}
		else if (source == buttons[1]) {
			back = true;
		}
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
	
	private JButton[] buttons = {new JButton("Next")};
	private boolean next;
	
	public ShakeTinPanel() {
		
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}
	}
	
	//getters
	public boolean getNext() {
		return next;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			next = true;
		}
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
	
	private JButton[] buttons = {new JButton("Add Member"),new JButton("Menu")}; 
	private boolean addMember,menu;
	
	public MembersPanel() {
		
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}
	}
	
	//getters
	public boolean get_addMember() {return addMember;}
	public boolean getMenu() {return menu;}

	//setters
	public void setMenu(boolean b) {menu = b;}
	public void set_addMember(boolean b) {addMember = b;}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == buttons[0]) {
			addMember = true;
		}
		else if (source == buttons[1]) {
			menu = true;
		}
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
	
	private JButton[] buttons = {new JButton("Confirm"), new JButton("Back")};
	private boolean confirm,back;
	
	private Avatar newAvatar; //change to member after - temporary to test physical appearance things
	private JButton[] gender_buttons = {new JButton("Male"),new JButton("Female")};
	private JButton[] skin_buttons = {new JButton(), new JButton(), new JButton(), new JButton(), new JButton()};
	private String[] skins = {"light","tanned","tanned2","dark","dark2"}; //related list for skin buttons
	private int skin_int = 0; //keep track of current skin
	private int gender_int = 0; //keep track of current gender

	//gender (male,female) x skin colors (light,tanned,tanned2,dark,dark2) x direction(up,down,right,left) x number of pictures
	private Image[][][][] sprites = new Image[2][5][4][10];

	private JTextField nameField; //textbox
	//private String textBoxName;
	//i give up so im going to get keyboard input for now
	//private JButton name_button = new JButton("Name");
	private JButton[] grade_buttons = {new JButton("9"),new JButton("10"),new JButton("11"),new JButton("12")}; //choose grade buttons
	private JButton[] exp_buttons = {new JButton("Senior"),new JButton("Junior")}; //choose exp buttons
	private String name;
	private int grade; //grade
	private boolean senior; //exp
	private JLabel name_label;
	private JLabel grade_label;
	private JLabel exp_label;

	
	public AddMemberPanel() {
				
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

		//lets try some text things
		nameField = new JTextField(20);
		nameField.addMouseListener(this);
		add(nameField);

		//grade buttons
		for(JButton b: grade_buttons){
			b.addMouseListener(this);
			add(b);
		}

		//senior buttons
		for(JButton b: exp_buttons){
			b.addMouseListener(this);
			add(b);
		}
		
		//button formatting
		for (JButton b : buttons) {
			b.addMouseListener(this);
			add(b);
		}

		name_label = new JLabel("Name: ");
		add(name_label);

		grade_label = new JLabel("Grade: ");
		add(grade_label);

		exp_label = new JLabel("Experience: ");
		add(exp_label);
		
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

	}
	
	//getters
	public boolean getConfirm() {return confirm;}
	public boolean getBack() {return back;}

	//setters
	public void setConfirm(boolean b) {confirm = b;}
	public void setBack(boolean b) {back = b;}
	
	//graphics
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		newAvatar.draw(g);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		//for page navigation
		if (source == buttons[0]) {
			if(name!=null && grade!=0){ //make sure these have been filled in
				confirm = true;
				Member m = new Member(name,grade,senior,gender_int,skin_int);
				String txtFileInfo; //the string that will be written into the txt file
				if(senior){
					txtFileInfo = name+" "+grade+" true 0 0 0 0 "+gender_int+" "+skin_int;
				}
				else{
					txtFileInfo = name+" "+grade+" false 0 0 0 0 "+gender_int+" "+skin_int;
				}

				try {
				//	Files.write(Paths.get("Debators.txt"),txtFileInfo.getBytes(), StandardOpenOption.APPEND);
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
			//TODO: make a tree
		}
		else if (source == buttons[1]) {
			back = true;
		}
		
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
		//name
		/*
		if(source == name_button){
			System.out.println("Please enter name in form 'First Last'");
			Scanner kb = new Scanner(System.in);
			name = kb.nextLine(); //get the name
			name = name.toLowerCase();
			char[] nameChars = name.toCharArray();

			//change first letters of first and last name to uppercase
			nameChars[0] = Character.toUpperCase(nameChars[0]);
			int spaceIndex = name.indexOf(" ");
			nameChars[spaceIndex+1] = Character.toUpperCase(nameChars[spaceIndex+1]);
			String u = "_";
			nameChars[spaceIndex] = u.charAt(0); //set the space to an underscore for the constructor
			name = new String(nameChars);
			System.out.println(name);

			//display name
			String[] firstNLast = name.split("_");
			if(!name.equals("")){
				name_label.setText("Name: "+firstNLast[0]+ " "+firstNLast[1]);
			}
		}*/

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
	//formmat the name in the textbox so it is in the correct format for the txt file
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		name = nameField.getText();
		fixName(); //make the name correct for txt and display the name

	}

	
}
