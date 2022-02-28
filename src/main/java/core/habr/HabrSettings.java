package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
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
        val jsonParser = new JsonParser(errorHandler);

        baseUrl = jsonParser.getBaseUrl();
        prefix = jsonParser.getPrefix();
        savePath = jsonParser.getSavePath();
        errorFlag = jsonParser.isErrorFlag();

        startPoint = start;
        endPoint = end;
    }

    /**
     * @param start начало пагинации.
     * @param end   конец пагинации.
     */
    public HabrSettings(final int start, final int end) {
        val jsonParser = new JsonParser();

        baseUrl = jsonParser.getBaseUrl();
        prefix = jsonParser.getPrefix();
        savePath = jsonParser.getSavePath();
        errorFlag = jsonParser.isErrorFlag();

        startPoint = start;
        endPoint = end;
    }
}
