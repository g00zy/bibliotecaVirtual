public class Main
{
    public static void main(String[] args)
    {
        BibliotecaVirtual biblioteca = new BibliotecaVirtual();
        
        listaDeLivros.lista(biblioteca);
        conectarLivros.conectar(biblioteca);

        MenuDeNavegacao interfaceUsuario = new MenuDeNavegacao(biblioteca);
        interfaceUsuario.exibirMenu();
    }
}