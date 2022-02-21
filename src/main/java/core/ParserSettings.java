package core;

import lombok.Getter;

/**
 * Задает основные поля и методы для реализации настроек парсера.
 */
public abstract class ParserSettings {
    /**
     * Адрес сайта.
     */
    public static String BASE_URL;

    /**
     * Префих страницы.
     */
    public static String PREFIX;

    /**
     * Путь для скачивания файлов.
     */
    public static String SAVE_PATH;

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
}
