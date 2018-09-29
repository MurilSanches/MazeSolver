package coordenada;

/**
A classe Coordenada representa uma coordenada x, y
Há metodos para definer a coordenada e também para consultar-lá

@author Amabile Pietrobon Ferreira e Murilo de Paula Sanches.
@RA 18198 e 18187
@since 2018.
*/

public class Coordenada implements Cloneable
{
	/**
	   Coordenada x.
    */
    protected int x;

    protected int y;
    /**
		Coordenada y.
    */


	/**
	Constroi uma nova instância da classe Coordenada.
	Para tanto, deve ser fornecido dois inteiros que serám utilizados como coordenada.
	@param linha  será a coordenada x.
		   coluna será a coordenada y.
	*/
    public Coordenada(int linha, int coluna)
    {
        this.x = linha;
        this.y = coluna;
    }

    /**
	*/
    public Coordenada()
    {
		this.x = 0;
		this.y = 0;
	}

	/**
		Transforma em String a coordenada (x, y).
		@return a coordenada em forma de String.
	*/
    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

	/**
	    Obtem   a coordenada y.
	    @return a coordenada y.
    */
    public int getY()
    {
        return this.y;
    }

    /**
	    Obtem   a coordenada x.
        @return a coordenada x.
    */
    public int getX()
    {
        return this.x;
    }

	/**
		Determina uma nova coordenada x, y substituindo a coordenada anterior.

		@param lin será a coordenada x.
			   col será a coordenada y.
		@throws Exception se os objetos passados como parâmetro forem menores que zero.
	*/
    public void setCoordenada(int lin, int col) throws Exception
	{
		if (lin < 0 || col < 0)
			throw new Exception ("Dados inválidos");

		this.y = col;
		this.x = lin;
    }

	/**
		Determina uma nova coordenada x, y substituindo a coordenada anterior.

		@param coord da classe Coordenada.

	*/
    public void setCoordenada(Coordenada coord) throws Exception
    {
		this.y = coord.y;
		this.x = coord.x;
	}

	/**
		Compara todos os atributos e verifica-se se o objeto passado como parâmetro é igual ao objeto atual.
		@param objeto a ser comparado.
		@return se o objeto recebido como parâmetro é ou não igual ao objeto atual.
	*/
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Coordenada coord = new Coordenada();

        if (coord.x != this.x)
            return false;

        if (coord.y != this.y)
            return false;

        return true;
    }

	 /**
		 Calcula o codigo hash da coordenada guardada.
		 @return o codigo hash da coordenada.
    */
    public int hashCode()
    {
        int ret = 11;

        ret = ret * 2 + new Integer(this.x).hashCode();
        ret = ret * 2 + new Integer(this.y).hashCode();

        return ret;
    }

	/**
		Constroi uma Coordenada a partir de outra que foi recebida como modelo.
		@param modelo da coordenada fornecerá seus dados para a coordenada atual.
		@throws Exception se o modelo recebido como parâmetro for nulo.
	*/
    public Coordenada(Coordenada modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("Modelo Ausente!");

        this.x = modelo.x;
        this.y = modelo.y;
    }

	/**
		Cria um clone da coordenada atual.
		@return um objeto igual a classe que chamou o metodo clone()
	*/
    public Object clone()
    {
        Coordenada ret = null;

        try
        {
            ret = new Coordenada(this);
        }

        catch(Exception erro) { }

        return ret;
    }
}