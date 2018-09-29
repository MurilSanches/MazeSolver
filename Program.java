import java.io.*;
import pilha.*;
import labirinto.*;
import coordenada.*;
import fila.*;

/**
@author Amabile Pietrobon Ferreira e Murilo Sanches de Paula.
@RA 18198 e 18187.
@since 2018.
*/

public class Program
{
    public static void main(String[] args) throws Exception
	{
		boolean parar = false;
		BufferedReader ler = new BufferedReader(new InputStreamReader(System.in));

		Labirinto lab = new Labirinto();

		try{
			while(!parar)
			{
				System.out.printf("\nEscreva o caminho até o arquivo de texto: ");
				String nomeArq = ler.readLine();
				lab.lerArquivo(nomeArq);

				while (!lab.encontrouS())
				   lab.resolver();

				System.out.println("\nO labirinto foi resolvido com sucesso\nAbra o arquivo " + nomeArq + ".res.txt\n");
				lab.escreverArquivo(nomeArq);

				System.out.println ("Deseja executar o programa novamente com outro labirinto? (S/N)");
				char resposta = Character.toUpperCase(ler.readLine().charAt(0));

				if (resposta == 'N')
					parar = true;
			}
		}

		catch (Exception erro)
		{
			System.err.print(erro.getMessage());
		}
	}
}
