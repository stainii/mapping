package be.stijnhooft.mapping.orika.exampleusage;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.orika.mapper.Mapper;
import be.stijnhooft.mapping.orika.mapper.MapperType;
import javax.inject.Inject;
import ma.glasnost.orika.MapperFacade;

/**
 * This is an example of how to use mappers in a business logic layer.
 * @author stijnhooft
 */
public class ExampleService {
    
    @Inject
    @Mapper(type = MapperType.STORE)
    private MapperFacade storeMapper;
    
    @Inject
    @Mapper(type = MapperType.BOOK)
    private MapperFacade bookMapper;
    
    
    public StoreVO findStore(String name) {
        Store store = new Store(); //in reality, we should call a repository which searches for a store.
        
        //we don't want to expose our domain Store, so let's map it to a StoreVO
        StoreVO storeVO = storeMapper.map(store, StoreVO.class);
        return storeVO;
    }
    
    public BookVO persistBook(BookVO bookVO) {
        Book book = bookMapper.map(bookVO, Book.class);
        
        //do some repository call to persist the book
        
        //eventually, flush the database and return the persisted book
        return bookMapper.map(book, BookVO.class);
    }
    
}
