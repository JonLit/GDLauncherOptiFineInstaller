package GDLOptiFineInstaller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class main {
    public static void main(String[] Args)
    {
        JFrame frame = new JFrame("OptiFine installer for GDLauncher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI(frame);
        frame.setSize(560, 200);
        frame.setVisible(true);
    }

    public static void createGUI(final JFrame frame)
    {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        final JLabel lbOptJar = new JLabel("Select Extracted OptiFine.jar");
        JButton btSelOptJar = new JButton("Select File");
        final JLabel lbSelOptJar = new JLabel("No File selected!");



        btSelOptJar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new JarFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);

                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    lbSelOptJar.setText(file.getName());
                }
                else
                {
                    lbSelOptJar.setText("No File selected!");
                }
            }
        });

        panel.add(lbOptJar);
        panel.add(btSelOptJar);
        panel.add(lbSelOptJar);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}
