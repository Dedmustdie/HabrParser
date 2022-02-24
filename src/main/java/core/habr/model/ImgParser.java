package core.habr.model;

import lombok.val;
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

        val elements = document.getElementsByAttributeValue("class", "tm-article-snippet");
        if (elements.isEmpty()) {
            return list;
        }

        elements.forEach(element -> {
            // Получаем элемент, содержащий URL картинки.
            val imgElement = element.getElementsByAttributeValue("class", "tm-article-snippet__lead-image").first();
            if (imgElement == null) {
                return;
            }
            list.add(imgElement.attr("src"));
        });
        return list;
    }
}
