package core.habr.model;

import core.habr.abstraction.ParserSettings;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Содержит инструменты для парсинга URL всех картинок статей страницы.
 */
@RequiredArgsConstructor
public class ImgParser {
    private final ParserSettings parserSettings;

    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статьи.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    public List<String> parse(final Document document) {

        val elements = document.getElementsByAttributeValue(parserSettings.getGeneralAtrName(), parserSettings.getArticleAtrValue());
        if (CollectionUtils.isEmpty(elements)) {
            return Collections.emptyList();
        }
        
        // Создаем лист с элементами, содержащими URL изображений.
        val urlElements = elements.
                stream().collect(ArrayList<Element>::new,
                        (list, item) -> list.add(item.getElementsByAttributeValue(parserSettings.getGeneralAtrName(), parserSettings.getImgAtrValue()).first()),
                        ArrayList::addAll);

        // Создаем лист с URL изображений.
        return urlElements.stream().filter(Objects::nonNull).
                collect(ArrayList::new,
                        (list, item) -> list.add(item.attr(parserSettings.getImgUrlAtrName())),
                        ArrayList::addAll);
    }
}
