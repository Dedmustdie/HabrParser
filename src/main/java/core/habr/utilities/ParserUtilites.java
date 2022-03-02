package core.habr.utilities;

import core.habr.abstraction.ParserSettings;
import lombok.val;
import org.jsoup.nodes.Element;

import java.util.Optional;
/**
 * Набор методов для работы с парсингом.
 */
public class ParserUtilites {
    /**
     * Извлекает элемент с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return извлеченный элемент.
     */
    public static Element findElementByClassAttrValue(final Element element, final String value, final ParserSettings parserSettings) {
        return element.getElementsByAttributeValue(parserSettings.getGeneralAtrName(), value)
                .first();
    }

    /**
     * Извлекает URL изображения с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param parserSettings  настройки парсера.
     * @return URL изображения статьи.
     */
    public static String extractDataFromImgElement(final Element element, final ParserSettings parserSettings) {
        // Получаем элемент, содержащий URL картинки.
        val imgElement = findElementByClassAttrValue(element, parserSettings.getImgAtrValue(), parserSettings);
        // Если не удалось найти URL изображения.
        if (imgElement != null) {
            return imgElement.attr(parserSettings.getImgUrlAtrName());
        }
        return parserSettings.getImgParseError();
    }

    /**
     * Извлекает текст статьи с указанными значениями атрибута.
     *
     * @param element     родительский элемент.
     * @param parserSettings  настройки парсера.
     * @return текст статьи.
     */
    public static String extractDataFromTextElement(final Element element, final ParserSettings parserSettings) {
        // Получаем элемент, содержащий текст статьи.
        val textElement = Optional.ofNullable(findElementByClassAttrValue(element, parserSettings.getFirstTextAtrValue(), parserSettings))
                // Если элемент, содержащий текст статьи, имеет альтернативное название класса.
                .orElseGet(() -> findElementByClassAttrValue(element, parserSettings.getSecondTextAtrValue(), parserSettings));

        // Если произошла ошибка парсинга текста.
        if (textElement != null) {
            return textElement.text();
        }
        return parserSettings.getTextParseError();
    }

    /**
     * Извлекает заголовок статьи с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param parserSettings настройки парсера.
     * @return заголовок статьи.
     */
    public static String extractDataFromHeaderElement(final Element element, final ParserSettings parserSettings) {

        // Если ошибка парсинга заголовка не произошла.
        val firstElementByClassAttrValue = findElementByClassAttrValue(element, parserSettings.getHeadAtrValue(), parserSettings);
        if (firstElementByClassAttrValue != null &&
                !firstElementByClassAttrValue.children().isEmpty()) {
            // Получаем элемент, содержащий заголовок статьи.
            return firstElementByClassAttrValue
                    .child(0)
                    .text();
        }
        return parserSettings.getHeaderParseError();
    }
}
