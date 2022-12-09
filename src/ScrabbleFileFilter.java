import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ScrabbleFileFilter extends FileFilter {

    public boolean accept(File file) {
        String filePath = file.getPath();
        int indexOfSuffix = filePath.indexOf(".");
        if (indexOfSuffix < 5) {
            return false;
        } else {
            return filePath.substring(indexOfSuffix - 4, indexOfSuffix).equals("sxyz");
        }
    }

    @Override
    public String getDescription() {
        return "Scrabble.xyz (*_sxyz.ser)";
    }
}
