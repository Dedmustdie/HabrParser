package core.habr.model;

import core.habr.constants.ParserConstants;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Содержит инструменты для парсинга URL всех картинок статей страницы.
 */
public class ImgParser {
    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статьи.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    public ArrayList<String> parse(final Document document) {
        val list = new ArrayList<String>();

        val elements = document.getElementsByAttributeValue(ParserConstants.CLASS_ATR_NAME, ParserConstants.ARTICLE_ATR_VALUE);
        if (CollectionUtils.isEmpty(elements)) {
            return list;
        }

        elements.forEach(element -> {
            // Получаем элемент, содержащий URL картинки.
            val imgElement = element.getElementsByAttributeValue(ParserConstants.CLASS_ATR_NAME, ParserConstants.IMG_ATR_VALUE).first();
            if (imgElement == null) {
                return;
            }
            list.add(imgElement.attr(ParserConstants.SRC_ATR_NAME));
        });
        return list;
    }
}
