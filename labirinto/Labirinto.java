package labirinto;

import java.io.*;
import coordenada.*;
import pilha.*;
import fila.*;

/**
A classe Labirinto representa um labirinto e todos os métodos necessários para sair dele.
Instâncias desta classe permitem a leitura do arquivo texto que contém o labirinto e
operações básicas para encontrar a entrada e a saída.
Nela encontramos, não só, métodos para descobrir a entrada e saída do labirinto, como também consultar o caminho percorrido.

@author Amabile Pietrobon Ferreira e Murilo de Paula Sanches.
@RA 18198 e 18187
@since 2018.
*/
public class Labirinto implements Cloneable
{
	protected  Coordenada atual;
	protected  Fila<Coordenada> fila;
	protected  boolean primeiraVez = true;

	protected  int cMax = -1;
	protected  int lMax = -1;

	protected  String[][] labirinto;
	protected  Pilha<Fila<Coordenada>> possibilidades;
	protected  Pilha<Coordenada> caminho;
	protected  String nomeArq = "";

    /**
	Constroi uma nova instância da classe Labirinto.
	Para tanto, deve ser fornecido uma Pilha<Fila<Coordenada>> que será usada para armazenar as possibiliades de caminhos a seguir,
	uma Pilha<Coordenada> que armazenará  caminho percorrido, uma Fila<Coordenada> que será uma fila de espaços nos quais podemos seguir,
	uma matriz de String que armazenará o labirinto e uma Coordenada que receberá a posição atua do objeto que está procurand a saída do labirinto.

	@param 	pfc Pilha<Fila<Coordenada>> que será usada para armazenar as possibiliades de caminhos a seguir;
		pc Pilha<Coordenada> que armazenará  caminho percorrido;
		fc Fila<Coordenada> que será uma fila de espaços nos quais podemos seguir;
		matriz matriz de String que armazenará o labirinto;
		coord Coordenada que receberá a posição atua do objeto que está procurand a saída do labirinto.
    */
	public Labirinto (Pilha<Fila<Coordenada>> pfc, Pilha<Coordenada> pc, Fila<Coordenada> fc, String[][] matriz, Coordenada coord)
	{
		this.labirinto = matriz;
		this.possibilidades = pfc;
		this.caminho = pc;
		this.fila = fc;
		this.atual = coord;
    }

	/**
	   Consroi uma nova instância da classe Labirinto, mas que não recebe parâmetros,
           apenas inicializa os valores dos atributos de maneira genérica
	*/
    public Labirinto() throws Exception
    {
		atual = new Coordenada(0,0);
		fila = new Fila<Coordenada>(3);

		labirinto = new String[0][0];
		possibilidades = new Pilha<Fila<Coordenada>>(1);
		caminho = new Pilha<Coordenada>(1);
	}

	/**
		Lê um arquivo texto e inicializa as variáveis com os dados resentes nele
		Recebe as inforações do labirinto que buscará a saída, a quantidade de linhas e counas desse labirinto.
		@param nomeArq string nome do arquivo que será lido.
		@throws Excepetion se der erro na abertura do arquivo texto.
	*/
	public void lerArquivo(String nomeArquivo) throws Exception
	{
		try
		{
        	boolean encontrouE = false, encontrou = false;
			BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo));
			nomeArq = nomeArquivo;
			lMax = Integer.parseInt(arq.readLine());
			cMax = Integer.parseInt(arq.readLine());
			caminho = new Pilha<Coordenada>(cMax*lMax);
			possibilidades = new Pilha<Fila<Coordenada>>(cMax*lMax);
            labirinto = new String[lMax][cMax];

			for (int li = 0; li < lMax; li++)
			{
			    String linha = arq.readLine();

			    for (int col = 0; col < cMax; col++)
			    {
			    	labirinto[li][col] = String.valueOf(linha.charAt(col));

					if (encontrouE == false)
			   			encontrouE = this.encontrarE(li, col);

					if (encontrou == false)
						encontrou = this.encontrarS(li, col);
				}
            }

			  arq.close();

			  if(!encontrouE)
			  	throw new Exception ("O labirinto não possui uma entrada!\n");

			  if(!encontrou)
			  	throw new Exception ("O labirinto não possui uma saida!\n");
		  }

		catch(IOException erro)
		{
			System.err.print("Erro na abertura do arquivo\n");
		}
	}

	/**
		Procura a entrada do labrinto pelas laterais.
		@param coordenadas linha e coluna para checara se há uma entrada
		@throws Exception se não houver uma entrada para o labirinto.
	*/
	protected boolean encontrarE(int linha, int coluna) throws Exception
	{
		if (linha < 0)
			throw new Exception("Linha Inválida\n");

		if (coluna < 0)
			throw new Exception("Coluna Inválida\n");

        boolean encontrouE = false;

		if (linha == 0 || linha == lMax-1)
			if (this.labirinto[linha][coluna].equals("E"))
			{
				encontrouE = true;
				this.atual = new Coordenada(linha, coluna);
			}

		if (coluna == 0 || coluna == cMax-1)
			if (this.labirinto[linha][coluna].equals("E"))
			{
			    encontrouE = true;
				this.atual = new Coordenada(linha, coluna);
			}

		return encontrouE;
  }

	  /**
		Procura a saida do labrinto.
		@param coordenadas linha e coluna para checara se há uma saída.
		@throws Exception se não houver uma saida para o labirinto.
	  */
      protected boolean encontrarS(int linha, int coluna) throws Exception
	  {
	  		if (linha < 0)
	  			throw new Exception("Linha Inválida\n");

	  		if (coluna < 0)
	  			throw new Exception("Coluna Inválida\n");

	        boolean encontrou = false;

	  		if (linha == 0 || linha == lMax-1)
				if (this.labirinto[linha][coluna].equals("S"))
					encontrou = true;

			if (coluna == 0 || coluna == cMax-1)
				if (this.labirinto[linha][coluna].equals("S"))
					encontrou = true;

	  		return encontrou;
      }


	/**
		Obtém a posição atual no labirinto
		@return a posicao atual que se encontra no labirinto
	*/
	public Coordenada getAtual()
	{
		return this.atual;
	}

	/**
		Chama os metodos avancar, retroceder e referencia para resolver o labirinto e
		coloca como falsa a variável boolean que identifica se esta passando pela primeira vez no método
	*/
	public void resolver() throws Exception
	{
		this.avancar();
		this.retroceder();
		this.referencia();

		this.primeiraVez = false;
	}

	/**
		Escreve no arquivo o labirinto resolvido e o caminho inversos
	*/
	public void escreverArquivo(String nomeArq) throws Exception
	{
		try
		{
			PrintStream resultado = new PrintStream(nomeArq + ".res.txt");
			resultado.println("Labirinto resolvido :\n" + this.toString());
			resultado.println("Caminho inverso percorrido pelo labirinto: ");
			resultado.println(this.inverso());
			resultado.close();
		}
		catch(Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}

	/**
		Avança na direção que existe espaço no labirinto ou retrocede caso esse espaço não exista,
		armazena na variável fila as opçoes de caminhos a percorrer no labirinto,
		empilha o caminho que foi percorrido e coloca um caracter de referência na coordenada do labirinto que está o objeto atual.
	*/
	public void avancar() throws Exception
	{
		try
		{
			this.fila = new Fila<Coordenada>(3);

			int x = this.atual.getX();
			int y = this.atual.getY();

			if (y + 1 < cMax)
				if (labirinto[x][y+1].equals(" ") || labirinto[x][y+1].equals("S"))
					this.fila.guarde(new Coordenada(x, y+1));

			if (y -1 >= 0)
				if (labirinto[x][y-1].equals(" ") || labirinto[x][y-1].equals("S"))
					this.fila.guarde(new Coordenada(x, y-1));

			if (x + 1 < lMax)
				if (labirinto[x+1][y].equals(" ") || labirinto[x+1][y].equals("S"))
					this.fila.guarde(new Coordenada(x+ 1, y));

			if (x - 1 >=0)
				if (labirinto[x-1][y].equals(" ") || labirinto[x-1][y].equals("S"))
					this.fila.guarde(new Coordenada(x - 1, y));
		}

		catch(Exception erro)
		{
			System.err.print(erro.getMessage());
		}
	}

	/**
		Verifica se a atual esta na saida do labirinto.
		@return um valor boolean.
	*/

	public boolean encontrouS()
	{
		int x = this.atual.getX();
		int y = this.atual.getY();

		return labirinto[x][y].equals("S");
	}

   /**
   	Rerocede posições e verifica se naquela posição existe mais possibilidades de caminhos para seguir,
	enquanto não encontra, continua retrocedeu e eliminando o caracter de referência do labirinto.
	@throws Exception se não houver mais possibilidades para retroceder, ou seja, não existe caminho até a saída
   */
    protected void retroceder() throws Exception
    {
		while (this.fila.isVazia())
		{
			this.fila = new Fila<Coordenada>(3);
			this.labirinto[this.atual.getX()][this.atual.getY()] = " ";


			this.caminho.jogueForaUmItem();

			if ((this.possibilidades.isVazia() || this.caminho.isVazia()) && !this.primeiraVez)
				throw new Exception("Não existe caminho até à saída\n");

			this.atual.setCoordenada(this.caminho.getUmItem());

			this.fila = this.possibilidades.getUmItem();
			this.possibilidades.jogueForaUmItem();
		}
	}

	/**
		Inclue um caracter de referencia na posição atual do labirinto
	*/
	public void referencia() throws Exception
	{
		this.atual.setCoordenada(this.fila.getUmItem());
		this.fila.jogueForaUmItem();

		if(!this.encontrouS())
		{
			this.labirinto[this.atual.getX()][this.atual.getY()] = "*";
			this.caminho.guarde(this.atual);

			this.possibilidades.guarde(this.fila);
		}
	}

	/**
		Compara todos os atributos e verifica-se se o objeto passado como parâmetro é igual ao objeto atual.
		@param objeto a ser comparado.
		@return se o objeto recebido como parâmetro é ou não igual ao objeto atual.
	*/
	public boolean equals(Labirinto obj)
	{
		if (this == obj)
			return true;

		if (this.getClass() != obj.getClass())
			return false;

		if (obj == null)
			return false;

		Labirinto lab = (Labirinto)obj;

		if (this.atual.equals(lab.atual))
			return false;

		if (this.fila.equals(lab.fila))
			return false;

		for (int li = 0; li < lMax; li++)
			for (int col = 0; col < cMax; col++)
				if (this.labirinto[li][col] != lab.labirinto[li][col])
					return false;

		if (this.possibilidades.equals(lab.possibilidades))
			return false;

		if (this.caminho.equals(lab.caminho))
			return false;

		if (this.lMax != lab.lMax)
			return false;

		if (this.cMax != lab.cMax)
			return false;

		return true;
	}

	/**
		Transforma o caminho percorrido em uma String
		@return uma String de todas as coordenadas do caminho percorrido
	*/
	public String inverso() throws Exception
	{
		String ret = "\r\n";

		while (!this.caminho.isVazia())
		{
			ret += caminho.getUmItem().toString();
			ret += "\r\n";

			this.caminho.jogueForaUmItem();
		}

		return ret;
	}

	/**
		Transforma o labirinto em uma String.
	 	@return uma String que representa o labirinto.
	*/
	public String toString()
	{
		String ret = "\r\n";

		for (int li = 0; li < this.lMax; li++)
		{
			for(int col = 0; col < this.cMax; col++)
				ret += this.labirinto[li][col];

			ret += "\r\n";
		}

		return ret;
	}

	/**
		Calcula o codigo hash do labirinto guardado
		@return o codigo hash do labirinto
	*/
	public int hashCode()
	{
		int ret = 11;

		ret = ret * 2 + this.atual.hashCode();

		ret = ret * 2 + this.fila.hashCode();

		ret = ret * 2 + this.possibilidades.hashCode();

		ret = ret * 2 + this.caminho.hashCode();

		for (int li = 0; li < lMax; li++)
			for (int col = 0; col < cMax; col++)
			{
				int lab = Integer.parseInt(this.labirinto[li][col]);
				ret = ret * 2 + new Integer(lab).hashCode();
			}

		ret = ret * 2 + new Integer(this.lMax).hashCode();

		ret = ret * 2 + new Integer(this.cMax).hashCode();

		return ret;
	}

	/**
		Constroi um Labirinto a partir de outra que foi recebida como modelo.
		@param modelo do labirinto fornecerá seus dados para o labirinto atual.
		@throws Exception se o modelo recebido como parâmetro for nulo.
	*/
	public Labirinto (Labirinto modelo) throws Exception
	{
		if (modelo == null)
			throw new Exception ("Modelo Ausente!");

		this.atual = modelo.atual;

		this.fila = modelo.fila;

		this.lMax = modelo.lMax;

		this.cMax = modelo.cMax;

		this.possibilidades = modelo.possibilidades;

		this.caminho = modelo.caminho;

		for (int li = 0; li < lMax; li++)
					for (int col = 0; col < cMax; col++)
						this.labirinto[li][col] = modelo.labirinto[li][col];
	}

	/**
		Cria um clone do labirinto atual.
		@return um objeto igual a classe que chamou o metodo clone()
	*/
	public Labirinto clone()
	{
		Labirinto ret = null;

		try
		{
			ret = new Labirinto(this);
		}

		catch(Exception erro)
		{ }

		return ret;
	}
}