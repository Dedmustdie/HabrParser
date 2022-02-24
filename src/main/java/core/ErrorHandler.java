package core;

/**
 * Устанавливает базовую реализацию обработчика ошибок.
 */
public interface ErrorHandler {
    /**
     * Выполняет ряд действией в случае ошибки.
     *
     * @param sender класс отправитель.
     * @param errorText текст ошибки.
     */
    void onError(final Object sender, final String errorText);
}
