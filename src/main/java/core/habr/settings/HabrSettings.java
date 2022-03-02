package core.habr.settings;

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
        val jsonParser = new SettingsJsonParser(errorHandler);

        baseUrl = jsonParser.getBaseUrl();
        prefix = jsonParser.getPrefix();
        savePath = jsonParser.getSavePath();
        articleAtrValue = jsonParser.getArticleAtrValue();
        firstTextAtrValue = jsonParser.getFirstTextAtrValue();
        secondTextAtrValue = jsonParser.getSecondTextAtrValue();
        headAtrValue = jsonParser.getHeadAtrValue();
        imgAtrValue = jsonParser.getImgAtrValue();
        imgUrlAtrName = jsonParser.getImgUrlAtrName();
        generalAtrName = jsonParser.getGeneralAtrName();
        settingsPath = jsonParser.getSettingsPath();

        startPoint = start;
        endPoint = end;

        errorFlag = jsonParser.isErrorFlag();
    }

    /**
     * @param start начало пагинации.
     * @param end   конец пагинации.
     */
    public HabrSettings(final int start, final int end) {
        val jsonParser = new SettingsJsonParser();

        baseUrl = jsonParser.getBaseUrl();
        prefix = jsonParser.getPrefix();
        savePath = jsonParser.getSavePath();
        articleAtrValue = jsonParser.getArticleAtrValue();
        firstTextAtrValue = jsonParser.getFirstTextAtrValue();
        secondTextAtrValue = jsonParser.getSecondTextAtrValue();
        headAtrValue = jsonParser.getHeadAtrValue();
        imgAtrValue = jsonParser.getImgAtrValue();
        imgUrlAtrName = jsonParser.getImgUrlAtrName();
        generalAtrName = jsonParser.getGeneralAtrName();
        settingsPath = jsonParser.getSettingsPath();

        startPoint = start;
        endPoint = end;

        errorFlag = jsonParser.isErrorFlag();
    }
}
