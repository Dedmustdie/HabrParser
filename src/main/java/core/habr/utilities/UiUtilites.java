package core.habr.utilities;

public class UiUtilites {
    /**
     * Проверяет введенный в поля промежутка парсинг текст на валидность.
     *
     * @param text текст промежутка.
     * @return true, если текст валидный. false, если текст не валидный.
     */
    public static boolean rangeTextValidation(final String text) {
        return text.matches("^.(([1][0])|([1-9]))?");
    }
}
