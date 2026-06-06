import java.lang.InterruptedException;
import java.time.Instant;

public class Time implements Runnable{
    private long end_Time;
    private Thread timeCount;
    public boolean allowPlay = false;
    public Time(int timeWanted)
    {
        int time = timeWanted * 60000;// the input from the user is in minutes, the methods need to use milliseconds
        //Can access abstract methods needed for the Thread to work (the method 'run')
        long current_Time = System.currentTimeMillis();// Checks current time
        end_Time = current_Time + time;// Finds the end of the session
    }

    public  void startTime()
    {
        timeCount = new Thread(this); //Constructs the Thread, and assigns it to run this class
        timeCount.start();//Starts Thread
    }

    @Override //This is from the 'implements Runnable'
    public void run() {
        while(timeCount != null) { 
            //While the Thread is active, this whole process loops
            long check_time = Instant.now().toEpochMilli();// This gives the current time in milliseconds
            if (check_time < end_Time) {
                //This checks that the session hasn't ended
                try
                {
                    Thread.sleep(1); //This gives the system a pause of 1 millisecond, and then continues the loop
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                //If the session has run out, it will dispose of all the elements and kill the program completely
                main.frame.dispose();
                main.layout.enemyFight.combatEncounter.dispose();
                System.exit(0); //This will be shown to the user
            }
        }
        }
    }