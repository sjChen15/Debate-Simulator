import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public Main() {
		super("Debate Simulator");
		setSize(Toolkit.getDefaultToolkit().getScreenSize()); //set the size of the screen = to the monitor size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cards = new JPanel(cLayout);
		cards.add(menu,"menu");
		getContentPane().add(cards);
	
		setVisible(true);
		
		myTimer = new Timer(100,this);
		myTimer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
}

class MenuPanel extends JPanel{
	
	private JButton[] buttons = {new JButton("Start"),new JButton("Members")};
	
	public MenuPanel() {

		for (JButton b : buttons) {
			add(b);
		}
		
	}
}
