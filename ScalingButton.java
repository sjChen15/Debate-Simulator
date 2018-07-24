import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

public class ScalingButton extends JButton implements ComponentListener{
	public ScalingButton(String text) {
		super(text);
		addComponentListener(this);
		
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
	}
	@Override
	public void componentResized(ComponentEvent e) {
		Font font = getFont();
		//had to do by height because words are different lengths and don't want different font sizes
		float size = getHeight()/4; //this was a guess and check thing I still don't know what font size is based on
		setFont(font.deriveFont(font.getStyle(),(int)size));
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}
	
}
