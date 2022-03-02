package core.habr.abstraction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * Задает основные поля и методы для реализации настроек парсера.
 */

@Getter
public abstract class ParserSettings {
    /**
     * Адрес сайта.
     */
    protected String baseUrl;

    /**
     * Префих страницы.
     */
    protected String prefix;

    /**
     * Путь для скачивания файлов.
     */
    protected String savePath;

    /**
     * Значение атрибута элемента с URL изображения.
     */
    protected String articleAtrValue;

    /**
     * Первое значение атрибута текстового элемента.
     */
    protected String firstTextAtrValue;

    /**
     * Второе значение атрибута текстового элемента.
     */
    protected String secondTextAtrValue;

    /**
     * Значение атрибута элемента заголовка.
     */
    protected String headAtrValue;

    /**
     * Значение атрибута элемента с URL изображения.
     */
    protected String imgAtrValue;

    /**
     * Название атрибута элемента с URL изображения.
     */
    protected String imgUrlAtrName;

    /**
     * Значение атрибута class элемента.
     */
    protected String generalAtrName;

    /**
     * Путь настроек парсера.
     */
    protected String settingsPath;

    /**
     * Ошибка парсинга страницы.
     */
    protected String pageParseError;

    /**
     * Ошибка парсинга текста статьи.
     */
    protected String textParseError;

    /**
     * Ошибка парсинга текста статьи.
     */
    protected String headerParseError;

    /**
     * Ошибка парсинга текста статьи.
     */
    protected String imgParseError;

    /**
     * Начало пагинации.
     */
    @JsonIgnore
    protected int startPoint;

    /**
     * Конец пагинации.
     */
    @JsonIgnore
    protected int endPoint;

    /**
     * Обработчик ошибок.
     */
    @JsonIgnore
    protected ErrorHandler errorHandler;

    /**
     * Флаг ошибок.
     */
    @JsonIgnore
    protected boolean errorFlag = false;
}
