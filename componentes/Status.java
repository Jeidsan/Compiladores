package componentes;

public enum Status {
	NAO_MODIFICADO("Não modificado"),
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
