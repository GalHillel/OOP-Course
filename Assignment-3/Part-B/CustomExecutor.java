package EX2_2;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    // The ExecutorService that will execute the tasks
    private final ArrayList<Object> arr = new ArrayList<>();
    private static final int processors = Runtime.getRuntime().availableProcessors();
    public static PriorityQueue<Task> pq = new PriorityQueue<>();

    /**
     * The constructor:
     * Get the number of available processors.
     * Initialize the Executor with a thread pool of size [processors / 2,
     * processors - 1].
     * The queue size is set to 300, and the threads will wait for 300 milliseconds
     * before timing out.
     */
    public CustomExecutor() {
        super(processors / 2, processors - 1,
                300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    /**
     * Submit a task WITHOUT priority TaskType (assign it to OTHER).
     *
     * @param task A Task to submit.
     * @param <V>  The type of the task's result.
     * @return A Future representing pending completion of the task.
     */
    public <V> Future submit(Task<V> task) {
        arr.add(TaskType.OTHER);
        MyFutureTask<V> future = new MyFutureTask<>(task);
        task.setType(TaskType.OTHER);
        this.execute(future);
        return future;
    }

    /**
     * Submit a task WITHOUT priority TaskType (assign it to OTHER).
     *
     * @param call the task to submit
     * @param <V>  The type of the task's result
     * @return A Future representing pending completion of the task.
     */
    @Override
    public <V> Future submit(Callable<V> call) {
        Task<V> task = new Task<>(call);
        return submit(task);
    }

    /**
     * Create a new Task object with the given Callable and TaskType, and then
     * submit it
     */
    public <V> Future submit(Callable<V> call, TaskType type) {
        final Task<V> task = Task.createTask(call, type);
        arr.add(type);
        pq.add(task);
        return submit(task);
    }

    /**
     * Get the maximum priority TaskType of the tasks currently in the queue
     */
    public int getCurrentMax() {
        if (arr.contains(TaskType.OTHER))
            return TaskType.OTHER.getPriorityValue();
        else if (arr.contains(TaskType.IO))
            return TaskType.IO.getPriorityValue();
        else if (arr.contains(TaskType.COMPUTATIONAL))
            return TaskType.COMPUTATIONAL.getPriorityValue();
        return -1;
        // int max_priority = 0;
        // for (Runnable r : this.getQueue()) {
        // if (r instanceof MyFutureTask &&
        // ((MyFutureTask<?>) r).getPriority() > max_priority)
        // max_priority = ((MyFutureTask<?>) r).getPriority();
        //
        // }
        // return max_priority;
        // if (this.pq.isEmpty())
        // return -1;
        // else
        // return this.pq.peek().getType().getPriorityValue();
    }

    /**
     * Gracefully terminate the Executor
     */
    public void gracefullyTerminate() {
        this.shutdown();
        arr.clear();
        pq.clear();
        try {
            if (!this.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                this.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
