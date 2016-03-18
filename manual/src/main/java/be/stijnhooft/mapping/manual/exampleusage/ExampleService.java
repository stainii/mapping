package be.stijnhooft.mapping.manual.exampleusage;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.manual.mapper.BookMapper;
import be.stijnhooft.mapping.manual.mapper.StoreMapper;
import be.stijnhooft.mapping.manual.mapper.UserMapper;
import javax.inject.Inject;

/**
 * This is an example of how to use mappers in a business logic layer.
 * @author stijnhooft
 */
public class ExampleService {
    
    @Inject
    private StoreMapper storeMapper;
    
    @Inject
    private BookMapper bookMapper;
    
    public StoreVO findStore(String name) {
        Store store = new Store(); //in reality, we should call a repository which searches for a store.
        
        //we don't want to expose our domain Store, so let's map it to a StoreVO
        StoreVO storeVO = storeMapper.mapToVO(store);
        return storeVO;
    }
    
    public BookVO persistBook(BookVO bookVO) {
        Book book = bookMapper.mapToDomain(bookVO, false);
        
        //do some repository call to persist the book
        
        //eventually, flush the database and return the persisted book
        return bookMapper.mapToVO(book, true);
    }
    
}
