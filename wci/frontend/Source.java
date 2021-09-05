package wci.frontend;


import java.io.BufferedReader;
import java.io.IOException;

import wci.message.*;
import static wci.message.MessageType.SOURCE_LINE; // make sure to use static on these enums

public class Source implements MessageProducer{
    public static final char EOL = '\n';
    public static final char EOF = (char) 0;

    private BufferedReader reader;
    private String line;
    private int lineNum;
    private int currentPos;

    private MessageHandler messageHandler;

    public Source(BufferedReader reader) throws IOException {
        this.lineNum = 0;
        this.currentPos = -2;  // set to -2 to read the first source line
        this.reader = reader;
        this.messageHandler = new MessageHandler();
    }

    public char currentChar() throws Exception {
        if (currentPos == -2) { // why -2 - it says that it is set to -2 to read the first source line
            readLine();
            return nextChar();
        } else if (line == null) { //at the end of file
            return EOF;
        } else if ((currentPos == -1) || (currentPos == line.length())) { // at the end of line
            return EOL;
        } else if (currentPos > line.length()) {
            readLine();
            return nextChar();
        } else {
            return line.charAt(currentPos);
        }
    }

    public int getLineNum(){return lineNum;}

    public int getPosition(){return currentPos;}

    public char nextChar() throws Exception {
        ++currentPos;
        return currentChar();
    }

    public char peekChar() throws Exception {
        currentChar();
        if (line == null) {
            return EOL;
        }

        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL; // if else statement - (expr)?exprIsTrue:exprIsFalse
    }


    public void close() throws Exception{
        if (reader != null){
            try {
                reader.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
                throw ex;
            }
        }

    }

    private void readLine() throws IOException{
        line = reader.readLine();
        currentPos = 0;

        if (line != null) {
            ++lineNum;
        }

        if (line != null) {
            sendMessage(new Message(SOURCE_LINE,
                                    new Object[] {lineNum,line}));
        }
    }


    public void sendMessage(Message message){messageHandler.sendMessage(message);}
    public void addMessageListener(MessageListener listener) {messageHandler.addListener(listener);};
    public void removeMessageListener(MessageListener listener){messageHandler.removeListener(listener);};


}

