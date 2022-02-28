package core.habr.model;

import core.habr.constants.Constants;
import core.habr.constants.ErrorConstants;
import core.habr.constants.ParserConstants;
import core.habr.utilities.ParserUtilites;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Содержит инструменты для парсинга статей из страницы.
 */
public class ArticlesParser {
    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статей.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    public ArrayList<String> parse(final Document document) {
        val list = new ArrayList<String>();

        val elements = document.getElementsByAttributeValue(ParserConstants.CLASS_ATR_NAME, ParserConstants.ARTICLE_ATR_VALUE);
        if (CollectionUtils.isEmpty(elements)) {
            list.add(ErrorConstants.PAGE_PARSE_ERROR + Constants.END_LINE);
            return list;
        }

        AtomicInteger index = new AtomicInteger(1);
        elements.forEach(element -> {
            // Парсим заголовки.
            list.add(index + ") " + ParserUtilites.extractDataFromHeaderElement(element, ParserConstants.HEAD_ATR_VALUE) + Constants.END_LINE);

            // Парсим текст.
            list.add(ParserUtilites.extractDataFromTextElement(element,
                    ParserConstants.FIRST_TEXT_ATR_VALUE,
                    ParserConstants.SECOND_TEXT_ATR_VALUE) +
                    Constants.END_LINE);

            // Парсим URL изображений.
            list.add(ParserUtilites.extractDataFromImgElement(element, ParserConstants.IMG_ATR_VALUE) + Constants.EMPTY_LINE);

            index.getAndIncrement();
        });
        return list;
    }
}
