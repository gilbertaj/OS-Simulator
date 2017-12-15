import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class EventQueue
{
    private static PriorityQueue<ECB> queue;

    public EventQueue() {
        queue = new PriorityQueue<>(new Comparator<ECB>() {
            @Override
            public int compare(ECB first, ECB last) {
                Integer num1 = first.getPriority();
                Integer num2 = last.getPriority();
                return num2.compareTo(num1);
            }
        });
    }

    public void enQueue(ECB event) {
        queue.add(event);
    }

    public ECB peek() {
        return queue.peek();
    }

    public ECB deQueue() {
        return queue.poll();
    }

    public int getSize() {
        return queue.size();
    }

    public void reset() {
        queue.clear();
    }

    public ArrayList<ECB> print() {
        ECB[] temp = queue.toArray(new ECB[queue.size()]);
        ArrayList<ECB> list = new ArrayList<ECB>(Arrays.asList(temp));
        return list;
    }
}

