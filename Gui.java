

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import static jdk.nashorn.internal.runtime.JSType.toDouble;


public class Gui extends JPanel {





        public JFrame GuiWindow;
        public JTextPane comLine;
        public JTextField user;
        public JScrollPane scroll;
        public StyledDocument styledoc;

        public String jobs[] = {"MediaPlayer", "PhotoEditing", "Messaging", "VideoGame", "VirusScan", "WebBrowser",
        "WordProcessor", "Calculator", "JavaIDE"};
        public PCBtable newtable;
        MemDisplay new_mem;
        int stopTime;
        OS os;
        public static List<Double> memory = new ArrayList<Double>();
        public static Graph mem_graph = new Graph(memory);
        ReadIn read;

        boolean t = false;

        public Gui(OS os){

            try {

                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            }
            catch (Exception exception)  {}
            this.os = os;
            stopTime = 0;
            GuiWindow = new JFrame();
            GuiWindow.setTitle("Gui");
            GuiWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mem_graph = new Graph(memory);
            newtable = new PCBtable(os);
            new_mem = new MemDisplay(os);


            comLine = new JTextPane();
            comLine.setEditable(false);
            comLine.setFont(new Font("Courier New" , Font.PLAIN, 12));
            comLine.setOpaque(false);

            styledoc = comLine.getStyledDocument();



            user = new JTextField();
            user.setEditable(true);
            user.setForeground(Color.WHITE);
            user.setCaretColor(Color.WHITE);
            user.setOpaque(false);


            user.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e){

                    String input_text = user.getText();
                    if (input_text.length()> 1){
                        performInput(input_text);
                        scrollDown();
                        user.selectAll();


                    }


                }


            });





            scroll = new JScrollPane(comLine);
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);


            GuiWindow.add(user, BorderLayout.SOUTH);

            GuiWindow.add(scroll, BorderLayout.CENTER);
            GuiWindow.getContentPane().setBackground(new Color(50, 50, 50));



            GuiWindow.setSize(1500, 600);
            GuiWindow.setLocationRelativeTo(null);
            GuiWindow.setResizable(false);
            GuiWindow.setVisible(true);

            GuiWindow.add(new_mem, BorderLayout.EAST);
            GuiWindow.add(newtable, BorderLayout.NORTH);

            memory.add(256.0);

            mem_graph = new Graph(memory);
            mem_graph.setPreferredSize(new Dimension(400, 600));
            GuiWindow.add(mem_graph, BorderLayout.WEST);

        }

        public void editGraph()
        {
            memory.add(toDouble(CacheMemory.memoryRemaining));
            mem_graph.setMemory(memory);

        }

        public void scrollUp(){
            comLine.setCaretPosition(0);

        }
        public void scrollDown(){
            comLine.setCaretPosition(comLine.getDocument().getLength());

        }

        public void print(String s, boolean trace, Color col){
            Style addStyle = comLine.addStyle("Style", null);
            StyleConstants.setForeground(addStyle, col);

            if (trace ){
                Throwable w = new Throwable();
                StackTraceElement[] e = w.getStackTrace();
                String c_var = e[0].getClassName();

                s = c_var  + s;

            }
            try{
                styledoc.insertString(styledoc.getLength(), s, addStyle);

            }
            catch(Exception ex){}

        }

        public void print_type_two(String s, boolean trace, Color color){
            print(s + "\n", trace, color);


        }

        public void performInput(String s){
            final String[] commands = s.split(" ");

            try {
                if(commands[0].equalsIgnoreCase("reset"))
                {

                    memory.clear();
                    editGraph();
                    os.scheduler.reset();
                    os.cpu.getInterruptProcessor().getIoScheduler().reset();

                    os.cpu.setInterrupt("False");
                    os.cpu.setCycle(0);
                    os.clock.reset();
                    CacheMemory.memoryRemaining = CacheMemory.totalMemory;

                    newtable.editPCBTable();
                    new_mem.editMemTable(os);

                    styledoc.remove(0,styledoc.getLength());
                }


                else if (commands[0].equalsIgnoreCase("proc")){
                    print_type_two("\n-----Execution Queue Contents-----", t, Color.WHITE);
                    for (int i = 0; i < os.scheduler.getExec().getSize(); i++) {
                        os.scheduler.getExec().printProc(i);
                        String string = os.scheduler.getExec().proc;
                        print_type_two(string, t, new Color(255, 255, 255));
                        new_mem.editMemTable(os);
                    }
                    if(os.scheduler.getExec().getSize() < 1) {
                        print_type_two("          Queue is Empty          \n", t, Color.WHITE);
                    }
                    print_type_two("----------------------------------\n", t, Color.WHITE);


                    print_type_two("\n\n-------Wait Queue Contents--------", t, Color.WHITE);
                    for (int i = 0; i < os.scheduler.getWait().getSize(); i++) {
                        os.scheduler.getWait().waitProc(i);
                        String string = os.scheduler.getWait().proc;
                        print_type_two(string, t, new Color(255, 255, 255));
                    }
                    if(os.scheduler.getWait().getSize() < 1) {
                        print_type_two("          Queue is Empty          \n", t, Color.WHITE);
                    }
                    print_type_two("----------------------------------\n", t, Color.WHITE);


                    print_type_two("\n\n--------New Queue Contents--------", t, Color.WHITE);
                    for (int i = 0; i < os.scheduler.getNewQueue().getSize(); i++) {
                        os.scheduler.getNewQueue().newProc(i);
                        String string = os.scheduler.getNewQueue().proc;
                        print_type_two(string, t, new Color(255, 255, 255));
                    }
                    if(os.scheduler.getNewQueue().getSize() < 1) {
                        print_type_two("          Queue is Empty          \n", t, Color.WHITE);
                    }
                    print_type_two("----------------------------------\n", t, Color.WHITE);
                    print_type_two("\n\n\n\n\n", t, Color.WHITE);

                }
                else if(commands[0].equalsIgnoreCase("mem")){

                    String mem = ("\nMemory Remaining: " + CacheMemory.memoryRemaining);
                    mem = mem + ("\nMemory Usage:");
                    if(os.scheduler.getExec().getSize() < 1)
                    {
                        mem = mem + ("\nNo Processes in Memory");
                    } else {
                        for (int i = 0; i < os.scheduler.getExec().getSize(); i++) {
                            mem = mem + "\n" + (os.scheduler.getExec().get(i).getName() + ": " +
                                    os.scheduler.getExec().get(i).getMemory());
                        }
                    }

                    print_type_two(mem, t, new Color(255,255,255));

                }

                else if (commands[0].equalsIgnoreCase("load") && commands.length == 1){

                    Random random = new Random();
                    int rand = random.nextInt(9);

                    String job = jobs[rand];
                    print_type_two(job, t, new Color(255,155,155));

                    read = new ReadIn();
                    System.out.println("here");
                    read.openFile(job);
                    System.out.println("here2");
                    read.readFile(job);
                    System.out.println("here3");
                    read.closeFile();
                    PCB pcb = new PCB();
                    pcb.setName(read.testArray.get(0));
                    pcb.setPriority(Integer.parseInt(read.testArray.get(1)));
                    pcb.setState("New");
                    os.scheduler.newProcess(pcb);

                    newtable.editPCBTable();
                    new_mem.editMemTable(os);

                    print_type_two("\nSUCCESS\n", t, Color.WHITE);
                }

                else if (commands[0].equalsIgnoreCase("load") && commands.length == 2){
                    String job = commands[1];

                    read = new ReadIn();
                    read.openFile(job);
                    read.readFile(job);
                    read.closeFile();
                    PCB pcb = new PCB();
                    pcb.setName(read.testArray.get(0));
                    pcb.setPriority(Integer.parseInt(read.testArray.get(1)));
                    pcb.setState("New");
                    os.scheduler.newProcess(pcb);

                    newtable.editPCBTable();
                    new_mem.editMemTable(os);

                    print_type_two("\nSUCCESS\n", t, Color.WHITE);
                }

                else if (commands[0].equalsIgnoreCase("exe") && commands.length == 2) {
                    os.stopTime = Integer.parseInt(commands[1]) + os.clock.getClock();
                    os.execute = true;
                }

                else if (commands[0].equalsIgnoreCase("exe") ){
                    os.stopTime = Integer.MAX_VALUE;
                    os.execute = true;
                }

                else if (commands[0].equalsIgnoreCase("stop")) {
                    os.stopTime = os.clock.getClock();
                }

                else if (commands[0].equalsIgnoreCase("exit") ){
                    String exe = "End and Exit simulation";

                    print_type_two(exe, t, new Color(255,255,255));
                    System.exit(0);
                }

                else if (commands[0].equalsIgnoreCase("help")) {
                    String text = "\n---HELP---\nProc\nMem\nLoad\nExe\nReset\nStop\nExit\n----------\n\n\n";

                    print_type_two(text, t, new Color(255,255,255));
                }

                else {
                    print_type_two("No Command Matching: " + s + "\nType help for list of commands",
                                    t, new Color(255,255,255));

                }

            }

            catch(Exception ex){
                print_type_two("There's an Error " + ex.getMessage(), t, new Color(255,155,155));

            }

        }

    }

