import java.util.concurrent.*;

public class Elevator implements Runnable {
    private int id;
    private int floor_cur;
    private String direction;
    private String status;//"IDLE" или "MOVING" или "DOORS_OPEN"
    private BlockingQueue<Integer> tarFloors = new LinkedBlockingQueue<>();

    public Elevator(int id, int floor_cur) {
        this.id = id;
        this.floor_cur = floor_cur;
        this.direction = "NONE";
        this.status = "IDLE";
    }

    public void addTargetFloor(int floor) {
        tarFloors.add(floor);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Integer target = tarFloors.poll(1, TimeUnit.SECONDS);
                if(target != null) {
                    moveToFloor(target);
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void moveToFloor(int target) throws InterruptedException {
        synchronized(this) {
            status = "MOVING";
            direction = target > floor_cur ? "UP" : "DOWN";
            System.out.println("Лифт " + id + " едет с " + floor_cur + " на " + target);
            while(floor_cur != target) {
                Thread.sleep(1000); // 1 секунда на этаж
                floor_cur += direction.equals("UP") ? 1 : -1;
                System.out.println("Лифт " + id + " на этаже " + floor_cur);
            }

            status = "DOORS_OPEN";
            System.out.println("Лифт " + id + " приехал на " + floor_cur + ", открывает двери");
            Thread.sleep(2000);
            status = "IDLE";
            direction = "NONE";
            System.out.println("Лифт " + id + " закрыл двери, ждет");
        }
    }

    public int getFloor_cur() { return floor_cur; }
    public String getDirection() { return direction; }
    public String getStatus() { return status; }
    public int getId() { return id; }
}