import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class HelpPage extends JPanel implements ActionListener{
	
	private MainFrame mFrame;
	private JScrollPane myScrollPane;
	private JTextArea txtArea;
	private JButton btnOk;
	
	public HelpPage(MainFrame mf){
		mFrame = mf;
		
		String readMeString = "";
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
	
	public void actionPerformed(ActionEvent e){
		mFrame.showMain();
	}
}