package be.stijnhooft.mapping.core.domain;

import java.util.List;

/**
 *
 * @author stijnhooft
 */
public class User {
    
    private String firstName;
    private String lastName;
    
    /** All the books a user has read **/
    private List<Book> books;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    

}
