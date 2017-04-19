//Libraries for java swing GUI
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
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
import java.util.Scanner;
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

/**
 * The MainFrame class is the main class that sorts between the different panels for the gui
 * and contains some key methods
 * @author Richard Lee
 * @version 2.0
 */ 
 
public class MainFrame extends JFrame{
    
    //Uses a CardLayout to switch between different JPanels
    JPanel p = new JPanel();
    CardLayout c = new CardLayout();
    MainMenu menu = new MainMenu(this);
    SearchScreen search = new SearchScreen(this);
    LoginPage logPage = new LoginPage(this);
	HelpPage helpPage = new HelpPage(this);
	
    //Variables for purpose of connecting to database declared here
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String POST_URL = "http://localhost:3000/api/submissions/submission";
	public String loginToken = "UA";
	public boolean loggedIN = false;
	
	//File config stuff
	FileConfig fCon = new FileConfig();
    
    /**
	 * The main function called to start the GUI. Createss a new MainFrame object
	 * @param args the input arguments, although it does nothing with them 
	 */
    public static void main(String args[]) {
        new MainFrame();
    }
    
    /**
	 * This constructor creates and returns an instance of the MainFrame object
	 */
    public MainFrame() {
        super("SUPER AWESOME WOW APP");
        setResizable(false);
        setSize(820, 430);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setLayout(c);
        p.add(menu, "mPage");
        p.add(search, "sPage");
        p.add(logPage, "lPage");
		p.add(helpPage, "hPage");
        c.show(p, "lPage");
        add(p);
        setVisible(true);
    }
    
	/**
	 * This function takes the user to the main menu 
	 */
    public void showMain() {
		setSize(820, 430);
        c.show(p, "mPage");
    }
    
	/**
	 * This function takes the user to the search screen 
	 */
    public void showSearch() {
		//setBounds(0, 0, 0, 0);
		setSize(1100, 600);
        c.show(p, "sPage");
    }
	
	/**
	 * This function takes the user to the login page
	 */
	public void showLog() {
		//setSize(820, 430);
		c.show(p, "lPage");
	}
	
	/**
	 * This function takes the user to the help page 
	 */
	public void showHelp() {
		c.show(p, "hPage");
	}
	
	/**
	 * This function is called in order to find the World Of Warcraft directory and save the location
	 */
	public void findDirectory(){						
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Please select World of Warcraft Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    
		chooser.setAcceptAllFileFilterUsed(false);  
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			String directory = chooser.getSelectedFile().getAbsolutePath();
			fCon.saveFileInfo(directory, "File");
		}
	}
	
    /**
	 * This function checks to see if it can find the World of Warcraft directory 
	 * If it can, it calls the upload function, if not, it notifies the user 
	 */
	public void startSEND(){
		String directory = fCon.getFileInfo("File");
		File file1 = new File(directory + "\\WTF\\Account\\");
		if (file1.exists() && file1.isDirectory()){
			System.out.println("Found in configs\n" + file1.getAbsolutePath());
			sendPOST(file1.getAbsolutePath());
		}
		else {
			File file2 = new File("C:\\Program Files (x86)\\World of Warcraft\\WTF\\Account\\");
			if (file2.exists() && file2.isDirectory()) {
				System.out.println("Found as default");
				sendPOST(file2.getAbsolutePath());
			}
			else {
				JOptionPane.showMessageDialog(null, "Error: The directory for World of Warcraft was not found.  Please\n"+
														"select the World of Warcraft directory from the Configuration option");
			}
		}
		
	}
	
	/**
	 * This function is the upload function.  It takes a string that contains info on the location
	 * of the World of Warcraft directory and uses it to upload data to the database 
	 * @param fileString the path to the correct directory 
	 */
	public void sendPOST(String fileString){
		System.out.println(fileString);
		File file = new File(fileString);
		if (!file.exists()){
			System.out.println("File 1 doesn't exist");
			return;
		}
		String[] account = file.list();
		
		File file2 = new File(fileString + "\\" + account[0] + "\\SavedVariables\\SStagger.lua");
		System.out.println("File 2 Path: " + file2.getAbsolutePath());
		if (!file2.exists()){
			System.out.println("File 2 doesn't exist");
			return;
		}
		else{
			System.out.println("All is well");
		}
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
		File screens = new File(fileString + "\\..\\..\\Screenshots");
		String[] scNames = screens.list();
		
		// Start with line 3 and ignore the last line
		int i = 3;
		String temp;
		
		while (i < list.size() - 7){
			
			temp = list.get(i);
			String[] splitData = temp.split("\\|");
		
			System.out.println("Attempting to send post...\n");

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(POST_URL);
			httpPost.addHeader("User-Agent", USER_AGENT);
		
			httpPost.addHeader("Authorization", loginToken);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
			// Here is where we can add in any data to send to the database
<<<<<<< HEAD
			//urlParameters.add(new BasicNameValuePair("Title", "Troll gets rekt m8"));
			
			System.out.println(splitData.length);
			for (int z = 0; z < splitData.length; z++){
				
				System.out.println(splitData[z]);
				
			}
			
=======
>>>>>>> origin/master
			urlParameters.add(new BasicNameValuePair("Description", splitData[1]));
			urlParameters.add(new BasicNameValuePair("Tags", splitData[2]));
			urlParameters.add(new BasicNameValuePair("Time", splitData[4]));	
			urlParameters.add(new BasicNameValuePair("ImagePath", "app-content/images/" + scNames[i - 3]));
			urlParameters.add(new BasicNameValuePair("Character", splitData[5]));
			urlParameters.add(new BasicNameValuePair("Location", splitData[7]));
<<<<<<< HEAD
			
			urlParameters.add(new BasicNameValuePair("Chat", splitData[9]));

			//urlParameters.add(new BasicNameValuePair("SubLocation", splitData[7]));	// This is problematic due to the way the addon delimits

=======
		
>>>>>>> origin/master
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

				httpClient.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			
			i++;
		}
		
		// Copy all images to server
		File source = new File(fileString + "\\..\\..\\Screenshots");
		File dest = new File("../Server/app/app-content/images");
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
	
	/**
	 * This method takes a token and saves it and logs the user in 
	 * @param token the token for accessing the database 
	 */
	public void logInUser(String token){
		loginToken = token;
		loggedIN = true;
		showMain();
		//System.out.println("Logging in");
	}
	
	/**
	 * This method logs the user out and takes them to the login page 
	 */
	public void logOutUser(){
		loginToken = "UA";
		loggedIN = false;
		showLog();
	}
	
	/**
	 * This method returns the token for accessing the database 
	 * @return loginToken token for accessing database 
	 */
	public String getToken(){
		return loginToken;
	}
	
}