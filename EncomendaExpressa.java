public class EncomendaExpressa extends EncomendaNormal {
    private int numEncomenda = 0;
    private String telefoneContato;
    private int prazoEntrega;
    private double porcentAcres = 0.25;

    public int getNumEncomenda() {
        return numEncomenda;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    public double getPorcentAcres() {
        return porcentAcres;
    }

    public EncomendaExpressa(int numeroPedido, String dataPostagem, double peso, int prazoEntrega, String telefoneContato){
        super(numeroPedido, dataPostagem, peso);
        this.prazoEntrega = prazoEntrega;
        this.telefoneContato = telefoneContato;
    }
    
    @Override
    public double calcularFrete(double precoKg){
        double frete = super.calcularFrete(precoKg);
        if(prazoEntrega <= 2){
            frete = frete + (porcentAcres * frete);
        }
        return frete;
    }

    @Override
    public String exibirEncomenda(){
        return "Numero pedido: " + getNumeroPedido() + ", " + "Peso: " + getPeso();
    }
}
