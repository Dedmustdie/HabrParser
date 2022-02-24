package core.habr.model;

import lombok.val;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Содержит инструменты для парсинга статей из страницы.
 */
public class ArticleParser {
    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статей.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    public ArrayList<String> parse(final Document document) {
        val list = new ArrayList<String>();

        val elements = document.getElementsByAttributeValue("class", "tm-article-snippet");
        if (elements.isEmpty()) {
            list.add("Ошибка парсинга страницы!\n");
            return list;
        }

        elements.forEach(element -> {
            list.add(extractDataFromHeaderElement(element, "tm-article-snippet__title-link") + "\n");

            list.add(extractDataFromTextElement(element,
                    "article-formatted-body article-formatted-body_version-1",
                    "article-formatted-body article-formatted-body_version-2") + "\n");

            list.add(extractDataFromImgElement(element, "tm-article-snippet__lead-image") + "\n\n");
        });
        return list;
    }

    /**
     * Извлекает элемент с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return извлеченный элемент.
     */
    private Element findElementByClassAttrValue(final Element element, final String value) {
        return element.getElementsByAttributeValue("class", value)
                .first();
    }

    /**
     * Извлекает заголовок статьи с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return заголовок статьи.
     */
    private String extractDataFromHeaderElement(final Element element, final String value) {
        // Если ошибка парсинга заголовка не произошла.
        val firstElementByClassAttrValue = findElementByClassAttrValue(element, value);
        if (firstElementByClassAttrValue != null &&
                !firstElementByClassAttrValue.children().isEmpty()) {
            // Получаем элемент, содержащий заголовок статьи.
            return firstElementByClassAttrValue
                    .child(0)
                    .text();
        }
        return "Ошибка парсинга заголовка статьи!";
    }

    /**
     * Извлекает текст статьи с указанными значениями атрибута.
     *
     * @param element     родительский элемент.
     * @param firstValue  первое значение атрибута.
     * @param secondValue второе значение атрибута.
     * @return текст статьи.
     */
    private String extractDataFromTextElement(final Element element, final String firstValue, final String secondValue) {
        // Получаем элемент, содержащий текст статьи.
        val textElement = Optional.ofNullable(findElementByClassAttrValue(element, firstValue))
                // Если элемент, содержащий текст статьи, имеет альтернативное название класса.
                .orElseGet(() -> findElementByClassAttrValue(element, secondValue));
        // Если произошла ошибка парсинга текста.
        if (textElement != null) {
            return textElement.text();
        }
        return "Ошибка парсинга текста статьи!";
    }

    /**
     * Извлекает URL изображения с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return URL изображения статьи.
     */
    private String extractDataFromImgElement(final Element element, final String value) {
        // Получаем элемент, содержащий URL картинки.
        val imgElement = findElementByClassAttrValue(element, value);
        // Если не удалось найти URL изображения.
        if (imgElement != null) {
            return imgElement.attr("src");
        }
        return "Изображение отсутствует или произошла ошибка!";
    }
}
