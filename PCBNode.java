
public class PCBNode
{
    PCBNode next;
    PCBNode prev;
    PCB pcb;

    public PCBNode(PCB pcb)
    {
        this.pcb = pcb;
        this.next = null;
        this.prev = null;
    }


}
