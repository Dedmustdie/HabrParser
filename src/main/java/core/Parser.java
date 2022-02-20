package core;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Устанавливает базовую реализации класса, осуществляющего парсинг документа.
 */
public interface Parser<T> {
    /**
     * Осуществляет парсинг документа.
     *
     * @param document страница для парсинга.
     * @return данные.
     */
    ArrayList<String> parse(Document document);
}
