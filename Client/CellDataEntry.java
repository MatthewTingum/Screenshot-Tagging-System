/**
 *Richard Lee
 *
 */

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

public class CellDataEntry {
 
    private String tag;
    private String desc;
	private String iconPath;
	private String character;
	private String location;
	private ImageIcon image;
	
 
    public CellDataEntry(String t, String d, String c, String l, String i) {
        tag = t;
        desc = d;
		iconPath = i;
		character = c;
		location = l;
		image = new ImageIcon(iconPath);
    }
 
    public String getTag() {
        return tag;
    }
 
    public void setTag(String t) {
        tag = t;
    }
 
    public String getDesc() {
        return desc;
    }
	
	public String getChar(){
		return character;
	}
	
	public String getLoc(){
		return location;
	}
	
	public ImageIcon getImage() {
		return image;
	}
} 