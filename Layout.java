import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.InterruptedException;
public class Layout extends JPanel implements Runnable {
    //Uses inheritance to access the feature of JPanel and Runnable to access abstract methods to use the Thread
    public static Thread game;
    public Combat enemyFight;
    public static boolean fightStatus = false;
    public static int place = 1;
    public final int updateTime = 80;
    public keyIn keyInput = new keyIn();
    private int startX = 290;
    private int startX1 = 285;
    private int startX2 = 280;
    private int startX3 = 295;
    private int startX4 = 300;
    private JLabel welcome = new JLabel();
    public static int enemyRemaining = 0;
    private int startX5 = 305;
    private int startX6 = 310;
    private int startX7 = 315;
    private int startX8 = 320;
    public static String name = "";
    private int startY = 290;
    private int startY1 = 295;
    private int steps = 1;
    private int startY2 = 300;
    private int startY3 = 305;
    private int startY4 = 315;
    private int startY5 = 320;
    private int startY6 = 325;
    private int startY7 = 330;
    private int startY8 = 345;
    private int startY9 = 355;

    public Layout() {
        this.setPreferredSize(new Dimension(1000, 400));
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.setBackground(Color.black);
        this.addKeyListener(keyInput); //This allows key inputs to be read
        this.setFocusable(true); //This allows the key inputs to be prioritised
    }
    public static void setLoc(int loc)
    {
        place = loc;
        if (place == 0)
        {
            name = "Tartarus";
            enemyRemaining = 13;
        }
        if(place==1)
        {
            name = "Elysium";
            enemyRemaining = 20;
        }
    }
    public String getLoc()
    {
        return name;
    }

    public void startGame()
    {
        game = new Thread(this); //Creates the Thread and assigns its 'task' as this class
        game.start();//This automatically calls the method 'run'
    }

    @Override
    public void run()
    {
        long drawTime = 1000000000 / updateTime;
        while (game != null)
        {
            updatePos(); //Allows the movement
            repaint(); //Updates the screen by calling paintComponent from awt
            stepCount(); //Each movement contributes to the counter which will trigger a fight
            long remainTime = drawTime;
            remainTime = remainTime / 100000;//This will act as a time buffer - it dictates the frames per second
            try
            {
                //noinspection BusyWait
                Thread.sleep(remainTime);
                //This allows the time buffer so that the actions can be read in and the display updated between loops
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            stepCount(); //This will check if it has been long enough to start a combat encounter
            if (fightStatus && enemyRemaining>0){
                //Makes sure that the conditions are met
                enemyFight = new Combat();
                enemyFight.setEnemy();
                enemyFight.setValues();
                enemyFight.addItems();
                enemyFight.startFight(); //Activates the Thread
                while(enemyFight.fighter != null) {
                    //This will mean that while a combat encounter is happening, the following code is followed
                    //noinspection SynchronizeOnNonFinalField
                    synchronized (game)
                    {
                        //Most Thread interactions have to be in a synchronised block
                        try
                        {
                            game.wait(); 
                            //This will temporarily stop this Thread from acting while there is an encounter active
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    enemyRemaining = enemyRemaining-1; //updates the enemy count
                    main.prompt.enemyUpdate(enemyRemaining); //displays the new enemy count
                }
                try {
                    //noinspection BusyWait
                    Thread.sleep(remainTime); //Acts as a time buffer so that the methods can be processed
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                fightStatus=false;
                synchronized (game)
                {
                    game.run(); //When the combat encounter is over, this Thread can begin again
                }
            }
            if(enemyRemaining ==0)
            {
                String moving = "You have cleared Tartarus, you will now enter Elysium, more battles await";
                String end = "Congratulations "+main.prompt.actualIn + "You have cleared Elysium of corruption. The gods thank you";
                if(place ==0)
                {
                    welcome.setText("<html><font size='5' color=white>" + moving +"</font></html>");
                    main.prompt.locUpdate("Elysium");
                    setLoc(1); //This will update the area
                }
                else
                {
                    welcome.setText("<html><font size='5' color=white>"+end+"/font></html>");
                }
                this.add(welcome);
                welcome.setVisible(true);
                try
                {
                    Thread.sleep(3000); //Allows enough time for the user to read the JLabel
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                this.remove(welcome);
            }
        }
    }

    public boolean stepCount() {
        if (steps % 20 == 0)
        {
            //Each time steps reaches a multiple of 20, it will meet the conditions for a combat encounter
            fightStatus = true;
            steps = steps+1; //Makes sure it doesn't continue to create combat encounters
        }
        return fightStatus;
    }

    public void paintComponent(Graphics g)
    {
        //a method from Java.awt - it is called by the method name repaint()
        super.paintComponent(g); //This 'super' refers to the inherited JPanel 
        Graphics2D g2 = (Graphics2D)g;
        if(place == 0) {
            //This code creates a pixel display of the Tartarus background I designed
            g2.setColor(new Color(165, 0, 33));
            g2.fillRect(280, 0, 45, 45);
            g2.fillRect(280, 90, 45, 45);
            g2.fillRect(280, 180, 90, 45);
            g2.fillRect(325, 450, 45, 45);
            g2.fillRect(370, 135, 45, 45);
            g2.fillRect(415, 0, 45, 45);
            g2.fillRect(460, 45, 90, 45);
            g2.fillRect(460, 135, 45, 90);
            g2.fillRect(505, 180, 45, 45);
            g2.fillRect(460, 450, 45, 45);
            g2.fillRect(550, 0, 45, 45);
            g2.fillRect(550, 450, 45, 45);
            g2.fillRect(595, 45, 45, 45);
            g2.fillRect(640, 90, 45, 135);
            g2.fillRect(640, 450, 45, 45);
            g2.fillRect(775, 135, 45, 90);
            g2.fillRect(685, 0, 45, 45);
            g2.fillRect(730, 90, 45, 45);
            g2.fillRect(775, 0, 45, 45);
            g2.setColor(new Color(212, 97, 18));
            g2.fillRect(280, 45, 45, 45);
            g2.fillRect(280, 450, 45, 45);
            g2.fillRect(325, 90, 45, 45);
            g2.fillRect(370, 45, 45, 45);
            g2.fillRect(505, 450, 45, 45);
            g2.fillRect(775, 450, 45, 45);
            g2.setColor(new Color(102, 0, 31));
            g2.fillRect(280, 135, 90, 45);
            g2.fillRect(325, 45, 45, 45);
            g2.fillRect(370, 90, 45, 45);
            g2.fillRect(370, 450, 45, 45);
            g2.fillRect(415, 45, 45, 45);
            g2.fillRect(415, 180, 45, 45);
            g2.fillRect(460, 0, 90, 45);
            g2.fillRect(460, 90, 45, 45);
            g2.fillRect(505, 135, 45, 45);
            g2.fillRect(550, 45, 45, 90);
            g2.fillRect(595, 135, 45, 45);
            g2.fillRect(595, 450, 45, 45);
            g2.fillRect(640, 0, 45, 90);
            g2.fillRect(685, 180, 45, 45);
            g2.fillRect(685, 450, 45, 45);
            g2.fillRect(730, 0, 45, 90);
            g2.fillRect(730, 135, 45, 45);
            g2.fillRect(775, 90, 45, 45);
            g2.setColor(new Color(204, 51, 0));
            g2.fillRect(325, 0, 90, 45);
            g2.fillRect(415, 90, 45, 90);
            g2.fillRect(370, 180, 45, 45);
            g2.fillRect(415, 450, 45, 45);
            g2.fillRect(505, 90, 45, 45);
            g2.fillRect(550, 135, 45, 90);
            g2.fillRect(595, 0, 45, 45);
            g2.fillRect(595, 90, 45, 45);
            g2.fillRect(595, 180, 45, 45);
            g2.fillRect(685, 45, 45, 135);
            g2.fillRect(730, 180, 45, 45);
            g2.fillRect(730, 450, 45, 45);
            g2.fillRect(775, 45, 45, 45);
            g2.setColor(new Color(51, 87, 69));
            g2.fillRect(280, 225, 540, 45);
            g2.fillRect(280, 405, 540, 45);
            g2.setColor(new Color(102, 102, 51));
            g2.fillRect(280, 270, 45, 135);
            g2.fillRect(325, 315, 45, 45);
            g2.fillRect(370, 270, 45, 45);
            g2.fillRect(415, 315, 45, 45);
            g2.fillRect(460, 270, 90, 45);
            g2.fillRect(505, 360, 45, 45);
            g2.fillRect(595, 360, 45, 45);
            g2.fillRect(640, 270, 135, 45);
            g2.fillRect(640, 315, 90, 45);
            g2.fillRect(685, 360, 90, 45);
            g2.fillRect(775, 315, 45, 45);
            g2.setColor(new Color(55, 86, 35));
            g2.fillRect(325, 270, 45, 45);
            g2.fillRect(325, 360, 180, 45);
            g2.fillRect(370, 315, 45, 45);
            g2.fillRect(415, 270, 45, 45);
            g2.fillRect(460, 315, 90, 45);
            g2.fillRect(550, 270, 90, 45);
            g2.fillRect(595, 315, 45, 45);
            g2.fillRect(640, 360, 45, 45);
            g2.fillRect(730, 315, 45, 45);
            g2.fillRect(775, 270, 45, 45);
            g2.fillRect(775, 360, 45, 45);
            g2.setColor(new Color(0, 51, 0));
            g2.fillRect(550, 315, 45, 90);
        }
        else
        {
            //This code will draw out the pixel design of Elysium
            g2.setColor(new Color(40,78,81));
            g2.fillRect(280,0,45,45);
            g2.fillRect(280,180,45,45);
            g2.fillRect(325,90,45,45);
            g2.fillRect(370,45,45,45);
            g2.fillRect(370,180,45,45);
            g2.fillRect(370,450,45,45);
            g2.fillRect(460,0,45,45);
            g2.fillRect(460,135,45,45);
            g2.fillRect(505,45,45,45);
            g2.fillRect(550,0,45,45);
            g2.fillRect(550,90,45,45);
            g2.fillRect(550,180,45,45);
            g2.fillRect(595,135,45,45);
            g2.fillRect(640,450,45,45);
            g2.fillRect(685,45,45,45);
            g2.fillRect(685,180,45,45);
            g2.setColor(new Color(62,112,103));
            g2.fillRect(280,45,45,45);
            g2.fillRect(325,135,45,45);
            g2.fillRect(325,450,45,45);
            g2.fillRect(415,0,45,45);
            g2.fillRect(415,135,45,45);
            g2.fillRect(460,450,45,45);
            g2.fillRect(505,180,45,45);
            g2.fillRect(550,45,45,45);
            g2.fillRect(550,135,45,45);
            g2.fillRect(640,90,45,45);
            g2.fillRect(685,135,45,45);
            g2.fillRect(730,90,45,45);
            g2.fillRect(730,450,90,45);
            g2.fillRect(775,0,45,45);
            g2.fillRect(775,180,45,45);
            g2.setColor(new Color(109,171,166));
            g2.fillRect(280,90,45,90);
            g2.fillRect(325,0,45,45);
            g2.fillRect(280,450,45,45);
            g2.fillRect(325,180,45,45);
            g2.fillRect(370,135,45,45);
            g2.fillRect(415,45,90,45);
            g2.fillRect(415,450,45,45);
            g2.fillRect(460,90,45,45);
            g2.fillRect(460,180,45,45);
            g2.fillRect(505,0,45,45);
            g2.fillRect(550,450,45,45);
            g2.fillRect(595,90,45,45);
            g2.fillRect(595,180,45,45);
            g2.fillRect(640,0,45,90);
            g2.fillRect(685,450,45,45);
            g2.fillRect(730,0,45,90);
            g2.fillRect(730,135,45,45);
            g2.fillRect(775,90,45,45);
            g2.setColor(new Color(80,140,130));
            g2.fillRect(325,45,45,45);
            g2.fillRect(370,0,45,45);
            g2.fillRect(370,90,90,45);
            g2.fillRect(415,180,45,45);
            g2.fillRect(505,90,45,90);
            g2.fillRect(505,450,45,45);
            g2.fillRect(595,0,45,90);
            g2.fillRect(595,450,45,45);
            g2.fillRect(640,135,45,90);
            g2.fillRect(685,0,45,45);
            g2.fillRect(685,90,45,45);
            g2.fillRect(730,180,45,45);
            g2.fillRect(775,45,45,45);
            g2.fillRect(775,135,45,45);
            g2.setColor(new Color(255,230,153));
            g2.fillRect(280,225,540,45);
            g2.fillRect(280,405,540,45);
            g2.setColor(new Color(244,140,94));
            g2.fillRect(280,270,90,45);
            g2.fillRect(370,315,45,90);
            g2.fillRect(460,270,45,45);
            g2.fillRect(505,315,45,45);
            g2.fillRect(640,315,45,90);
            g2.fillRect(730,315,45,45);
            g2.setColor(new Color(255,174,73));
            g2.fillRect(280,315,45,45);
            g2.fillRect(325,360,45,45);
            g2.fillRect(370,270,45,45);
            g2.fillRect(415,315,90,45);
            g2.fillRect(505,360,45,45);
            g2.fillRect(550,270,90,45);
            g2.fillRect(595,360,45,45);
            g2.fillRect(685,315,45,45);
            g2.fillRect(730,270,45,45);
            g2.fillRect(730,360,90,45);
            g2.setColor(new Color(255,216,94));
            g2.fillRect(280,360,45,45);
            g2.fillRect(325,315,45,45);
            g2.fillRect(415,270,45,45);
            g2.fillRect(415,360,90,45);
            g2.fillRect(505,270,45,45);
            g2.fillRect(550,315,45,90);
            g2.fillRect(595,315,45,45);
            g2.fillRect(640,270,90,45);
            g2.fillRect(685,360,45,45);
            g2.fillRect(775,270,45,90);
        }
        //This code creates the players avatar
        g2.setColor(new Color(152,50,0));
        g2.fillRect(startX,startY,25,10);
        g2.fillRect(startX1,startY1,10,10);
        g2.fillRect(startX2,startY3,10,10);
        g2.fillRect(startX1,startY4,10,5);
        g2.fillRect(startX7,startY1,5,25);
        g2.fillRect(startX8,startY3,5,10);
        g2.setColor(new Color(248,203,173));
        g2.fillRect(startX3,startY2,20,5);
        g2.fillRect(startX4,startY3,5,10);
        g2.fillRect(startX3,startY4,20,5);
        g2.setColor(new Color(0,128,128));
        g2.fillRect(startX3,startY3,5,10);
        g2.fillRect(startX5,startY3,5,10);
        g2.setColor(new Color(255,255,255));
        g2.fillRect(startX,startY3,5,10);
        g2.fillRect(startX6,startY3,5,10);
        g2.setColor(new Color(189,215,238));
        g2.fillRect(startX3,startY5,15,5);
        g2.fillRect(startX,startY6,25,20);
        g2.setColor(new Color(0,112,192));
        g2.fillRect(startX1,startY6,5,10);
        g2.fillRect(startX7,startY6,5,10);
        g2.fillRect(startX2,startY7,5,5);
        g2.fillRect(startX8,startY7,5,5);
        g2.fillRect(startX,startY8,10,10);
        g2.fillRect(startX5,startY8,10,10);
        g2.setColor(new Color(64,64,64));
        g2.fillRect(startX1,startY9,15,5);
        g2.fillRect(startX5,startY9,15,5);
    }
    public void updatePos()
    {
        if(keyInput.upGo)
        {
            //This allows the players avatar to move according to the key inputs
            startY = startY - 10;
            startY1 = startY1 -10;
            startY2 = startY2 -10;
            startY3 = startY3-10;
            startY4 = startY4-10;
            startY5 = startY5-10;
            startY6 = startY6-10;
            startY7 = startY7-10;
            startY8 = startY8-10;
            startY9 = startY9-10;
            steps++;
        }
        if(keyInput.downGo)
        {
            startY = startY +10;
            startY1 = startY1 +10;
            startY2 = startY2 +10;
            startY3 = startY3+10;
            startY4 = startY4+10;
            startY5 = startY5+10;
            startY6 = startY6+10;
            startY7 = startY7+10;
            startY8 = startY8+10;
            startY9 = startY9+10;
            steps++;
        }
        if(keyInput.leftGo)
        {
            startX = startX-10;
            startX1 = startX1-10;
            startX2 = startX2-10;
            startX3 = startX3-10;
            startX4 = startX4-10;
            startX5 = startX5-10;
            startX6 = startX6-10;
            startX7 = startX7-10;
            startX8 = startX8-10;
            steps++;
        }
        if(keyInput.rightGo)
        {
            startX = startX+10;
            startX1 = startX1+10;
            startX2 = startX2+10;
            startX3 = startX3+10;
            startX5 = startX5+10;
            startX4 = startX4+10;
            startX6 = startX6+10;
            startX7 = startX7+10;
            startX8 = startX8+10;
            steps++;
        }
    }
}