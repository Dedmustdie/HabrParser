package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ParserSettings;
import core.habr.constants.ParserSettingsConstants;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Набор инструментов для работы с парсером настроек из json файла.
 */
public class JsonParser extends ParserSettings {

    /**
     * @param errorHandler обработчик ошибок.
     */
    public JsonParser(final ErrorHandler errorHandler) {
        try {
            val parser = new JSONParser();
            val reader = new FileReader(ParserSettingsConstants.SETTINGS_PATH);
            val rootObject = (JSONObject) parser.parse(reader);
            reader.close();

            baseUrl = rootObject.get(ParserSettingsConstants.BASE_URL_KEY).toString();
            prefix = rootObject.get(ParserSettingsConstants.PREFIX_KEY).toString();
            savePath = rootObject.get(ParserSettingsConstants.SAVE_PATH_KEY).toString();
        } catch (IOException | ParseException | NullPointerException exception) {
            errorFlag = true;
            errorHandler.onError(exception.getMessage());
        }
    }

    public JsonParser() {
        try {
            val parser = new JSONParser();
            val reader = new FileReader(ParserSettingsConstants.SETTINGS_PATH);
            val rootObject = (JSONObject) parser.parse(reader);
            reader.close();

            baseUrl = rootObject.get(ParserSettingsConstants.BASE_URL_KEY).toString();
            prefix = rootObject.get(ParserSettingsConstants.PREFIX_KEY).toString();
            savePath = rootObject.get(ParserSettingsConstants.SAVE_PATH_KEY).toString();
        } catch (IOException | ParseException | NullPointerException exception) {
            errorFlag = true;
        }
    }


}

