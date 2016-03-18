package be.stijnhooft.mapping.core.vo;

import be.stijnhooft.mapping.core.domain.User;
import java.util.List;
import java.util.Set;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author stijnhooft
 */
public class BookVO {
    
    private String title;
    private String isbn;
    private XMLGregorianCalendar releaseDate;
    
    /** How thick is a book. The larger the number, the thicker. **/
    private int thickness;
    
    /** All users who have read this book **/
    private Set<UserVO> readers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(XMLGregorianCalendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public Set<UserVO> getReaders() {
        return readers;
    }

    public void setReaders(Set<UserVO> readers) {
        this.readers = readers;
    }

}
