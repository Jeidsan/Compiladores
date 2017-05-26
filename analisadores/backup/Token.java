package analisadores;

public class Token
{
    private int id;
    private String lexeme;
    private int position;
    private int line;

    public Token(int id, String lexeme, int position, int line)
    {
        this.id = id;
        this.lexeme = lexeme;
        this.position = position;
        this.line = line;
    }

    public final int getId()
    {
        return id;
    }

    public final String getLexeme()
    {
        return lexeme;
    }

    public final int getPosition()
    {
        return position;
    }
    
    public final int getLine()
    {
    	return line;
    }
    
    public final String getClasse()
    {
    	String classe = "";
    	
    	switch(getId())
    	{
    		case 2: 
    			classe = "Identificador\t";
    			break;
    		case 3:
    			classe = "Constante inteira";
    			break;
    		case 4: 
    			classe = "Constante real\t";
    			break;
    		case 5: 
    			classe = "Constante caracter";
    			break;
    		case 6:
    		case 7:
    		case 8:
    		case 9:
    		case 10:
    		case 11:
    		case 12:
    		case 13:
    		case 14:
    		case 15:
    		case 16:
    		case 17:
    		case 18:
    		case 19:
    		case 20:
    		case 21:
    		case 22:
    		case 23:
    		case 24:
    		case 25:
    		case 26:
    		case 27:
    		case 28:
    		case 29:
    		case 30:
    		case 31:
    		case 32:
    			classe = "Palavra reservada";
    			break;
    		case 33:
    		case 34:
    		case 35:
    		case 36:
    		case 37:
    		case 38:
    		case 39:
    		case 40:
    		case 41:
    		case 42:
    		case 43:
    		case 44:
    		case 45:
    		case 46:
    		case 47:
    		case 48:
    		case 49:
    		case 50:
    		case 51: 
    			classe = "Símbolo especial";
    			break;
    		default: 
    			classe = "Símbolo não identificado";
    			break;    			
    	}
    	
    	return classe;
    }

    public String toString()
    {
    	return id + ": " + lexeme + " ( " + line + " , " + position + " )";
        //return id+" ( "+lexeme+" ) @ "+position;
    };
}
