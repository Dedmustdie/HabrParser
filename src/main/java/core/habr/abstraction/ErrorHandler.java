package core.habr.abstraction;

/**
 * Устанавливает базовую реализацию обработчика ошибок.
 */
public interface ErrorHandler {
    /**
     * Выполняет ряд действией в случае ошибки.
     *
     * @param errorText текст ошибки.
     */
    void onError(final String errorText);
}
