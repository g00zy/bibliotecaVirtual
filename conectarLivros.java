import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class conectarLivros
{
    public static void conectar(BibliotecaVirtual biblioteca, String titulo1, String titulo2)
    {
        Livro livro1 = biblioteca.buscarLivroPorTitulo(titulo1);
        Livro livro2 = biblioteca.buscarLivroPorTitulo(titulo2);

        if (livro1 != null && livro2 != null)
        {
            biblioteca.criarRecomendacao(livro1, livro2);
            biblioteca.criarRecomendacao(livro2, livro1);
        }
        else
        {
            System.out.println("[Aviso] Um dos livros não foi encontrado (" + titulo1 + " / " + titulo2 + ")");
        }
    }

    public static void conectar(BibliotecaVirtual biblioteca)
    {
        String caminhoArquivo = "data/conexoes.txt";
        int contadorConexoes = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo)))
        {
            String linha;
            while ((linha = leitor.readLine()) != null)
            {
                if (linha.trim().isEmpty()) continue;

                String[] titulos = linha.split(";");

                if (titulos.length == 2)
                {
                    String tituo1 = titulos[0].trim();
                    String tituo2 = titulos[1].trim();
                    
                    conectar(biblioteca, tituo1, tituo2);
                    contadorConexoes++;
                }
            }
            System.out.println("[Sistema] " + contadorConexoes + " conexões de recomendações estabelecidas.");

        } 
        catch (IOException exceptionConectarLivros)
        {
            System.err.println("[Erro] Não foi possível ler o arquivo conexoes.txt.");
            System.out.println("O Java está procurando arquivos em: " + new java.io.File(".").getAbsolutePath());
        }
    }
}