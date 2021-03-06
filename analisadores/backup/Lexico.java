package analisadores;

public class Lexico implements Constants
{
    private int position;
    private int line;
    private String[] lines;
    private String input;    

    public Lexico()
    {
    	init("");
    }
    
    public Lexico(String input)
    {
    	init(input);        
    }

    private void init(String input)
    {
    	this.lines = input.split("\n");
    	setPosition(0);
        setLine(1);
        setInput(lines[0]);
    }
    
    public void setInput(String input)
    {    	
    	this.input = input;        
    }

    public void setPosition(int pos)
    {
        position = pos;
    }

    public void setLine(int lin)
    {
    	line = lin;
    }
    
    public Token nextToken() throws LexicalError
    {
        if (!hasContent() )
            return null;

        int start = position;

        int state = 0;
        int lastState = 0;
        int endState = -1;
        int end = -1;

        while (hasInput())
        {
            lastState = state;                                   
            state = nextState(nextChar(), state);

            if (state < 0)
                break;

            else
            {
                if (tokenForState(state) >= 0)
                {
                    endState = state;
                    end = position;
                }
            }
        }
        if (endState < 0 || (endState != state && tokenForState(lastState) == -2))
        {
        	if (lastState == 0)
        	{
        		throw new LexicalError(SCANNER_ERROR[lastState], start, line, input.substring(start, start + 1));
        	}
        	else
        	{
        		throw new LexicalError(SCANNER_ERROR[lastState], start, line);
        	}
        }

        position = end;

        int token = tokenForState(endState);

        if (token == 0)
            return nextToken();
        else
        {
            String lexeme = input.substring(start, end);
            token = lookupToken(token, lexeme);            
            return new Token(token, lexeme, start, line);
        }
    }

    private int nextState(char c, int state)
    {
        int start = SCANNER_TABLE_INDEXES[state];
        int end   = SCANNER_TABLE_INDEXES[state+1]-1;

        while (start <= end)
        {
            int half = (start+end)/2;

            if (SCANNER_TABLE[half][0] == c)
                return SCANNER_TABLE[half][1];
            else if (SCANNER_TABLE[half][0] < c)
                start = half+1;
            else  //(SCANNER_TABLE[half][0] > c)
                end = half-1;
        }

        return -1;
    }

    private int tokenForState(int state)
    {
        if (state < 0 || state >= TOKEN_STATE.length)
            return -1;

        return TOKEN_STATE[state];
    }

    public int lookupToken(int base, String key)
    {
        int start = SPECIAL_CASES_INDEXES[base];
        int end   = SPECIAL_CASES_INDEXES[base+1]-1;

        key = key.toUpperCase();

        while (start <= end)
        {
            int half = (start+end)/2;
            int comp = SPECIAL_CASES_KEYS[half].compareTo(key);

            if (comp == 0)
                return SPECIAL_CASES_VALUES[half];
            else if (comp < 0)
                start = half+1;
            else  //(comp > 0)
                end = half-1;
        }

        return base;
    }

    private boolean hasContent()
    {
    	if (hasInput())
    	{
    		return true;
    	}
    	
    	while (line < lines.length)
    	{
    		position = 0;
    		setInput(lines[line]);
    		line++;
    		if (hasInput())
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private boolean hasInput()
    {		
        return position < input.length() ;
    }

    private char nextChar()
    {
        if (hasInput())
            return input.charAt(position++);
        else
            return (char) -1;
    }
}
