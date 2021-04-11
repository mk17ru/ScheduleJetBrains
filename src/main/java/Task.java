import java.util.Collection;

interface Task {
    // выполняет задачу
    void execute();

    // возвращает зависимости для данной задачи
    Collection<Task> dependencies();
}