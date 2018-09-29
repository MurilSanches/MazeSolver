package pilha;

import java.lang.reflect.*;

/**
A classe Pilha<X> representa uma pilha de objetos gen�ricos.

Inst�ncias desta classe permitem a reliza��o das opera��es b�sicas de uma pilha.
Nela encontramos, n�o s�, m�todos para empilhar e desempilhar, mas tamb�m m�todos que permitem consult�-la.

@author Amabile Pietrobon Ferreira e Murilo Sanches de Paula.
@RA 18198 e 18187.
@since 2018.
*/
public class Pilha<X> implements Cloneable
{
    private Object[] vetor;
    private int qtd = 0;

	/**
	Constroi uma nova inst�ncia da classe Pilha<X>.
	Para tanto, deve ser fornecido um inteiro que ser� utilizado como capacidade da inst�ncia rec�m criada.
	@param capacidade o n�mero inteiro a ser utilizado como capacidade.
	@throws Exception se a capacidade for negativa ou zero.
    */
    public Pilha(int capacidade) throws Exception
    {
		if(capacidade < 0)
		   throw new Exception("Capacidade inv�lida");

		this.vetor = new Object[capacidade];
	}

	/**
		Trata o fato da classe gen�rica poder ou n�o ser clonada.
		@param x objeto da classe gen�rica X.
		@return o par�metro clonado.
	*/
	private X meuCloneDeX(X x)
	{
		X ret = null;
		try
		{
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParametrosFormais = null;
			Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(x, parametrosReais);
		}
		catch(NoSuchMethodException erro)
		{}
		catch(IllegalAccessException erro)
		{}
		catch(InvocationTargetException erro)
		{}

		return ret;
	}

	/**
	Inclui um objeto da mesma classe que j� est� sendo armazenada na pilha na �ltima posi��o dispon�vel
	clonando-o se for poss�vel e acrescenta um na quantidade de elementos da pilha.

	@param s o objeto que ser� empilhado.
	@throws Exception se o objeto passado como par�metro for nulo ou uma cadeia vazia
					  ou se a pilha j� se encontra na sua capacidade m�xima.
	*/
    public void guarde(X s) throws Exception
    {
		if(s == null || s == "")
		   throw new Exception("Informa��o ausente");

		if(this.isCheia())
		   throw new Exception("Pilha cheia");

		if(s instanceof Cloneable)
			this.vetor[this.qtd] = meuCloneDeX(s);
		else
			this.vetor[this.qtd] = s;
        this.qtd++;
    }

    /**
    Obtem o objeto da �ltima posi��o da pilha e retorna-o clonado, se poss�vel, ou n�o.
    @return o objeto da �ltima posi��o da pilha.
    @throws Exception se n�o for fornecido houver nada na pilha.
    */
    public X getUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Nada a recuperar");

		if(this.vetor[this.qtd-1] instanceof Cloneable)
        	return meuCloneDeX((X)this.vetor[this.qtd-1]);

        return (X)this.vetor[this.qtd-1];
    }

    /**
	Remove um dos componentes da pilha, reduzindo o n�mero de componentes totais e apagando um �tem do vetor.
	@throws Exception se a pilha estiver vazia, ou seja, se n�o houver nada para ser retirado.
    */
    public void jogueForaUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Pilha vazia");

        this.qtd--;
        this.vetor[this.qtd] = null;
    }

	/**
		Compara a quantidade de elementos da pilha com sua capacidade, para testar se est� cheia.
		@return um valor boolean dependendo da quantidade de elementos em compara��o com o zero.
	*/
    public boolean isCheia()
    {
		return this.qtd == this.vetor.length;
	}

	/**
		Compara a quantidade de elementos da pilha com sua capacidade, para testar se est� vazia.
		@return um valor boolean dependendo da quantidade de elementos em compara��o com o zero.
	*/
    public boolean isVazia()
    {
		return this.qtd == 0;
	}

	/**
		Transforma em String a quantidade de elementos na pilha e o �ltimo valor que est� na pilha
		@return a quantidade de elementos e o �ltimo elemento em forma de String.
				E se est� vazia.
	*/
	public String toString()
	{
		if(this.qtd==0)
		   return "Vazia";

		return this.qtd+" elementos, sendo o �ltimo "+this.vetor[this.qtd-1];
	}

	/**
		Compara todos os atributos e verifica-se se o objeto passado como par�metro � igual ao objeto atual.
		@param objeto a ser comparado.
		@return se o objeto recebido como par�metro � ou n�o igual ao objeto atual.

	*/
	public boolean equals (Object obj)
	{
		if(this==obj)
		   return true;

		if(obj == null)
		   return false;

		if(this.getClass()!=obj.getClass())
		   return false;

		Pilha<X> pil = (Pilha<X>)obj;

        if(this.qtd!=pil.qtd)
           return false;

        for(int i = 0; i < this.qtd; i++)
           if(!this.vetor[i].equals(pil.vetor[i]))
              return false;

        return true;
	}

	 /**
		Calcula o codigo hash dos objetos quardados na pilha.
		@return o codigo hash dos objetos quardados na pilha.
    */

	public int hashCode()
	{
		int ret = 1;

		ret = ret * 2 + new Integer(this.qtd).hashCode();

		for(int i=0; i<this.qtd; i++)
				ret = ret*2 + this.vetor[i].hashCode();

		return ret;
	}

	/**
		Constroi uma Pilha a partir de outra que foi recebida como modelo.
		@param modelo da pilha fornecer� seus dados para a pilha atual.
		@throws Exception se o modelo recebido como par�metro for nulo.
	*/
	public Pilha (Pilha<X> modelo) throws Exception
	{
		if(modelo == null)
		   throw new Exception("Modelo ausente");

		this.qtd = modelo.qtd;

		this.vetor = new Object[modelo.vetor.length];

		for(int i=0; i<=modelo.qtd; i++)
		    this.vetor[i] = modelo.vetor[i];
	}

	/**
		Cria um clone da pilha atual.
		@return um objeto igual a classe que chamou o metodo clone().s.
	*/
	public Object clone()
	{
		Pilha<X> ret = null;
		try
		{
			ret = new Pilha<X>(this);
		}
		catch(Exception erro)
		{}

		return ret;
	}

	/**
		Obtem a quantidade de objetos armazenados na classe pilha.
		@return a quantidade (qtd).
	*/
	public int size()
	{
		return this.qtd;
	}
}