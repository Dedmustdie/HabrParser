package core.habr.model;

import core.habr.abstraction.ParserSettings;
import core.habr.constants.Constants;
import static core.habr.utilities.ParserUtilites.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Содержит инструменты для парсинга статей из страницы.
 */
@RequiredArgsConstructor
public class ArticlesParser {
    private final ParserSettings parserSettings;
    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статей.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    public List<String> parse(final Document document) {
        val list = new ArrayList<String>();

        val elements = document.getElementsByAttributeValue(parserSettings.getGeneralAtrName(), parserSettings.getArticleAtrValue());
        if (CollectionUtils.isEmpty(elements)) {
            list.add(parserSettings.getPageParseError() + Constants.END_LINE);
            return list;
        }

        val index = new AtomicInteger(1);
        elements.forEach(element -> {
            // Парсим заголовки.
            list.add(index + ") " + extractDataFromHeaderElement(element, parserSettings) + Constants.END_LINE);

            // Парсим текст.
            list.add(extractDataFromTextElement(element,
                    parserSettings) +
                    Constants.END_LINE);

            // Парсим URL изображений.
            list.add(extractDataFromImgElement(element, parserSettings) + Constants.EMPTY_LINE);

            index.getAndIncrement();
        });
        return list;
    }
}
