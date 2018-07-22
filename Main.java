import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener{
		
	private Timer myTimer;
	private JPanel cards;
	private CardLayout cLayout = new CardLayout();
	
	//JButtons
	private JButton[][] buttons = {{new JButton("Start"),new JButton("Members")},{new JButton("Add Ballot"), new JButton("Shake Tin"), new JButton("Menu")},
			{new JButton("Add Member"),new JButton("Menu")},{new JButton("Confirm"), new JButton("Back")},
			{new JButton("Confirm"), new JButton("Back")},{new JButton("Next")},
			{new JButton("Menu")}};
	private String[] panelKey = {"menu","start","members","addBallot","addMember","shakeTin","debates"};
	
	//JPanels
	private JPanel[] panels = {new MenuPanel(buttons[0]),new StartPanel(buttons[1]), new MembersPanel(buttons[2]), new AddBallotPanel(buttons[3]), new AddMemberPanel(buttons[4]),
				new ShakeTinPanel(buttons[5]), new DebatesPanel(buttons[6])};	

	public Main() {
		super("Debate Simulator");
		setSize(Toolkit.getDefaultToolkit().getScreenSize()); //set the size of the screen = to the monitor size
		setMinimumSize(new Dimension(700,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//add buttons to corresponding panels
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].addActionListener(this);
				//panels[i].add(buttons[i][j]);
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
		
		System.out.println(this.getSize().toString());
		
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
	
	private Font chalkFont;
	private Image chalkboard;
	private JLabel[] labels = {new ScalingLabel("Vincent Massey Secondary School"),new ScalingLabel("Debate Simulator")};
	private JButton[] buttons;
	
	
	public MenuPanel(JButton[] buttons) {
		
		//load files
		try {
			chalkFont = Font.createFont(Font.TRUETYPE_FONT, new File("MenuFiles/chalkFont.ttf")).deriveFont(Font.PLAIN, 100);
		} catch (FontFormatException | IOException e) {	e.printStackTrace();}
		
		//Layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//JLabels
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 0.3;
		c.weighty = 0.3;
		c.anchor = GridBagConstraints.CENTER;		
		c.fill = GridBagConstraints.BOTH;
		labels[0].setFont(chalkFont.deriveFont(Font.PLAIN,50));
		labels[0].setVerticalAlignment(SwingConstants.BOTTOM);
		add(labels[0],c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 0.4;
		c.weighty = 0.4;
		//c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		labels[1].setFont(chalkFont);
		labels[1].setVerticalAlignment(SwingConstants.TOP);
		add(labels[1],c);
		
		//JButtons
		this.buttons = buttons;
	
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.3;
		c.weighty = 0.3;
		c.insets = new Insets(20,20,20,10);
		c.fill = GridBagConstraints.BOTH;
		//c.anchor = GridBagConstraints.CENTER;
		add(buttons[0],c);
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.3;
		c.weighty = 0.3;
		c.insets = new Insets(20,10,20,20);
		c.fill = GridBagConstraints.BOTH;
		//c.anchor = GridBagConstraints.CENTER;
		add(buttons[1],c);

	}
	
	//graphics
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
	
	private JButton[] buttons;
		
	public StartPanel(JButton[] buttons) {
		
		//buttons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
		
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
	
	private JButton[] buttons;
	private JComboBox enter_name;
	private String[] sample_names = {"Rahma","Jenny","Adam","Vinh","Albert","Zak","Poonam","Georgia"}; //replace with list of member names
	
	public AddBallotPanel(JButton[] buttons) {
		
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
		
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
	
	private JButton[] buttons;
	
	public ShakeTinPanel(JButton[] buttons) {
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
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
	
	private JButton[] buttons;
	
	private Avatar newAvatar; //change to member after - temporary to test physical appearance things
	private JButton[] gender_buttons = {new JButton("Male"),new JButton("Female")};
	private JButton[] skin_buttons = {new JButton(), new JButton(), new JButton(), new JButton(), new JButton()};
	private String[] skins = {"light","tanned","tanned2","dark","dark2"}; //related list for skin buttons
	private int skin_int = 0; //keep track of current skin
	private int gender_int = 0; //keep track of current gender
	//gender (male,female) x skin colors (light,tanned,tanned2,dark,dark2) x direction(up,down,right,left) x number of pictures
	private Image[][][][] sprites = new Image[2][5][4][10];  
		
	private JTextField name;
	
	public AddMemberPanel(JButton[] buttons) {
		
		//JButtons
		this.buttons = buttons;
		for (JButton b : buttons) {
			add(b);
		}
		
		//JTextField
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
