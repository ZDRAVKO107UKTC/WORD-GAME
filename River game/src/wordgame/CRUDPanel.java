package wordgame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONArray;

public class CRUDPanel extends JPanel {

    private final DefaultListModel<String> listModel;
    private final JList<String> itemList;
    private final JTextField inputField;
    private final JPanel buttonPanelForWords; // Holds dynamically created buttons
    private final String categoryName; // Category name used for saving/loading file

    public CRUDPanel(String categoryName, List<String> wordList) {
        this.categoryName = categoryName;

        this.listModel = new DefaultListModel<>();
        wordList.forEach(listModel::addElement);

        this.itemList = new JList<>(listModel);
        this.inputField = new JTextField();

        // This panel will hold dynamically created buttons for each word
        buttonPanelForWords = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanelForWords.setBackground(Color.LIGHT_GRAY);

        // CRUD buttons: Add, Delete, Update
        JButton addButton = new JButton("Добави");
        JButton deleteButton = new JButton("Изтрий");
        JButton updateButton = new JButton("Обнови");

        // Attach button listeners
        addButton.addActionListener(e -> addWord());
        deleteButton.addActionListener(e -> deleteWord());
        updateButton.addActionListener(e -> updateWord());

        JPanel crudButtonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        crudButtonPanel.add(addButton);
        crudButtonPanel.add(deleteButton);
        crudButtonPanel.add(updateButton);

        // Layout configuration
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(itemList), BorderLayout.CENTER); // Word list
        add(inputField, BorderLayout.NORTH);                // Input field
        add(crudButtonPanel, BorderLayout.SOUTH);           // CRUD buttons
        add(new JScrollPane(buttonPanelForWords), BorderLayout.EAST); // Word buttons

        // Load saved data (if file exists)
        loadFromFile();

        // Add buttons for existing words
        listModel.elements().asIterator().forEachRemaining(this::addButtonForWord);
    }

    // Add a word to the list and dynamically create a button
    private void addWord() {
        String newWord = inputField.getText().trim();
        if (!newWord.isEmpty() && !listModel.contains(newWord)) {
            listModel.addElement(newWord); // Add to list model
            addButtonForWord(newWord);    // Create a button for the word
            saveToFile();                 // Save the data to file
            inputField.setText("");       // Clear the input field
        } else {
            JOptionPane.showMessageDialog(this, "Думата вече съществува или е невалидна.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete the selected word and remove its button
    private void deleteWord() {
        String selectedWord = itemList.getSelectedValue();
        if (selectedWord != null) {
            listModel.removeElement(selectedWord); // Remove from list model
            removeButtonForWord(selectedWord);    // Remove the button
            saveToFile();                         // Save the updated data
        } else {
            JOptionPane.showMessageDialog(this, "Моля, изберете дума за изтриване.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Edit the selected word and update its button
    private void updateWord() {
        String selectedWord = itemList.getSelectedValue();
        if (selectedWord != null) {
            String newWord = JOptionPane.showInputDialog(this, "Въведете новата дума:", selectedWord);
            if (newWord != null && !newWord.trim().isEmpty() && !listModel.contains(newWord)) {
                int index = listModel.indexOf(selectedWord);
                listModel.set(index, newWord);       // Update the list model
                updateButtonForWord(selectedWord, newWord); // Update the button
                saveToFile();                       // Save the updated data
            } else {
                JOptionPane.showMessageDialog(this, "Думата е невалидна или вече съществува.", "Грешка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Моля, изберете дума за редактиране.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add a button for the given word
    private void addButtonForWord(String word) {
        JButton wordButton = new JButton(word);

        // Add a sample action listener to the button
        wordButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Бутонът за думата \"" + word + "\" беше натиснат.",
                "Информация за думата",
                JOptionPane.INFORMATION_MESSAGE
        ));

        buttonPanelForWords.add(wordButton); // Add the button to the panel
        revalidate(); // Recalculate layout
        repaint();    // Refresh the UI
    }

    // Remove the button corresponding to the given word
    private void removeButtonForWord(String word) {
        for (Component component : buttonPanelForWords.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(word)) {
                    buttonPanelForWords.remove(button); // Remove the button
                    break;
                }
            }
        }
        revalidate(); // Recalculate layout
        repaint();    // Refresh the UI
    }

    // Update the button when a word is updated
    private void updateButtonForWord(String oldWord, String newWord) {
        for (Component component : buttonPanelForWords.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(oldWord)) {
                    button.setText(newWord); // Update the button's text
                    break;
                }
            }
        }
        revalidate(); // Recalculate layout
        repaint();    // Refresh the UI
    }

    // Save the current list of words to a file
    private void saveToFile() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listModel.size(); i++) {
            jsonArray.put(listModel.get(i));
        }

        try {
            Files.write(Paths.get(categoryName + ".json"), jsonArray.toString().getBytes());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Неуспешно записване на файла.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load the list of words from a file
    private void loadFromFile() {
        Path filePath = Paths.get(categoryName + ".json");

        if (Files.exists(filePath)) {
            try {
                String content = new String(Files.readAllBytes(filePath));
                JSONArray jsonArray = new JSONArray(content);

                listModel.clear(); // Clear the existing list
                for (int i = 0; i < jsonArray.length(); i++) {
                    String word = jsonArray.getString(i);
                    listModel.addElement(word); // Add words to the model
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Неуспешно зареждане на файла.", "Грешка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}