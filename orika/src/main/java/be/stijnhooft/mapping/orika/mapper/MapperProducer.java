package be.stijnhooft.mapping.orika.mapper;

import be.stijnhooft.mapping.core.domain.Book;
import be.stijnhooft.mapping.core.domain.ISBN;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.domain.Thickness;
import be.stijnhooft.mapping.core.domain.User;
import be.stijnhooft.mapping.core.vo.BookVO;
import be.stijnhooft.mapping.core.vo.StoreVO;
import be.stijnhooft.mapping.core.vo.UserVO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 *
 * This is a CDI producer, with which you will be able to inject the needed
 * mapping configuration.
 * 
 * Each configuration is differentiated by a MapperType. Provide this MapperType
 * when injecting the MapperFacade in you business logic layer. Check out the
 * ExampleService for an example.
 * 
 * @author stijnhooft
 */
@ApplicationScoped
public class MapperProducer {
    
    private Map<MapperType, MapperFacade> mappers;

    /**
     * Initializes the mapper producer
     */
    @PostConstruct
    public void init() {
        mappers = new HashMap<MapperType, MapperFacade>();
    }

    /**
     * /**
     * Creates a mapper facade, configured to convert objects
     * @param ip Injection Point, used by the CDI framework.
     * @return a mapper facade, configured to convert objects
     */
    @Produces
    @Mapper(type = MapperType.UNDEFINED)
    public MapperFacade getMapper(InjectionPoint ip) {
        MapperType type = ip.getAnnotated().getAnnotation(Mapper.class).type();
        if (!mappers.containsKey(type)) {
            MapperFactory factory = new DefaultMapperFactory.Builder().build();
            configureMapperFactory(factory, type);
            mappers.put(type, factory.getMapperFacade());
        }

        return mappers.get(type);
    }

    /**
     * Configures a mapperFactory for a specified mapper type. For example: configures a mapperFactory for a
     * road transport undertaking vo for a screen which displays specific information of a road transport undertaking.
     *
     * @param factory mapper factory
     * @param type    mapper type
     */
    public void configureMapperFactory(MapperFactory factory, MapperType type) {
        configureCustomConverters(factory);
        
        switch (type) {
            case STORE: configureStoreMapper(factory);
                        break;
            case BOOK:  configureBookMapper(factory);
                        break;
            case USER:  configureUserMapper(factory);
                        break;
            default: throw new UnsupportedOperationException("Could not configure a mapper for type " + type.name());
        }
    }

    private void configureStoreMapper(MapperFactory factory) {
        //bidirectional one-on-one mapping, very easy!
        
        factory.classMap(Store.class, StoreVO.class)
                
            //by default registers all one-one-one relationships.
            //if you still want to exclude something from mapping,
            //use exclude("propertyName")
            .byDefault()
                
            .register();
    }
    
    private void configureBookMapper(MapperFactory factory) {
        //bidirectional one-on-one mapping, very easy!
        //we configure the mapping of the book and its readers.
        factory.classMap(Book.class, BookVO.class)
                .field("title", "title")
                
                //different structure? Not a problem, we can use the dot
                //notation to travel through objects!
                .field("isbn.number", "isbn")
                
                //Orika already contains some custom mappers. It's perfectly
                //capable to convert numbers to Strings, Dates to Calendars, ...
                .field("releaseDate", "releaseDate")
                
                //do you still need more control? Use  a custom converter!
                .fieldMap("thickness", "thickness").converter("thicknessConverter").add()
                
                //list to set and vica versa? No problem for Orika!
                .field("readers", "readers")
                
                .register();
        
        factory.classMap(User.class, UserVO.class)
            .field("firstName", "firstName")
                
            //let lastName map to familyName and vice versa
            .field("lastName", "familyName")
            
            //because there is a bidirectional relationship between user and book,
            //we let a book contain users, but a user no longer contains books,
            //otherwise we get an infinite loop while mapping!
                
            .register();
    }
    
    private void configureUserMapper(MapperFactory factory) {
        //we configure the mapping of the user and its books.
        factory.classMap(User.class, UserVO.class)
            .field("firstName", "firstName")
            //let lastName map to familyName and vice versa
            .field("lastName", "familyName")
            .field("books", "books")
            .register();
        
        factory.classMap(Book.class, BookVO.class)
            .field("title", "title")
            .field("isbn.number", "isbn")
            .field("releaseDate", "releaseDate")
            .fieldMap("thickness", "thickness").converter("thicknessConverter").add()
                        
            //because there is a bidirectional relationship between user and book,
            //we let a user contain books, but a book no longer contains users,
            //otherwise we get an infinite loop while mapping!
                
            .register();
    }

    private void configureCustomConverters(MapperFactory factory) {
        //Need more control? Use  a custom converter! We need to register them.
        factory.getConverterFactory().registerConverter("thicknessConverter", new ThicknessConverter());
    }

}
