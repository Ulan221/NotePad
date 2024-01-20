import javax.swing.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class FontDialog {
    private JComboBox<String> fontComboBox;
    private JComboBox<String> styleComboBox;
    private JComboBox<String> sizeComboBox;
    private JTextArea previewText;

    private final Viewer viewer;

    public FontDialog(Viewer viewer) {
        this.viewer = viewer;
    }

    public void showFontDialog() {
        Font currentFont = viewer.getFontTextArea();

        JLabel fontLabel = new JLabel("Font:");
        String[] fontOptions = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fontOptions);
        fontComboBox.setSelectedItem(currentFont.getFamily());
        fontComboBox.addActionListener(viewer);

        JLabel styleLabel = new JLabel("Font style:");
        String[] fontStyleOptions = { "PLAIN", "BOLD", "ITALIC", "Verdana" };
        styleComboBox = new JComboBox<>(fontStyleOptions);
        String currentStyle = (currentFont.isBold() ? "BOLD" : "") + (currentFont.isItalic() ? "ITALIC" : "");
        styleComboBox.setSelectedItem(currentStyle);
        styleComboBox.addActionListener(viewer);

        JPanel fontJPanel = new JPanel();
        JLabel sizeLabel = new JLabel("Select font size:");
        String[] fontSizeOptions = { "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72" };
        sizeComboBox = new JComboBox<>(fontSizeOptions);
        sizeComboBox.setSelectedItem(Integer.toString(currentFont.getSize()));
        sizeComboBox.addActionListener(viewer);

        previewText = new JTextArea("AaBbYyZz");
        Font previewFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize());
        previewText.setFont(previewFont);

        fontJPanel.setLayout(new GridLayout(3, 2, 20, 20));
        fontJPanel.setPreferredSize(new Dimension(350, 100));
        fontJPanel.add(fontLabel);
        fontJPanel.add(sizeLabel);
        fontJPanel.add(styleLabel);
        fontJPanel.add(fontComboBox);
        fontJPanel.add(styleComboBox);
        fontJPanel.add(sizeComboBox);
        fontJPanel.add(new JLabel("Sample"));
        fontJPanel.add(previewText);

        int result = JOptionPane.showConfirmDialog(viewer.getFrame(), fontJPanel, "Font Chooser",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int size = Integer.parseInt((String) sizeComboBox.getSelectedItem());

            String selectedFont = getFont(fontComboBox.getSelectedItem().toString());
            int selectedStyle = getFontStyle((String) styleComboBox.getSelectedItem());
            Font selectedFontFeatures = new Font(selectedFont, selectedStyle, size);
            viewer.setFontTextArea(selectedFontFeatures);
        }

    }

    public int getFontStyle(String selectedStyle) {
        int style = Font.PLAIN;
        if (selectedStyle.equals("BOLD")) {
            style = Font.BOLD;
        } else if (selectedStyle.equals("ITALIC")) {
            style = Font.ITALIC;
        } else if (selectedStyle.equals("Verdana")) {
            style = Font.BOLD | Font.ITALIC;
        }
        return style;
    }

    public String getFont(String selectedFont) {
        String font = "Arial";
        if (selectedFont.equals("Serif")) {
            font = "Serif";
        } else if (selectedFont.equals("Sans Serif")) {
            font = "Sans Serif";
        } else if (selectedFont.equals("Dialog")) {
            font = "Dialog";
        } else if (selectedFont.equals("Dialog Input")) {
            font = "Dialog Input";
        } else if (selectedFont.equals("Monospaced")) {
            font = "Monospaced";
        }
        return font;
    }

    public String getFontComboBox() {
        return fontComboBox.getSelectedItem().toString();
    }

    public String getStyleComboBox() {
        return styleComboBox.getSelectedItem().toString();
    }

    public String getSizeComboBox() {
        return sizeComboBox.getSelectedItem().toString();
    }

    public void setPreviewText(Font font) {
        previewText.setFont(font);
    }

}
