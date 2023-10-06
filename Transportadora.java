import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class Transportadora implements ImportacaoArquivos {

    //Carregar Configurações /////////////////////////////////////////////////////////////////////////////////////////
    public void carregarConfiguracoes(String arqConfig){
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(arqConfig)))) {
            String linha = "";
            boolean primeiraLinha = true; // Variável para identificar a primeira linha
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pula a primeira linha (cabeçalho)
                }
                
                String[] parametros = linha.split(";");
                if (parametros.length >= 3) {
                    String tipo = parametros[0];
                    String precoStr = parametros[2].replace(',', '.'); // Substitui ',' por '.'
                    
                    System.out.println("Valor de precoStr: " + precoStr); // Adicione esta linha para depurar
                    
                    double preco = Double.parseDouble(precoStr);
        
                    if ("Normal".equalsIgnoreCase(tipo)) {
                        precoKgN = preco;
                    } else if ("Expressa".equalsIgnoreCase(tipo)) {
                        precoKgE = preco;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao importar o arquivo: " + e.getMessage());
        }
        System.out.println("Preço por quilo Normal: " + precoKgN);
        System.out.println("Preço por quilo Expressa: " + precoKgE);
        
        }
    
    //Importar Dados /////////////////////////////////////////////////////////////////////////////////////////
    public void importarDados(String arqDadosEntrada) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(arqDadosEntrada)))) {
            String linha;
            reader.readLine();
    
            while ((linha = reader.readLine()) != null) {
                    String[] parametros = linha.split(";");
                    int numeroPedido = Integer.parseInt(parametros[0]);
                    String dataPostagem = parametros[1];
                    String tipoEncomenda = parametros[2];                  
                    if ("EN".equals(tipoEncomenda)) {
                        double peso = Double.parseDouble(parametros[4]);
                        encomendaNormal[eNormal] = new EncomendaNormal(numeroPedido, dataPostagem, peso);
                        eNormal++;
                    } else if ("EE".equals(tipoEncomenda)) {
                        int prazoEntrega = Integer.parseInt(parametros[3]);
                        double peso = Double.parseDouble(parametros[4]);
                        String telefoneContato = parametros[5];
                        encomendaExpressa[eExpressa] = new EncomendaExpressa(numeroPedido, dataPostagem, peso, prazoEntrega, telefoneContato);
                        eExpressa++;
                    }
                }
            }
         catch (IOException e) {
            System.err.println("Erro ao importar o arquivo: " + e.getMessage());
        }
    }
    

    // Exibir encomendas normais /////////////////////////////////////////////////////////////////////////////////////////
    DecimalFormat df = new DecimalFormat("0.00");
    public void exibirEncomendasN(){
        for(int i = 0; i < eExpressa; i++){
                EncomendaNormal enc = encomendaNormal[i];
                if(enc != null){
                System.out.println(enc.exibirEncomenda() + "," + "Valor frete: " + df.format(enc.calcularFrete(precoKgN)));
                }
        }
    }

    // Exibir encomendas expressas /////////////////////////////////////////////////////////////////////////////////////////
    public void exibirEncomendasE(){
        for(int i = 0; i < eNormal; i++){
            EncomendaExpressa enc = encomendaExpressa[i];
            if(enc != null){
            System.out.println(enc.exibirEncomenda() + ", " + "Valor frete: " + df.format(enc.calcularFrete(precoKgE)));
            }
        }
    }

    // declaração e inicialização de variáveis /////////////////////////////////////////////////////////////////////////////////////////
    private double precoKgN;
    private double precoKgE;
    private EncomendaNormal encomendaNormal[];
    private EncomendaExpressa encomendaExpressa[];
    private int eNormal;
    private int eExpressa;

    public Transportadora(){
        this.encomendaNormal = new EncomendaNormal[100];
        this.encomendaExpressa = new EncomendaExpressa[100];
        this.eNormal = 0;
        this.eExpressa = 0;
    }
        // função main (menus) /////////////////////////////////////////////////////////////////////////////////////////
        public static void main(String[] args) throws Exception{
            BufferedReader usu = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Digite o nome do arquivo de configuração (nome padrão = arqConfig.csv)");
            String arqConfig = usu.readLine().trim();
            File configFile;
            
            while(true){
                configFile = new File(arqConfig);
                if (!arqConfig.isEmpty() && configFile.exists()){
                    break;
                }
                else{
                    System.out.println("Arquivo Inexistente, digite um arquivo válido:");
                    arqConfig = usu.readLine().trim();
                }
            }
            Transportadora transportadora = new Transportadora();
            transportadora.carregarConfiguracoes(arqConfig);

            int opcao;
            while(true){
                System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-MENU-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
                System.out.println("1. Importar arquivo de encomendas.");
                System.out.println("2. Exibir a lista de encomendas Normais.");
                System.out.println("3. Exibir a lista de encomendas Expressas.");
                opcao = Integer.parseInt(usu.readLine());

                switch(opcao){
                    case 1:
                        System.out.println("Digite o nome do arquivo de encomendas: ");
                        String nomeArq = usu.readLine();
                        transportadora.importarDados(nomeArq);
                        break;
                    
                    case 2: 
                        System.out.println("Encomendas Normais: \n");
                        transportadora.exibirEncomendasN();
                        break;
                    case 3:
                        System.out.println("Encomendas Expressas: \n");
                        transportadora.exibirEncomendasE();
                        break;
                    default:
                        System.out.println("Digite uma opção válida");
                        break;
                }
            }
            
        }
}
