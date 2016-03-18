package be.stijnhooft.mapping.core.vo;

import be.stijnhooft.mapping.core.domain.Book;
import java.util.List;
import java.util.Set;

/**
 *
 * @author stijnhooft
 */
public class UserVO {

    private String firstName;
    private String familyName;
    
    /** All the books a user has read **/
    private List<BookVO> books;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
    
}
