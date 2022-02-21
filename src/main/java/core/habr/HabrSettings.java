package core.habr;

import core.ErrorHandler;
import core.ParserSettings;
import lombok.Getter;

/**
 * Отвечает за настройки парсера сайта habr.com.
 */
public class HabrSettings extends ParserSettings {
    @Getter
    private ErrorHandler errorHandler;
    /**
     * @param start начало пагинации.
     * @param end   конец пагинации.
     * @param errorHandler обработчик ошибок.
     */
    public HabrSettings(int start, int end, ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        JsonParser jsonParser = new JsonParser("habrSettings", "parser_settings.json", errorHandler);

        BASE_URL = jsonParser.getBaseUrl();
        PREFIX = jsonParser.getPrefix();
        SAVE_PATH = jsonParser.getSavePath();

        startPoint = start;
        endPoint = end;
    }
}
