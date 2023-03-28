package EX2_2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExecutorTest {
    @Test
    public void testSubmitTask() throws ExecutionException, InterruptedException {
        // Create a new EX2_2.CustomExecutor
        CustomExecutor executor = new CustomExecutor();

        // Create a Callable task that returns a string
        Callable<String> task = () -> "Hello, World!";

        // Submit the task for execution
        Future<String> future = executor.submit(task, TaskType.COMPUTATIONAL);

        // Check that the task completed successfully
        assertEquals("Hello, World!", future.get());
    }

    // @Test
    // public void testGetCurrentMax() {
    // // Create a new EX2_2.CustomExecutor
    // CustomExecutor executor = new CustomExecutor();
    //
    // // Create a Callable task that returns a string
    // Callable<String> task = () -> "Hello, World!";
    //
    // assertEquals("There is no task", executor.getCurrentMax());
    // Future<String> future3 = executor.submit(task, TaskType.OTHER);
    // assertEquals("Unknown Task", executor.getCurrentMax());
    // Future<String> future2 = executor.submit(task, TaskType.IO);
    // assertEquals("IO-Bound Task", executor.getCurrentMax());
    // Future<String> future1 = executor.submit(task, TaskType.COMPUTATIONAL);
    // assertEquals("Computational Task", executor.getCurrentMax());
    // }

    @Test
    public void testGracefullyTerminate() throws InterruptedException, ExecutionException {
        // Create a new EX2_2.CustomExecutor
        CustomExecutor executor = new CustomExecutor();

        // Create a Callable task that returns a string
        Callable<String> task = () -> {
            Thread.sleep(500);
            return "Hello, World!";
        };

        Future<String> future = executor.submit(task, TaskType.COMPUTATIONAL);

        executor.gracefullyTerminate();

        try {
            future.get(500, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // Expected exception
        }
    }

    @Test
    public void test() {
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        for (int i = 0; i < 100; i++) {
            var sumTask = customExecutor.submit(task);
        }
    }

}
