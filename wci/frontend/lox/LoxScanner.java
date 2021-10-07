package wci.frontend.lox;

import wci.frontend.*;
import static wci.frontend.Source.EOF;


public class LoxScanner extends Scanner{

    /**
     * Constructor
     * @param source the source to use with this scanner
     */
    public LoxScanner(Source source) { super(source); }

    /**
     * Extract and return the next token.
     * @return the next token.
     * @throws Exception if an error occurred.
     */

    protected Token extractToken()
        throws Exception
    {
        Token token;
        char currentChar = currentChar();

        if (currentChar == EOF) {
            token = new EofToken(source);
        }
        else {
            token = new Token(source);
        }
        return token;
    }


}
