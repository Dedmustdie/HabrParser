package core.habr.model;

import core.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Содержит инструменты для парсинга статей из страницы.
 */
public class ArticleParser implements Parser<ArrayList<String>> {

    /**
     * Осуществляет парсинг заголовка, текста и URL картинки статей.
     *
     * @param document страница для парсинга.
     * @return заголовок, текст и URL картинки статьи.
     */
    @Override
    public ArrayList<String> parse(Document document) {
        ArrayList<String> list = new ArrayList<>();

        Elements elements = document.getElementsByAttributeValue("class", "tm-article-snippet");
        if (elements != null) {
            elements.forEach(element -> {
                // Получаем элемент, содержащий заголовок статьи.
                Element headElement = element.getElementsByAttributeValue("class", "tm-article-snippet__title-link").first().child(0);
                // Получаем элемент, содержащий текст статьи.
                Element textElement = element.getElementsByAttributeValue("class", "article-formatted-body article-formatted-body_version-2").first();
                // Получаем элемент, содержащий URL картинки.
                Element imgElement = element.getElementsByAttributeValue("class", "tm-article-snippet__lead-image").first();

                // Если элемент, содержащий текст статьи, имеет альтернативное название класса.
                if (textElement == null) {
                    textElement = element.getElementsByAttributeValue("class", "article-formatted-body article-formatted-body_version-1").first();
                }

                if (headElement != null && textElement != null) {
                    if (imgElement != null) {
                        String imgUrl = imgElement.attr("src");
                        list.add(headElement.text() + "\n"
                                + textElement.text() + "\n"
                                + imgUrl + "\n\n");
                    } else {
                        list.add(headElement.text() + "\n"
                                + textElement.text() + "\n"
                                + "Картинка отсутствует!" + "\n\n");
                    }
                }
            });
        }
        return list;
    }
}
