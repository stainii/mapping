package be.stijnhooft.mapping.orika.mapper;

import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.StoreVO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author stijnhooft
 */
public class StoreMapperTest {
    
    private MapperFacade storeMapper;
    
    @Before
    public void init() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperProducer mapperProducer = new MapperProducer();
        mapperProducer.configureMapperFactory(mapperFactory, MapperType.STORE);
        storeMapper = mapperFactory.getMapperFacade();
    }
    
    @Test
    public void testMapToVO() {
        //dataset
        String name = "name";
        String address = "address";
        
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);
        
        //execute
        StoreVO storeVO = storeMapper.map(store, StoreVO.class);
        
        //assert
        assertEquals(name, storeVO.getName());
        assertEquals(address, storeVO.getAddress());
    }
    
    @Test
    public void testMapToDomain() {
        //dataset
        String name = "name";
        String address = "address";
        
        StoreVO storeVO = new StoreVO();
        storeVO.setName(name);
        storeVO.setAddress(address);
        
        //execute
        Store store = storeMapper.map(storeVO, Store.class);
        
        //assert
        assertEquals(name, store.getName());
        assertEquals(address, store.getAddress());
    }

}
