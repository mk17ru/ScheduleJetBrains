import java.util.Collection;
import java.util.Objects;

public class TaskExecutor {

    void execute(Collection<Task> tasks) {
        tasks.forEach(Objects::requireNonNull);
        if (TreeBuilder.build(tasks)) {
            ExecutableSequence executableSequence = new ExecutableSequence.build(tasks);
        } else {
            System.err.println("Error there are cycle dependencies");
        }
    }
}