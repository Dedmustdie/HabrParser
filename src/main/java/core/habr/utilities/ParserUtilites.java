package core.habr.utilities;

import core.habr.constants.ErrorConstants;
import core.habr.constants.ParserConstants;
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
    public static Element findElementByClassAttrValue(final Element element, final String value) {
        return element.getElementsByAttributeValue("class", value)
                .first();
    }

    /**
     * Извлекает URL изображения с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return URL изображения статьи.
     */
    public static String extractDataFromImgElement(final Element element, final String value) {
        // Получаем элемент, содержащий URL картинки.
        val imgElement = findElementByClassAttrValue(element, value);
        // Если не удалось найти URL изображения.
        if (imgElement != null) {
            return imgElement.attr(ParserConstants.SRC_ATR_NAME);
        }
        return ErrorConstants.IMG_PARSE_ERROR;
    }

    /**
     * Извлекает текст статьи с указанными значениями атрибута.
     *
     * @param element     родительский элемент.
     * @param firstValue  первое значение атрибута.
     * @param secondValue второе значение атрибута.
     * @return текст статьи.
     */
    public static String extractDataFromTextElement(final Element element, final String firstValue, final String secondValue) {
        // Получаем элемент, содержащий текст статьи.
        val textElement = Optional.ofNullable(findElementByClassAttrValue(element, firstValue))
                // Если элемент, содержащий текст статьи, имеет альтернативное название класса.
                .orElseGet(() -> findElementByClassAttrValue(element, secondValue));
        // Если произошла ошибка парсинга текста.
        if (textElement != null) {
            return textElement.text();
        }
        return ErrorConstants.TEXT_PARSE_ERROR;
    }

    /**
     * Извлекает заголовок статьи с указанным значением атрибута.
     *
     * @param element родительский элемент.
     * @param value   значение атрибута.
     * @return заголовок статьи.
     */
    public static String extractDataFromHeaderElement(final Element element, final String value) {

        // Если ошибка парсинга заголовка не произошла.
        val firstElementByClassAttrValue = findElementByClassAttrValue(element, value);
        if (firstElementByClassAttrValue != null &&
                !firstElementByClassAttrValue.children().isEmpty()) {
            // Получаем элемент, содержащий заголовок статьи.
            return firstElementByClassAttrValue
                    .child(0)
                    .text();
        }
        return ErrorConstants.HEADER_PARSE_ERROR;
    }
}
