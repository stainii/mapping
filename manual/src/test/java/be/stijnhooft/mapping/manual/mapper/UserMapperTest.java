package be.stijnhooft.mapping.manual.mapper;

import be.stijnhooft.mapping.manual.mapper.BookMapper;
import be.stijnhooft.mapping.manual.mapper.UserMapper;
import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.domain.User;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.core.vo.UserVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class UserMapperTest {
    
    @InjectMocks
    private UserMapper userMapper;
    
    @Mock
    private BookMapper bookMapper;
    
    @Test
    public void testMapToVOIncludingBooks() {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        Book book = new Book();
        List<Book> books = new ArrayList();
        books.add(book);
        
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBooks(books);
        
        BookVO bookVO = new BookVO();
        
        //mock
        when(bookMapper.mapToVO(book, false)).thenReturn(bookVO);
        
        //execute
        UserVO userVO = userMapper.mapToVO(user, true);
        
        //assert
        verify(bookMapper, times(1)).mapToVO(book, false);
        
        assertEquals(firstName, userVO.getFirstName());
        assertEquals(lastName, userVO.getFamilyName());
        assertEquals(1, userVO.getBooks().size());
        assertSame(bookVO, userVO.getBooks().get(0));
    }
    
    @Test
    public void testMapToVOExcludingBooks() {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        Book book = new Book();
        List<Book> books = new ArrayList();
        books.add(book);
        
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBooks(books);
        
        //execute
        UserVO userVO = userMapper.mapToVO(user, false);
        
        //assert
        verify(bookMapper, times(0)).mapToVO(book, false);
                
        assertEquals(firstName, userVO.getFirstName());
        assertEquals(lastName, userVO.getFamilyName());
        assertNull(userVO.getBooks());
    }
    
    @Test
    public void testMapToDomainIncludingBooks() {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        BookVO bookVO = new BookVO();
        List<BookVO> books = new ArrayList();
        books.add(bookVO);
        
        UserVO userVO = new UserVO();
        userVO.setFirstName(firstName);
        userVO.setFamilyName(lastName);
        userVO.setBooks(books);
        
        Book book = new Book();
        
        //mock
        when(bookMapper.mapToDomain(bookVO, false)).thenReturn(book);
        
        //execute
        User user = userMapper.mapToDomain(userVO, true);
        
        //assert
        verify(bookMapper, times(1)).mapToDomain(bookVO, false);
                
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(1, user.getBooks().size());
        assertSame(book, user.getBooks().get(0));
    }
    
    @Test
    public void testMapToDomainExcludingBooks() {
        //dataset
        String firstName = "firstName";
        String lastName = "lastName";
        
        BookVO bookVO = new BookVO();
        List<BookVO> books = new ArrayList();
        books.add(bookVO);
        
        UserVO userVO = new UserVO();
        userVO.setFirstName(firstName);
        userVO.setFamilyName(lastName);
        userVO.setBooks(books);
        
        //execute
        User user = userMapper.mapToDomain(userVO, false);
        
        //assert
        verify(bookMapper, times(0)).mapToDomain(bookVO, false);
                
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertNull(user.getBooks());
    }

}
