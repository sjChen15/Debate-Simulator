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
	
	public Main() {
		super("Debate Simulator");
		setSize(Toolkit.getDefaultToolkit().getScreenSize()); //set the size of the screen = to the monitor size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cards = new JPanel(cLayout);
		cards.add(menu,"menu");
		cards.add(start,"start");
		getContentPane().add(cards);
	
		setVisible(true);

		myTimer = new Timer(100,this);
		myTimer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(123);
		if (menu.getStart()) {
			cLayout.show(cards, "start");
		}
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
}

class MenuPanel extends JPanel implements MouseListener{
	
	private JButton[] buttons = {new JButton("Start"),new JButton("Members")};
	private boolean start,members;
	
	public MenuPanel() {
	
		addMouseListener(this);
		for (JButton b : buttons) {
			add(b);
		}	
	}
	
	//getters
	public boolean getStart() {return start;}
	public boolean getMembers() {return members;};

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

class StartPanel extends JPanel {
	private JButton[] buttons = {new JButton("Add Ballot"), new JButton("Shake Tin"), new JButton("Menu")};
	
	public StartPanel() {
		
		for (JButton b : buttons) {
			add(b);
		}
	}
}
