package fila;

import java.lang.reflect.*;
/**
A classe Fila<X> representa uma pilha de objetos gen�ricos.
Inst�ncias desta classe permitem a reliza��o das opera��es b�sicas de uma fila.
Nela encontramos, n�o s�, m�todos para emfileirar e tira da fila, mas tamb�m m�todos que permitem consult�-la.

@author Amabile Pietrobon Ferreira e Murilo de Paula Sanches.
@RA 18198 e 18187
@since 2018.
*/


public class Fila<X> implements Cloneable
{
	    private Object[] vetor;
	    private int qtd = 0;
	    private int inicio = 0;
	    private int fim = 0;


		/**
			Constroi uma nova inst�ncia da classe Fila<X>.
			Para tanto, deve ser fornecido um inteiro que ser� utilizado como capacidade da inst�ncia rec�m criada.
			@param capacidade o n�mero inteiro a ser utilizado como capacidade.
			@throws Exception se a capacidade for negativa ou zero.
    	*/
	    public Fila(int capacidade) throws Exception
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
				Class<?>[] tiposDeParametrosFormais = null;
				Method metodo = classe.getMethod("clone", tiposDeParametrosFormais);
				Object[] tiposDeParametrosReais = null;
				ret = (X)metodo.invoke(x, tiposDeParametrosReais);
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
			Inclui um objeto da mesma classe que j� est� sendo armazenada na fila na �ltima posi��o dispon�vel
			clonando-o se for poss�vel e acrescenta um na quantidade de elementos da pilha.

			@param s o objeto que ser� empilhado.
			@throws Exception se o objeto passado como par�metro for nulo ou uma cadeia vazia
							  ou se a pilha j� se encontra na sua capacidade m�xima.
		*/
	    public void guarde(X s) throws Exception
	    {
			if(s==null)
			   throw new Exception("Informa��o ausente");

			if(this.isCheia())
			   throw new Exception("Fila cheia");

			if(s instanceof Cloneable)
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = meuCloneDeX(s);
				}
				else
					this.vetor[this.fim++] = meuCloneDeX(s);
			}
			else
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = s;
				}
				else
					this.vetor[this.fim++] = s;
			}
			this.qtd++;
	    }

		  /**
		    Obtem o objeto da primeira posi��o da fila e retorna-o clonado, se poss�vel, ou n�o.
		    @return o objeto da primeira posi��o da pilha.
		    @throws Exception se n�o for fornecido houver nada na pilha.
   	 */
	    public X getUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Nada a recuperar");

	        if(this.vetor[this.inicio] instanceof Cloneable)
	        	return meuCloneDeX((X)this.vetor[this.inicio]);

	        return (X)this.vetor[this.inicio];
	    }

		/**
			Remove um dos componentes da fila, reduzindo o n�mero de componentes totais e apagando um �tem do vetor.
			@throws Exception se a fila estiver vazia, ou seja, se n�o houver nada para ser retirado.
   		 */
	    public void jogueForaUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Pilha vazia");

			this.vetor[this.inicio] = null;

			if(this.inicio == this.vetor.length-1)
			   inicio = 0;
			else
				inicio++;

	        qtd--;
	    }

		/**
			Compara a quantidade de elementos da fila com sua capacidade, para testar se est� cheia.
			@return um valor boolean dependendo da quantidade de elementos em compara��o com o zero.
		*/
	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}

		/**
			Compara a quantidade de elementos da fila com sua capacidade, para testar se est� vazia.
			@return um valor boolean dependendo da quantidade de elementos em compara��o com o zero.
		*/
	    public boolean isVazia()
	    {
			return this.qtd == 0;
		}

		/**
			Transforma em String a quantidade de elementos na fila e o primeiro valor que est� na pilha
			@return a quantidade de elementos e o primeiro elemento em forma de String.
					E se est� vazia.
		*/
		public String toString()
		{
			if(this.qtd==0)
			   return "Vazia";

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

		/**
			Compara todos os atributos e verifica-se se o objeto passado como par�metro � igual ao objeto atual.
			@param objeto a ser comparado.
			@return se o objeto recebido como par�metro � ou n�o igual ao objeto atual.
		*/
		public boolean equals (Object obj) //compara this e obj
		{
			if(this==obj) //dispens�vel, mas deixa m�todo mais r�pido
			   return true;

			if(obj == null)
			   return false;

			if(this.getClass()!=obj.getClass())
			   return false;

			Fila<X> fila = (Fila<X>)obj; // java enxerga que existe uma Fila chamada fila (que � o mesmo obj)

	        if(this.qtd!=fila.qtd)
	           return false;

	        for(int i = 0,
	                posThis=this.inicio,
	                posFila=fila.inicio;

	            i < this.qtd;

	            i++,
	            posThis=(posThis<this.vetor.length-1?posThis+1:0),
	            posFila=(posFila<fila.vetor.length-1?posFila+1:0))

	           if(!this.vetor[posThis].equals(fila.vetor[posFila]))
	              return false;

	        return true;
		}

		/**
			Calcula o codigo hash dos objetos quardados na fila.
			@return o codigo hash dos objetos quardados na fila.
		*/
		public int hashCode()
		{
			int ret = 1; //s� n�o pode ser 0

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}

		/**
			Constroi uma Fila a partir de outra que foi recebida como modelo.
			@param modelo da fila fornecer� seus dados para a fila atual.
			@throws Exception se o modelo recebido como par�metro for nulo.
		*/
		public Fila (Fila<X> modelo) throws Exception
		{
			if(modelo == null)
		    	throw new Exception("Modelo ausente");

			this.qtd = modelo.qtd;

			this.inicio = modelo.inicio;

			this.fim = modelo.fim;

			this.vetor = new Object[modelo.vetor.length];

			for(int i=0; i<modelo.vetor.length-1; i++)
		    	this.vetor[i] = modelo.vetor[i];
		}

		/**
			Cria um clone da fila atual.
			@return um objeto igual a classe que chamou o metodo clone()
		*/
		public Object clone()
		{
			Fila<X> ret = null;
			try
			{
				ret = new Fila<X>(this);
			}
			catch(Exception erro)
			{}

			return ret;
		}

}