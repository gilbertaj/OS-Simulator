import static java.lang.Integer.parseInt;

public class Scheduler {

    public static ExecutionQueue exec;
    public static WaitQueue wait;
    public static WaitQueue newQueue;
    public Clock clock;

    public Scheduler(Clock nclock)
    {
        exec = new ExecutionQueue();
        wait = new WaitQueue();
        newQueue = new WaitQueue();
        clock = nclock;
    }

    //Insert into new Queue
    public static void newProcess(PCB pcb)
    {
        newQueue.enQueue(pcb);
    }

    //Insert PCB into the proper queue
    public void insertPCB() {
        if (newQueue.getSize() != 0) {

            PCB pcb = newQueue.getFirst();
            ReadIn read = new ReadIn();
            read.openFile(pcb.name + "Proc");
            read.readFile(pcb.name + "Proc");
            read.closeFile();
            pcb.setMemory(parseInt(read.testArray.get(0)));
            pcb.setCpuTimeNeeded(parseInt(read.testArray.get(1)));

            read.testArray.remove(0);
            read.testArray.remove(0);

            pcb.setInstructions(read.testArray);
            if (pcb.memory > CacheMemory.memoryRemaining) {
                pcb.setState("Ready");
                wait.enQueue(pcb);
            } else {
                if(exec.getSize() > 0) {
                    pcb.setState("Wait");
                } else {
                    pcb.setState("Run");
                }
                exec.enQueue(pcb);
                CacheMemory.memoryRemaining = CacheMemory.memoryRemaining - pcb.getMemory();
                pcb.setArrival(clock.getClock());
            }
        }
    }

    //Remove the PCB from queue
    public void removePCB()
    {
        if(exec.getSize() == 0) {
            return;
        }

        PCB pcb = exec.getFirst();
        if (pcb.state.equalsIgnoreCase("Exit"))
        {
            CacheMemory.memoryRemaining = CacheMemory.memoryRemaining + pcb.memory;
            exec.deQueue();

            if(wait.getSize() > 0)
            {
                if (wait.getFirst().getMemory() <= CacheMemory.memoryRemaining) {
                    PCB temp = wait.deQueue();
                    temp.setState("Wait");
                    temp.arrival = clock.getClock();
                    exec.enQueue(temp);
                    CacheMemory.memoryRemaining = CacheMemory.memoryRemaining - temp.memory;
                }
            }

        }
    }

    public void cycle(){
        exec.cycle();
    }

    public int getWait(PCB pcb)
    {
        return pcb.timeElapsed;
    }

    //Set wait(?) for a given process
    public void setWait(PCB pcb, int wait)
    {
        pcb.timeElapsed = wait;
    }

    public int getArrival(PCB pcb)
    {
        return pcb.arrival;
    }

    public void setArrival(PCB pcb, int arrival)
    {
        pcb.arrival = arrival;
    }

    public int getCPUTime()
    {
        return clock.getClock();
    }

    public ExecutionQueue getExec(){
        return exec;
    }

    public WaitQueue getWait() {
        return wait;
    }

    public WaitQueue getNewQueue() {
        return  newQueue;
    }

    public void reset() {
        exec.reset();
        wait.reset();
        newQueue.reset();
    }
}
