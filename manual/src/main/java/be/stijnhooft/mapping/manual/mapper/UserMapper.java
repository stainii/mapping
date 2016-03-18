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
import java.util.List;
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
public class UserMapper {
    
    @Inject
    private BookMapper bookMapper;
    
    public UserVO mapToVO(User user, boolean includeBooks) {
        UserVO userVO = new UserVO();
        userVO.setFirstName(user.getFirstName());
        
        //notice the different names, they don't map 1-on-1 ("family" <> "last")
        userVO.setFamilyName(user.getLastName());
        
        //map list of books to list of book vos
        if (includeBooks) {
            List<BookVO> books = new ArrayList();
            for (Book book: user.getBooks()) {
                books.add(bookMapper.mapToVO(book, false));
            }
            userVO.setBooks(books);
        }
        
        return userVO;
    }
    
    public User mapToDomain(UserVO userVO, boolean includeBooks) {
        User user = new User();
        user.setFirstName(userVO.getFirstName());
        
        //notice the different names, they don't map 1-on-1 ("family" <> "last")
        user.setLastName(userVO.getFamilyName());
        
        //map list of books to list of book vos
        if (includeBooks) {
            List<Book> books = new ArrayList();
            for (BookVO book: userVO.getBooks()) {
                books.add(bookMapper.mapToDomain(book, false));
            }
            user.setBooks(books);
        }
        
        return user;
    }

}
