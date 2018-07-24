import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScalingLabel extends JLabel implements ComponentListener{

	public ScalingLabel(String text) {
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
		FontMetrics metrics = getFontMetrics(font);
		float size = font.getSize2D();
		float textWidth = metrics.stringWidth(getText())+100; // + 50 to add a border
		size = (float)Math.floor((getWidth() / textWidth) * size);
		setFont(font.deriveFont(Font.CENTER_BASELINE,(int)size));
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}
}
