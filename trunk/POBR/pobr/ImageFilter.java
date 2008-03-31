package pobr;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String fileName = f.getName();
        if(fileName.lastIndexOf(".") == -1){
        	return false;
        }
        String extension = fileName.substring(fileName.lastIndexOf("."));
        
        if (extension != null) {
            if (extension.equals(".jpg") ||
                extension.equals(".gif") ||
                extension.equals(".bmp") ||
                extension.equals(".png")) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}