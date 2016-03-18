package be.stijnhooft.mapping.orika.mapper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Annotation to inject our mappers. Accepts a MapperType, to differentiate the
 * different mapping configurations.
 * @author stijnhooft
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, TYPE, METHOD, PARAMETER})
@Qualifier
public @interface Mapper {

    /**
     * The type of mapper which should be injected
     * @return MapperType mapperType
     */
    @Nonbinding MapperType type();
}
