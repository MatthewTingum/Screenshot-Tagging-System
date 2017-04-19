import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

/**
 * The HelpPage class sets up a JPanel that has a text area which
 * displays the text from the readme text file 
 * @author Richard Lee
 * @version 2.0
 */ 
public class HelpPage extends JPanel implements ActionListener{
	
	private MainFrame mFrame;
	private JScrollPane myScrollPane;
	private JTextArea txtArea;
	private JButton btnOk;
	
	/**
	 * This constructor creates and returns an instance of HelpPage 
	 * @param mf The MainFrame of the GUI
	 */
	public HelpPage(MainFrame mf){
		mFrame = mf;
		
		String readMeString = "";
		
		//Access the readme text file and store in a String
		try
		{
			Scanner sc = new Scanner(new File("readme.txt"));
			while (sc.hasNext())
				readMeString += sc.nextLine() + "\n";
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
		
		//Initialize components
		myScrollPane = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea(readMeString);
        btnOk = new javax.swing.JButton();

        setLayout(null);

        txtArea.setColumns(20);
        txtArea.setRows(5);
		txtArea.setEditable(false);
        myScrollPane.setViewportView(txtArea);

        add(myScrollPane);
        myScrollPane.setBounds(0, 0, 800, 360);

        btnOk.setText("OK");
        add(btnOk);
        btnOk.setBounds(700, 370, 87, 23);
		btnOk.addActionListener(this);
	}
	
	/**
	 * This is the method that is called when the button on this page is pressed
	 * @param e the ActionEvent from a button press
	 */
	public void actionPerformed(ActionEvent e){
		//Show the main menu if button is pressed 
		mFrame.showMain();
	}
}