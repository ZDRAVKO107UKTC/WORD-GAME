package wordgame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CRUDPanel extends JPanel {

    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public CRUDPanel(String category, List<String> words) {
        setLayout(new BorderLayout(10, 10));

        // Инициализиране на списъка с думи
        for (String word : words) {
            listModel.addElement(word);
        }

        JList<String> wordList = new JList<>(listModel);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(wordList);

        // Панел за бутоните
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton addButton = new JButton("Добави");
        JButton removeButton = new JButton("Премахни");
        JButton editButton = new JButton("Редактиране");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        // Добавяне на думи
        addButton.addActionListener(e -> {
            String newWord = JOptionPane.showInputDialog(this, "Въведете нова дума за категорията '" + category + "':");
            if (newWord != null && !newWord.trim().isEmpty()) {
                listModel.addElement(newWord);
                words.add(newWord);
            }
        });

        // Премахване на думи
        removeButton.addActionListener(e -> {
            int selectedIndex = wordList.getSelectedIndex();
            if (selectedIndex != -1) {
                String wordToRemove = listModel.getElementAt(selectedIndex);
                listModel.remove(selectedIndex);
                words.remove(wordToRemove);
            } else {
                JOptionPane.showMessageDialog(this, "Моля, изберете дума за премахване.");
            }
        });

        // Редактиране на думи
        editButton.addActionListener(e -> {
            int selectedIndex = wordList.getSelectedIndex();
            if (selectedIndex != -1) {
                String wordToEdit = listModel.getElementAt(selectedIndex);
                String newWord = JOptionPane.showInputDialog(this, "Редактирайте думата:", wordToEdit);
                if (newWord != null && !newWord.trim().isEmpty()) {
                    listModel.set(selectedIndex, newWord);
                    words.set(selectedIndex, newWord);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Моля, изберете дума за редактиране.");
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}