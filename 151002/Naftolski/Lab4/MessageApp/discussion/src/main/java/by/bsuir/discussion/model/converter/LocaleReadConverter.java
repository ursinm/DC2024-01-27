package by.bsuir.discussion.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
@ReadingConverter
public class LocaleReadConverter implements Converter<String, Locale> {
    @Override
    public Locale convert(String s) {
        Locale locale = StringUtils.parseLocaleString(s);
        return locale;
    }
}
