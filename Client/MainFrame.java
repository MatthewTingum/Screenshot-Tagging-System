//Libraries for java swing GUI
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.CardLayout;
import java.util.Random;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Libraries for connecting to database
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.commons.io.FileUtils;

//MainFrame is the main class that sorts between the different panels for the gui
public class MainFrame extends JFrame{
    
    //Uses a CardLayout to switch between different JPanels
    JPanel p = new JPanel();
    CardLayout c = new CardLayout();
    MainMenu menu = new MainMenu(this);
    SearchScreen search = new SearchScreen(this);
	
    //Variables for purpose of connecting to database declared here
	private static final String USER_AGENT = "Mozilla/5.0";

	//private static final String GET_URL = "http://localhost:9090/SpringMVCExample";

	private static final String POST_URL = "http://localhost:3000/api/submissions/submission";
    
    //Creates a new MainFrame object
    public static void main(String args[]) {
        new MainFrame();
    }
    
    //Initializes the GUI's variables
    public MainFrame() {
        super("SUPER AWESOME WOW APP");
        setResizable(false);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setLayout(c);
        p.add(menu, "mPage");
        p.add(search, "sPage");
        c.show(p, "mPage");
        add(p);
        setVisible(true);
    }
    
    //This function activates when a button is pushed that should take you to the Main Menu
    public void showMain() {
        c.show(p, "mPage");
    }
    
    //This function activates when a button is pushed that should take you to the Search Page
    public void showSearch() {
        c.show(p, "sPage");
    }
	
    //Function connects to database and attempts to send data to it
	public void sendPOST(){
		
		// Find and open a log file
		File file = new File("C:\\Program Files (x86)\\World of Warcraft\\WTF\\Account\\");
		String[] account = file.list();
		
		File file2 = new File("C:\\Program Files (x86)\\World of Warcraft\\WTF\\Account\\" + account[0] + "\\SavedVariables\\SStagger.lua");
		BufferedReader reader2 = null;
		List<String> list = new ArrayList<String>();
		
		try {
			reader2 = new BufferedReader(new FileReader(file2));
			String text = null;

			while ((text = reader2.readLine()) != null) {
				list.add(text);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader2 != null) {
				reader2.close();
				}
			} catch (IOException e) {
			}
		}
		
		// Get all screenshot names (Must be done here due to limitations of WoW addon)
		File screens = new File("C:\\Program Files (x86)\\World of Warcraft\\Screenshots\\");
		String[] scNames = screens.list();
		
		// Start with line 3 and ignore the last line
		int i = 3;
		String temp;
		
		while (i < list.size() - 1){
			
			//System.out.println(list.get(i));
			temp = list.get(i);
			String[] splitData = temp.split("\\|");
			//System.out.println(splitData[0]);
		
			System.out.println("Attempting to send post...\n");

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(POST_URL);
			httpPost.addHeader("User-Agent", USER_AGENT);
		
			// This is where the user auth token will be (Using a static one for now -- linked to a test account)
			httpPost.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ODMxZjVjMzNkYjBhODE5MzAwNGVmODAiLCJpYXQiOjE0Nzk2Nzg4OTB9.Zc03s4RXZmydhAUb-rb4AbQwAXbZZ56ICMwG_0SI5iM");

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
			// Here is where we can add in any data to send to the database
			//urlParameters.add(new BasicNameValuePair("Title", "Troll gets rekt m8"));
			urlParameters.add(new BasicNameValuePair("Description", splitData[1]));
			urlParameters.add(new BasicNameValuePair("Tags", splitData[2]));
			urlParameters.add(new BasicNameValuePair("Time", splitData[4]));		// Date/Time format still needs to be figured out
			urlParameters.add(new BasicNameValuePair("ImagePath", "app-content/images/" + scNames[i - 3]));
			urlParameters.add(new BasicNameValuePair("Character", splitData[5]));
			//urlParameters.add(new BasicNameValuePair("Server", splitData[5]));	// The server doesn't like chinese chars
			urlParameters.add(new BasicNameValuePair("Location", splitData[7]));
			//urlParameters.add(new BasicNameValuePair("SubLocation", splitData[7]));	// This is problematic due to the way the addon delimits

			try{
				HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
				httpPost.setEntity(postParams);

				CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

				System.out.println("POST Response Status:: "
					+ httpResponse.getStatusLine().getStatusCode());

				BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();

				// print result
				System.out.println(response.toString());
				httpClient.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			
			i++;
		}
		
		// Copy all images to server
		File source = new File("C:\\Program Files (x86)\\World of Warcraft\\Screenshots");
		File dest = new File("C:\\Matt\\School\\Senioritus\\the one thing\\app\\app-content\\images");
		
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Delete files from source
		try {
			FileUtils.cleanDirectory(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Clear log file
		file2.delete();
		
	}
}

/*
datebasename = {
	"SESSION START AT TINE"
	"tags|descript|filename|asdsad|asdsad|adsad|asdsad" [1]
	
	
	
	
	
	
	
}
*/
