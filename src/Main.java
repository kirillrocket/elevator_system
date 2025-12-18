import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Система управления лифтами ---");

        Elevator elevator1 = new Elevator(1, 0);
        Elevator elevator2 = new Elevator(2, 6);
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        elevators.add(elevator2);
        Dispatcher dispatcher = new Dispatcher(elevators);

        Thread t1 = new Thread(elevator1);
        Thread t2 = new Thread(elevator2);
        Thread dispatcherThread = new Thread(dispatcher);
        t1.start();
        System.out.println("Лифт 1 начал работу");
        t2.start();

        dispatcherThread.start();
        System.out.println("Лифт 2 начал работу");

        System.out.println("\n--- Открыт прием заявок ---");

        dispatcher.addRequest(new Request(3, "UP", 7));
        Thread.sleep(2000);

        dispatcher.addRequest(new Request(0, "UP", 7));
        Thread.sleep(2000);

        dispatcher.addRequest(new Request(5, "DOWN", 1));

        Thread.sleep(5000);

        t1.interrupt();
        System.out.println("\nЛифт 1 завершил работу");
        t2.interrupt();
        System.out.println("Лифт 2 завершил работу");
        dispatcherThread.interrupt();
        System.out.println("Диспетчер завершил работу");

        System.out.println("\n--- Программа завершена ---");
    }
}