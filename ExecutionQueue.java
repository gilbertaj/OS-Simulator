

import java.util.ArrayList;

public class ExecutionQueue {

    ArrayList<PCB> queue;
    public static String proc;

    public ExecutionQueue() {
        queue = new ArrayList<>();
    }

    public void enQueue(PCB pcb) {
        queue.add(pcb);
    }

    public void deQueue() {
        queue.remove(0);
    }

    public void cycle() {
        if (queue.size() < 2) {
            return;
        } else {
            PCB temp = queue.remove(0);
            queue.add(temp);
        }

    }

    public PCB getFirst() {
        return queue.get(0);
    }

    public PCB get(int pos) {
        return queue.get(pos);
    }

    public int getSize() {
        return queue.size();
    }

    public void reset() {
        queue.clear();
    }

    public void printPCB() {
        if (queue.isEmpty()) {
            System.out.println("Execution Queue is Empty");
            return;
        }

        System.out.println("Current Execution Queue Contents");
        for (int i = 0; i < queue.size(); i++) {
            String name = queue.get(i).getName();
            int memory = queue.get(i).getMemory();
            int arrival = queue.get(i).getArrival();
            int timeElapsed = queue.get(i).getTimeElapsed();
            int counter = queue.get(i).getCounter();
            String state = queue.get(i).getState();
            int priority = queue.get(i).getPriority();
            int cpuNeeded = queue.get(i).getCpuBurst();

            System.out.println("Name: " + name +
                    "\nMemory: " + memory +
                    "\nArrival: " + arrival +
                    "\nTime Elapsed: " + timeElapsed +
                    "\nCounter: " + counter +
                    "\nState: " + state +
                    "\nPriority: " + priority +
                    "\nCPU Burst: " + cpuNeeded + "\n");
        }
    }

    public String printProc(int i) {
        if (queue.isEmpty()) {
            System.out.println("Execution Queue is Empty");
            return "";
        }

            String name = queue.get(i).getName();
            String state = queue.get(i).getState();
            int cpuNeeded = queue.get(i).getCpuTimeNeeded();
            int cpuUsed = queue.get(i).getCpuTimeUsed();
            int ioRequests = queue.get(i).getIoRequests();

            proc = ("Name: " + name +
                    "\nState: " + state +
                    "\nCPU Needed: " + cpuNeeded +
                    "\nCPU Used: " + cpuUsed +
                    "\nIO Requests: " + ioRequests +
                    "\n");

            return proc;
        }
    }
