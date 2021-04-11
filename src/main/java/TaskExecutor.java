import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskExecutor {

    void execute(Collection<Task> tasks) {
        tasks.forEach(Objects::requireNonNull);
        TaskTree taskTree = new TaskTree();
        Collection<Task> taskFilter = tasks.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (taskTree.build(taskFilter)) {
            taskTree.execute();
        } else {
            System.err.println("Error there are cycle dependencies");
        }
    }



}