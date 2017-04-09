package analisadores.lexico;

public class LexicalError extends AnalysisError
{
	private String simbolo = null;
	
    public LexicalError(String msg, int position, int line)
	{
        super(msg, position, line);
    }
    
    public LexicalError(String msg, int position, int line, String simbolo)
	{    	
        super(msg, position, line);
        this.simbolo = simbolo;
    }

    public LexicalError(String msg)
    {
        super(msg);
    }
    
    @Override    
    public String toString() 
    {
    	return "Erro na linha " + super.getLine() + " - " + (simbolo != null ? simbolo + " " : "") + super.getMessage() + ".";
    }
}
