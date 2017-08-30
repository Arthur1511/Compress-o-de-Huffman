package aeds2;

public class Palavra {

    int tamanho;
    String palavra;
    String codigo;
    char acentuacao;

    public Palavra(int tamanho, String palavra) {
        this.tamanho = tamanho;
        this.palavra = palavra;
    }

    public Palavra(String palavra, String codigo) {
        this.codigo = codigo;
        this.palavra = palavra;
    }
        
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Palavra(String palavra) {
        this.palavra = palavra;
    }
    
    
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getPalavra() {
        return palavra;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return ("Tamanho: " + tamanho + " Palavra:  " + palavra);
    }

    public String ImprimirTabela() {
        return ("Palavra: " + palavra + " Código: " + codigo);
    }

}
