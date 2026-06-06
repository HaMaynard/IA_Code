import javax.swing.Box;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Combat extends JPanel implements Runnable
{
    //Inheritance allows access to the features of a JPanel and the implementation provides access to abstract methods for the Thread
    public Thread fighter;
    private int positionx = 400;
    private int positiony = 350;
    private final keyIn keyin = new keyIn();
    public  JFrame combatEncounter = new JFrame();
    private int enemyAttack;
    private int enemyHealth;
    private int typex=0;
    private int typey=0;
    private final JLabel end = new JLabel("<html><font size ='5' color =white"+"You have been killed, yet another sole lost"+"</font></html>");
    private int type;
    private int playerHealth;
    private JLabel showEH;
    private int Current_turn;
    private JLabel showPH = new JLabel();
    private Image showE;
    private Image showP;
    private int count =5;
    private JPanel sidePanel1 = new JPanel();
    private JPanel sidePanel2 = new JPanel();
    private JLabel showTurn;
    public Combat(){
        this.setSize(600,2000);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyin);
        this.setFocusable(true);
        showEH = new JLabel();
        showTurn = new JLabel();
        int min = 1;
        int max = 3;
        type = (int)Math.floor(Math.random()*(max-min+1)+min); //Generates either 1, 2, or 3 randomly
        playerHealth = 100;
        Current_turn = 0;
    }
    public void setEnemy()
    {
        if(type ==1)
        {
            //The randomly generated number is used to determine the enemy the player will face
            enemyHealth = 120; //The stats are slightly different for each
            enemyAttack = 15;
            showE = Toolkit.getDefaultToolkit().getImage("Ghost.png");//Gets access to a png of the pixel art for each creature
        }
        else{
            if(type==2)
            {
                enemyHealth = 110;
                enemyAttack = 25;
                showE = Toolkit.getDefaultToolkit().getImage("Gorgon.png");
            }
            else
            {
                enemyHealth = 90;
                enemyAttack = 30;
                showE = Toolkit.getDefaultToolkit().getImage("Minotaur.png");
            }
        }
    }
    public void turn()
    {
        if(Current_turn == 0)
        {
            showTurn.setText("<html><font size='5' color=white>"+"Enemy's turn! Try not to get hit"+"</font></html>");
        }
        else
        {
            showTurn.setText("<html><font size='5' color=white>"+"Your turn! Press space to attack"+"</font></html>");
        }
        showTurn.setBackground(Color.BLACK);
        sidePanel1.add(showTurn);//Lets the player see which turn it is, and how to attack
        showTurn.setVisible(true);
    }
    public void setValues(){
        combatEncounter.setExtendedState(Frame.MAXIMIZED_BOTH);//Makes it full screen
        showP = Toolkit.getDefaultToolkit().getImage("characterFight.png");//Accesses a pixel image of the avatars face
        sidePanel1.setBackground(Color.BLACK);
        sidePanel1.setSize(80,1000);
        sidePanel1.setVisible(true);
        sidePanel2.setBackground(Color.BLACK);
        sidePanel2.setSize(80,1000);
        sidePanel2.setVisible(true);
        showEH.setText("<html><font size='5' color=white>"+enemyHealth+" </font></html>");//Shows the health of the enemy
        showEH.setBackground(Color.BLACK);
        sidePanel2.add(showEH);
        showEH.setVisible(true);
        showPH.setText("<html><font size='5' color=white>"+playerHealth+"</font></html>");//Shows the players health
        showPH.setBackground(Color.BLACK);
        sidePanel2.setLayout(new BoxLayout(sidePanel2, BoxLayout.Y_AXIS));//Allows me to add more than one JLabel on a JPanel
        sidePanel2.add(showPH);
        sidePanel2.add(Box.createGlue());//Gives space
        sidePanel2.add(showEH);
        showEH.setVisible(true);
        showPH.setVisible(true);
    }
    public void displayHealth()
    {
        if(Current_turn==1&&keyin.spaceHit)
        {
            //When it is the player's turn and they use the attack button, the enemy looses health
            enemyHealth = enemyHealth-25;
            showEH.setText("<html><font size='5' color=white>"+enemyHealth+" </font></html>");
            Current_turn=0;//Changes to the enemy's turn
        }
        else
        {
            if((positionx-10<=typex&& typex<=positionx+10)&&((positiony-10<=typey&&typey<=positiony+10)))
            {
                //If the enemy attack hit the players hitbox, they will loose health
                playerHealth=playerHealth-enemyAttack;
                showPH.setText("<html><font size='5' color=white>"+playerHealth+"</font></html>");
            }
        }
        sidePanel2.add(showEH);
        sidePanel2.add(showPH);
        turn();//This calls the method that displays the turn prompts
    }
    public void addItems()
    {
        combatEncounter.add(sidePanel1, BorderLayout.WEST);
        combatEncounter.add(sidePanel2, BorderLayout.EAST);
        combatEncounter.add(this, BorderLayout.CENTER);
        //Adds everything to the base JFrame
        combatEncounter.setVisible(true);
    }
    public void startFight() {
        fighter = new Thread(this);//Creates the Thread
        fighter.start();//Starts the Thread - it will automatically call the 'run' method below
    }
    @Override
    public void run() {
        while(fighter != null)
        {
            displayHealth();//Updates the health 
            updatePosition();//Updates the players position
            repaint();//Redraws everything by calling the paintComponent() method
            if(playerHealth<=0||enemyHealth<=0)
            {
                //If either loose all health, code will be executed
                if(playerHealth <=0)
                {
                    //This means that the player has died
                    this.setBackground(Color.black);
                    end.setVisible(true);
                    this.add(end);
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    main.frame.dispose();
                    combatEncounter.dispose();
                    System.exit(0);//Stops the whole system
                }
                fighter=null;
                combatEncounter.dispose();
                //noinspection SynchronizeOnNonFinalField
                synchronized (Layout.game)
                {
                    main.layout.game.notify();//This will call the Thread in Layout to prompt it to resume
                }
            }
            try{
                long drawTime = 1000000000 / 80;
                long remainTime = drawTime / 100000;//provides time buffer for graphic update
                Thread.sleep(remainTime);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        resetValues();//The encounter will be over at this point, so the values are reset
    }

    public void resetValues()
    {
        showE = null;
        showTurn = null;
        showEH = null;
        Current_turn =0;
        this.removeAll();
    }



    public void paintComponent(Graphics g)
    {
        int minx = 50;
        int maxx = 540;
        typex = (int)Math.floor(Math.random()*(maxx - minx +1)+ minx);//Generates a random x co-ordinate in a range to spawn an enemy attack
        int maxy = 480;
        int miny = 310;
        typey = (int)Math.floor(Math.random()*(maxy - miny +1)+ miny);//Generates a random y co-ordinate in a range to spawn an enemy attack
        super.paintComponent(g);
        g.drawImage(showE,200,10,this);//This is an effective way to display the enemy png since it does not interact with any other displays
        g.drawImage(showP,200,530,this);//Same as above for the player's avatar
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE); //This sets a combat area for the player - no enemy attacks can spawn outside it
        g2.fillRect(50,300,500,10);
        g2.fillRect(50, 490,500,10);
        g2.fillRect(50,300,10,190);
        g2.fillRect(550,300,10,200);
        g2.setColor(Color.RED);//This is the player's hitbox - they control it with the keys to avoid damage
        g2.fillRect(positionx,positiony,20,20);
        if(Current_turn == 0) {
            //If it's the enemy's go to attack, the following code is executed
            if (count < 30) {
                //This means that there will be 30 attacks spawned per enemy turn
                if (type ==1 ) {
                    //This allows the attacks to appear slightly differently for each enemy
                    g2.setColor(Color.GREEN);
                    g2.fillOval(typex, typey, 20, 10);
                    if (positionx > typex) {
                        typex = typex + 10;//This will try to hit the player by limiting the range of co-ordinates it can spawn in based off the current position of the player
                    } else {
                        typex = typex - 10;
                    }
                    if (positiony > typey) {
                        typey = typey + 10;
                    } else {
                        typey = typey - 10;
                    }
                } else {
                    if (type == 2) {
                        g2.setColor(Color.GREEN);
                        g2.fillRect(typex, typey, 5, 10);
                        if (positionx > typex) {
                            typex = typex + 10;
                        } else {
                            typex = typex - 10;
                        }
                        if (positiony > typey) {
                            typey = typey + 10;
                        } else {
                            typey = typey - 10;
                        }
                    } else {
                        g2.setColor(Color.GREEN);
                        g2.fillRect(typex, typey, 10, 5);
                        if (positionx > typex) {
                            typex = typex + 10;
                        } else {
                            typex = typex - 10;
                        }
                        if (positiony > typey) {
                            typey = typey + 10;
                        } else {
                            typey = typey - 10;
                        }
                    }
                }
                count++;
            }
            else {
                Current_turn = 1;//The turn is changed to the player's
                count=0;
            }
        }
    }
    public void updatePosition()
    {
        if(keyin.upGo)
        {
            //Allows the player's hitbox to move with the arrows
            positiony = positiony-10;
        }
        if(keyin.downGo)
        {
            positiony = positiony+10;
        }
        if(keyin.leftGo)
        {
            positionx = positionx-10;
        }
        if(keyin.rightGo)
        {
            positionx= positionx+10;
        }
    }
}