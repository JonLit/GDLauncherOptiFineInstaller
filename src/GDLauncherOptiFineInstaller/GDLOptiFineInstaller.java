package GDLauncherOptiFineInstaller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GDLOptiFineInstaller {

    public static String OPTIFINE_VERS = "";
    public static String OPTIFINE_NAME = "";
    public static String OPTIFINE_PATH = "";
    public static String GDL_USER_PATH = "";

    public static void main(String[] Args)
    {
        JFrame frame = new JFrame("OptiFine installer for GDLauncher v.1.1");
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


        JButton btconfirm = new JButton("Install!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 3;
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

        btconfirm.addActionListener(actionEvent -> {
            OPTIFINE_VERS = tfOrgOpt.getText();
            OPTIFINE_NAME = "OptiFine-" + OPTIFINE_VERS;

            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("OptiFine Version: \t\t\t" + OPTIFINE_VERS);
            System.out.println("OptiFine Path: \t\t\t\t" + OPTIFINE_PATH);
            System.out.println("GDLauncher User Data Path: \t" + GDL_USER_PATH);
            System.out.println("OptiFine File Name: \t\t\t" + OPTIFINE_NAME);

            if (!OPTIFINE_VERS.equals("") && !OPTIFINE_PATH.equals("") && !GDL_USER_PATH.equals("") && isOptiFineInMinecraftLibraries() && isOptiFineInGDLLibraries())
            {
                JOptionPane.showMessageDialog(frame, "selected OptiFine version is Already installed!");
                System.out.println("OptiFine already installed!");
            }
            else if (!OPTIFINE_VERS.equals("") && !OPTIFINE_PATH.equals("") && !GDL_USER_PATH.equals(""))
            {
                if (!isOptiFineInMinecraftLibraries())
                {
                    System.out.println("OptiFine not found in .minecraft/libraries/, installing...");
                    if (!copyOptiFineToMinecraftLibraries())
                    {
                        JOptionPane.showMessageDialog(panel, "Error while copying OptiFine to Minecraft libraries", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (!isOptiFineInGDLLibraries())
                {
                    System.out.println("OptiFine not found in GDLauncher datastore, installing...");
                    if (!copyOptiFineToGDLLibraries())
                    {
                        JOptionPane.showMessageDialog(panel, "Error while copying OptiFine to GDLauncher datastore", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                Timer timer = new Timer();
                TimerTask task = new TimerTask(){
                    @Override
                    public void run(){
                        if (isOptiFineInMinecraftLibraries() && isOptiFineInGDLLibraries()) {
                            JOptionPane.showMessageDialog(frame, "OptiFine was successfully installed!");
                            System.out.println("OptiFine successfully installed!");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "OptiFine was NOT installed due to an Error", "OptiFine was NOT installed!", JOptionPane.ERROR_MESSAGE);
                            System.out.println("OptiFine NOT installed!");
                        }
                    }
                };

                timer.schedule(task, 100);
            }
            else
            {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        btconfirm.setText("Install!");
                    }
                };
                btconfirm.setText("Please select / fill everything!");
                timer.schedule(task, 3000);
            }
        });

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static boolean isOptiFineInMinecraftLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            try {
                File f = new File(System.getProperty("user.home") + "\\AppData\\.minecraft\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar");
                return f.exists() && !f.isDirectory();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("OptiFine detected in .minecraft/libraries");
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {
            try {
                File f = new File(System.getProperty("user.home") + "//Library//Application\\ Support//minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar");
                return f.exists() && !f.isDirectory();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("OptiFine detected in .minecraft/libraries");
            }
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            try {
                File f = new File(System.getProperty("user.home") + "//.minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar");
                return f.exists() && !f.isDirectory();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("OptiFine detected in .minecraft/libraries");
            }
        }
        return false;
    }

    public static boolean isOptiFineInGDLLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            try {
                File f = new File(GDL_USER_PATH + "\\datastore\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "\\" + OPTIFINE_NAME + ".jar");
                return f.exists() && !f.isDirectory();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("OptiFine detected in GDLauncher datastore");
                return false;
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X") || System.getProperty("os.name").contains("Linux"))
        {
            try {
                File f = new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar");
                return f.exists() && !f.isDirectory();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("OptiFine detected in GDLauncher datastore");
            }
        }
        return false;
    }

    public static boolean copyOptiFineToMinecraftLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "\\AppData\\.minecraft\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").toPath(), REPLACE_EXISTING);
                System.out.println("OptiFine successfully copied to .minecraft/libraries");
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "//Library//Application\\ Support//minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").toPath(), REPLACE_EXISTING);
                System.out.println("OptiFine successfully copied to .minecraft/libraries");
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            try {
                new File(System.getProperty("user.home") + "//.minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").mkdirs();
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "//.minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").toPath(), REPLACE_EXISTING);
                System.out.println("OptiFine successfully copied to .minecraft/libraries");
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean copyOptiFineToGDLLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            new File(OPTIFINE_PATH).mkdirs();
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(GDL_USER_PATH + "\\datastore\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").toPath(), REPLACE_EXISTING);
                System.out.println("OptiFine successfully copied to GDLauncher datastore");
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X") || System.getProperty("os.name").contains("Linux"))
        {
            try {
                new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS).mkdirs();
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_NAME + ".jar").toPath(), REPLACE_EXISTING);
                System.out.println("OptiFine successfully copied to GDLauncher datastore");
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
