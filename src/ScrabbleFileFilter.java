import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * A FileFilter for loading in a previous game of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleFileFilter extends FileFilter {

    /**
     * @param file The File to test.
     * @return True, if the specified file was accepted. False, if not.
     * @author Guy Morgenshtern 101151430
     */
    @Override
    public boolean accept(File file) {
        String filePath = file.getPath();
        int indexOfSuffix = filePath.indexOf(".");
        if (indexOfSuffix < 5) {
            return false;
        } else {
            return filePath.substring(indexOfSuffix - 4, indexOfSuffix).equals("sxyz");
        }
    }

    /**
     * @return A String representing the proper suffix of a file to load in a game of Scrabble.
     * @author Guy Morgenshtern 101151430
     */
    @Override
    public String getDescription() {
        return "Scrabble.xyz (*_sxyz.ser)";
    }
}
