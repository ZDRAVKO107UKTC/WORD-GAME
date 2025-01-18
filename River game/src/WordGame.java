import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class WordGame {

    private static final List<String> RIVERS = Arrays.asList(
            "Лом", "Скът", "Цибрица", "Росица", "Огоста", "Вит", "Осъм",
            "Янтра","Русенски Лом", "Провадийска","Камчия","Факийска",
            "Ропотамо","Велека","Резовска","Арда","Въча","Тополница",
            "Луда Яна","Стряма","Марица","Искър","Струма","Места","Тунджа",
            "Чепеларска","Чепинска","Сазлийка"
    );

    private static final List<String> RESERVOIRS = Arrays.asList(
            "яз. Кърджали", "яз. Студен кладенец", "яз. Ивайловград",
            "яз. Доспат", "яз. Батак","яз. Александър Стамболийски",
            "яз. Копринка", "яз. Жребчево", "яз. Искър",
            "яз. Цонево", "яз. Огоста", "яз. Пчелина", "яз. Тича", "яз. Сопот"
    );

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Random Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Click a button to get a random word:", SwingConstants.CENTER);
        JButton riverButton = new JButton("Get Random River");
        JButton reservoirButton = new JButton("Get Random Язовир");
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

        // Adding tooltips for better user insight
        riverButton.setToolTipText("Get a random river name");
        reservoirButton.setToolTipText("Get a random reservoir name");
        crudButton.setToolTipText("Open CRUD window");

        riverButton.addActionListener(e -> {
            String randomRiver = RIVERS.get(RANDOM.nextInt(RIVERS.size()));
            resultLabel.setText("Random River: " + randomRiver);
        });

        reservoirButton.addActionListener(e -> {
            String randomReservoir = RESERVOIRS.get(RANDOM.nextInt(RESERVOIRS.size()));
            resultLabel.setText("Random Reservoir: " + randomReservoir);
        });

        crudButton.addActionListener(e -> openCRUDWindow());

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void openCRUDWindow() {
        JFrame crudFrame = new JFrame("CRUD System");
        crudFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> itemList = new JList<>(listModel);
        JTextField inputField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");

        addButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                listModel.addElement(text);
                inputField.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1 && JOptionPane.showConfirmDialog(crudFrame,
                    "Are you sure you want to delete this item?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                listModel.remove(selectedIndex);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            String text = inputField.getText().trim();
            if (selectedIndex != -1 && !text.isEmpty()) {
                listModel.set(selectedIndex, text);
                inputField.setText("");
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(new JPanel(new GridLayout(1, 3, 5, 5)) {{
            add(addButton);
            add(deleteButton);
            add(updateButton);
        }}, BorderLayout.SOUTH);

        crudFrame.setLayout(new BorderLayout(10, 10));
        crudFrame.add(new JScrollPane(itemList), BorderLayout.CENTER);
        crudFrame.add(inputPanel, BorderLayout.SOUTH);

        crudFrame.setSize(400, 300);
        crudFrame.setMinimumSize(new Dimension(300, 200));
        crudFrame.setLocationRelativeTo(null);
        crudFrame.setVisible(true);
    }
}