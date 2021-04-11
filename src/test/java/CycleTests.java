import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;


public class CycleTests {

    @Test
    public void checkCycle() {
        ArrayList<Task> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            arrayList.add(new Task() {
                @Override
                public void execute() {
                    System.out.println(finalI);
                }

                @Override
                public Collection<Task> dependencies() {
                    return null;
                }
            });
        }
        ArrayList<Task> arrayList2 = new ArrayList<>(arrayList);

        for (int j = 11; j < 20; ++j) {
            int finalJ = j;
            arrayList.add(new Task() {
                @Override
                public void execute() {
                    System.out.println(finalJ);
                }

                @Override
                public Collection<Task> dependencies() {
                    return arrayList;
                }
            });
        }
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.execute(arrayList2);

    }
}
