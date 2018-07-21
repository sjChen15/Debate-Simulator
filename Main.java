import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener{
		
	private Timer myTimer;
	private JPanel cards;
	private CardLayout cLayout = new CardLayout();
	
	//JPanels
	private JPanel[] panels = {new MenuPanel(),new StartPanel(), new MembersPanel(), new AddBallotPanel(), new AddMemberPanel(),
			new ShakeTinPanel(), new DebatesPanel()};
	
	//JButtons
	private JButton[][] buttons = {{new JButton("Start"),new JButton("Members")},{new JButton("Add Ballot"), new JButton("Shake Tin"), new JButton("Menu")},
			{new JButton("Add Member"),new JButton("Menu")},{new JButton("Confirm"), new JButton("Back")},
			{new JButton("Confirm"), new JButton("Back")},{new JButton("Next")},
			{new JButton("Menu")}};
	private String[] panelKey = {"menu","start","members","addBallot","addMember","shakeTin","debates"};
	
	public Main() {
		super("Debate Simulator");
		setSize(Toolkit.getDefaultToolkit().getScreenSize()); //set the size of the screen = to the monitor size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//add buttons to corresponding panels
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].addActionListener(this);
				panels[i].add(buttons[i][j]);
			}
		}
		
		//CardLayout
		cards = new JPanel(cLayout);
		for (int i = 0; i < panels.length; i++) {
			cards.add(panels[i],panelKey[i]);
		}
		getContentPane().add(cards);

		setVisible(true);

		myTimer = new Timer(100,this);
		myTimer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		repaint(); //graphics
		
		Object source = e.getSource();
		//MenuPanel
		if (source == buttons[0][0]) { //Start button
			cLayout.show(cards, "start");
		}
		else if (source == buttons[0][1]) { //Members button
			cLayout.show(cards, "members");
		}
		//StartPanel
		else if (source == buttons[1][0]) { //Add Ballot button
			cLayout.show(cards, "addBallot");
		}
		else if (source == buttons[1][1]) { //Shake Tin button
			cLayout.show(cards, "shakeTin");
		}
		else if (source == buttons[1][2]) { //Menu button
			cLayout.first(cards);
		}
		//MembersPanel
		else if (source == buttons[2][0]) { //Add Member button
			cLayout.show(cards, "addMember");
		}
		else if (source == buttons[2][1]) { //Menu button
			cLayout.first(cards);
		}
		//AddBallotPanel
		else if (source == buttons[3][0]) { //Confirm button
			cLayout.show(cards, "start");
		}
		else if (source == buttons[3][1]) { //Back button
			cLayout.show(cards, "start");
		}
		//AddMemberPanel
		else if (source == buttons[4][0]) { //Confirm button
			cLayout.show(cards, "members");
		}
		else if (source == buttons[4][1]) { //Back button
			cLayout.show(cards, "members");
		}
		//ShakeTinPanel
		else if (source == buttons[5][0]) { //Next button
			cLayout.show(cards, "debates");
		}
		//DebatesPanel
		else if (source == buttons[6][0]) { //Menu button
			cLayout.first(cards);
		}
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
}

class MenuPanel extends JPanel implements MouseListener{
	
	private JLabel[] labels = {new JLabel("Massey Debate"),new JLabel("Simulator")};
	
	public MenuPanel() {
		//labels
		for (JLabel l : labels) {
			add(l);
		}
	}
	
	//key listener methods
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

class StartPanel extends JPanel implements MouseListener{

	public StartPanel() {
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

class AddBallotPanel extends JPanel implements MouseListener{
	
	private JComboBox enter_name;
	private String[] sample_names = {"Rahma","Jenny","Adam","Vinh","Albert","Zak","Poonam","Georgia"}; //replace with list of member names
	
	public AddBallotPanel() {
	
		//JComboBox to enter names
		enter_name = new JComboBox(sample_names);
		enter_name.setEditable(true);
		enter_name.addMouseListener(this);
		add(enter_name);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {		
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
}

class ShakeTinPanel extends JPanel implements MouseListener {
	
	public ShakeTinPanel() {
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
	
	public MembersPanel() {
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

class AddMemberPanel extends JPanel implements MouseListener{
	
	private Avatar newAvatar; //change to member after - temporary to test physical appearance things
	private JButton[] gender_buttons = {new JButton("Male"),new JButton("Female")};
	private JButton[] skin_buttons = {new JButton(), new JButton(), new JButton(), new JButton(), new JButton()};
	private String[] skins = {"light","tanned","tanned2","dark","dark2"}; //related list for skin buttons
	private int skin_int = 0; //keep track of current skin
	private int gender_int = 0; //keep track of current gender
	//gender (male,female) x skin colors (light,tanned,tanned2,dark,dark2) x direction(up,down,right,left) x number of pictures
	private Image[][][][] sprites = new Image[2][5][4][10];  
		
	private JTextField name;
	
	public AddMemberPanel() {
		
		name = new JTextField(20);
		add(name);
		
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
		
	}

	//graphics
	@Override
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
	}
	@Override
	public void mouseEntered(MouseEvent e) {
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

}
