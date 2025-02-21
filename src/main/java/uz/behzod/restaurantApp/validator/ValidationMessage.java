package uz.behzod.restaurantApp.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static uz.behzod.restaurantApp.service.LocalizationService.getLocale;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationMessage {

    MessageSource messageSource;

    public String localize(String code) {
        return this.localize(code, null, code, getLocale());
    }

    public String localize(String code, Locale locale) {
        return this.localize(code, null, code, locale != null ? locale : getLocale());
    }

    public String localize(String code, Object[] params) {
        return this.localize(code, params, getLocale());
    }

    public String localize(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, getLocale());
    }

    public String localize(String code, Object[] params, Locale locale) {
        return this.localize(code, params, code, locale);
    }

    public String localize(String code, String... params) {
        return localize(code, params, getLocale());
    }

    public String localize(String code, Object[] params, String defaultMessage, Locale locale) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return messageSource.getMessage(code, params, defaultMessage, locale);
    }


}
