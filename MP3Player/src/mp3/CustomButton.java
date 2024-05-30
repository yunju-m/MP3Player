package mp3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text);
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Don't paint border
    }
}
