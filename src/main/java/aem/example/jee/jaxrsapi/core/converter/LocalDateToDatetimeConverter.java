package aem.example.jee.jaxrsapi.core.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateToDatetimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime x) {
        return x == null ? null : Timestamp.valueOf(x);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp y) {
        return y == null ? null : y.toLocalDateTime();
    }

}
