/**
 * Created by Michael on 11/25/2016.
 */
public class Clock
{

    public static int clock = 0;

    public void execute()
    {
        clock++;
    }

    public int getClock()
    {
        return clock;
    }

    public void reset(){
        clock = 0;
    }
}
