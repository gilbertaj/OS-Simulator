

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;

public class MemDisplay extends JPanel {

    class Table {


        String ioInterrupt;
        int memory;
        int cycle;
        int clock;
        String priority;
        Integer counter;
        String ioBurst;

        String ioName;
        String ioPriority;
        String ioHandler;
        int ioCounter;

        OS os;

        public Table(OS os) {
            this.ioInterrupt = "";
            this.memory = CacheMemory.memoryRemaining;
            this.cycle = 0;
            this.clock = 0;
            this.priority = "";
            this.counter = null;
            this.ioBurst = "";

            ioName = "";
            ioPriority = "";
            ioHandler = "";
            ioCounter = 0;

            this.os = os;
        }

        public void editTable() {
            if(!os.cpu.interrupt.equalsIgnoreCase("False")){
                this.ioInterrupt = os.cpu.interruptProcessor.getEvent().getName();
                this.counter = os.cpu.interruptProcessor.getEvent().getCounter();
                this.ioBurst = Integer.toString(os.cpu.interruptProcessor.getEvent().getIoBurst());
                this.priority = Integer.toString(os.cpu.interruptProcessor.getEvent().getPriority());
            } else {
                this.ioInterrupt = null;
                this.counter = null;
                this.ioBurst = "";
                this.priority = "";
            }
            this.memory = CacheMemory.memoryRemaining;
            this.cycle = os.cpu.cycle;
            this.clock = os.clock.getClock();
        }

        public void editTable(int i) {
            ArrayList<ECB> temp = os.cpu.getInterruptProcessor().getIoScheduler().getEventQueue().print();
            this.ioName = temp.get(i).getName();
            this.ioPriority = Integer.toString(temp.get(i).getPriority());
            this.ioHandler = temp.get(i).getHandler();
            this.ioCounter = temp.get(i).getCounter();

        }


    }

    Table prac;
    Object[][] memory_table = new Object[13][8];
    String[] mem_col = {"Additional Resources", ""};
    JTable mem_Jtable;

    public MemDisplay(OS os){
        super(new GridLayout(1,1));
        prac = new Table(os);
        memory_table = new Object[16][2];

        memory_table[0][0] = "Memory Left: ";
        memory_table[1][0] = "Clock: ";
        memory_table[2][0] = "Current CPU Cycle: ";
        memory_table[4][0] = "IO Interrupt - Priority: " + prac.priority;
        memory_table[7][0] = "Waiting IO Interrupts";

        memory_table[0][1] = prac.memory;
        memory_table[1][1] = prac.clock;
        memory_table[2][1] = prac.cycle;
        memory_table[4][1] = "IO Burst Time: " + prac.ioBurst;
        memory_table[5][1] = prac.counter;
        memory_table[5][0] = prac.ioInterrupt;

        mem_Jtable = new JTable(memory_table, mem_col);
        mem_Jtable.setPreferredScrollableViewportSize(new Dimension(300, 200));

        JScrollPane scrollPane = new JScrollPane(mem_Jtable);

        add(scrollPane);
    }

    public void editMemTable(OS os) {

        memory_table[0][0] = "Memory Left: ";
        memory_table[1][0] = "Clock: ";
        memory_table[2][0] = "Current CPU Cycle: ";
        memory_table[3][0] = null;
        memory_table[6][0] = null;
        memory_table[7][0] = "Waiting IO Interrupts";

        prac.editTable();
        memory_table[4][0] = "IO Interrupt - Priority: " + prac.priority;
        memory_table[0][1] = prac.memory;
        memory_table[1][1] = prac.clock;
        memory_table[2][1] = prac.cycle;
        memory_table[3][1] = null;
        memory_table[4][1] = "IO Burst Time: " + prac.ioBurst;
        memory_table[5][1] = prac.counter;
        memory_table[5][0] = prac.ioInterrupt;
        memory_table[6][1] = null;
        memory_table[7][1] = null;
//        mem_Jtable.repaint();

        int i;
        if(os.cpu.getInterruptProcessor().getIoScheduler().getEventQueue().getSize() > 0 &&
                os.cpu.interrupt.equalsIgnoreCase("False")) {
            i = 0;
        } else {
            i = 1;
            memory_table[8][0] = null;
            memory_table[8][1] = null;
        }

        while( i < os.cpu.getInterruptProcessor().getIoScheduler().getEventQueue().getSize() && i < 8)
        {
            prac.editTable(i);
            memory_table[8+i][0] = "N: " + prac.ioName + " | H: " + prac.ioHandler;
            memory_table[8+i][1] = "Priority: " + prac.ioPriority + " | Counter: " + prac.ioCounter;

            i++;
        }
        for (int j = os.cpu.getInterruptProcessor().getIoScheduler().getEventQueue().getSize(); j < 8; j++)
        {
            memory_table[8+j][0] = null;
            memory_table[8+j][1] = null;
        }
        mem_Jtable.repaint();
    }







}