package uz.behzod.restaurantApp.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LocalizationService {

    MessageSource messageSource;

    public String localize(String code, Object... params) {
        return messageSource.getMessage(code, params, code, getLocale());
    }

    public String localize(String code) {
        return messageSource.getMessage(code, null, code, getLocale());
    }

    public String localize(String code, String param) {
        return messageSource.getMessage(code, List.of(param).toArray(), code, getLocale());
    }

    public String localize(String code, String... params) {
        return messageSource.getMessage(code, params, code, getLocale());
    }

    public static Locale getLocale() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            String language = request.getHeader("LANGUAGE");
            if (language != null && !language.isEmpty()) {
                Locale requestedLocale = Locale.forLanguageTag(language);
                if (Arrays.asList(Locale.getAvailableLocales()).contains(requestedLocale)) {
                    return requestedLocale;
                }
            }

            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
                return Locale.lookup(Locale.LanguageRange.parse(acceptLanguage), Arrays.asList(Locale.getAvailableLocales()));
            }
        }
        return Locale.ENGLISH;
    }

    private static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }
}
