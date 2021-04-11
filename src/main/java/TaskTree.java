import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskTree {

    private static final HashMap<Task, VertexState> used = new HashMap<>();
    private static final List<Task> executableSequence = Collections.synchronizedList(new ArrayList<Task>());

    private enum VertexState {
        INTERNAL, USED, UNUSED
    }

    public void execute() {
        fillUsed(executableSequence);
        for (Task task : executableSequence) {
            execute(task);
        }
    }

    private static void execute(Task currentTask) {
        final ExecutorService service = Executors.newCachedThreadPool();
        if (currentTask.dependencies() == null) {
            for (Task task : currentTask.dependencies()) {
                service.execute(() -> execute(task));
            }
        }
        service.shutdown();
        currentTask.execute();
        used.put(currentTask, VertexState.USED);
    }


    public TaskTree() {
    }

    public boolean build(Collection<? extends Task> tasks) {
        fillUsed(tasks);
        for (Map.Entry<Task, VertexState> vertex : used.entrySet()) {
            if (vertex.getValue() == VertexState.UNUSED) {
                boolean f = addTask(vertex.getKey());
                if (!f) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean addTask(Task task) {
        used.put(task, VertexState.INTERNAL);
        executableSequence.add(task);
        if (task.dependencies() != null) {
            for (Task currentTask : task.dependencies()) {
                if (used.get(currentTask) == VertexState.INTERNAL) {
                    return false;
                }
                if (used.get(currentTask) == VertexState.UNUSED) {
                    if (!addTask(currentTask)) {
                        return false;
                    }
                }
            }
        }
        used.put(task, VertexState.USED);
        return true;
    }

    private void fillUsed(Collection<? extends Task> collection) {
        used.clear();
        for (Task task : collection) {
            used.put(task, VertexState.UNUSED);
        }
    }


}
