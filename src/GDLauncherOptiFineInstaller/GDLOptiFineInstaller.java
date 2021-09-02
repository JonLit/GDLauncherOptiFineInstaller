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
            OPTIFINE_VERS = tfOrgOpt.getText();

            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("OptiFine Version: \t\t\t" + OPTIFINE_VERS);
            System.out.println("OptiFine Path: \t\t\t\t" + OPTIFINE_PATH);
            System.out.println("GDLauncher User Data Path: \t" + GDL_USER_PATH);
            System.out.println("Minecraft Instance Path: \t" + INSTANCE_PATH);

            if (!OPTIFINE_VERS.equals("") && !OPTIFINE_PATH.equals("") && !GDL_USER_PATH.equals("") && !INSTANCE_PATH.equals(""))
            {
                if (!isOptiFineInMinecraftLibraries())
                {
                    System.out.println("OptiFine not found in .minecraft/libraries/, installing...");
                    copyOptiFineToMinecraftLibraries();
                }

                if (!isOptiFineInGDLLibraries())
                {
                    System.out.println("OptiFine not found in GDLauncher datastore, installing...");
                    copyOptiFineToGDLLibraries();
                }
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
            File f = new File(System.getProperty("user.home") + "\\AppData\\.minecraft\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {
            File f = new File(System.getProperty("user.home") + "//Library//Application\\ Support//minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            File f = new File(System.getProperty("user.home") + "//.minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        return false;
    }

    public static boolean isOptiFineInGDLLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            File f = new File(GDL_USER_PATH + "\\datastore\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "\\" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {
            File f = new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            File f = new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar");
            return f.exists() && !f.isDirectory();
        }
        return false;
    }

    public static void copyOptiFineToMinecraftLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "\\AppData\\.minecraft\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar").toPath(), REPLACE_EXISTING);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "//Library//Application\\ Support//minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar").toPath(), REPLACE_EXISTING);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(System.getProperty("user.home") + "//.minecraft//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar").toPath(), REPLACE_EXISTING);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void copyOptiFineToGDLLibraries()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(GDL_USER_PATH + "\\datastore\\libraries\\optifine\\OptiFine\\" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar").toPath(), REPLACE_EXISTING);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (System.getProperty("os.name").contains("Mac OS X") || System.getProperty("os.name").contains("Linux"))
        {
            try {
                Files.copy(new File(OPTIFINE_PATH).toPath(), new File(GDL_USER_PATH + "//datastore//libraries//optifine//OptiFine//" + OPTIFINE_VERS + "//" + OPTIFINE_VERS + ".jar").toPath(), REPLACE_EXISTING);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
