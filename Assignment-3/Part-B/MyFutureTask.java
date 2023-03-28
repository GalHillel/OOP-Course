package EX2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyFutureTask<V> extends FutureTask<V> implements Comparable<MyFutureTask<V>> {
    private final Task<V> task;

    public MyFutureTask(Task<V> task) {
        super(task);
        this.task = task;
    }

    public int getPriority() {
        return this.task.getType().getPriorityValue();
    }

    @Override
    public int compareTo(MyFutureTask<V> t1) {
        return t1.task.getType().compareTo(task.getType());
    }
}
