import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class listaDeLivros
{   
    public static void lista(BibliotecaVirtual biblioteca)
    {
        String caminhoArquivo = "data/livros.txt";

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo)))
        {
            String linha;
            int contador = 0;

            while ((linha = leitor.readLine()) != null)
            {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");

                if (dados.length == 5)
                {
                    String titulo = dados[0].trim();
                    String autor = dados[1].trim();
                    int anoPublicacao = Integer.parseInt(dados[2].trim());
                    String genero = dados[3].trim();
                    String localidade = dados[4].trim();

                    Livro novoLivro = new Livro(titulo, autor, anoPublicacao, genero, localidade);
                    biblioteca.adicionarLivro(novoLivro);
                    contador++;
                }
            }
            System.out.println("[Sistema] " + contador + " livros importados com sucesso do arquivo .txt!");

        } 
        catch (IOException ioExceptionListaDeLivros)
        {
            System.err.println("[Erro] Não foi possível ler o arquivo livros.txt.");
            System.out.println("O Java está procurando arquivos em: " + new java.io.File(".").getAbsolutePath());
        } 
        catch (NumberFormatException numberFormatExceptionListaDeLivros)
        {
            System.err.println("[Erro] Há um erro na digitação do Ano de Publicação no arquivo de texto.");
        }
    }
}