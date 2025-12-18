import java.util.*;
import java.util.concurrent.*;

public class Dispatcher implements Runnable {
    private List<Elevator> elevators;
    private BlockingQueue<Request> requests = new LinkedBlockingQueue<>();
    public Dispatcher(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void addRequest(Request request) {
        requests.add(request);
        System.out.println("Диспетчер получил заявку: с этажа " + request.floor_req + " на " + request.tarFloor);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Request request = requests.take();
                Elevator bestElevator = null;
                int minDistance = Integer.MAX_VALUE;

                for(Elevator elevator : elevators) {
                    if(elevator.getStatus().equals("IDLE")) {
                        int distance = Math.abs(elevator.getFloor_cur() - request.floor_req);
                        if(distance < minDistance) {
                            minDistance = distance;
                            bestElevator = elevator;
                        }
                    }
                }

                if(bestElevator != null) {
                    System.out.println("Диспетчер назначил лифт " + bestElevator.getId() + " на заявку с этажа " + request.floor_req);
                    bestElevator.addTargetFloor(request.floor_req);
                }
                else {
                    System.out.println("Все лифты заняты, ждем...");
                    requests.put(request); // возвращаем заявку в очередь
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}