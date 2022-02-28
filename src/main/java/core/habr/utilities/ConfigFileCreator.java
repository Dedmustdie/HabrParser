package core.habr.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.habr.InitialParserSettings;
import core.habr.abstraction.ErrorHandler;
import core.habr.constants.ParserSettingsConstants;

import java.io.File;
import java.io.IOException;

/**
 * Отвечает за создание конфиг. файла.
 */
public class ConfigFileCreator {
    /**
     * Создает конфиг. файл.
     *
     * @param errorHandler обработчик ошибок.
     */
    public void createInitialSettingsJsonFile(final ErrorHandler errorHandler) {
        var mapper = new ObjectMapper();
        var initialParserSettings = new InitialParserSettings(ParserSettingsConstants.INITIAL_BASE_URL,
                ParserSettingsConstants.INITIAL_PREFIX,
                ParserSettingsConstants.INITIAL_SAVE_PATH);
        if (new File(ParserSettingsConstants.SETTINGS_PATH).exists()) {
            return;
        }
        try {
            mapper.writeValue(new File(ParserSettingsConstants.SETTINGS_PATH), initialParserSettings);
        } catch (IOException exception) {
            errorHandler.onError(exception.getMessage());
        }
    }
}
