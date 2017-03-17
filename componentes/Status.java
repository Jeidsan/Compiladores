package componentes;

public enum Status {
	NAO_MODIFICADO("N�o modificado"),
	MODIFICADO("Modificado");
	
	
	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return this.descricao;
	}
}
