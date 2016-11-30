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

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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
		System.out.println("Attempting to send post...\n");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(POST_URL);
		httpPost.addHeader("User-Agent", USER_AGENT);
		
		// This is where the user auth token will be (Using a static one for now -- linked to a test account)
		httpPost.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ODMxZjVjMzNkYjBhODE5MzAwNGVmODAiLCJpYXQiOjE0Nzk2Nzg4OTB9.Zc03s4RXZmydhAUb-rb4AbQwAXbZZ56ICMwG_0SI5iM");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
		// Here is where we can add in any data to send to the database
		urlParameters.add(new BasicNameValuePair("Title", "Troll gets rekt m8"));
		urlParameters.add(new BasicNameValuePair("Description", "description goes here"));
		urlParameters.add(new BasicNameValuePair("Tags", "Troll,pokemon go,lettuce"));
		urlParameters.add(new BasicNameValuePair("Time", "4-20-69:00,00,00"));		// Date/Time format still needs to be figured out
		urlParameters.add(new BasicNameValuePair("ImagePath", "C:\\data\\images\\image01.png"));

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
	}
}
