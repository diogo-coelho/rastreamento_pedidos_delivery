package br.com.foody_delivery.order_tracking.infra.config;

import br.com.foody_delivery.order_tracking.infra.exception.LocalDateTimeStringConverterException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Converter
public class LocalDateTimeStringConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter STORAGE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<DateTimeFormatter> PARSE_FORMATS = List.of(
            STORAGE_FORMAT,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    );

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.format(STORAGE_FORMAT);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        for (DateTimeFormatter formatter : PARSE_FORMATS) {
            try {
                return LocalDateTime.parse(dbData, formatter);
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }

        try {
            return OffsetDateTime.parse(dbData, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime();
        } catch (DateTimeParseException ignored) {
            throw new LocalDateTimeStringConverterException("Formato não suportado: " + dbData);
        }
    }
}
