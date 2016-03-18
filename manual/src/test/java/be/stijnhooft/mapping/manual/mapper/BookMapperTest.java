package be.stijnhooft.mapping.manual.mapper;

import be.stijnhooft.mapping.manual.mapper.BookMapper;
import be.stijnhooft.mapping.manual.mapper.UserMapper;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
@RunWith(MockitoJUnitRunner.class)
public class BookMapperTest {
    
    @InjectMocks
    private BookMapper bookMapper;
    
    @Mock
    private UserMapper userMapper;
    
    @Test
    public void testMapToVOIncludingReaders() {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        Thickness thickness = Thickness.THICK_BOOK;
        
        ISBN isbn = new ISBN();
        String isbnNumber = "test";
        isbn.setNumber(isbnNumber);
    
        List<User> readers = new ArrayList();
        User reader = new User();
        readers.add(reader);
        
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setReleaseDate(releaseDate);
        book.setThickness(thickness);
        book.setReaders(readers);
        
        UserVO readerVO = new UserVO();
        
        //mock
        when(userMapper.mapToVO(reader, false)).thenReturn(readerVO);
        
        //execute
        BookVO bookVO = bookMapper.mapToVO(book, true);
        
        //assert
        verify(userMapper, times(1)).mapToVO(reader, false);
        
        assertEquals(title, bookVO.getTitle());
        assertEquals(isbnNumber, bookVO.getIsbn());
        assertEquals(releaseDate, bookVO.getReleaseDate().toGregorianCalendar().getTime());
        assertEquals(2, bookVO.getThickness());
        assertEquals(1, bookVO.getReaders().size());
        assertSame(readerVO, bookVO.getReaders().iterator().next());
    }
    
    @Test
    public void testMapToVOExcludingBooks() {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        Thickness thickness = Thickness.THICK_BOOK;
        
        ISBN isbn = new ISBN();
        String isbnNumber = "test";
        isbn.setNumber(isbnNumber);
    
        List<User> readers = new ArrayList();
        User reader = new User();
        readers.add(reader);
        
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setReleaseDate(releaseDate);
        book.setThickness(thickness);
        book.setReaders(readers);
        
        //execute
        BookVO bookVO = bookMapper.mapToVO(book, false);
        
        //assert
        verify(userMapper, times(0)).mapToVO(reader, false);
        
        assertEquals(title, bookVO.getTitle());
        assertEquals(isbnNumber, bookVO.getIsbn());
        assertEquals(releaseDate, bookVO.getReleaseDate().toGregorianCalendar().getTime());
        assertEquals(2, bookVO.getThickness());
        assertNull(bookVO.getReaders());
    }
    
    @Test
    public void testMapToDomainIncludingBooks() throws DatatypeConfigurationException {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        String isbnNumber = "test";
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(releaseDate);
        XMLGregorianCalendar releaseDateCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    
        Set<UserVO> readerVOs = new HashSet();
        UserVO readerVO = new UserVO();
        readerVOs.add(readerVO);
        
        BookVO bookVO = new BookVO();
        bookVO.setTitle(title);
        bookVO.setIsbn(isbnNumber);
        bookVO.setReleaseDate(releaseDateCalendar);
        bookVO.setThickness(2);
        bookVO.setReaders(readerVOs);
        
        User reader = new User();
        
        //mock
        when(userMapper.mapToDomain(readerVO, false)).thenReturn(reader);
        
        //execute
        Book book = bookMapper.mapToDomain(bookVO, true);
        
        //assert
        verify(userMapper, times(1)).mapToDomain(readerVO, false);
        
        assertEquals(title, book.getTitle());
        assertEquals(isbnNumber, book.getIsbn().getNumber());
        assertEquals(releaseDate, book.getReleaseDate());
        assertEquals(Thickness.THICK_BOOK, book.getThickness());
        assertEquals(1, book.getReaders().size());
        assertSame(reader, book.getReaders().iterator().next());
    }
    
    @Test
    public void testMapToDomainExcludingBooks() throws DatatypeConfigurationException {
        //dataset
        String title = "title";
        Date releaseDate = new Date();
        String isbnNumber = "test";
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(releaseDate);
        XMLGregorianCalendar releaseDateCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    
        Set<UserVO> readerVOs = new HashSet();
        UserVO readerVO = new UserVO();
        readerVOs.add(readerVO);
        
        BookVO bookVO = new BookVO();
        bookVO.setTitle(title);
        bookVO.setIsbn(isbnNumber);
        bookVO.setReleaseDate(releaseDateCalendar);
        bookVO.setThickness(2);
        bookVO.setReaders(readerVOs);
        
        //execute
        Book book = bookMapper.mapToDomain(bookVO, false);
        
        //assert
        verify(userMapper, times(0)).mapToDomain(readerVO, false);
        
        assertEquals(title, book.getTitle());
        assertEquals(isbnNumber, book.getIsbn().getNumber());
        assertEquals(releaseDate, book.getReleaseDate());
        assertEquals(Thickness.THICK_BOOK, book.getThickness());
        assertNull(book.getReaders());
    }

}
