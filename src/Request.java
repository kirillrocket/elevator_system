public class Request {
    int floor_req;
    int tarFloor;
    String direction; //"UP" или "DOWN"

    public Request(int floor, String direction, int tarFloor) {
        this.floor_req = floor;
        this.direction = direction;
        this.tarFloor = tarFloor;
    }
}