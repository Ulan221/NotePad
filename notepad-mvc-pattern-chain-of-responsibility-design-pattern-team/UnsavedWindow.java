import javax.swing.*;
public class UnsavedWindow {
    private JFrame frame;
    private boolean unsavedChanges;

    public UnsavedWindow(JFrame frame) {
        this.frame = frame;
        this.unsavedChanges = false;
    }

    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        this.unsavedChanges = unsavedChanges;
    }

    public JFrame getFrame() {
        return frame;
    }
}