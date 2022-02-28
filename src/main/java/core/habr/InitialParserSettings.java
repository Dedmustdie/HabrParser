package core.habr;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

/**
 * Содержит объекты для создания конфиг. файла.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@AllArgsConstructor
public class InitialParserSettings {
    /**
     * Адрес сайта.
     */
    private final String baseUrl;

    /**
     * Префих страницы.
     */
    private final String prefix;

    /**
     * Путь для скачивания файлов.
     */
    private final String savePath;
}
