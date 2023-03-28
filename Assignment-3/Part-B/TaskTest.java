package EX2_2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void testCreateTask() {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        assertEquals(taskType, t.getType());
    }

    @Test
    void testCall() throws Exception {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        assertEquals(1, (int) t.call());
    }

    @Test
    void testSetType() {
        Callable<Integer> task = () -> 1;
        TaskType taskType = TaskType.IO;
        Task<Integer> t = Task.createTask(task, taskType);
        TaskType newTaskType = TaskType.COMPUTATIONAL;
        t.setType(newTaskType);
        assertEquals(newTaskType, t.getType());
    }
}
