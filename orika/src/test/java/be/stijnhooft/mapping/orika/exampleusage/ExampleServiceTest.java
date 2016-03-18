package be.stijnhooft.mapping.orika.exampleusage;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import ma.glasnost.orika.MapperFacade;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author stijnhooft
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleServiceTest {
    
    @InjectMocks
    private ExampleService exampleService;
    
    @Mock
    private MapperFacade storeMapper;
    
    @Mock
    private MapperFacade bookMapper;
    
    @Test
    public void findStore() {
        //dataset
        StoreVO storeVO = new StoreVO();
        
        //mock
        when(storeMapper.map(isA(Store.class), eq(StoreVO.class))).thenReturn(storeVO);
        
        //execute
        StoreVO result = exampleService.findStore("test");
        
        //assert
        verify(storeMapper, times(1)).map(isA(Store.class), eq(StoreVO.class));
        
        assertSame(storeVO, result);
    }
    
    @Test
    public void persistBook() {
        //dataset
        BookVO bookVO = new BookVO();
        BookVO persistedBookVO = new BookVO();
        Book book = new Book();
        
        //mock
        when(bookMapper.map(bookVO, Book.class)).thenReturn(book);
        when(bookMapper.map(book, BookVO.class)).thenReturn(persistedBookVO);
        
        //execute
        BookVO result = exampleService.persistBook(bookVO);
        
        //assert
        verify(bookMapper, times(1)).map(bookVO, Book.class);
        verify(bookMapper, times(1)).map(book, BookVO.class);
        
        assertSame(persistedBookVO, result);
    }
    
}
