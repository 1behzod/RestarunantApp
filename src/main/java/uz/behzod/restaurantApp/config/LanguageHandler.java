package uz.behzod.restaurantApp.config;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class LanguageHandler {

/*    public Locale resolveLocale(String acceptLanguageHeader) {
        if (acceptLanguageHeader == null || acceptLanguageHeader.isEmpty()) {
            return Locale.getDefault();
        }

        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);

        List<Locale> supportedLocales = List.of(
                Locale.ENGLISH,
                Locale.FRENCH,
                Locale.GERMAN
        );

        return Locale.lookup(languageRanges, supportedLocales);
    }*/





    private static final String BASE_NAME = "messages";
    private static final String FILE_EXTENSION = ".properties";

    public List<Locale> getSupportedLocales() {
        List<Locale> locales = new ArrayList<>();

        try {
            Path resourcePath = Paths.get(getClass().getClassLoader().getResource("").toURI());
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(resourcePath, BASE_NAME + "*" + FILE_EXTENSION);

            for (Path path : directoryStream) {
                String fileName = path.getFileName().toString();

                String localePart = fileName.replace(BASE_NAME, "").replace(FILE_EXTENSION, "");

                if (localePart.isBlank()) {
                    locales.add(Locale.ROOT);
                } else {
                    locales.add(Locale.forLanguageTag(localePart.replace("_", "-")));
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load locales", e);
        }

        return locales.stream().distinct().collect(Collectors.toList());
    }
}
