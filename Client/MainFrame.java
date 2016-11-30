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

//MainFrame is the main class that sorts between the different panels for the gui
public class MainFrame extends JFrame{
    
    //Uses a CardLayout to switch between different JPanels
    JPanel p = new JPanel();
    CardLayout c = new CardLayout();
    MainMenu menu = new MainMenu(this);
    SearchScreen search = new SearchScreen(this);
    
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
}
