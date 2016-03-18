package be.stijnhooft.mapping.manual.mapper;

import be.stijnhooft.mapping.manual.mapper.StoreMapper;
import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.StoreVO;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author stijnhooft
 */
public class StoreMapperTest {
    
    private StoreMapper storeMapper;
    
    @Before
    public void init() {
        storeMapper = new StoreMapper();
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
        StoreVO storeVO = storeMapper.mapToVO(store);
        
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
        Store store = storeMapper.mapToDomain(storeVO);
        
        //assert
        assertEquals(name, store.getName());
        assertEquals(address, store.getAddress());
    }

}
