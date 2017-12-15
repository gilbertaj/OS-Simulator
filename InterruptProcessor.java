
public class InterruptProcessor
{
    IOScheduler ioScheduler;
    IOBurst ioBurst;
    String interrupt;

    public InterruptProcessor() {
        ioScheduler = new IOScheduler();
        ioBurst = new IOBurst();
        interrupt = "False";
    }

    public String signalInterrupt()
    {

        return ioScheduler.startIO();
    }

    public void addEvent(String name, String handler, int priority)
    {
        ECB ecb = new ECB();
        ecb.setName(name);
        ecb.setHandler(handler);
        ecb.setPriority(priority);
        ecb.setIoBurst(ioBurst.generateIOBurst());
        ioScheduler.insertECB(ecb);
    }

    public ECB getEvent()
    {
        return ioScheduler.getEventQueue().peek();
    }

    public void removeEvent() {
        ioScheduler.removeECB();
    }

    public IOScheduler getIoScheduler() {
        return ioScheduler;
    }
}
