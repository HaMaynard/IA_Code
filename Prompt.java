import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Prompt extends JPanel {
    //Uses inheritance to gain access to the features of a JPanel
    public JLabel showName; //This will contain the users chosen name
    public JLabel showLoc; //This will contain the name of the current area the user is in
    public JLabel showEnemy; //This will contain the number of enemies remaining in the area
    private int line = 0; //Designates a certain line
    private String text = null;
    public String actualIn = "";
    public Prompt()
    {
        this.setPreferredSize(new Dimension(350,800));
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); //Allows the addition of multiple JLabels in a row
    }
    public void readIn(int pass)
    {
        line = pass;
        try
        {
            text = Files.readAllLines(Paths.get("input.txt")).get(line); 
            //This will read in the whole document, but only access the specific line
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        showName = new JLabel();
        if(text != null) {
            String[] info = text.split(";");
            //Breaks up the text line where there is a semi-colon, this then becomes a list of strings
            JButton allowIn = new JButton(info[1]); 
            //This will add the string held in index 1 to a JButton
            actualIn = JOptionPane.showInputDialog(allowIn, info[2]); 
            //This allows the user to input a value into the JButton
            showName.setText("<html><font size='5' color=white>" + actualIn + "</font></html>"); 
            //This displays the text prompt to the user 
        }
    }

    public void display()
    {
        //This adds the elements for the information panel to the JPanel base, and makes it visible
        this.add(Box.createHorizontalGlue()); // This allows JLabels to be spaced
        showName.setBounds(50,20,50,50);
        this.add(showName);
        this.add(Box.createGlue());
        showLoc = new JLabel("<html><font size='5' color=white>"+ main.layout.getLoc() +"</font></html>");
        showLoc.setBounds(70,20,50,50);
        this.add(showLoc);
        this.add(Box.createGlue());
        showEnemy = new JLabel("<html><font size='5' color=white>" + Layout.enemyRemaining +"</font></html>");
        showEnemy.setBounds(120,50,50,50);
        this.add(showEnemy);
    }

    public void enemyUpdate(int remains)
    {
        showEnemy.setText("<html><font size='5' color=white>" + remains +"</font></html>");
        this.add(showEnemy);
        //Each time an enemy is defeated, the number remaining will decrease
    }
    public void locUpdate(String locName)
    {
        showLoc.setText("<html><font size ='5' color=white>"+locName+"</font></html>");
        this.add(showLoc);
        //When the user changes location (by beating all the enemies), the location is updated
    }

}
