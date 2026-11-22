import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RecursiveListerGUI extends JFrame {

    private final JTextArea outputArea = new JTextArea(20, 55);

    public RecursiveListerGUI() {
        setTitle("Lab 11 - Recursive File Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel =
                new JLabel("Recursive File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        add(titleLabel, BorderLayout.NORTH);

        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> chooseDirectory());
        quitButton.addActionListener(e -> System.exit(0));

        pack();
        setLocationRelativeTo(null);
    }

    private void chooseDirectory() {
        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Select a Directory");
        chooser.setFileSelectionMode(
                JFileChooser.DIRECTORIES_ONLY
        );

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = chooser.getSelectedFile();

            outputArea.setText("");
            outputArea.append("Selected Directory: "
                    + selectedDirectory.getAbsolutePath() + "\n\n");

            listFiles(selectedDirectory, 0);
        }
    }

    private void listFiles(File directory, int level) {
        File[] files = directory.listFiles();

        if (files == null) {
            outputArea.append("Unable to access: "
                    + directory.getAbsolutePath() + "\n");
            return;
        }

        for (File file : files) {
            String indent = "    ".repeat(level);

            if (file.isDirectory()) {
                outputArea.append(indent
                        + "[Folder] " + file.getName() + "\n");

                listFiles(file, level + 1);
            } else {
                outputArea.append(indent
                        + "[File] " + file.getName() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecursiveListerGUI gui = new RecursiveListerGUI();
            gui.setVisible(true);
        });
    }
}
