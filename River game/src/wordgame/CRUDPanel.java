package wordgame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CRUDPanel extends JPanel {

    private final DefaultListModel<String> listModel;
    private final JList<String> itemList;
    private final JTextField inputField;
    private final JTabbedPane tabbedPane;
    private final Map<String, CRUDPanel> categoryPanels;

    public CRUDPanel(List<String> wordList, JTabbedPane tabbedPane, Map<String, CRUDPanel> categoryPanels) {
        this.tabbedPane = tabbedPane;
        this.categoryPanels = categoryPanels;

        this.listModel = new DefaultListModel<>();
        wordList.forEach(listModel::addElement);

        this.itemList = new JList<>(listModel);
        this.inputField = new JTextField();

        JButton addButton = new JButton("Добави");
        JButton deleteButton = new JButton("Изтрий");
        JButton updateButton = new JButton("Обнови");

        addButton.addActionListener(e -> addWord());
        deleteButton.addActionListener(e -> deleteWord());
        updateButton.addActionListener(e -> updateWord());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(itemList), BorderLayout.CENTER);
        add(inputField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addWord() {
        String newWord = inputField.getText().trim();
        if (!newWord.isEmpty() && !listModel.contains(newWord)) {
            listModel.addElement(newWord);
            inputField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Думата вече съществува или е невалидна.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteWord() {
        String selectedWord = itemList.getSelectedValue();
        if (selectedWord != null) {
            listModel.removeElement(selectedWord);
        } else {
            JOptionPane.showMessageDialog(this, "Моля, изберете дума за изтриване.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateWord() {
        String selectedWord = itemList.getSelectedValue();
        if (selectedWord != null) {
            String newWord = JOptionPane.showInputDialog(this, "Въведете новата дума:", selectedWord);
            if (newWord != null && !newWord.trim().isEmpty() && !listModel.contains(newWord)) {
                listModel.set(listModel.indexOf(selectedWord), newWord);
            } else {
                JOptionPane.showMessageDialog(this, "Думата е невалидна или вече съществува.", "Грешка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Моля, изберете дума за редактиране.", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> getWordList() {
        return java.util.Collections.list(listModel.elements());
    }
}
