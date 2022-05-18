package core.habr;

import core.habr.abstraction.ErrorHandler;
import core.habr.abstraction.ProgressHandler;
import core.habr.settings.HabrSettings;
import core.habr.utilities.ConfigFileCreator;
import core.habr.utilities.UiUtilites;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Отвечает за реализацию пользовательского интерфейса пользователя.
 */
public class UI extends JFrame {
    // Текстовые поля.
    final JTextArea textArea = new JTextArea();
    final JTextField startTextField = new JTextField(10);
    final JTextField endTextField = new JTextField(10);
    final JProgressBar progressBar = new JProgressBar();
    final JTextField processTextField = new JTextField(10);
    // Создаем две панели.
    final JPanel rightPanel = new JPanel();
    final JPanel leftPanel = new JPanel();
    // Создаем кнопки.
    final JButton startButton = new JButton("Start");
    final JButton clearButton = new JButton("Clear");
    // Создаем текстовую панель с прокруткой.
    final JScrollPane scroll = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    public UI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Habr Parser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(1200, 500));
        setMaximumSize(new Dimension(2000, 600));
        setMinimumSize(new Dimension(600, 500));

        processTextField.setEnabled(false);

        // Указываем размеры панелей.
        rightPanel.setPreferredSize(new Dimension(200, 500));
        leftPanel.setPreferredSize(new Dimension(1000, 500));

        // Указываем привязки панелей.
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.CENTER);

        // Настраиваем сетку панелей.
        rightPanel.setLayout(new GridLayout(0, 1, 0, 15));
        leftPanel.setLayout(new GridLayout(0, 1, 1, 1));

        // Добавляем элементы на правую панель.
        rightPanel.add(new JLabel("Первая страница"));
        rightPanel.add(startTextField);
        rightPanel.add(new JLabel("Последняя страница"));
        rightPanel.add(endTextField);
        rightPanel.add(startButton);
        rightPanel.add(clearButton);
        rightPanel.add(processTextField);
        rightPanel.add(progressBar);

        // Добавляем элементы на левую панель.
        leftPanel.add(scroll);

        // Создаем конфиг. файл.
        ConfigFileCreator configFileCreator = new ConfigFileCreator();
        configFileCreator.createInitialSettingsJsonFile(new Error());

        // Обработчик нажатий на кнопку старт.
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setEnabled(false);
                clearButton.setEnabled(false);
                startTextField.setEnabled(false);
                endTextField.setEnabled(false);

                super.mouseClicked(e);
                val startText = startTextField.getText();
                val endText = endTextField.getText();
                var validationFlag = true;

                // Ограничения на вводимые данные.
                if (!(UiUtilites.rangeTextValidation(startText))) {
                    startTextField.setText("1");
                    validationFlag = false;
                }
                if (!(UiUtilites.rangeTextValidation(endText))) {
                    endTextField.setText("1");
                    validationFlag = false;
                }
                if (!validationFlag) {
                    return;
                }

                val start = Integer.parseInt(startText);
                val end = Integer.parseInt(endText);

                if (start > end) {
                    startTextField.setText(endTextField.getText());
                    return;
                }
                // Устанавливаем настройки.
                val parser = new ParserWorker(new HabrSettings(start, end, new Error()), new Error(), new Progress());
                // Добавляем обработчики.
                parser.setCompletedHandler(new Completed());
                parser.setNewDataHandler(new NewData());
                parser.start();
            }
        });

        // Обработчик нажатий на кнопку отчистить.
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setText("");
            }
        });
        pack();
        setVisible(true);
    }

    /**
     * Обработчик окончания парсинга.
     */
    class Completed implements ParserWorker.OnCompletedHandler {
        @Override
        public void onCompleted() {
            textArea.append("Парсинг завершен!\n");
            startButton.setEnabled(true);
            clearButton.setEnabled(true);
            startTextField.setEnabled(true);
            endTextField.setEnabled(true);
        }
    }

    /**
     * Обработчик полученных при парсинге данных.
     */
    class NewData implements ParserWorker.OnNewDataHandler {
        @Override
        public void onNewData(final List<String> dataList) {
            for (String data : dataList) {
                textArea.append(data);
            }
        }
    }

    /**
     * Обработчик полученных при парсинге данных.
     */
    class Error implements ErrorHandler {
        @Override
        public void onError(final String errorText) {
            textArea.append("\nОшибка: " + errorText + "\n");
        }
    }

    /**
     * Обработчик прогресса приложения.
     */
    class Progress extends ProgressHandler {
        @Override
        public void onProgress(String processName, int progressValue) {
            progressBar.setMaximum(getBarDivisionCount());
            processTextField.setText(processName);
            progressBar.setValue(progressValue);
        }
    }
}
