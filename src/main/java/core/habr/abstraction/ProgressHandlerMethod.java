package core.habr.abstraction;

import lombok.Setter;

/**
 * Устанавливает базовую реализацию обработчика прогресса приложения.
 */
public interface ProgressHandlerMethod {
    /**
     * Передает данные о прогрессе процесса.
     *
     * @param processName имя процесса.
     * @param progressValue значение прогресса.
     */
    void onProgress(final String processName, final int progressValue);

}
