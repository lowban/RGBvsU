package rgbvsu.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import rgbvsu.ResourceManager;

public class DialogueLoader {

    private char separator = ';';
    private String filename;

    public DialogueLoader(String filename) {
        this.filename = filename;
    }
    
    public void readDialogues(){
        try {

            //Load mapinfo
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = reader.readLine();
            int lineNumber = 0;
            while (line != null) {
                String command = getArgument(line, 0, separator);
                ResourceManager.addDialogue(Integer.parseInt(command), line.substring(2));
                
                while (line.length() == 0) {
                    line = reader.readLine();
                    lineNumber++;
                }
                line = reader.readLine();
                lineNumber++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getArgument(String line, int argumentIndex, char separator) {
        String temp = line;
        for (int i = 0; i < argumentIndex; i++) {
            temp = temp.replaceFirst(temp.substring(0, temp.indexOf(separator) + 1), "");
        }
        return temp.substring(0, temp.indexOf(separator));
    }
}
