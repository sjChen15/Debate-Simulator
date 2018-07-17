import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//to be combined with the member class in the future
public class Avatar {
	private String gender,skin_colour;
	private Image avatar;
	
	public Avatar(String gender, String skin_colour) throws IOException{
		this.gender = gender;
		this.skin_colour = skin_colour;
	

		if (skin_colour.charAt(skin_colour.length()-1) == '2') //because i am bad at naming files we have to take this extra step
			avatar = ImageIO.read(new File("Avatar/body/"+gender+"/"+skin_colour+"/"+skin_colour+"_19.png"));
		else
			avatar = ImageIO.read(new File("Avatar/body/"+gender+"/"+skin_colour+"/"+skin_colour+"19.png"));
	
	}
	
	public void draw (Graphics g) {
		g.drawImage(avatar, 0, 0, null);
	}
	
	
}
