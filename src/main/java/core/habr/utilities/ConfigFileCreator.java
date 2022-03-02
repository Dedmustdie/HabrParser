package core.habr.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.habr.constants.Constants;
import core.habr.settings.InitialParserSettings;
import core.habr.abstraction.ErrorHandler;

import java.io.File;
import java.io.IOException;

/**
 * Отвечает за создание конфиг. файла.
 */
public class ConfigFileCreator {
    /**
     * Создает конфиг. файл.
     *
     * @param errorHandler обработчик ошибок.
     */
    public void createInitialSettingsJsonFile(final ErrorHandler errorHandler) {
        var file = new File(Constants.SETTINGS_PATH);

        if (file.exists()) {
            return;
        }

        try {
            new ObjectMapper().writeValue(file, new InitialParserSettings("https://habr.com/ru/news",
                    "page{CurrentId}",
                    "./img/",
                    "tm-article-snippet",
                    "article-formatted-body article-formatted-body_version-1",
                    "article-formatted-body article-formatted-body_version-2",
                    "tm-article-snippet__title-link",
                    "tm-article-snippet__lead-image",
                    "src",
                    "class",
                    "Ошибка парсинга страницы!",
                    "Ошибка парсинга текста статьи!",
                    "Ошибка парсинга заголовка статьи!",
                    "Изображение отсутствует или произошла ошибка!"));
        } catch (IOException ex) {
            errorHandler.onError(ex.getMessage());
        }
    }

    /**
     * Создает конфиг. файл.
     */
    public void createInitialSettingsJsonFile() {
        var file = new File(Constants.SETTINGS_PATH);

        if (file.exists()) {
            return;
        }

        try {
            new ObjectMapper().writeValue(file, new InitialParserSettings("https://habr.com/ru/news",
                    "page{CurrentId}",
                    "./img/",
                    "tm-article-snippet",
                    "article-formatted-body article-formatted-body_version-1",
                    "article-formatted-body article-formatted-body_version-2",
                    "tm-article-snippet__title-link",
                    "tm-article-snippet__lead-image",
                    "src",
                    "class",
                    "Ошибка парсинга страницы!",
                    "Ошибка парсинга текста статьи!",
                    "Ошибка парсинга заголовка статьи!",
                    "Изображение отсутствует или произошла ошибка!"));
        } catch (IOException ignored) {}
    }
}
