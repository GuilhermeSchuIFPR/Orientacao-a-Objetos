public class EncomendaNormal{
    private int numeroPedido;
    private String dataPostagem;
    private double peso;

    public EncomendaNormal(int numeroPedido, String dataPostagem, double peso){
        this.numeroPedido = numeroPedido;
        this.dataPostagem= dataPostagem;
        this.peso = peso;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    public String getDataPostagem() {
        return dataPostagem;
    }
    public void setDataPostagem(String dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
    
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double calcularFrete(double precoKg){
        return peso * precoKg;
    }
    public String exibirEncomenda(){
        return "Numero pedido: " + getNumeroPedido() + ", " + "Peso: " + getPeso();
    }
    
}
