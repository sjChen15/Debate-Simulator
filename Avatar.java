import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//to be combined with the member class in the future
public class Avatar {
	private Image[][] sprites; //up,down,right,left x 10 (4x10)
	private int animate = 0;
	
	public Avatar(Image[][] sprites){
		this.sprites = sprites;
	}
	
	
	//graphics
	public void draw (Graphics g) {
		g.drawImage(sprites[2][0], 0, 0, null); //forward position
		
		for (int i = 0; i < 4; i++) { //testing animate method		
			animate(g,i,i*100,100);
		}
		
	}  
	
	public void animate(Graphics g, int direction, int x, int y) {
		int currPic = 0;
		
		if (animate == 29)  {
			animate = 0; //reset counter when it reaches the max
		}
		
		if (animate%3 == 0) {
			currPic = animate/3;
			g.drawImage(sprites[direction][currPic], x, y, null);
		}
		
		g.drawImage(sprites[direction][currPic], x, y, null);
		animate++;
	}
	
	
	
}
