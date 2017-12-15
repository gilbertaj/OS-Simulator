
public class IOScheduler
{
    EventQueue eventQueue;
    String interrupt;

    public IOScheduler() {
        eventQueue = new EventQueue();
        interrupt = "False";
    }

    public void insertECB(ECB ecb) {
        eventQueue.enQueue(ecb);
    }

    public void removeECB() {
        if(eventQueue.getSize() < 1) {
            return;
        }

        eventQueue.deQueue();
    }

    public String startIO()
    {
        if(eventQueue.getSize() > 0) {
            if(eventQueue.peek().getHandler().equalsIgnoreCase("System")) {
                interrupt = "System";
            } else {
                interrupt = "Process";
            }
        } else {
            interrupt = "False";
        }

        return interrupt;
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public void reset() {
        eventQueue.reset();
    }

}
