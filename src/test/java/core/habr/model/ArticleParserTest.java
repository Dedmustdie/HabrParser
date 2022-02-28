package core.habr.model;

import core.habr.HabrSettings;
import core.habr.HtmlLoader;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class ArticleParserTest {
    /**
     * Тест на кол-во полученных статей с 10-ти страниц.
     */
    @Test
    public void articlesSumCountTest() {
        int articlesCount = 0;
        val parserSettings = new HabrSettings(1, 10);

        for (int index = parserSettings.getStartPoint(); index <= parserSettings.getEndPoint(); index++) {
            val document = new HtmlLoader(parserSettings).getSourceByPageId(index);
            if (document == null) {
                return;
            }
            articlesCount += new ArticlesParser().parse(document).size();
        }

        // На каждой странице должно быть 20 статей => на 10 страницах - 200 статей.
        // Парсер возвращает список строк, для каждой статьи выделно 3 строки =>
        // делим размер списка на 3.
        Assert.assertEquals(200, articlesCount / 3);
    }

    /**
     * Тест на кол-во статей на каждой статей.
     */
    @Test
    public void articleCountTest() {
        val parserSettings = new HabrSettings(1, 10);
        boolean errorFlag = false;

        for (int index = parserSettings.getStartPoint(); index <= parserSettings.getEndPoint(); index++) {
            val document = new HtmlLoader(parserSettings).getSourceByPageId(index);
            if (document == null) {
                return;
            }

            // На каждой странице должно быть 20 статей.
            // Парсер возвращает список строк, для каждой статьи выделно 3 строки =>
            // делим размер списка на 3.
            if (new ArticlesParser().parse(document).size() / 3 != 20) {
                errorFlag = true;
                break;
            }
        }
        Assert.assertFalse(errorFlag);
    }
}