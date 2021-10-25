package model;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException{
        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches())
            throw new IllegalArgumentException("Email pattern does not match!");
        else{
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getEmail() {
        return this.email;
    }
    @Override
    public String toString() {
        return "First Name: " + firstName + " Last Name: " + lastName + " Email: " + email;
    }
    @Override 
    public boolean equals(Object o){
        if(o instanceof Customer){
            Customer customer = (Customer) o;
            return Objects.equals(this.firstName, customer.getFirstName()) 
                   && Objects.equals(this.lastName, customer.getLastName())
                   && Objects.equals(this.email, customer.getEmail());
        }
        return false;
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.firstName, this.lastName, this.email);
    }
}
