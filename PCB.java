
import java.util.ArrayList;

public class PCB {

    String state;
    String name;
    ArrayList<String> instructions = new ArrayList<>();
    int memory;
    int arrival;
    int timeElapsed;
    int counter;
    int priority;
    int cpuBurst;
    int ioRequests;
    int pointer;
    int cpuTimeNeeded;
    int cpuTimeUsed;


    public PCB() {
        this.name = "Name";
        this.priority = 0;
        this.state = "New";
        this.arrival = 0;
        this.timeElapsed = 0;
        this.counter = 0;
        this.instructions = null;
        this.cpuBurst = 0;
        this.ioRequests = 0;
        this.pointer = 0;
        int cpuTimeNeeded = 0;
        int cpuTimeUsed = 0;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public int getMemory() {
        return memory;
    }

    public String getName() {
        return name;
    }

    public int getArrival() {
        return arrival;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public int getCounter() {
        return counter;
    }

    public int getPriority() {
        return priority;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public int getIoRequests() {
        return ioRequests;
    }

    public int getCpuTimeNeeded() {
        return cpuTimeNeeded;
    }

    public int getCpuTimeUsed() {
        return cpuTimeUsed;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public void setMemory(int memory)
    {
        this.memory = memory;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public void incrementTimeElapsed() {
        timeElapsed++;
    }

    public void incrementCpuTimeUsed() {
        cpuTimeUsed++;
    }

    public void decrementCpuTimeNeeded() {
        cpuTimeNeeded--;
    }

    public void printMemory()
    {
        memory = this.memory;
        System.out.println(memory);
    }

    public void setTimeElapsed(int time)
    {
        this.timeElapsed = time;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public void setCpuTimeNeeded(int time) {
        this.cpuTimeNeeded = time;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public void printPCB()
    {
        String name = this.name;
        int memory = this.memory;
        int arrival = this.arrival;
        int timeElapsed = this.timeElapsed;
        int counter = this.counter;
        String state = this.state;
        int priority = this.priority;
        int cpuBurst = this.cpuBurst;

        System.out.println("Name: " + name + "\n" + "Memory: " + memory + "\n" + "Arrival: " + arrival + "\n" + "Time Elapsed: " + timeElapsed +
                "\n" + "Counter: " + counter + "\n" + "State: " + state + "\n" + "Priority: " + priority + "\n" + "CPU Burst: " + cpuBurst);

    }
}



