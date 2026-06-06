import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyIn implements KeyListener {
    //The use of implementation allows me to access abstract methods
    public boolean upGo;
    public boolean downGo;
    public boolean leftGo;
    public boolean spaceHit;
    public boolean rightGo;

    public keyIn()
    {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Whenever a key is pressed by the user, it is checked to find if it is one of the arrow keys.
        //This means that each time an arrow is pressed, the variable becomes true - this is then used to update the position
        int in = e.getKeyCode();
        if(in == KeyEvent.VK_UP)
        {
            upGo = true;
        }
        if(in == KeyEvent.VK_DOWN)
        {
            downGo = true;
        }
        if(in == KeyEvent.VK_LEFT)
        {
            leftGo = true;
        }
        if(in == KeyEvent.VK_RIGHT)
        {
            rightGo = true;
        }
        if(in == KeyEvent.VK_SPACE)
        {
            spaceHit = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //this method will make the variables false, which means that the player can control the character without it moving off screen
        int in = e.getKeyCode();
        if(in == KeyEvent.VK_UP)
        {
            upGo = false;
        }
        if(in == KeyEvent.VK_DOWN)
        {
            downGo = false;
        }
        if(in == KeyEvent.VK_LEFT)
        {
            leftGo = false;
        }
        if(in == KeyEvent.VK_RIGHT) {
            rightGo = false;
        }
        if(in==KeyEvent.VK_SPACE)
        {
            spaceHit=false;
        }
    }
}
