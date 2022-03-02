package core.habr.settings;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import core.habr.abstraction.ParserSettings;
import lombok.RequiredArgsConstructor;

/**
 * Содержит объекты для создания конфиг. файла.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@RequiredArgsConstructor
public class InitialParserSettings extends ParserSettings {
    public InitialParserSettings(String baseUrl,
                                 String prefix,
                                 String savePath,
                                 String articleAtrValue,
                                 String firstTextAtrValue,
                                 String secondTextAtrValue,
                                 String headAtrValue,
                                 String imgAtrValue,
                                 String imgUrlAtrName,
                                 String generalAtrName,
                                 String pageParseError,
                                 String textParseError,
                                 String headerParseError,
                                 String imgParseError) {
        this.articleAtrValue = articleAtrValue;
        this.firstTextAtrValue = firstTextAtrValue;
        this.secondTextAtrValue = secondTextAtrValue;
        this.headAtrValue = headAtrValue;
        this.imgAtrValue = imgAtrValue;
        this.imgUrlAtrName = imgUrlAtrName;
        this.generalAtrName = generalAtrName;
        this.savePath = savePath;
        this.baseUrl = baseUrl;
        this.prefix = prefix;
        this.pageParseError = pageParseError;
        this.textParseError = textParseError;
        this.headerParseError = headerParseError;
        this.imgParseError = imgParseError;
    }
}
