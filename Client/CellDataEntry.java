import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

public class CellDataEntry {
 
    private String tag;
    private String desc;
	private String iconPath;
	private URL url;
	private ImageIcon image;
	
 
    public CellDataEntry(String t, String d, String i) {
        tag = t;
        desc = d;
		iconPath = i;
		makeURL();
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
	
	public ImageIcon getImage() {
//if (image == null) {
			//image = new ImageIcon(iconPath);
		//}
		return image;
	}
	
	public void makeURL(){
		try{
			url = new URL(iconPath);
			Image im = ImageIO.read(url);
			image = new ImageIcon(im);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
} 