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
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author stijnhooft
 */
public class BookMapperTest {
    
    private MapperFacade bookMapper;
    
    @Before
    public void init() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperProducer mapperProducer = new MapperProducer();
        mapperProducer.configureMapperFactory(mapperFactory, MapperType.BOOK);
        bookMapper = mapperFactory.getMapperFacade();
    }
    
    @Test
    public void testMapToVO() {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        Thickness thickness = Thickness.THICK_BOOK;
        
        ISBN isbn = new ISBN();
        String isbnNumber = "test";
        isbn.setNumber(isbnNumber);
        
        String familyName = "familyName";
        String firstName = "firstName";
        
        List<Book> books = new ArrayList();
        books.add(new Book());
    
        List<User> readers = new ArrayList();
        User reader = new User();
        reader.setLastName(familyName);
        reader.setFirstName(firstName);
        reader.setBooks(books);
        readers.add(reader);
        
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setReleaseDate(releaseDate);
        book.setThickness(thickness);
        book.setReaders(readers);
        
        //execute
        BookVO bookVO = bookMapper.map(book, BookVO.class);
        
        //assert
        assertEquals(title, bookVO.getTitle());
        assertEquals(isbnNumber, bookVO.getIsbn());
        assertEquals(releaseDate, bookVO.getReleaseDate().toGregorianCalendar().getTime());
        assertEquals(2, bookVO.getThickness());
        assertEquals(1, bookVO.getReaders().size());
        
        UserVO readerVO = bookVO.getReaders().iterator().next();
        assertEquals(firstName, readerVO.getFirstName());
        assertEquals(familyName, readerVO.getFamilyName());
        assertNull(readerVO.getBooks());
    }
    
    @Test
    public void testMapToDomain() throws DatatypeConfigurationException {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        String isbnNumber = "test";
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(releaseDate);
        XMLGregorianCalendar releaseDateCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    
        String familyName = "familyName";
        String firstName = "firstName";
        
        List<BookVO> books = new ArrayList();
        books.add(new BookVO());
    
        Set<UserVO> readerVOs = new HashSet();
        UserVO readerVO = new UserVO();
        readerVO.setFamilyName(familyName);
        readerVO.setFirstName(firstName);
        readerVO.setBooks(books);
        readerVOs.add(readerVO);
        
        BookVO bookVO = new BookVO();
        bookVO.setTitle(title);
        bookVO.setIsbn(isbnNumber);
        bookVO.setReleaseDate(releaseDateCalendar);
        bookVO.setThickness(2);
        bookVO.setReaders(readerVOs);
        
        //execute
        Book book = bookMapper.map(bookVO, Book.class);
        
        //assert
        assertEquals(title, book.getTitle());
        assertEquals(isbnNumber, book.getIsbn().getNumber());
        assertEquals(releaseDate, book.getReleaseDate());
        assertEquals(Thickness.THICK_BOOK, book.getThickness());
        assertEquals(1, book.getReaders().size());
        
        User reader = book.getReaders().get(0);
        assertEquals(firstName, reader.getFirstName());
        assertEquals(familyName, reader.getLastName());
        assertNull(reader.getBooks());
    }
    
}
