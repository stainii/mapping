package be.stijnhooft.mapping.manual.mapper;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.ISBN;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.domain.Thickness;
import be.stijnhooft.mapping.core.domain.User;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.core.vo.UserVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author stijnhooft
 */
@ApplicationScoped
public class BookMapper {
    
    @Inject
    private UserMapper userMapper;
    
    public BookVO mapToVO(Book book, boolean includeReaders) {
        BookVO bookVO = new BookVO();
        bookVO.setTitle(book.getTitle());
        
        //ISBN to String
        String isbn = book.getIsbn().getNumber();
        bookVO.setIsbn(isbn);
       
        //list of domain to set of VO
        if (includeReaders) {
            Set<UserVO> readers = new HashSet();
            for (User user : book.getReaders()) {
                readers.add(userMapper.mapToVO(user, false));
            }
            bookVO.setReaders(readers);
        }
        
        //Date to XMLGregorianCalendar
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(book.getReleaseDate());
            XMLGregorianCalendar releaseDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            bookVO.setReleaseDate(releaseDate);
        } catch (DatatypeConfigurationException ex) {
            throw new UnsupportedOperationException("Can not convert the date!", ex);
        }
        
        //convert a object to another type
        int thickness = book.getThickness().value();
        bookVO.setThickness(thickness);
        
        return bookVO;
    }
    
    public Book mapToDomain(BookVO bookVO, boolean includeReaders) {
        Book book = new Book();
        book.setTitle(bookVO.getTitle());
        
        //String to ISBN
        ISBN isbn = new ISBN();
        isbn.setNumber(bookVO.getIsbn());
        book.setIsbn(isbn);
       
        //set of VOs to list of domain objects
        if (includeReaders) {
            List<User> readers = new ArrayList();
            for (UserVO user : bookVO.getReaders()) {
                readers.add(userMapper.mapToDomain(user, false));
            }
            book.setReaders(readers);
        }
        
        //Date to XMLGregorianCalendar
        Date releaseDate = bookVO.getReleaseDate().toGregorianCalendar().getTime();
        book.setReleaseDate(releaseDate);
        
        //convert a object to another type
        Thickness thickness = Thickness.of(bookVO.getThickness());
        book.setThickness(thickness);
        
        return book;
    }

}
