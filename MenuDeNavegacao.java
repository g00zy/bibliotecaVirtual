import java.util.Scanner;

public class MenuDeNavegacao
{
    private BibliotecaVirtual minhaBiblioteca;
    private Scanner inputDoUsuario;

    public MenuDeNavegacao(BibliotecaVirtual biblioteca)
    {
        this.minhaBiblioteca = biblioteca;
        this.inputDoUsuario = new Scanner(System.in);
    }

    public static void clearConsole()
    {
        try
        {
            String sistemaOperacional = System.getProperty("os.name");

            if (sistemaOperacional.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } 
        catch (Exception exceptionClearConsole)
        {
            exceptionClearConsole.printStackTrace();
        }
    }

    public static void threadSleep(int duration)
    {
        try
        {
            Thread.sleep(duration);
        }
        catch(InterruptedException sleep)
        {
            Thread.currentThread().interrupt();
        }
        
    }

    public void exibirMenu()
    {
        boolean sistemaRodando = true;

        while (sistemaRodando)
        {
            System.out.println("\n=============================================");
            System.out.println("     BEM-VINDO À SUA BIBLIOTECA VIRTUAL      ");
            System.out.println("=============================================");
            System.out.println("1 - LISTAR TODOS OS LIVROS\n" + 
                               "2 - ACESSAR LIVRO\n" + 
                               "3 - EXIBIR HISTÓRICO\n" + 
                               "4 - ENTRAR NA FILA DE ESPERA\n" + 
                               "5 - SUGERIR LEITURAS\n" + 
                               "6 - DEVOLVER LIVRO\n" + 
                               "7 - SAIR DO SISTEMA\n");
            
            if (!inputDoUsuario.hasNextLine())
            {
                sistemaRodando = false;
                break;
            }
            
            String respostaMenuPrincipal = inputDoUsuario.nextLine();
            int idDigitado;
            Livro livroEncontrado;

            switch (respostaMenuPrincipal) 
            {
                case "1":
                    clearConsole();
                    minhaBiblioteca.listarTodosOsLivros();
                    break;
                    
                case "2":
                    clearConsole();
                    System.out.print("Digite o ID do livro que deseja acessar: ");
                    try 
                    {
                        idDigitado = Integer.parseInt(inputDoUsuario.nextLine());
                        livroEncontrado = minhaBiblioteca.buscarLivroPorId(idDigitado);
                        
                        if (livroEncontrado != null) 
                        {
                            clearConsole();
                            minhaBiblioteca.acessarLivro(livroEncontrado);
                        } 
                        else 
                        {
                            clearConsole();
                            System.out.println("[Erro] Nenhum livro encontrado com o ID " + idDigitado);
                        }
                    } 
                    catch (NumberFormatException exceptionAcessarLivro) 
                    {
                        clearConsole();
                        System.out.println("[Erro] Digite apenas números para o ID.");
                    }
                    break;
                    
                case "3":
                    clearConsole();
                    minhaBiblioteca.exibirHistorico();
                    break;
                    
                case "4":
                    clearConsole();
                    System.out.print("Digite o ID do livro que deseja entrar na fila: ");
                    try 
                    {
                        idDigitado = Integer.parseInt(inputDoUsuario.nextLine());
                        livroEncontrado = minhaBiblioteca.buscarLivroPorId(idDigitado);
                        
                        if (livroEncontrado != null) 
                        {
                            clearConsole();
                            System.out.print("Qual é o seu nome? ");
                            String nomePessoa = inputDoUsuario.nextLine();
                            minhaBiblioteca.entrarNaFila(livroEncontrado, nomePessoa);
                        } 
                        else 
                        {
                            clearConsole();
                            System.out.println("[Erro] Nenhum livro encontrado com o ID " + idDigitado);
                        }
                    } 
                    catch (NumberFormatException exceptionEntrarNaFila)
                    {
                        clearConsole();
                        System.out.println("[Erro] Digite apenas números para o ID.");
                    }
                    break;
                    
                case "5":
                    clearConsole();
                    System.out.print("Digite o ID do livro base para receber recomendações: ");
                    try 
                    {
                        idDigitado = Integer.parseInt(inputDoUsuario.nextLine());
                        livroEncontrado = minhaBiblioteca.buscarLivroPorId(idDigitado);
                        
                        if (livroEncontrado != null) 
                        {
                            clearConsole();
                            minhaBiblioteca.sugerirLeituras(livroEncontrado);
                        } 
                        else
                        {
                            clearConsole();
                            System.out.println("[Erro] Nenhum livro encontrado com o ID " + idDigitado);
                        }
                    } 
                    catch (NumberFormatException exceptionReceberRecomendacoes)
                    {
                        clearConsole();
                        System.out.println("[Erro] Digite apenas números para o ID.");
                    }
                    break;
                    
                case "6":
                    clearConsole();
                    System.out.print("Digite o ID do livro que está devolvendo: ");
                    try 
                    {
                        idDigitado = Integer.parseInt(inputDoUsuario.nextLine());
                        livroEncontrado = minhaBiblioteca.buscarLivroPorId(idDigitado);
                        
                        if (livroEncontrado != null) 
                        {
                            clearConsole();
                            System.out.println("Livro '" + livroEncontrado.getTitulo() + "' devolvido com sucesso.");
                            minhaBiblioteca.notificarProximoDaFila(livroEncontrado);
                        } 
                        else 
                        {
                            clearConsole();
                            System.out.println("[Erro] Nenhum livro encontrado com o ID " + idDigitado);
                        }
                    } 
                    catch (NumberFormatException exceptionDevolverLivro) 
                    {
                        clearConsole();
                        System.out.println("[Erro] Digite apenas números para o ID.");
                    }
                    break;

                case "7":
                clearConsole();
                System.out.println("Encerrando a Biblioteca Virtual. Até logo!");
                threadSleep(2500);
                
                sistemaRodando = false; 
                break;
                    
                default:
                    clearConsole();
                    System.out.println("Opção inválida! Digite um número de 0 a 5.");
                    threadSleep(2500);
                    break;
            }
        }
        
        inputDoUsuario.close();
    }
}