package ui.Cook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class RecipeAddingPanel extends JPanel {

    private JTextField nameField;
    private JTextField categoryField;
    private JTextArea ingredientsArea;
    private JTextArea stepsArea;
    private JLabel imagePreview;
    private File selectedImage;

    public RecipeAddingPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // Card Panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("➕ Add New Recipe");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // Split Layout
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        contentPanel.setBackground(Color.WHITE);

        // === LEFT PANEL ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel nameLabel = createLabel("Recipe Name:");
        nameField = createTextField();

        JLabel categoryLabel = createLabel("Category:");
        categoryField = createTextField();

        JLabel ingredientsLabel = createLabel("Ingredients (one per line):");
        ingredientsArea = new JTextArea(8, 20);
        styleTextArea(ingredientsArea);
        JScrollPane ingredientScroll = new JScrollPane(ingredientsArea);
        ingredientScroll.setBorder(BorderFactory.createEmptyBorder());

        JLabel imageLabel = createLabel("Image:");
        imagePreview = new JLabel("No Image", SwingConstants.CENTER);
        imagePreview.setOpaque(true);
        imagePreview.setBackground(new Color(230, 230, 230));
        imagePreview.setPreferredSize(new Dimension(200, 200));
        imagePreview.setMaximumSize(new Dimension(120, 120));
        imagePreview.setMinimumSize(new Dimension(120, 120));
        imagePreview.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        imagePreview.setVerticalAlignment(SwingConstants.CENTER);
        imagePreview.setIconTextGap(0);



        JButton uploadButton = new JButton("Upload Image");
        stylePrimaryButton(uploadButton);
        uploadButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        uploadButton.addActionListener(e -> chooseImage());

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(nameField);
        leftPanel.add(Box.createVerticalStrut(10));

        leftPanel.add(categoryLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(categoryField);
        leftPanel.add(Box.createVerticalStrut(10));

        leftPanel.add(ingredientsLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(ingredientScroll);
        leftPanel.add(Box.createVerticalStrut(15));

        leftPanel.add(imageLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(imagePreview);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(uploadButton);

        // === RIGHT PANEL ===
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel stepsLabel = createLabel("Steps:");
        stepsArea = new JTextArea(15, 30);
        styleTextArea(stepsArea);
        JScrollPane stepsScroll = new JScrollPane(stepsArea);
        stepsScroll.setBorder(BorderFactory.createEmptyBorder());

        rightPanel.add(stepsLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(stepsScroll);

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // === BOTTOM BUTTONS ===
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("💾 Save Recipe");
        JButton cancelButton = new JButton("Cancel");
        stylePrimaryButton(saveButton);
        styleSecondaryButton(cancelButton);

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(cardPanel, BorderLayout.CENTER);
    }

    // --- Image Chooser ---
//    private void chooseImage() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Select Recipe Image");
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
//
//        int result = fileChooser.showOpenDialog(this);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            selectedImage = fileChooser.getSelectedFile();
//            ImageIcon icon = new ImageIcon(selectedImage.getAbsolutePath());
//            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//            imagePreview.setText("");
//            imagePreview.setIcon(new ImageIcon(img));
//        }
//    }
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Recipe Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selectedImage.getAbsolutePath());

            // Get the size of the preview box
            int boxWidth = 120;
            int boxHeight = 120;

            // Calculate scaling factor to fit within the box
            Image img = icon.getImage();
            double widthRatio = (double) boxWidth / img.getWidth(null);
            double heightRatio = (double) boxHeight / img.getHeight(null);
            double scale = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (img.getWidth(null) * scale);
            int newHeight = (int) (img.getHeight(null) * scale);

            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            imagePreview.setText("");
            imagePreview.setIcon(new ImageIcon(scaledImg));
            imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
            imagePreview.setVerticalAlignment(SwingConstants.CENTER);
            imagePreview.revalidate();
            imagePreview.repaint();
        }
    }


    // --- UI Helper Methods ---
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.DARK_GRAY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(Color.DARK_GRAY);
        area.setBackground(Color.WHITE);
        area.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(220, 220, 220));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    // --- Test main ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Recipe");
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            RecipeAddingPanel panel = new RecipeAddingPanel();
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}
