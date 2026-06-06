import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
public class main {

    public static Layout layout;
    public static Prompt prompt;
    public static Time session;
    public static JFrame frame = window(); //Base for main section of GUI

    public static void main(String[] args)
    {
        prompt = new Prompt();
        frame.add(prompt, BorderLayout.EAST); //Allows me to add multiple elements
        layout = new Layout();
        Layout.setLoc(0); //This will be Tartarus
        layout.startGame(); //Calls method to start the Thread
        frame.add(layout, BorderLayout.CENTER);
        frame.setVisible(true);
        session = new Time(settingTime()); //This creates the session using the method 'settingTime()' in this class
        session.startTime(); //This calls the method to start the other Thread in the Time class
        prompt.readIn(0); //Allows a specific line from 'input.txt' to be read
        prompt.display(); //Allows the player to see relevant information on the screen
    }
    public static JFrame window()
    {
        JFrame panel = new JFrame();
        panel.setSize(2000, 1000);
        panel.setLocationRelativeTo(null); //Places the JFrame in the centre of the screen
        panel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setTitle("Game");
        return panel;
    }
    public static int settingTime() {
        JButton timeIn = new JButton("How long do you want to play for?");
        String timeChose = JOptionPane.showInputDialog(timeIn,"I want to play for:"); //Allows user to input a value
        int timeTotal = Integer.parseInt(timeChose); //The input has to be checked in case it is not an integer
        return timeTotal;
    }
}