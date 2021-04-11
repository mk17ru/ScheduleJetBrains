import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionTask implements Task {

   private final Task task;
//    private List<ExecutionTask> taskDependencies;
    private final TaskTree taskTree;
    private final AtomicInteger countDependencies;
    public ExecutionTask(Task task, TaskTree taskTree) {
        this.task = task;
        this.taskTree = taskTree;
        this.countDependencies = new AtomicInteger(task.dependencies().size());
    }
//
    @Override
    public void execute() {
        try {
            task.execute();
        } catch (Exception exception) {
            System.err.println("Error with task execute" + exception.getMessage());
        }
//        for (ExecutionTask task : taskDependencies) {
//            if (task.countDependencies.decrementAndGet() == 0) {
//                taskTree.getAllowedTasks().add(task);
//            }
//        }
    }

    @Override
    public Collection<Task> dependencies() {
        return task.dependencies();
    }
//
//
//    public void setTaskDependence(ExecutionTask task) {
//        this.taskDependencies.add(task);
//    }
//
//    public List<ExecutionTask> getTaskDependencies() {
//        return taskDependencies;
//    }
}
