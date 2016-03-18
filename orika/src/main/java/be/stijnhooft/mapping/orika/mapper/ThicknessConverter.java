package be.stijnhooft.mapping.orika.mapper;

import be.stijnhooft.mapping.core.domain.Thickness;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * This is a custom converter, which gives you more control of your mapping.
 * @author stijnhooft
 */
public class ThicknessConverter extends BidirectionalConverter<Thickness, Integer> {

    @Override
    public Integer convertTo(Thickness thickness, Type<Integer> type) {
        return thickness.value();
    }

    @Override
    public Thickness convertFrom(Integer thickness, Type<Thickness> type) {
        return Thickness.of(thickness);
    }


}
