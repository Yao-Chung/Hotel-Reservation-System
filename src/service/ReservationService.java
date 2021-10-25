package service;

import model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservationService {
    private Set<Reservation> allReservations;
    private Map<String, ArrayList<Reservation>> findReservationByEmail;
    private Map<String, IRoom> findRoomById;
    private Map<String, ArrayList<Reservation>> findReservationByRoomId;
    private static ReservationService reservationServiceInstance = null;

    private ReservationService(){
        allReservations = new HashSet<>();
        findReservationByEmail = new HashMap<>();
        findRoomById = new HashMap<>();
        findReservationByRoomId = new HashMap<>();
    }
    public static ReservationService getInstance(){
        if(reservationServiceInstance == null)
            reservationServiceInstance = new ReservationService();
        return reservationServiceInstance;
    } 
    public void addRoom(IRoom room) throws IllegalArgumentException, NullPointerException{
        if(findRoomById.containsKey(room.getRoomNumber()))
            throw new IllegalArgumentException("The room is already existed.");
        findRoomById.put(room.getRoomNumber(), room);
    }
    public List<IRoom> getAllRooms(){
        List<IRoom> ret = new ArrayList<>();
        for(IRoom r: findRoomById.values())
            ret.add(r);
        return ret;
    }
    public IRoom getRoom(String roomId) {
        return findRoomById.get(roomId);
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws IllegalArgumentException{
        Reservation ret = new Reservation(customer, room, checkInDate, checkOutDate);
        findReservationByEmail.putIfAbsent(customer.getEmail(), new ArrayList<>());
        findReservationByEmail.get(customer.getEmail()).add(ret);
        findReservationByRoomId.putIfAbsent(room.getRoomNumber(), new ArrayList<>());
        findReservationByRoomId.get(room.getRoomNumber()).add(ret);
        allReservations.add(ret);
        return ret;
    }
    List<IRoom> findRoomHelper(Date checkInDate, Date checkOutDate){
        List<IRoom> ret = new ArrayList<>();
        for(String roomId: findRoomById.keySet()){
            ArrayList<Reservation> arr = findReservationByRoomId.get(roomId);
            boolean valid = true;
            if(arr != null){
                for(Reservation r: arr){
                    if((r.getCheckInDate().before(checkInDate) && r.getCheckOutDate().after(checkInDate)) 
                    || (r.getCheckInDate().before(checkOutDate) && r.getCheckOutDate().after(checkOutDate))
                    || (checkInDate.before(r.getCheckInDate()) && checkOutDate.after(r.getCheckInDate()))
                    || (checkInDate.before(r.getCheckOutDate()) && checkInDate.after(r.getCheckOutDate()))
                    || (checkInDate.equals(r.getCheckInDate()) && checkOutDate.equals(r.getCheckOutDate())))
                        valid = false;
                }
            }
            if(valid)
                ret.add(findRoomById.get(roomId));
        }
        return ret;
    }
    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) throws IllegalArgumentException{
        if(checkInDate.after(checkOutDate))
            throw new IllegalArgumentException("Date order is wrong.");
        List<IRoom> ret = findRoomHelper(checkInDate, checkOutDate);
        if(ret.isEmpty()){      //if there is no room available now
            Date newCheckIn = new Date(checkInDate.getTime()+(1000 * 60 * 60 * 24 * 7));    //find rooms for other check in and
            Date newCheckOut = new Date(checkOutDate.getTime()+(1000 * 60 * 60 * 24 * 7));  //check out dates (7 days later)
            ret = findRoomHelper(newCheckIn, newCheckOut);
            if(!ret.isEmpty()){
                System.out.println("There is no room on your selected dates. Below is the recommended rooms for another dates.");
                System.out.println("Check in: " + newCheckIn);
                System.out.println("Check out: " + newCheckOut);
            }
        }
        return ret;
    }
    public Collection<Reservation> getCustomersReservation(Customer customer) throws NullPointerException{
        String email = customer.getEmail();
        return findReservationByEmail.get(email);
    }
    public void printAllReservation() {
        System.out.printf("There are %d reservations:%n", allReservations.size());
        for(Reservation r: allReservations)
            System.out.println(r);
    }
}
