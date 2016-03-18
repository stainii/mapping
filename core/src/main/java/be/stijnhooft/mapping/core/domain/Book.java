package be.stijnhooft.mapping.core.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author stijnhooft
 */
public class Book {

    private String title;
    private ISBN isbn;
    private Date releaseDate;
    private Thickness thickness;
    
    /** All users who have read this book **/
    private List<User> readers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Thickness getThickness() {
        return thickness;
    }

    public void setThickness(Thickness thickness) {
        this.thickness = thickness;
    }

    public List<User> getReaders() {
        return readers;
    }

    public void setReaders(List<User> readers) {
        this.readers = readers;
    }
    
    
}
