package wci.frontend;

import wci.intermediate.ICode;
import wci.intermediate.SymTab;
import wci.message.MessageProducer;
import wci.message.*;


public abstract class Parser implements MessageProducer
{
    protected ICode iCode;
    protected Scanner scanner;

    protected static SymTab symTab;
    protected static MessageHandler messageHandler;

    static { // here static {} instead of omitting `{}` ???
        symTab = null;
        messageHandler = new MessageHandler();
    }

    protected Parser(Scanner scanner){
        this.scanner = scanner;
        this.iCode = null;
    }


    public Scanner getScanner(){return scanner;}

    public ICode getICode(){return iCode;}

    public SymTab getSymTab(){return symTab;}

    public MessageHandler getMessageHandler(){return messageHandler;}

    public abstract int getErrorCount();

    public abstract void parse() throws Exception;

    public Token currentToken(){
        return scanner.currentToken();
    }

    public Token nextToken()
            throws Exception
    {
        return scanner.nextToken();
    }

    public void addMessageListener(MessageListener listener){
        messageHandler.addListener(listener);
    }

    public void removeMessageListener(MessageListener listener){
        messageHandler.removeListener(listener);
    }

    public void sendMessage(Message message){
        messageHandler.sendMessage(message);
    }


}
