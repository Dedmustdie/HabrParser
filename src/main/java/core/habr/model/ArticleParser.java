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
        if (elements.size() > 0) {
            elements.forEach(element -> {
                // Получаем элемент, содержащий текст статьи.
                Element textElement = element.getElementsByAttributeValue("class", "article-formatted-body article-formatted-body_version-2").first();
                // Если элемент, содержащий текст статьи, имеет альтернативное название класса.
                if (textElement == null) {
                    textElement = element.getElementsByAttributeValue("class", "article-formatted-body article-formatted-body_version-1").first();
                }
                // Получаем элемент, содержащий URL картинки.
                Element imgElement = element.getElementsByAttributeValue("class", "tm-article-snippet__lead-image").first();

                // Если ошибка парсинга заголовка не произошла.
                if (element.getElementsByAttributeValue("class", "tm-article-snippet__title-link").first() != null &&
                        element.getElementsByAttributeValue("class", "tm-article-snippet__title-link").first().children().size() > 0) {
                    // Получаем элемент, содержащий заголовок статьи.
                    Element headElement = element.getElementsByAttributeValue("class", "tm-article-snippet__title-link").first().child(0);
                    list.add(headElement.text() + "\n");
                } else {
                    list.add("Ошибка парсинга заголовка статьи!\n");
                }

                // Если произошла ошибка парсинга текста.
                if (textElement == null) {
                    list.add("Ошибка парсинга текста статьи!\n");
                } else {
                    list.add(textElement.text() + "\n");
                }

                // Если не удалось найти URL изображения.
                if (imgElement == null) {
                    list.add("Изображение отсутствует или произошла ошибка!");
                } else {
                    String imgUrl = imgElement.attr("src");
                    list.add(imgUrl);
                }
                list.add("\n\n");
            });
        } else {
            list.add("Ошибка парсинга страницы!\n");
        }
        return list;
    }
}
