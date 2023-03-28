package EX2_2;

import java.util.concurrent.*;

public class Task<V> implements Callable<V>, Comparable<Task<V>> {

    // The operation that the task will execute
    private final Callable<V> call;

    // The type of the task
    private TaskType type;

    /**
     * Constructor that takes only a Callable
     *
     * @param task Callable Task.
     */
    public Task(Callable<V> task) {
        this.call = task;
        this.type = TaskType.OTHER;
    }

    /**
     * Constructor that takes a Callable and a TaskType.
     *
     * @param task Callable Task.
     * @param type TaskType representing the priority of the task.
     */
    public Task(Callable<V> task, TaskType type) {
        this.call = task;
        this.type = type;
    }

    /**
     * Factory method that creates and returns a new Task object.
     *
     * @param task     Callable Task.
     * @param taskType TaskType representing the priority of the task.
     * @param <V>      Generic type.
     * @return Result value of type <V>.
     */
    public static <V> Task<V> createTask(Callable<V> task, TaskType taskType) {
        return new Task<>(task, taskType);
    }

    /**
     * Executes the operation that was passed to the Task constructor
     *
     * @return
     * @throws Exception
     */
    public V call() throws Exception {
        CustomExecutor.pq.poll();
        return this.call.call();
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public int compareTo(Task<V> task) {
        return task.getType().compareTo(this.getType());
    }
}
