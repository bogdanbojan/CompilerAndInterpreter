package wci.frontend.lox;

import wci.frontend.*;
import wci.message.Message;

import static wci.frontend.lox.LoxToken.*;
import static wci.frontend.lox.LoxTokenType.*;
import static wci.frontend.lox.LoxErrorCode.*;
import static wci.message.MessageType.*;

public class LoxParserTD extends Parser
{

    protected static LoxErrorHandler errorHandler = new LoxErrorHandler();

    /**
     * Constructor.
     * @param scanner the scanner to be used with this parser.
     */
    public LoxParserTD(Scanner scanner) {super(scanner);}


    /**
     * Parse a Lox source program and generate the symbol table
     * and the intermediate code.
     */
    public void parse()
        throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();

        try {
            // Loop over each token until the end of file.
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                if (tokenType != ERROR) {

                    // Format each token.
                    sendMessage(new Message(TOKEN,
                            new Object[] {token.getLineNumber(),
                                    token.getPosition(),
                                    tokenType,
                                    token.getText(),
                                    token.getValue()}));
                }
                else {
                    errorHandler.flag(token, (LoxErrorCode) token.getValue(),
                            this);
                }



            } // hmm.

            float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
            sendMessage(new Message(PARSER_SUMMARY,
                    new Number[]{token.getLineNumber(),
                            getErrorCount(),
                            elapsedTime}));




        }
        catch (java.io.IOException ex) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }
    public int getErrorCount() { return errorHandler.getErrorCount(); }
}
