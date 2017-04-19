import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The CellDataEntry class stores all the information of an entry of the database.
 * @author Richard Lee
 * @version 2.0
 */

public class CellDataEntry {
 
    private String tag;
    private String desc;
	private String iconPath;
	private String character;
	private String location;
	private ImageIcon image;
	
	/**
	 * The constructor for the CellDataEntry object
	 * @param t the tag of the entry
	 * @param d the description of the entry
	 * @param c the character name of the entry
	 * @param l the loccation of the entry
	 * @param i the path to the image to be used
	 */
    public CellDataEntry(String t, String d, String c, String l, String i) {
        tag = t;
        desc = d;
		iconPath = i;
		character = c;
		location = l;
		image = new ImageIcon(iconPath);
    }
 
	/**
	 * This get method returns the tag string value
	 * @return tag The tag of the associated entry
	 */
    public String getTag() {
        return tag;
    }
 
	/**
	 * This get method returns the description string value
	 * @return desc The description of the associated entry
	 */
    public String getDesc() {
        return desc;
    }
	
	/**
	 * This get method returns the character name
	 * @return character The character name associated with the entry
	 */
	public String getChar(){
		return character;
	}
	
	/**
	 * This get method returns the location name
	 * @return location The location name associated with the entry
	 */
	public String getLoc(){
		return location;
	}
	
	/**
	 * This get method returns the ImageIcon
	 * @return image The ImageIcon associated with the entry
	 */
	public ImageIcon getImage() {
		return image;
	}
} 