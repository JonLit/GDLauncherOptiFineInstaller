package GDLOptiFineInstaller;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

public class JarFilter extends FileFilter
{
    public final static String JAR = "jar";

    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null)
        {
            if (extension.equals(JAR))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription()
    {
        return ".jar Files";
    }

    String getExtension(File f)
    {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1)
        {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
