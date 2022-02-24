package core.habr;

import core.ErrorHandler;
import core.ParserSettings;
import lombok.val;

/**
 * Отвечает за настройки парсера сайта habr.com.
 */
public class HabrSettings extends ParserSettings {
    /**
     * @param start начало пагинации.
     * @param end   конец пагинации.
     * @param errorHandler обработчик ошибок.
     */
    public HabrSettings(final int start, final int end, final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        val jsonParser = new JsonParser("habrSettings", "src/main/resources/parser_settings.json", errorHandler);

        BASE_URL = jsonParser.getBaseUrl();
        PREFIX = jsonParser.getPrefix();
        SAVE_PATH = jsonParser.getSavePath();
        errorFlag = jsonParser.isErrorFlag();

        startPoint = start;
        endPoint = end;
    }
}
