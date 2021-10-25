package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }
    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }
    @Override
    public Double getRoomPrice() {
        return this.price;
    }
    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }
    @Override
    public boolean isFree() {
        return (price == 0.0); // not sure how to implement this function
    }
    @Override public String toString() {
        return "Room Number = " + roomNumber + " price = " + price + " type = " + enumeration;
    }
}
