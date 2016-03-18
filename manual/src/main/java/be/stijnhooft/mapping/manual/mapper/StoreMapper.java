package be.stijnhooft.mapping.manual.mapper;

import be.stijnhooft.mapping.core.domain.Store;
import be.stijnhooft.mapping.core.vo.StoreVO;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author stijnhooft
 */
@ApplicationScoped
public class StoreMapper {
    
    public StoreVO mapToVO(Store store) {
        StoreVO storeVO = new StoreVO();
        storeVO.setName(store.getName());
        storeVO.setAddress(store.getAddress());
        return storeVO;
    }
    
    public Store mapToDomain(StoreVO storeVO) {
        Store store = new Store();
        store.setName(storeVO.getName());
        store.setAddress(storeVO.getAddress());
        return store;
    }

}
