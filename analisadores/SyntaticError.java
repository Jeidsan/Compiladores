package analisadores;

public class SyntaticError extends AnalysisError
{
	private String simbolo = null;
	
    /*public SyntaticError(String msg, int position, int line)
	{
        super(msg, position, line);
    }*/

    public SyntaticError(String msg, int position, int line, String simbolo)
	{
        super(msg, position, line);
        this.simbolo = simbolo;
    }
    
    public SyntaticError(String msg)
    {
        super(msg);
    }
    
    @Override    
    public String toString() 
    {
    	return "Erro na linha " + super.getLine() + " coluna " + super.getPosition() + " - " + (simbolo != null ? "encontrado " + simbolo + " " : "") + super.getMessage() + ".";
    }    
}
