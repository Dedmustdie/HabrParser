package core;

import org.jsoup.nodes.Document;

/**
 * Устанавливает базовую реализации класса, осуществляющего парсинг документа.
 */
public interface Parser<T> {
    /**
     * Осуществляет парсинг документа.
     * @param document страница для парсинга.
     */
    T Parse(Document document);
}
