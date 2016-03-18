package be.stijnhooft.mapping.orika.mapper;

import be.stijnhooft.mapping.core.domain.Thickness;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author stijnhooft
 */
public class ThicknessConverterTest {
    
    private ThicknessConverter converter;
    
    @Before
    public void init() {
        converter = new ThicknessConverter();
    }
    
    @Test
    public void convertFromWhenThin() {
       assertEquals(Thickness.THIN_BOOK, converter.convertFrom(1, null));
    }
    
    @Test
    public void convertFromWhenThick() {
       assertEquals(Thickness.THICK_BOOK, converter.convertFrom(2, null));
    }
    
    @Test
    public void convertToWhenThin() {
       assertEquals(Integer.valueOf(1), converter.convertTo(Thickness.THIN_BOOK, null));
    }
    
    @Test
    public void convertToWhenThick() {
       assertEquals(Integer.valueOf(2), converter.convertTo(Thickness.THICK_BOOK, null));
    }

}
