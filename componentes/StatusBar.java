package componentes;

import javax.swing.JLabel;

public class StatusBar extends JLabel
{	
	private static final long serialVersionUID = 5674131234234605041L;

	public StatusBar()
	{
		super();		
	}
	
	public void setStatus(String pasta, String nomeArquivo, Status status)
	{
		if(pasta == null || nomeArquivo == null)
		{
			setText("Status: " + status.getDescricao());
		}
		else
		{			
			setText("Pasta: " + pasta + "     |      Arquivo: " + nomeArquivo + "     |     Status: " + status.getDescricao());
		}
	}
	
	public void limparStatus()
	{
		setText("");
	}
}
