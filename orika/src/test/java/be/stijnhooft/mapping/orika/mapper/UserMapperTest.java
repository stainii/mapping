package be.stijnhooft.mapping.orika.mapper;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.ISBN;
import be.stijnhooft.mapping.core.domain.Thickness;
import be.stijnhooft.mapping.core.domain.User;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.UserVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author stijnhooft
 */
public class UserMapperTest {
    
    private MapperFacade userMapper;
    
    @Before
     public void init() {
         MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
         MapperProducer mapperProducer = new MapperProducer();
         mapperProducer.configureMapperFactory(mapperFactory, MapperType.USER);
         userMapper = mapperFactory.getMapperFacade();
     }
    
    @Test
    public void testMapToVOIncludingBooks() {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        String title = "title";
        Date releaseDate = new Date();
        String isbnNumber = "test";
        
        ISBN isbn = new ISBN();
        isbn.setNumber(isbnNumber);
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(releaseDate);
        
        List<User> readers = new ArrayList();
        readers.add(new User());
        
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setReleaseDate(releaseDate);
        book.setThickness(Thickness.THICK_BOOK);
        book.setReaders(readers);
        List<Book> books = new ArrayList();
        books.add(book);
        
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBooks(books);
        
        //execute
        UserVO userVO = userMapper.map(user, UserVO.class);
        
        //assert
        assertEquals(firstName, userVO.getFirstName());
        assertEquals(lastName, userVO.getFamilyName());
        assertEquals(1, userVO.getBooks().size());
        
        BookVO bookVO = userVO.getBooks().get(0);
        assertEquals(title, bookVO.getTitle());
        assertEquals(isbnNumber, bookVO.getIsbn());
        assertEquals(2, bookVO.getThickness());
        assertEquals(releaseDate, bookVO.getReleaseDate().toGregorianCalendar().getTime());
        assertNull(bookVO.getReaders());
    }
    
    @Test
    public void testMapToDomainIncludingBooks() throws DatatypeConfigurationException {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        String title = "title";
        Date releaseDate = new Date();
        String isbnNumber = "test";
        
        ISBN isbn = new ISBN();
        isbn.setNumber(isbnNumber);
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(releaseDate);
        XMLGregorianCalendar releaseDateCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        Set<UserVO> readers = new HashSet();
        readers.add(new UserVO());
        
        BookVO bookVO = new BookVO();
        bookVO.setTitle(title);
        bookVO.setIsbn(isbnNumber);
        bookVO.setReleaseDate(releaseDateCalendar);
        bookVO.setThickness(2);
        bookVO.setReaders(readers);
        List<BookVO> bookVOs = new ArrayList();
        bookVOs.add(bookVO);
        
        UserVO userVO = new UserVO();
        userVO.setFirstName(firstName);
        userVO.setFamilyName(lastName);
        userVO.setBooks(bookVOs);
       
        //execute
        User user = userMapper.map(userVO, User.class);
              
        //assert
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(1, user.getBooks().size());
        
        Book book = user.getBooks().get(0);
        assertEquals(title, book.getTitle());
        assertEquals(isbnNumber, book.getIsbn().getNumber());
        assertEquals(Thickness.THICK_BOOK, book.getThickness());
        assertEquals(releaseDate, book.getReleaseDate());
        assertNull(book.getReaders());
    }

}
