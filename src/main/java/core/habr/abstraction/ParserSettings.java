package core.habr.abstraction;

import lombok.Getter;

/**
 * Задает основные поля и методы для реализации настроек парсера.
 */
public abstract class ParserSettings {
    /**
     * Адрес сайта.
     */
    @Getter
    protected String baseUrl;

    /**
     * Префих страницы.
     */
    @Getter
    protected String prefix;

    /**
     * Путь для скачивания файлов.
     */
    @Getter
    protected String savePath;

    /**
     * Начало пагинации.
     */
    @Getter
    protected int startPoint;

    /**
     * Конец пагинации.
     */
    @Getter
    protected int endPoint;

    /**
     * Обработчик ошибок.
     */
    @Getter
    protected ErrorHandler errorHandler;

    /**
     * Флаг ошибок.
     */
    @Getter
    protected boolean errorFlag = false;
}
