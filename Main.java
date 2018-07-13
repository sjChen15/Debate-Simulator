import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener{
	
	private Timer myTimer;
	private JPanel cards;
	private CardLayout cLayout = new CardLayout();
	
	private MenuPanel menu = new MenuPanel();
	private StartPanel start = new StartPanel();
	private MembersPanel members = new MembersPanel();
	private AddBallotPanel addBallot = new AddBallotPanel();
	private AddMemberPanel addMember = new AddMemberPanel();
	private ShakeTinPanel shakeTin = new ShakeTinPanel();
	private DebatesPanel debates = new DebatesPanel();
	
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

		myTimer = new Timer(100,this);
		myTimer.start();
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

class AddMemberPanel extends JPanel implements MouseListener{
	
	private JButton[] buttons = {new JButton("Confirm"), new JButton("Back")};
	private boolean confirm,back;
	
	public AddMemberPanel() {
		
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
