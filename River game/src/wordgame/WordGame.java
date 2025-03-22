package wordgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGame {

    private static final String DATA_FILE = "wordgame_data.ser";
    static final List<String> RIVERS = new ArrayList<>();
    static final List<String> RESERVOIRS = new ArrayList<>();
    static final List<String> CATEGORIES = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void createAndShowGUI() {
        loadData();

        JFrame frame = new JFrame("РЕКИ/ЯЗОВИРИ ИГРА");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Натиснете бутон за да получите дума:", SwingConstants.CENTER);
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton riverButton = new JButton("Река");
        JButton reservoirButton = new JButton("Язовир");
        JButton crudButton = new JButton("Админ");

        buttonPanel.add(riverButton);
        buttonPanel.add(reservoirButton);
        buttonPanel.add(crudButton);

        // Dynamically add category buttons
        for (String category : CATEGORIES) {
            JButton categoryButton = new JButton(category);
            categoryButton.addActionListener(e -> showCategoryWords(resultLabel, category));
            buttonPanel.add(categoryButton);
        }

        riverButton.addActionListener(e -> showRandomWord(resultLabel, RIVERS, "Река"));
        reservoirButton.addActionListener(e -> showRandomWord(resultLabel, RESERVOIRS, "Язовир"));
        crudButton.addActionListener(e -> new AdminWindow());

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(frame, label, resultLabel, buttonPanel.getComponents());
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showRandomWord(JLabel resultLabel, List<String> words, String type) {
        if (!words.isEmpty()) {
            String randomWord = words.get(RANDOM.nextInt(words.size()));
            resultLabel.setText("Получен(а) " + type + ": " + randomWord);
        } else {
            resultLabel.setText("Няма налични думи. Моля добавете " + type.toLowerCase() + " през Админ Менюто.");
        }
    }

    private static void showCategoryWords(JLabel resultLabel, String category) {
        resultLabel.setText("Избрана категория: " + category);
    }

    private static void adjustFontSize(JFrame frame, JLabel label, JLabel resultLabel, Component... components) {
        int newFontSize = Math.min(frame.getWidth(), frame.getHeight()) / 20;
        Font baseFont = new Font("Arial", Font.PLAIN, newFontSize);
        label.setFont(baseFont);
        resultLabel.setFont(baseFont);
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setFont(baseFont);
            }
        }
    }

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(RIVERS);
            oos.writeObject(RESERVOIRS);
            oos.writeObject(CATEGORIES);
        } catch (IOException e) {
            showErrorDialog("Грешка при запазване на данни.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                RIVERS.addAll((List<String>) ois.readObject());
                RESERVOIRS.addAll((List<String>) ois.readObject());
                CATEGORIES.addAll((List<String>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                showErrorDialog("Грешка при зареждане на данни.", e);
            }
        }
    }

    private static void showErrorDialog(String message, Exception e) {
        JOptionPane.showMessageDialog(null, message + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
