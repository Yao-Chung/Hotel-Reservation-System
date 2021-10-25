package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HotelResource {
    private static ReservationService reservationService = ReservationService.getInstance();
    private static CustomerService customerService = CustomerService.getInstance();
    public static Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public static void createACustomer(String firstName, String lastName, String email) throws IllegalArgumentException, NullPointerException{
        customerService.addCustomer(firstName, lastName, email);
    }
    public static IRoom getRoom(String roomNumber) {
        return reservationService.getRoom(roomNumber);
    }
    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) throws IllegalArgumentException{
        Reservation ret = null;
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null)
            throw new IllegalArgumentException("This customer has not registered.");
        ret = reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        return ret;
    }
    public static Collection<Reservation> getCustomersReservations(String customerEmail) throws IllegalArgumentException, NullPointerException{
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null)
            throw new IllegalArgumentException("This customer has not registered yet.");
        return reservationService.getCustomersReservation(customer);
    }
    public static List<IRoom> findARoom(Date checkIn, Date checkOut) throws IllegalArgumentException{
        return reservationService.findRooms(checkIn, checkOut);
    }
}
