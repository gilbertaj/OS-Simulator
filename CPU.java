import java.awt.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CPU {
    Clock clock;
    int cycle;
    private final int cycleMax = 30;
    Scheduler scheduler;
    Gui gui;
    InterruptProcessor interruptProcessor;
    String interrupt;
    String interruptType;

    public CPU(Clock nclock, Scheduler nscheduler, Gui gui) {
        clock = nclock;
        scheduler = nscheduler;
        cycle = 0;
        this.gui = gui;
        interruptProcessor = new InterruptProcessor();
        interrupt = "False";
        interruptType = "False";
    }

    public InterruptProcessor getInterruptProcessor()
    {
        return interruptProcessor;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setInterrupt(String interrupt) {
        this.interrupt = interrupt;
    }

    public void detectInterrupt() {
        if(interrupt.equalsIgnoreCase("False")) {
            interrupt = interruptProcessor.signalInterrupt();
        }

        if(!interrupt.equalsIgnoreCase("False")) {
            if(scheduler.getExec().getSize() > 0) {
                if (interruptProcessor.getEvent().getPriority() >= scheduler.getExec().getFirst().getPriority()) {
                    scheduler.getExec().getFirst().setState("Wait");
                    interruptType = interrupt;
                } else {
                    interrupt = "False";
                }
            }
        }

        if(interruptType.equalsIgnoreCase("Process") && interrupt.equalsIgnoreCase("False")) {
            interruptType = "False";
        }

    }

    public void detectPreemption() {
        if(scheduler.getExec().getSize() == 0 || !interrupt.equalsIgnoreCase("False")) {
            return;
        }

        if(scheduler.getExec().get(0).getState().equalsIgnoreCase("Ready")) {
            scheduler.getExec().get(0).setState("Run");

            System.out.println("\nPreemption\n");
        }

        if(scheduler.getExec().get(0).getState().equalsIgnoreCase("Wait") &&
                                    interruptType.equalsIgnoreCase("System")) {
            scheduler.getExec().get(0).setState("Run");
            interruptType = "False";

            System.out.println("\nPreemption\n");
        } else if(scheduler.getExec().get(0).getState().equalsIgnoreCase("Wait")) {
            scheduler.cycle();
            scheduler.getExec().getFirst().setState("Run");
            cycle = 0;

            System.out.println("\nPreemption\n");
        }

        if(scheduler.getExec().get(0).getState().equalsIgnoreCase("Exit")) {
            scheduler.getExec().printPCB();
            scheduler.getWait().printPCB();
            scheduler.removePCB();

            if(scheduler.getExec().getSize() > 0) {
                scheduler.getExec().get(0).setState("Run");
            }

            System.out.println("\nProcess Removed Successfully\n");

            scheduler.getExec().printPCB();
            scheduler.getWait().printPCB();
            cycle = 0;

            System.out.println("\nPreemption\n");
        }

        if(cycle == cycleMax)
        {
            scheduler.getExec().get(0).setState("Wait");
            scheduler.cycle();
            scheduler.getExec().get(0).setState("Run");

            cycle = 0;
            System.out.println("\nPreemption\n");
        }
    }

    public void run() {

        if(!interrupt.equalsIgnoreCase("False")){


            ECB cpuECB = interruptProcessor.getEvent();
            cpuECB.setCounter(cpuECB.getCounter() + 1);
            System.out.println("\nName: " + cpuECB.getName() +
                                "\nHandler: " + cpuECB.getHandler() +
                                "\nCounter: " + cpuECB.getCounter());

            if(cpuECB.getCounter() >= cpuECB.getIoBurst()){
                interrupt = "False";
                interruptProcessor.removeEvent();
                return;
            }

            if (scheduler.getExec().getSize() > 0) {
                for (int i = 0; i < scheduler.getExec().getSize(); i++) {
                    scheduler.getExec().get(i).incrementTimeElapsed();
                }
            }

            return;
        }

        if (scheduler.getNewQueue().getSize() > 0 && cycle == 0) {
            scheduler.insertPCB();
            scheduler.getNewQueue().deQueue();
        }

        if (cycle == 1)
            gui.editGraph();

        if(scheduler.getExec().getSize() == 0) {
            return;
        }

        PCB cpuPCB = scheduler.getExec().getFirst();
        String command = cpuPCB.getInstructions().get(cpuPCB.getPointer());
        System.out.println("\n" + cpuPCB.getName() + "\n" + cpuPCB.getState());
        cycle++;
        cpuPCB.setTimeElapsed(0);
        cpuPCB.decrementCpuTimeNeeded();
        cpuPCB.incrementCpuTimeUsed();

        if (scheduler.getExec().getSize() > 1) {
            for (int i = 1; i < scheduler.getExec().getSize(); i++) {
                scheduler.getExec().get(i).incrementTimeElapsed();
            }
        }

        if (command.equalsIgnoreCase("Calculate")) {
            cpuPCB.counter++;
            System.out.println(command + ": " + cpuPCB.getCounter());

            if (cpuPCB.counter == parseInt(cpuPCB.getInstructions().get(cpuPCB.getPointer() + 1))) {
                if (cpuPCB.getInstructions().get(cpuPCB.getPointer() + 2) != null) {
                    cpuPCB.setPointer(cpuPCB.getPointer() + 2);
                    cpuPCB.setCounter(0);
                } else {
                    cpuPCB.setState("Exit");
                }
            }

        }

        if (command.equalsIgnoreCase("Yield")) {
            cpuPCB.setState("Wait");
            cpuPCB.setPointer(cpuPCB.getPointer() + 1);
            System.out.println(command);
        }

        if(command.equalsIgnoreCase("Out")){
            cpuPCB.setPointer(cpuPCB.getPointer() + 1);
            String output = scheduler.getExec().printProc(0);
            gui.print_type_two("\n--------Process Called Information--------\n" + output +
                                "------------------------------------------", true, Color.WHITE);
            System.out.println("Out");
        }

        if(command.equalsIgnoreCase("IO")) {


            String name = cpuPCB.getInstructions().get(cpuPCB.getPointer() + 1);
            int priority = Integer.parseInt(cpuPCB.getInstructions().get(cpuPCB.getPointer() + 2));

            interruptProcessor.addEvent(name, cpuPCB.getName(), priority);

            cpuPCB.ioRequests++;
            cpuPCB.setPointer(cpuPCB.getPointer() + 3);
        }

        if (command.equalsIgnoreCase("Stop")) {
            cpuPCB.setState("Exit");
            System.out.println(command);
        }

        System.out.println("Clock: " + clock.getClock() + "\nCycle: " + cycle);
    }
}