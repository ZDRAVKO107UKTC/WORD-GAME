import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class WordGame {

    // Lists of rivers and reservoirs
    private static final List<String> RIVERS = Arrays.asList(
            "Лом", "Скът", "Цибрица", "Росица", "Огоста", "Вит", "Осъм", "Янтра", "Русенски Лом",
            "Провадийска", "Камчия", "Факийска", "Ропотамо", "Велека", "Резовска", "Арда", "Въча",
            "Тополница", "Луда Яна", "Стряма", "Марица", "Искър", "Струма", "Места", "Тунджа",
            "Чепеларска", "Чепинска", "Сазлийка"
    );

    private static final List<String> RESERVOIRS = Arrays.asList(
            "яз. Кърджали", "яз. Студен кладенец", "яз. Ивайловград", "яз. Доспат", "яз. Батак",
            "яз. Александър Стамболийски", "яз. Копринка", "яз. Жребчево", "яз. Искър",
            "яз. Цонево", "яз. Огоста", "яз. Пчелина", "яз. Тича", "яз. Сопот"
    );

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Random Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Create components
        JLabel label = new JLabel("Click a button to get a random word:", SwingConstants.CENTER);
        JButton riverButton = new JButton("Get Random River");
        JButton reservoirButton = new JButton("Get Random Язовир");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        // Set layout
        frame.setLayout(new GridLayout(4, 1));
        frame.add(label);
        frame.add(riverButton);
        frame.add(reservoirButton);
        frame.add(resultLabel);

        // Add action listeners
        riverButton.addActionListener(_ -> {
            String randomRiver = RIVERS.get(new Random().nextInt(RIVERS.size()));
            resultLabel.setText("Random River: " + randomRiver);
        });

        reservoirButton.addActionListener(_ -> {
            String randomReservoir = RESERVOIRS.get(new Random().nextInt(RESERVOIRS.size()));
            resultLabel.setText("Random Reservoir: " + randomReservoir);
        });

        // Show the frame
        frame.setVisible(true);
    }
}
