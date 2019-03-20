package rgbvsu.gui;

import java.awt.*;
import java.util.ArrayList;
import rgbvsu.ResourceManager;

/**
 *
 * @author Robin Horneman
 */
public class Dialogue {
    
    private static int maxLines = 6;
    private int fieldSize = 40, line = 0, letter = 0;
    private boolean showDialogue = false;
    private boolean showedAll = false;
    private boolean filledBuffert = false;
    private ArrayList<String> textBlocks;
    private String textDialogue, showBuffert = "";
    private String frameBuffert = ""; 
    private Font dialogueFont;
    private Color dialogueBackgroundColor, dialogueFontColor;
    private FontMetrics dialogueFontMetrics;
        
    /*Constructors*****************************/
    public Dialogue(){
        
        textBlocks = new ArrayList<>();
        dialogueFont = ResourceManager.getFont("dialogueFont");
        dialogueBackgroundColor = new Color(0.3f,0.3f,0.3f,0.8f);
	dialogueFontColor = Color.WHITE;
    }
    
    public Dialogue(String textDialogue){
        this();
        
        this.textDialogue = textDialogue;
        textBlocks = blockSentences(textDialogue, fieldSize);        
    }
    
    public Dialogue(String textDialogue, int fieldSize){
        this(textDialogue);
        this.fieldSize = fieldSize;
        buildFrameBuffert();
    }
    
    /*Getters and setters*********************/
    public void clearAll(){
        line=0; letter=0;showDialogue=false;
        textBlocks.clear();
        textDialogue = ""; showBuffert="";
        frameBuffert = "";
    }
    
    public String getBuffert(){
        return showBuffert;
    }
    
    public String getFrameBuffert(){
        return frameBuffert;
    }
    
    public boolean getShownAll(){
        return showedAll;
    }
    
    public Color getBackgroundColor(){
        return dialogueBackgroundColor;
    }
    
    public Color getFontColor(){
        return dialogueFontColor;
    }
    
    public Font getFont(){
        return dialogueFont;
    }
    
    public boolean getShowStatus(){
        return showDialogue;
    }
    
    public boolean getIfFilledBuffert(){
        return filledBuffert;
    }
    
    public ArrayList<String> getTextBlocks(){
        return textBlocks;
    }
    
    public void showDialogue(boolean bool){
        showDialogue = bool; 
    }
    
    public void showDialogue(String text){        
        showDialogue = true;
        textBlocks = blockSentences(text, fieldSize);
    }
    
    public void showDialogue(){
        showDialogue = true; 
    }
    
    public void setDialogue(String dialogue){
        clearAll();
        textDialogue = dialogue;
        textBlocks = blockSentences(textDialogue, fieldSize);
        buildFrameBuffert();
        showDialogue();
    }
    
    public void setFieldSize(int fieldSize){
        this.fieldSize = fieldSize;
    }
    
    public void resetShowBuffert(){
        letter = 0;
        showBuffert = "";
    }
    
    /*Helper and debug methods****************/
    private ArrayList<String> blockSentences(String sentence, int cutLength){
        ArrayList<String> blocks = new ArrayList<String>();
        twoStrings tempCut;
        boolean finished = false;
        for(;;){
            tempCut = cutSentence(sentence, cutLength);
            if("".equals(tempCut.getString(1))){
                blocks.add(tempCut.getString(2));
                finished = true;
            }
            else if("".equals(tempCut.getString(2))){
                blocks.add(tempCut.getString(1));
                finished = true;
            }
            else{
                blocks.add(tempCut.getString(1));
                sentence = tempCut.getString(2);
            }
            if(finished){
                break;
            }
        }
        return blocks;
    }
    
    private twoStrings cutSentence(String sentence, int cutLength){
        
        if(cutLength<0){
            return new twoStrings("", sentence);
        }
        if(sentence == null){
            System.err.println("sentence is null!");
        }
        if(cutLength>=sentence.length()){
            return new twoStrings(sentence, "");
        }
        else{
            if(sentence.charAt(cutLength)==' '){
                String tempString = sentence.substring(0, cutLength);
                for(int i=0; i<tempString.length(); i++){
                    if(tempString.charAt(i)=='>'){
                        if(i-1>0){
                            return new twoStrings(tempString.substring(0,i-1), tempString.substring(i+1,tempString.length())+sentence.substring(cutLength, sentence.length()));
                        }
                        else{
                            return new twoStrings(" ", sentence.substring(1, sentence.length()));
                        }
                    }
                }
                return new twoStrings(sentence.substring(0, cutLength),sentence.substring(cutLength+1,sentence.length()));
            }
        }
        return cutSentence(sentence, cutLength-1);
    }
    
    public void showBlocks(){
        System.out.println("Showing blocks");
        for(int i=0; i<textBlocks.size();i++){
            System.out.println(textBlocks.get(i));
        }
    }
    
    public void fillBuffert(){
        showBuffert = frameBuffert;
        filledBuffert = true;        
    }
    
    public void buildFrameBuffert(){
        filledBuffert=false;
        if(textBlocks.size()<=maxLines){
            showedAll = true;
            if(!textBlocks.isEmpty()){
                frameBuffert = textBlocks.get(0);
                for(int i=1;i<textBlocks.size();i++){
                    frameBuffert+="\n";
                    frameBuffert+=textBlocks.get(i);
                }
            }
        }
        else{
            frameBuffert = textBlocks.get(0);
            for(int i=1;i<maxLines;i++){
                frameBuffert+="\n";
                frameBuffert+=textBlocks.get(i);
            }
            for(int i=0;i<maxLines;i++){
                textBlocks.remove(0);
            }
        }
    }
    
    public void buildBuffert(){
        if(!filledBuffert){
            if(letter<frameBuffert.length()){
                showBuffert+=frameBuffert.charAt(letter);
                ++letter;
            }
            if(showBuffert.equals(frameBuffert)){
                filledBuffert = true;
            }
        }
    }
    
    /*Helper class***************************/
    private class twoStrings{
        private String string1;
        private String string2;
        
        public twoStrings(String string1, String string2){
            this.string1 = string1;
            this.string2 = string2;
        }
        
        public String getString(int whichString){
            if(whichString == 1){
                return string1;
            }
            else return string2;
        }
    }
    
    /*RENDER*********************************/
    
            private String fixLines(Graphics2D g, String s)
	{
		String temp = "";
		int numLines = 0;
		dialogueFontMetrics = g.getFontMetrics(getFont());
		int lastSpaceIndex, spaceIndex=-1;
		
		for(int i = 0;i<s.length();i++)
		{
			temp += s.charAt(i);
			if(dialogueFontMetrics.stringWidth(temp) > (numLines+1)*(GUI.getWidth()-60))
			{		
				int newSpaceIndex = s.indexOf(' ', i); 
				if(spaceIndex != newSpaceIndex )
				{
					lastSpaceIndex = spaceIndex;
					spaceIndex = newSpaceIndex;
				}
				
				temp += "\n";numLines++;
				if(i<s.length()-1)
					if(s.charAt(i+1)==' ')
						i++;
			}
		}
		
		
		return temp;
	}
        
        private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}
    
    
    public void render(int x, int y, Graphics2D g){
        g.setColor(getBackgroundColor());
            g.fillRect(0, GUI.getScreenHeight()-200, GUI.getWidth(),200);
            g.setColor(getFontColor());
            g.setFont(getFont());


            buildBuffert();
            drawString(g,getBuffert(), 0, GUI.getScreenHeight()-200);
    }
}
