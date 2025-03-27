package wordgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class WordGame {

    private static final String DATA_FILE = "wordgame_data.json"; // JSON файл за данните
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // Gson инстанция
    private static final Random RANDOM = new Random();
    private static final Map<String, List<String>> categoriesMap = new HashMap<>();
    private static JPanel buttonPanel;
    private static JLabel resultLabel;

    public static void createAndShowGUI() {
        loadData();

        JFrame frame = new JFrame("РЕКИ/ЯЗОВИРИ ИГРА");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Натиснете бутон, за да получите дума:", SwingConstants.CENTER);
        resultLabel = new JLabel("", SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        // Добавяме бутоните за категориите + "Админ"
        updateCategoryButtons();

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        // Listener за промяна на размера на прозореца
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(frame);
            }
        });

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Запазваме данните при затваряне на прозореца
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });
    }

    public static void updateCategoryButtons() {
        buttonPanel.removeAll();

        // Създаваме бутони за всяка категория
        for (String category : categoriesMap.keySet()) {
            JButton categoryButton = new JButton(category);
            categoryButton.addActionListener(e -> showRandomWord(categoriesMap.get(category), category));
            buttonPanel.add(categoryButton);
        }

        // Добавяме бутон "Админ" накрая
        JButton adminButton = new JButton("Админ");
        adminButton.addActionListener(e -> new AdminWindow(categoriesMap));
        buttonPanel.add(adminButton);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private static void adjustFontSize(JFrame frame) {
        int frameHeight = frame.getHeight();
        int componentCount = buttonPanel.getComponents().length > 0 ? buttonPanel.getComponents().length : 1;
        int newFontSize = Math.max(12, Math.min(frameHeight / (componentCount + 2), 18));

        Font baseFont = new Font("Arial", Font.PLAIN, newFontSize);

        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setFont(baseFont);
            }
        }

        resultLabel.setFont(baseFont);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public static void saveData() {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            GSON.toJson(categoriesMap, writer); // Записваме map като JSON
        } catch (IOException e) {
            showErrorDialog("Грешка при запазване на данните в JSON файл.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Map<String, List<String>> data = GSON.fromJson(reader, categoriesMap.getClass());
                if (data != null) {
                    categoriesMap.putAll(data); // Зареждаме JSON данните
                } else {
                    initializeDefaultCategories();
                }
            } catch (IOException e) {
                showErrorDialog("Грешка при зареждане на данните от JSON файл.", e);
                initializeDefaultCategories();
            }
        } else {
            initializeDefaultCategories();
        }
    }

    private static void initializeDefaultCategories() {
        categoriesMap.put("Реки", new ArrayList<>());
        categoriesMap.put("Язовири", new ArrayList<>());
    }

    private static void showRandomWord(List<String> words, String category) {
        if (!words.isEmpty()) {
            String randomWord = words.get(RANDOM.nextInt(words.size()));
            resultLabel.setText("Получена дума от категория '" + category + "': " + randomWord);
        } else {
            resultLabel.setText("Няма налични думи в категория '" + category + "'.");
        }
    }

    private static void showErrorDialog(String message, Exception e) {
        JOptionPane.showMessageDialog(null, message + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::createAndShowGUI);
    }
}