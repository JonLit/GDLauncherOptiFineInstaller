package GDLauncherOptiFineInstaller;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GDLOptiFineInstaller {

    public static String OPTIFINE_PATH = "";
    public static String GDL_USER_PATH = "";
    public static String INSTANCE_PATH = "";

    public static void main(String[] Args)
    {
        JFrame frame = new JFrame("OptiFine installer for GDLauncher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI(frame);
        frame.setSize(650, 180);
        frame.setVisible(true);
    }

    public static void createGUI(final JFrame frame)
    {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        final JLabel lbOrgOpt = new JLabel("Version of Optifine");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(lbOrgOpt, c);

        JTextField tfOrgOpt = new JTextField("e.g. " + '"' + "1.17.1_HD_U_G9" + '"');
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(tfOrgOpt, c);

        final JLabel lbOptJar = new JLabel("Select Extracted OptiFine.jar");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(lbOptJar, c);

        JButton btSelOptJar = new JButton("Select File");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(btSelOptJar, c);

        final JLabel lbSelOptJar = new JLabel("No File selected!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(lbSelOptJar, c);


        final JLabel lbGDLUserDataPath = new JLabel("Select GDLauncher User Data Path");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(lbGDLUserDataPath, c);

        JButton btGDLUserDataPath = new JButton("Select Directory");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(btGDLUserDataPath, c);

        final JLabel lbSelGDLUserDataPath = new JLabel("No Directory selected!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        panel.add(lbSelGDLUserDataPath, c);


        final JLabel lbInstPath = new JLabel("Select Path for your Instance");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(lbInstPath, c);

        JButton btInstPath = new JButton("Select Directory");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(btInstPath, c);

        final JLabel lbSelInstPath = new JLabel("No Directory selected!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        panel.add(lbSelInstPath, c);


        JButton btconfirm = new JButton("Install!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(btconfirm, c);

        btSelOptJar.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new JarFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileHidingEnabled(false);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                lbSelOptJar.setText(file.getName());
                GDLOptiFineInstaller.OPTIFINE_PATH = file.getAbsolutePath();
                System.out.println("OptiFine.jar PATH " + GDLOptiFineInstaller.OPTIFINE_PATH);
            }
            else
            {
                lbSelOptJar.setText("No File selected!");
            }
        });

        btGDLUserDataPath.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setFileHidingEnabled(false);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                lbSelGDLUserDataPath.setText(file.getName());
                GDLOptiFineInstaller.GDL_USER_PATH = file.getAbsolutePath();
                System.out.println("GDL User Data Path " + GDLOptiFineInstaller.GDL_USER_PATH);
            }
        });

        btInstPath.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setFileHidingEnabled(false);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                lbSelInstPath.setText(file.getName());
                GDLOptiFineInstaller.INSTANCE_PATH = file.getAbsolutePath();
                System.out.println("Instance Path " + GDLOptiFineInstaller.INSTANCE_PATH);
            }
        });

        btconfirm.addActionListener(actionEvent -> {
            if (!isOptiFineInLibraries())
            {
                copyOptiFineToLibraries();
            }
        });

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static boolean isOptiFineInLibraries()
    {
        String pS = System.getProperty("path.seperator");
        String OptiFineVer;
        File optifine = new File(OPTIFINE_PATH);

        OptiFineVer = optifine.getName().substring(0, optifine.getName().indexOf('_'));

        if (System.getProperty("os.name").contains("Windows"))
        {
            File f = new File(System.getProperty("user.home") + "\\AppData\\.minecraft\\libraries\\optifine\\OptiFine\\" + OptiFineVer + "");
            return true;
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {

        }
        else if (System.getProperty("os.name").contains("Linux"))
        {

        }
        return false;
    }

    public static void copyOptiFineToLibraries()
    {

    }
}
