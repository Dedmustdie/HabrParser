package core.habr;

import core.ErrorHandler;
import lombok.Getter;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Набор инструментов для работы с парсером настроек из json файла.
 */
public class JsonParser {
    @Getter
    private String baseUrl = null;
    @Getter
    private String prefix = null;
    @Getter
    private String savePath = null;
    @Getter
    private boolean errorFlag = false;

    /**
     * Осуществляет извлечение настроек из json файла.
     *
     * @param settingsName     название настройки в файле настроек.
     * @param settingsFilePath путь к файлу настроек.
     * @param errorHandler     обработчик ошибок.
     */
    public JsonParser(final String settingsName, final String settingsFilePath, final ErrorHandler errorHandler) {
        try {
            val parser = new JSONParser();
            val reader = new FileReader(settingsFilePath);
            val rootObject = (JSONObject) parser.parse(reader);
            val settingsObject = (JSONObject) rootObject.get(settingsName);
            reader.close();

            baseUrl = settingsObject.get("baseUrl").toString();
            prefix = settingsObject.get("prefix").toString();
            savePath = settingsObject.get("savePath").toString();
        } catch (IOException | ParseException | NullPointerException e) {
            errorFlag = true;
            // Добавляем обработчик ошибок в лист.
            val onErrorList = new ArrayList<ErrorHandler>();
            onErrorList.add(errorHandler);
            // Вызываем обработчик ошибок.
            onErrorList.get(0).onError(this, e.getMessage());
        }
    }
}

