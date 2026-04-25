import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BibliotecaVirtual
{
    private LinkedList<Livro> acervo;
    private Stack<Livro> historico;
    private HashMap<Livro, Queue<String>> filasDeEspera;
    private HashMap<Livro, Set<Livro>> grafoRecomendacoes;

    public BibliotecaVirtual()
    {
        this.acervo = new LinkedList<>();
        this.historico = new Stack<>();
        this.filasDeEspera = new HashMap<>();
        this.grafoRecomendacoes = new HashMap<>();
    }

    public void adicionarLivro(Livro novoLivro)
    {
        acervo.add(novoLivro);
        
        if (grafoRecomendacoes.get(novoLivro) == null)
        {
            grafoRecomendacoes.put(novoLivro, new HashSet<>());
        }
    }

    public void listarTodosOsLivros()
    {
        System.out.println("\n--- Catálogo da Biblioteca ---\n");
        for (Livro livro : acervo)
        {
            System.out.println("- " + livro.toString());
        }
    }

    public void acessarLivro(Livro livro)
    {
        historico.push(livro);
        System.out.println("\n[Sessão] Você abriu a página do livro: " + livro.getTitulo());
    }

    public Livro buscarLivroPorId(int idBuscado)
    {
        for (Livro livroAtual : acervo)
        {
            if (livroAtual.getId() == idBuscado)
            {
                return livroAtual;
            }
        }
        return null;
    }

    public Livro buscarLivroPorTitulo(String titulo)
    {
        for (Livro livroAtual : acervo)
        {
            if (livroAtual.getTitulo().equalsIgnoreCase(titulo))
            {
                return livroAtual;
            }
        }
        return null;
    }

    public void exibirHistorico()
    {
        System.out.println("\n--- Seu Histórico de Acesso (Pilha) ---\n");
        if (historico.isEmpty())
        {
            System.out.println("Histórico vazio.");
        }
        else
        {
            for (int i = historico.size() - 1; i >= 0; i--)
            {
                System.out.println((historico.size() - i) + ". " + historico.get(i).getTitulo());
            }
        }
    }

    public void entrarNaFila(Livro livro, String nomePessoa)
    {
        if (filasDeEspera.get(livro) == null)
        {
            filasDeEspera.put(livro, new LinkedList<>());
        }
        
        Queue<String> fila = filasDeEspera.get(livro);
        fila.add(nomePessoa);
        System.out.println("[Espera] " + nomePessoa + " entrou na fila para o livro: " + livro.getTitulo());
    }

    public void notificarProximoDaFila(Livro livro)
    {
        Queue<String> fila = filasDeEspera.get(livro);
        
        if (fila != null && !fila.isEmpty())
        {
            String pessoaSorteada = fila.poll();
            System.out.println("\n[Aviso] O livro '" + livro.getTitulo() + "' chegou! Notificando: " + pessoaSorteada);
        } 

        else
        {
            System.out.println("\n[Aviso] Ninguém na fila de espera para '" + livro.getTitulo() + "'.");
        }
    }

    public void criarRecomendacao(Livro livroLido, Livro livroRecomendado)
    {
        grafoRecomendacoes.putIfAbsent(livroLido, new HashSet<>());
        grafoRecomendacoes.putIfAbsent(livroRecomendado, new HashSet<>());

        grafoRecomendacoes.get(livroLido).add(livroRecomendado);
        grafoRecomendacoes.get(livroRecomendado).add(livroLido);
    }

    public Map<Livro, Integer> djikstraSimples(Livro origem)
    {
        Map<Livro, Integer> distancias = new HashMap<>();
        Queue<Livro> fila = new LinkedList<>();
     
        distancias.put(origem, 0);
        fila.add(origem);

        int limite = 4;
     
        while (!fila.isEmpty())
        {
            Livro atual = fila.poll();
            int distanciaAtual = distancias.get(atual);

            if (distanciaAtual >= limite) continue;
            {
                for (Livro vizinho : grafoRecomendacoes.getOrDefault(atual, Collections.emptySet()))
                {
                    int novaDistancia = distanciaAtual + 1;

                    if (!distancias.containsKey(vizinho) || novaDistancia < distancias.get(vizinho))
                    {
                        distancias.put(vizinho, novaDistancia);
                        fila.add(vizinho);
                    }
                }
            }
     
        }
        
        return distancias;
    }

    public void sugerirLeituras(Livro livroBase)
    {
        System.out.println("\n--- Recomendações Inteligentes ---\n");
        System.out.println("Com base em '" + livroBase.getTitulo() + "', os livros mais próximos são:\n");

        Map<Livro, Integer> distancias = djikstraSimples(livroBase);

        distancias.remove(livroBase);

        List<Map.Entry<Livro, Integer>> listaOrdenada = new ArrayList<>(distancias.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue());

        if (listaOrdenada.isEmpty())
        {
            System.out.println("Nenhuma recomendação encontrada.");
            return;
        }

        for (Map.Entry<Livro, Integer> entrada : listaOrdenada)
        {
            Livro livro = entrada.getKey();
            int distancia = entrada.getValue();

            String infoExtra = "";

            if (livro.getAutor().equals(livroBase.getAutor()))
            {
                infoExtra += " | Mesmo autor";
            }

            if (livro.getGeneroLiterario().equals(livroBase.getGeneroLiterario()))
            {
                infoExtra += " | Mesmo gênero";
            }

            if (livro.getLocalidade().equals(livroBase.getLocalidade()))
            {
                infoExtra += " | Mesma nacionalidade";
            }

            System.out.println("[Distância " + distancia + "] " + livro.getTitulo() + " (" + livro.getAutor() + ")" + infoExtra);
        }
    }
}