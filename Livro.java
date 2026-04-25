import java.util.Objects;

public class Livro 
{
    private static int contadorId = 1;
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String generoLiterario;
    private String localidade;

    public Livro(String titulo, String autor, int anoPublicacao, String generoLiterario, String localidade) 
    {
        this.id = contadorId++; 
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.generoLiterario = generoLiterario;
        this.localidade = localidade;
    }

    public int getId() {return id;}
    public String getTitulo() {return titulo;}
    public String getAutor() {return autor;}
    public int getAnoPublicacao() {return anoPublicacao;}
    public String getGeneroLiterario() {return generoLiterario;}
    public String getLocalidade() {return localidade;}

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        
        Livro outroLivro = (Livro) obj;
        return this.id == outroLivro.id;
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id);
    }

    @Override
    public String toString() 
    {
        return "[ID: " + id + "] " + titulo + " (" + autor + ", " + anoPublicacao + ", " + generoLiterario + ", " + localidade + ")";
    }
}