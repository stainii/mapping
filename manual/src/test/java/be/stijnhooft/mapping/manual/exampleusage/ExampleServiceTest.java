package be.stijnhooft.mapping.manual.exampleusage;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.manual.mapper.BookMapper;
import be.stijnhooft.mapping.manual.mapper.StoreMapper;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.isA;
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
public class ExampleServiceTest {
    
    @InjectMocks
    private ExampleService exampleService;
    
    @Mock
    private StoreMapper storeMapper;
    
    @Mock
    private BookMapper bookMapper;
    
    @Test
    public void findStore() {
        //dataset
        StoreVO storeVO = new StoreVO();
        
        //mock
        when(storeMapper.mapToVO(isA(Store.class))).thenReturn(storeVO);
        
        //execute
        StoreVO result = exampleService.findStore("test");
        
        //assert
        verify(storeMapper, times(1)).mapToVO(isA(Store.class));
        
        assertSame(storeVO, result);
    }
    
    @Test
    public void persistBook() {
        //dataset
        BookVO bookVO = new BookVO();
        BookVO persistedBookVO = new BookVO();
        Book book = new Book();
        
        //mock
        when(bookMapper.mapToDomain(bookVO, false)).thenReturn(book);
        when(bookMapper.mapToVO(book, true)).thenReturn(persistedBookVO);
        
        //execute
        BookVO result = exampleService.persistBook(bookVO);
        
        //assert
        verify(bookMapper, times(1)).mapToDomain(bookVO, false);
        verify(bookMapper, times(1)).mapToVO(book, true);
        
        assertSame(persistedBookVO, result);
    }
    
}
