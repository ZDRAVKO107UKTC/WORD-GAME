import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGame {

    private static final List<String> RIVERS = new ArrayList<>();
    private static final List<String> RESERVOIRS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Random Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Click a button to get a random word:", SwingConstants.CENTER);
        JButton riverButton = new JButton("Get Random River");
        JButton reservoirButton = new JButton("Get Random Reservoir");
        JButton crudButton = new JButton("Go to CRUD System");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(riverButton);
        buttonPanel.add(reservoirButton);
        buttonPanel.add(crudButton);

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        riverButton.setToolTipText("Get a random river name");
        reservoirButton.setToolTipText("Get a random reservoir name");
        crudButton.setToolTipText("Open CRUD window");

        riverButton.addActionListener(e -> {
            if (!RIVERS.isEmpty()) {
                String randomRiver = RIVERS.get(RANDOM.nextInt(RIVERS.size()));
                resultLabel.setText("Random River: " + randomRiver);
            } else {
                resultLabel.setText("No rivers available. Please add rivers in the CRUD system.");
            }
        });

        reservoirButton.addActionListener(e -> {
            if (!RESERVOIRS.isEmpty()) {
                String randomReservoir = RESERVOIRS.get(RANDOM.nextInt(RESERVOIRS.size()));
                resultLabel.setText("Random Reservoir: " + randomReservoir);
            } else {
                resultLabel.setText("No reservoirs available. Please add reservoirs in the CRUD system.");
            }
        });

        crudButton.addActionListener(e -> openCRUDWindow());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(frame, label, resultLabel, riverButton, reservoirButton, crudButton);
            }
        });

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void adjustFontSize(JFrame frame, JLabel label, JLabel resultLabel, JButton... buttons) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int newFontSize = Math.min(frameWidth, frameHeight) / 20;

        Font baseFont = new Font("Arial", Font.PLAIN, newFontSize);

        label.setFont(baseFont);
        resultLabel.setFont(baseFont);

        for (JButton button : buttons) {
            button.setFont(baseFont);
        }
    }

    private static void openCRUDWindow() {
        JFrame crudFrame = new JFrame("CRUD System");
        crudFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> riverModel = new DefaultListModel<>();
        DefaultListModel<String> reservoirModel = new DefaultListModel<>();
        RIVERS.forEach(riverModel::addElement);
        RESERVOIRS.forEach(reservoirModel::addElement);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Rivers", createCRUDPanel(riverModel, RIVERS));
        tabbedPane.addTab("Reservoirs", createCRUDPanel(reservoirModel, RESERVOIRS));

        crudFrame.add(tabbedPane);
        crudFrame.setSize(400, 300);
        crudFrame.setMinimumSize(new Dimension(300, 200));
        crudFrame.setLocationRelativeTo(null);
        crudFrame.setVisible(true);
    }

    private static JPanel createCRUDPanel(DefaultListModel<String> listModel, List<String> list) {
        JList<String> itemList = new JList<>(listModel);
        JTextField inputField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");

        addButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                listModel.addElement(text);
                list.add(text);
                inputField.setText("");
                inputField.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Input cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                list.remove(listModel.get(selectedIndex));
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            String text = inputField.getText().trim();
            if (selectedIndex != -1 && !text.isEmpty()) {
                list.set(list.indexOf(listModel.get(selectedIndex)), text);
                listModel.set(selectedIndex, text);
                inputField.setText("");
                inputField.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item and provide valid input to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }
}
