import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Created by Michael on 11/25/2016.
 */
public class CommandInterface
{
    ReadIn read;
    ExecutionQueue execqueue;
    Scheduler scheduler;

    public CommandInterface(Scheduler newScheduler)
    {
        scheduler = newScheduler;
        execqueue = scheduler.getExec();
    }

    public void proc()
    {
//        for (int i; i = unfinished processes; i++)
//        getState();
//        getCPUTime();
//        getPriority();
//        getIORequests();
    }

    public String mem()
    {
        String mem = ("\nMemory Remaining: " + CacheMemory.memoryRemaining);
        mem = mem + ("\nMemory Usage:");
        if(scheduler.getExec().getSize() < 1)
        {
            mem = mem + ("\nNo Processes in Memory");
            return mem;
        }

        for (int i = 0; i < scheduler.getExec().getSize(); i++)
        {
            mem = mem + "\n" + (scheduler.getExec().get(i).getName() + ": " +
                                scheduler.getExec().get(i).getMemory());
        }
        System.out.println(mem);
        return mem;
    }

    public void load(String job)
    {
        read = new ReadIn();
        read.openFile(job);
        read.readFile(job);
        read.closeFile();
        PCB pcb = new PCB();
        pcb.setName(read.testArray.get(0));
        pcb.setPriority(Integer.parseInt(read.testArray.get(1)));
        pcb.setState("New");
        scheduler.newProcess(pcb);


    }

    public void reset()
    {
//        Clear everything
    }

    public void promptUser()
    {
//        get command from user
    }

}
