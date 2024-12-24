package uz.behzod.restaurantApp.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Locale;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LocalizationService {

    @Autowired
    MessageSource messageSource;

    public String localize(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    public String localize(String code) {
        return messageSource.getMessage(code, null, getLocale());
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
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }









/*    public static Locale getLocale() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            String language = request.getHeader("LANGUAGE");
            if (language != null && !language.isEmpty()) {
                return Locale.forLanguageTag(language);
            } else {
                String acceptLanguage = request.getHeader("Accept-Language");
                if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
                    return Locale.lookup(Locale.LanguageRange.parse(acceptLanguage), Arrays.asList(Locale.getAvailableLocales()));

                }
            }
        }
        return Locale.getDefault();
    }

    private static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }*/



    /*
    public String localize(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, Locale.getDefault());
    }

    public String localize(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String localize(String code, Object[] params, Locale locale) {
        return messageSource.getMessage(code, params, locale);
    }

    public String localize(String code, String defaultMessage, Locale locale, HttpServletRequest request) {
        String local = request.getHeader("LANGUAGE");
        return messageSource.getMessage(code, null, defaultMessage, getLocale());
    }*/

    /*    public static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }*/

/*    public static Locale getLocale() {
        HttpServletRequest request = getCurrentRequest();
        String language=request.getHeader("Language");
        if (request != null) {
            return request.getLocale();
        }
        return Locale.getDefault();
    }*/
}
