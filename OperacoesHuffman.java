package aeds2;

//Importa��o dos pacotes usados
import aeds2.Palavra;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class OperacoesHuffman {

    // declaracao de atributos utilizados
    private final ArrayList<Palavra> vetor_de_palavras;
    private int valor;
    private final ArrayList<ArvoreNo> Arvore;
    private ArrayList<Palavra> Tabela;

    //Construtor da classe
    public OperacoesHuffman() {
        this.vetor_de_palavras = new ArrayList<>();
        this.Arvore = new ArrayList<>();
        this.Tabela = new ArrayList<>();
    }

    public void criaPalavras() throws FileNotFoundException, IOException {

        //Leitura do arquivo .txt
        try {
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);

            //String linha que recebe a primeira linha do arquivo
            String linha = leitor.readLine();
            
            // verifica se arquivo esta vazio
            if (linha == null){
                System.out.println("Vetor de palavras vazio");
                System.exit(0);
            }
            
            // contera palavras que sao separadas pelos delimitadores
            char[] palavra_criada = new char[99999999]; //Vetor de char
            // controle das palavras
            int j = 0;
            
            while (linha != null) {
            
                // copia conteudo do arquivo para um aux
                char[] aux = linha.toCharArray();
                // é necessario criar outro pois a ultima palavra tb conta
                char[] linhast = new char[aux.length + 1];
                
                // percorrer o vetor que contem todos os caractres do arquivo
                // atribui a linhast o conteudo de aux
                for( int i = 0;i<aux.length;i++){ 
                    linhast[i] = aux[i];
                }
                
                // coloca espaco branco no ultimo caractere do arquivo, para reconhecer a ultima
                linhast[linhast.length -1] = ' ';  
                
                // percorre  o vetor com todos os caracteres do arquivo
                for (int i= 0; i < linhast.length; i++) { 
                   
                    // se linhast nao e ' ', (nao e fim de arquivo) coloco no vetor que contera a palavra a ser criada
                    if (linhast[i] != ' ') { 
                        palavra_criada[j] = linhast[i]; 
                        j++; //incrementa variavel que controla as palavras 
                        
                    }else {
                        
                        // na ultima vez que o j foi incrementado, ele tem o indice da proxima palavra assim é preciso decrementar ser valor para comparacoes
                        
                        // verifica se o ultimo caractere analizado é os de acentuacao
                        if (palavra_criada[j - 1] == '!' || palavra_criada[j - 1] == '?' || palavra_criada[j - 1] == '.' || palavra_criada[j - 1] == ',' || palavra_criada[j - 1] == ':' || palavra_criada[j - 1] == ';') {
                            
                            // cria um novo vetor, com o tanhanho j-1, pois o caractere nao entra na palavra
                            char[] palavra_definitiva = new char[j - 1]; 
                            
                            // exerce a funcao strcpy em C
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                            
                            // vetor de palavras contem as palavras que vao sendo criadas
                            
                            if (vetor_de_palavras.isEmpty() == true) { 
                                // Se esta vazio, crio um objeto palavra, para add
                                Palavra p = new Palavra(1, String.copyValueOf(palavra_definitiva)); 
                                vetor_de_palavras.add(p); 
                                
                            } 
                            // Se nao esta vazio, verifica se há incidencia da palavra no array
                            
                            else if (verificaExistencia(vetor_de_palavras, palavra_definitiva) == true) { // chama a fun��o Contem, se retornar true
                            
                                // Para este caso, a palavra ja esta presente, assim e necessario apenas mudar sua frequencia
                                vetor_de_palavras.get(valor).setTamanho(vetor_de_palavras.get(valor).getTamanho() + 1);
                            } 
                            
                            // palavra nao esta presente, assim esta sera adicionada mo array
                            else if (verificaExistencia(vetor_de_palavras, palavra_definitiva) == false) { 
                                Palavra p = new Palavra(1, String.copyValueOf(palavra_definitiva)); 
                                vetor_de_palavras.add(p); 
                            }
                    
                            // Limpa o conteudo do vetor para a proxima iteracao
                            ResetaVetor(palavra_definitiva);
                            ResetaVetor(palavra_criada);
                            
                            // zera variavel que controla tamanho de palavras
                            j = 0;
                            
                            
                            
                        // para este caso o ultimo caractere lido nao é de pontuacao, portanto nao e necessario manipular o valor de j, para palavra_definitiva
                        } else {
                            // cria um novo vetor, com o tanhanho j, pois o caractere nao entra na palavra
                            char[] palavra_definitiva = new char[j]; 
                            
                            // exerce a funcao strcpy em C
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                            
                            // vetor de palavras contem as palavras que vao sendo criadas
                            
                            if (vetor_de_palavras.isEmpty() == true) { 
                                // Se esta vazio, crio um objeto palavra, para add
                                Palavra p = new Palavra(1, String.copyValueOf(palavra_definitiva)); 
                                vetor_de_palavras.add(p); 
                                
                            } 
                            // Se nao esta vazio, verifica se há incidencia da palavra no array
                            
                            else if (verificaExistencia(vetor_de_palavras, palavra_definitiva) == true) { // chama a fun��o Contem, se retornar true
                            
                                // Para este caso, a palavra ja esta presente, assim e necessario apenas mudar sua frequencia
                                vetor_de_palavras.get(valor).setTamanho(vetor_de_palavras.get(valor).getTamanho() + 1);
                            } 
                            
                            // palavra nao esta presente, assim esta sera adicionada mo array
                            else if (verificaExistencia(vetor_de_palavras, palavra_definitiva) == false) { 
                                Palavra p = new Palavra(1, String.copyValueOf(palavra_definitiva)); 
                                vetor_de_palavras.add(p); 
                            }
                    
                            // Limpa o conteudo do vetor para a proxima iteracao
                            ResetaVetor(palavra_definitiva);
                            ResetaVetor(palavra_criada);
                            
                            // zera variavel que controla tamanho de palavras
                            j = 0;
                            
                        }

                    }
                }
                ResetaVetor(aux);
                ResetaVetor(linhast);
                linha = leitor.readLine(); // String linha recebe uma nova linha do texto
            }
            
           // System.out.println(word.get(0).getPalavra());
            
            // O ordena vetor e necessario, pois na construcao da arvore, o processo se inicia pelas folhas
            // ou seja pelas de menor frequencia
            OrdenaVetor(vetor_de_palavras);  
            
            
            // neste instante ja tenho o vetor de palavras pronto
            // passo para o processo de construcao da arvore
            ConstruirArvore(vetor_de_palavras, Arvore); 

        } 
        // tratamento das excessoes que podem ocorrer com a manipulacao de arquivo
        catch (FileNotFoundException e) { 
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

    
    // verifica a existencia de uma palavra no vetor de palavras
    public boolean verificaExistencia(ArrayList<Palavra> vetor_de_palavras, char[] palav) {
        for (int i = 0; i < vetor_de_palavras.size(); i++) { 
            
            if (vetor_de_palavras.get(i).getPalavra().equals(String.copyValueOf(palav))) { 
                // indice de onde o elemento buscado esta
                // valor é global
                valor = i; 
                return true;
            }
        }
        return false; 
    }

    
    // Coloca espaco branco nos elementos do vetor
    public void ResetaVetor(char[] p) { 
        for (int i = 0; i < p.length; i++) { 
            p[i] = ' ';
        }
    }

    public void OrdenaVetor(ArrayList<Palavra> vetor_de_palavras) { //Recebe uma ArrayList de palavras para ser ordenada

        // funcao lambda para ordenar as palavras
        Collections.sort(vetor_de_palavras, (Palavra p1, Palavra p2) -> {

            //Comparando objeto 1 com objeto 2
            if (p1.tamanho < p2.tamanho) { //se p1 > p2, retornar algum numero negativo
                return -1;
            } else if (p1.tamanho > p2.tamanho) { //se p1 > p2, retornar algum numero positivo
                return 1;
            } else { // se iguais, retornar 0
                return 0;
            }
        });
    }

    
    // recebe o vetor de palavras, e a arvore em que sera inserido os nos
    public void ConstruirArvore(ArrayList<Palavra> vetor_de_palavras, ArrayList<ArvoreNo> Arvore) throws IOException { 
        try {
            // tratamento para o caso em que se tem uma palavra com varias incidencias
            if(vetor_de_palavras.size() == 1){
                    
                    // Cria um no e seta a frequencia da palavra no nó
                    ArvoreNo N1 = new ArvoreNo(vetor_de_palavras.get(0).getTamanho()); 
                    
                    // Seta palavra no no
                    N1.setFrase(vetor_de_palavras.get(0).getPalavra());
                    
                    // seta o codigo do no
                    N1.setBit("1");
                    
                    // Adiciona o no na Arvore
                    Arvore.add(N1);
                    
                    
                    // cria no pai,
                    ArvoreNo NO = new ArvoreNo(N1.getFreq());
                    // seta o N1 criado acima, como esquerda da arvore
                    
                    NO.setEsquerda(N1);
                    
                    NO.setBit("0");
                    // add na arvore
                    Arvore.add(NO);
            }
            
            
            // caso haja mais de 1 palavra
            while (vetor_de_palavras.size() != 1) { 
                
                // verifica se a palavra na posicao 0 do vetor de palavras esta presente na arvore
                // && verifica se a palavra na posicao 1 do vetor de palavras esta presente na arvore
                
                
                // se nao contem palavra nem da posicao 0 e da posicao 1
                if (verificaExixte(Arvore, vetor_de_palavras.get(0).getPalavra()) == false && verificaExixte(Arvore, vetor_de_palavras.get(1).getPalavra()) == false) { //Se a Arvore n�o conter a posi��o 0 e 1 da Word
                    
                // sera necessario criar dois nós, o da direita e da esquerda
                    
                    // cria no, setando a frequencia da palavra
                    ArvoreNo N1 = new ArvoreNo(vetor_de_palavras.get(0).getTamanho()); 
                    // seta frase do no 
                    N1.setFrase(vetor_de_palavras.get(0).getPalavra()); //seta a frase do N1 com a palavra de Word na posi��o 0
                    
                    // apos a criação , este é adicionado na arvore
                    Arvore.add(N1);
                    
                    // o mesmo processo se repete para o no 2
                    
                    ArvoreNo N2 = new ArvoreNo(vetor_de_palavras.get(1).getTamanho()); 
                    N2.setFrase(vetor_de_palavras.get(1).getPalavra()); 
                    Arvore.add(N2);
                    
                    
                    // cria no pai dos 2 acima
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + N2.getFreq()); 
                    
                    // verificacao para saber para qual parte da arvore o bit vai
                    // neste caso o maior sempre recebe 0
                    
                    if (N1.getFreq() < N2.getFreq()) { 
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1");// N1 seta o bit como 1
                        NO.setDireita(N2); //seta N2 no lado direito de N0
                        N2.setBit("0"); // N2 seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // seta a frase de N0 com a concatenacao de N1 e N2
                    } 
                    
                    else { //se a frequencia de N1 > N2
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(N2);//seta N2 no lado esquerdo de N0
                        N2.setBit("1"); // N2 seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), N2.getFrase())); // seta a frase de N0 com a concatena��o de N1 e N2
                    }
                    
                    // ATRIBUTO MARCA UTILIZADO PARA IDENTIDICAR QUE O NO NAO E UMA FOLHA
                    // NECESSARIO NA CONTRUCAO DA TABELA DE FREQUENCIAS
                    
                    
                    // flag utilizada para saber se é ou nao uma folha
                    NO.setMarca(5); 
                    
                    // adiciona no na arvore
                    Arvore.add(NO); 
                    
                    // COMO NA VERIFICACAO, SEMPRE É PEGO vetor de palavras NA POSICAO 0 e 1, 
                    // E OS DADOS JA ESTAO NA ARVORE, AS DUAS SAO REMOVIDOS DO VETOR DE PALAVRAS
                    
                    vetor_de_palavras.remove(0);
                    vetor_de_palavras.remove(0); 
                    
                    // cria palavra e coloca no final do array de palavras
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); 
                    vetor_de_palavras.add(P);// adiciona o objeto em word
                    OrdenaVetor(vetor_de_palavras); // chama a fun��o OrdenaVetor

                } 
                
                
                
                else if (verificaExixte(Arvore, vetor_de_palavras.get(0).getPalavra()) == true && verificaExixte(Arvore, vetor_de_palavras.get(1).getPalavra()) == true) { //Se a Arvore conter a posi��o 0 e 1 da Word
                
                // Neste caso os as duas palavras estao presentes na arvore
                // assim nao ha a necessidade de criar novos nos
                    
                    
                    // E preciso saber onde a palavra esta, para saber a frequencia
                    // e assim criar um no, que é a soma das frequencias dos filhos
                    
                    
                    // o metodo contem uma variavel global que guarda a posicao quando a palavra é encontrada
                    verificaExixte(Arvore, vetor_de_palavras.get(0).getPalavra());
                    
                    // salva o valor em uma variavel auxiliar, pois precisa para as duas que estao sendo buscadas
                    int a = valor; 
                    
                    // Para a segunda nao precisa, pois ja esta armazenada na variavel 'vetor'
                    verificaExixte(Arvore, vetor_de_palavras.get(1).getPalavra()); 
                    
                    // Cria o no, setando usando os indices anteriores para saber as frequencias
                    ArvoreNo NO = new ArvoreNo(Arvore.get(a).getFreq() + Arvore.get(valor).getFreq());
                    
                    // verificacao para saber quem é direita/esquerda
                    if (Arvore.get(a).getFreq() < Arvore.get(valor).getFreq()) { 
                        NO.setEsquerda(Arvore.get(a)); 
                        Arvore.get(a).setBit("1");
                        NO.setDireita(Arvore.get(valor)); 
                        Arvore.get(valor).setBit("0"); 
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); 
                    } 
                    
                    else { 
                        NO.setDireita(Arvore.get(a)); 
                        Arvore.get(a).setBit("0"); 
                        NO.setEsquerda(Arvore.get(valor));
                        Arvore.get(valor).setBit("1");
                        NO.setFrase(Concatenar(Arvore.get(a).getFrase(), Arvore.get(valor).getFrase())); 
                    }
                    
                    // como o NO nao é uma folha, ele necessita da flag (usado em Cria tabela)
                    NO.setMarca(5); // NO recebe marca 5 
                    
                    // adiciona NO em Arvore
                    Arvore.add(NO); 
                    
                    // operacoes ja relaizadas, pode remover os primeiros elementos do vetor de palavras
                    vetor_de_palavras.remove(0);
                    vetor_de_palavras.remove(0); 
                    
                    // uma nova palavra é criada e adicionada no final do vetor 
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); 
                    vetor_de_palavras.add(P); // adiciona em Word
                    
                    OrdenaVetor(vetor_de_palavras); // chama funcao OrdenaVetor

                } 
                
                // se chegou ate aqui, so acha uma palavra contida na Arvore
                
                
                // se a primeira é true, entao a segunda é false
                else if (verificaExixte(Arvore, vetor_de_palavras.get(0).getPalavra()) == true) {
                    
                    //so é necessario criar um no
                    ArvoreNo N1 = new ArvoreNo(vetor_de_palavras.get(1).getTamanho());
                    N1.setFrase(vetor_de_palavras.get(1).getPalavra()); //seta a frase do N1 vetor de palavras na posicao 1
                    
                    Arvore.add(N1); // adiciona N1 na Arvore
                    
                    // cria no pai com frequencia do n1 + vet palavras na posicao valor
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); 
                    
                    
                    // verificacao para setar qual bit assume
                    // maior recebe 0
                    
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { //se a frequencia de N1 < Arvore na posicao valor
                        NO.setEsquerda(N1); //seta N1 no lado esquerdo de N0
                        N1.setBit("1"); // N1 seta o bit como 1
                        NO.setDireita(Arvore.get(valor)); //seta Arvore na posi��o valor no lado direito de N0
                        Arvore.get(valor).setBit("0"); // Arvore na posi��o valor seta o bit como 0
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    } 
                    
                    else { //se a frequencia de N1 > Arvore na posicao valor
                        NO.setDireita(N1); //seta N1 no lado direito de N0
                        N1.setBit("0"); // N1 seta o bit como 0
                        NO.setEsquerda(Arvore.get(valor)); //seta Arvore na posicao valor no lado esquerdo de N0
                        Arvore.get(valor).setBit("1"); // Arvore na posi��o valor seta o bit como 1
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); // seta a frase de N0 com a concatena��o de N1 e Arvore na posi��o valor
                    }
                    
                    // no pai recebe flag para na hora de gerar a tabela saber que ele nao é uma folha
                    NO.setMarca(5); // NO recebe marca 5 
                    
                    
                    // adiciona no na arvore
                    Arvore.add(NO); 
                    
                    //remove palavras
                    vetor_de_palavras.remove(0);
                    vetor_de_palavras.remove(0); 
                    
                    // cria palavra,    do NO e add no vetor de palavras
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); 
                    vetor_de_palavras.add(P);
                    
                    OrdenaVetor(vetor_de_palavras); // chama a funcao OrdenaVetor
                
                }
                
                // caso contrario do item anterior
                
                // agora a segunda palavra esta presente na arvore
                else if (verificaExixte(Arvore, vetor_de_palavras.get(1).getPalavra()) == true) { 
                    
                    // cria um no
                    ArvoreNo N1 = new ArvoreNo(vetor_de_palavras.get(0).getTamanho());
                    N1.setFrase(vetor_de_palavras.get(0).getPalavra());  
                    
                    // adiciona no na arvore
                    
                    Arvore.add(N1); 
                    
                    // cria no pai
                    ArvoreNo NO = new ArvoreNo(N1.getFreq() + Arvore.get(valor).getFreq()); 
                    
                    if (N1.getFreq() < Arvore.get(valor).getFreq()) { 
                        NO.setEsquerda(N1);  
                        N1.setBit("1"); 
                        NO.setDireita(Arvore.get(valor)); 
                        Arvore.get(valor).setBit("0"); 
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); 
                    } else {
                        NO.setDireita(N1); 
                        N1.setBit("0"); 
                        NO.setEsquerda(Arvore.get(valor));
                        Arvore.get(valor).setBit("1"); 
                        NO.setFrase(Concatenar(N1.getFrase(), Arvore.get(valor).getFrase())); 
                    }
                    
                    // seta flag do no para facilitar tabela
                    NO.setMarca(5); 
                    Arvore.add(NO); 
                    vetor_de_palavras.remove(0);
                    vetor_de_palavras.remove(0); 
                    
                    // cria palavra
                    Palavra P = new Palavra(NO.getFreq(), NO.getFrase()); 
                    vetor_de_palavras.add(P); 
                    OrdenaVetor(vetor_de_palavras); 
                }
            }

            // cria tabela de frequencias
            CriaTabela(Arvore); 
            
        } catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public String Concatenar(String s1, String s2) { 
        StringBuffer strBuf = new StringBuffer(); 
        strBuf.append(s1);
        strBuf.append(s2); 
        return strBuf.toString(); 
    }

    public boolean verificaExixte(ArrayList<ArvoreNo> Arvore, String palavra) { 
        for (int i = 0; i < Arvore.size(); i++) { 
            if (Arvore.get(i).getFrase().equals(palavra)) { 
                valor = i; 
                return true; 
            }
        }
        return false;// retorna false
    }

    
    // funciona como srtcpy
    public char[] StrCpy(char[] a, char[] b) { 
        for (int i = 0; i < a.length; i++) { 
            a[i] = b[i];
         }
        return a;
    }

    
    
    
    public void CriaTabela(ArrayList<ArvoreNo> Arv) throws IOException { //recebe como par�metro a ArrayList Arv
        try {
            
            // Seta o bit da raiz como 1, raiz ta na ultima posicao do vetor de palavras
            Arv.get(Arv.size() - 1).setBit("1"); 
            
            // Cria um no, que vai percorrer a arvore, e comeca na raiz
            ArvoreNo Arvore = Arv.get(Arv.size() - 1);
            
            // string auxiliar que vai recebendo o caminho do percurso(recebe a palavra contida em cada no)
            String aux; 
            
            String aux2 = null;// recebera o codigo da palavra, concatena com o aux1
            
            // comeca o percurso com o bit do no raiz
            aux = Arvore.getBit(); // pego o Bit do no raiz
            
            
            // while != 1 , pois se é -1 so tem a raiz
            while (Arv.size() != 1) { 
                
                // veridica no a esquerda da arvore e null
                if (Arvore.getEsquerda() != null) { 
                
                    if (Arvore.getEsquerda().getEsquerda() == null && Arvore.getEsquerda().getDireita() == null) { 
                        // crio no auxiliar, pois a esquerda sera removida
                        ArvoreNo no = Arvore.getEsquerda();
                        
                        // esqueda do no -> null
                        Arvore.setEsquerda(null);
                        
                        // continua a partir do auxiliar
                        Arvore = no; 
                        
                        // concatena o bit do caminho com o bit do no atual
                        aux = Concatenar(aux, Arvore.getBit());
                    } else {
                        
                        // vai para a esquerda
                        Arvore = Arvore.getEsquerda();
                        
                        // concatena o bit do caminho com o bit do no atual
                        aux = Concatenar(aux, Arvore.getBit());
                    }
                    
                } 
                
                // faz a verificacao para a direita
                else if (Arvore.getDireita() != null) {
                    // olha e esquerda da direita e a direita da direita
                    
                    if (Arvore.getDireita().getEsquerda() == null && Arvore.getDireita().getDireita() == null) {
                        
                        // No recebe direita
                        ArvoreNo no = Arvore.getDireita();
                        
                        // arvore seta direita como null
                        Arvore.setDireita(null);
                        
                        Arvore = no;//continua while a partir do no auxiliar
                        
                        aux = Concatenar(aux, Arvore.getBit());// concateno a palavra com a palavra anterior
                    }
                    
                    else {
                        //continuo a busca para a direita
                        Arvore = Arvore.getDireita();
                        // concateno a palavra com a palavra anterior
                        aux = Concatenar(aux, Arvore.getBit());
                    }
                } else {
                    
                    // a Flag que foi utilizada na criacao da arvore e utilizada aqui para que pai nao seja add na tabela
                    
                    // verifica marca
                    if (Arvore.getMarca() == 5) {
                        
                        // remove o no folha do array de NOs
                        Arv.remove(Arvore);
                        
                        // recome busca a partir da raiz
                        Arvore = Arv.get(Arv.size() - 1);
                       
                        aux = Arvore.getBit();// coloco o bit da raiz no aux
                    } else {
                        
                        // pega frase e coloca em aux 2
                        aux2 = Arvore.getFrase();
                        
                        // Remove folha do array de no
                        Arv.remove(Arvore); 
                        
                        // comeca busca a partir da raiz navamente
                        Arvore = Arv.get(Arv.size() - 1); 
                        
                        // cria um no com o codigo e a palavra do no folha
                        
                        Palavra p = new Palavra(aux2, aux);
                        
                        // add a tabela
                        Tabela.add(p);
                        
                        // bit da raiz no nó aux
                        aux = Arvore.getBit();
                    }
                }
            
                
            // escreve dados na tabela
            EscreveTabela(Tabela);
            
            // escreve dados compactados
            EscreverCompactado(Tabela);
        }
        }catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void EscreveTabela(ArrayList<Palavra> arv) throws IOException { // recebe a ArrayList arv
        
        
            try {
                
            // cria arquivo
            FileWriter arq = new FileWriter("Tabela.txt"); 
            BufferedWriter escritor = new BufferedWriter(arq);

            
            // percorre a tabela  que tem os valores os codigos
            
            for (int i = 0; i < arv.size(); i++) {
             
             // escreve codigo
                String s = arv.get(i).palavra;
                escritor.write(s); 
                escritor.flush(); 
                escritor.write(" "); 
                escritor.flush();
                
             // escreve codigo
                
                s = arv.get(i).codigo; 
                escritor.write(s); 
                escritor.flush(); 
                escritor.write(" ");
                escritor.flush();
                escritor.newLine(); 
            }
            escritor.close(); 
        } catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void EscreverCompactado(ArrayList<Palavra> arv) throws IOException, FileNotFoundException {
        
        
        try {
            //Leitura e escrita dos arquivos .txt
            FileReader arquivo = new FileReader("texto.txt");
            BufferedReader leitor = new BufferedReader(arquivo);
            FileWriter arq = new FileWriter("Compactado.txt");
            BufferedWriter escritor = new BufferedWriter(arq);

            String linha = leitor.readLine(); 
            String palavra; 
            
            if (linha == null){
                
                System.out.println("Vetor de palavras vazio");
                System.exit(0);
            }
            
            
            char[] palavra_criada = new char[99999999]; // Vetor do tipo char
            int j = 0; // inicializa j em 0
            
            
            while (linha != null) { // Enquanto linha diferente de nulo
            
                char[] aux = linha.toCharArray();
                
                char[] linhast = new char[aux.length + 1];//Vetor char que recebe a linha em caractere   
                
                for( int i = 0;i<aux.length;i++){
                    linhast[i] = aux[i];
                }
                linhast[linhast.length -1] = ' ';
                
                for (int i = 0; i < linhast.length; i++) { // Percorre o tamanho de linhast
                
                    if (linhast[i] != ' ' && i != linhast.length - 1) { // se linhast na posicao i  for diferente de espaco e do tamanho - 1
                    
                        palavra_criada[j] = linhast[i]; 
                        
                        j++; //incrementa j
                    } else {
                        
                        if (palavra_criada[j - 1] == '!' || palavra_criada[j - 1] == '?' || palavra_criada[j - 1] == '.' || palavra_criada[j - 1] == ',' || palavra_criada[j - 1] == ':' || palavra_criada[j - 1] == ';') {
                        
                            char[] palavra_definitiva = new char[j - 1]; 
                            
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 

                            // palavra recebe o vetor palavra_definitiva como String
                            palavra = String.copyValueOf(palavra_definitiva); 
                            
                            
                            // percorre tabela
                            for (int k = 0; k < arv.size(); k++) { 
                                
                                //se a palavra do arquivo for igual a palavra da tabela
                                
                                if (arv.get(k).getPalavra().equals(palavra) == true) {
                                
                                  // escreve codigo
                                    String p = arv.get(k).getCodigo(); 
                                    escritor.write(p); 
                                    escritor.flush();
                                 
                                 // imprime caractere de pontuacao
                                    escritor.write(palavra_criada[j - 1]);
                                    escritor.flush();
                                    escritor.write(" "); // escreve o espa�o 
                                    escritor.flush();// limpa o buffer
                                }
                            }
                            j = 0;   // zera o j
                            
                            
                            ResetaVetor(palavra_definitiva); // esvazia
                            ResetaVetor(palavra_criada); // vetores
                        } 
                        
                        else {
                            
                            char[] palavra_definitiva = new char[j]; 
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                            j = 0; // zera o j
                            
                            
                            palavra = String.copyValueOf(palavra_definitiva); 
                            
                            for (int k = 0; k < arv.size(); k++) { 
                                
                                if (arv.get(k).getPalavra().equals(palavra) == true) { 
                                   // escreve string
                                    String p = arv.get(k).getCodigo(); 
                                    escritor.write(p); 
                                    escritor.flush(); 
                                   // escreve " "
                                    escritor.write(" ");  
                                    escritor.flush();
                                }
                            }
                            ResetaVetor(palavra_definitiva);
                            ResetaVetor(palavra_criada); 
                        }
                    }
                }
                escritor.flush();
                escritor.newLine();
                linha = leitor.readLine();
            }
            
            escritor.close(); 
            leitor.close(); 
            
            
        } catch (FileNotFoundException e) { 
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

    public void Descompactar() throws IOException, FileNotFoundException {
       
        
        
        // RECONSTROI TABELA
        
        
        try {
            //Leitura e escrita dos arquivos .txt
            FileReader arquivo = new FileReader("Tabela.txt");
            BufferedReader leitor = new BufferedReader(arquivo);
            FileReader arquiv = new FileReader("Compactado.txt");
            BufferedReader leit = new BufferedReader(arquiv);
            FileWriter arq = new FileWriter("Descompactado.txt");
            BufferedWriter escritor = new BufferedWriter(arq);
            
            // limpa array de tabelas
            Tabela = new ArrayList<>();
            
            String linha = leitor.readLine(); 
            
            if (linha == null){
                System.out.println("Vetor de palavras vazio");
                System.exit(0);
            }
            
            char[] palavra_criada = new char[99999999];
            String palavra;
            // variaveis auxiliares
            
            int j = 0, indice = 0;  
            
            while (linha != null) {
                
                char[] linhast = linha.toCharArray();
                
                for (int i = 0; i < linhast.length; i++) { //percorro meu vetor char
                    if (linhast[i] != ' ' && i != linhast.length - 1) { // se linhast na posicao i  for diferente de espaco e do tamanho - 1
                    
                        palavra_criada[j] = linhast[i]; //coloco cada letra lida em um outro vetor auxiliar
                        
                        j++; // incremento o j
                    } else {
                        // palavra encontrada
                        indice++;// incremento o indice
                        
                        // crio um novo vetor do tamanho de minha palavra 
                        char[] palavra_definitiva = new char[j];
                        
                        palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                        
                        j = 0;// recomeca contador do vetor auxiliar referente a palavra
                        
                        palavra = String.copyValueOf(palavra_definitiva);// transformo meu vetor de char em uma String
                        
                        if (indice == 1) {// se indice igual a 1 eu li primeira palavra
                        
                            // preguica de criar construtor
                            Palavra p = new Palavra(palavra, " ");
                            
                            Tabela.add(0, p);// adiciono ele sempre no inicio da minha ArrayList
                        } 
                        
                        // se entra -> leu o codigo
                        else {
                            Tabela.get(0).setCodigo(palavra);// seto o codigo lido naquele contrutor criado acima
                        
                            indice = 0;// indice recebe 0
                        }
                        
                        ResetaVetor(palavra_definitiva);
                        ResetaVetor(palavra_criada);
                    }
                }
                
                // proxima linha do arquivo
                linha = leitor.readLine();
                
            }
            
            
            // le arquivo compactado
            linha = leit.readLine();
            
            if (linha == null){
                System.out.println("Vetor de palavras vazio");
                System.exit(0);
            }
            
            j = 0;

            while (linha != null) { 
                
                //transformo a String lida em vetor de char 
                char[] linhast = linha.toCharArray(); 
                
                for (int i = 0; i < linhast.length; i++) {
                    
                    if (linhast[i] != ' ' && i != linhast.length - 1) { 
                        palavra_criada[j] = linhast[i];
                        j++;// incremento o j
                    }
                    
                    else {// se entrar aqui achei uma palavra
                    
                        if (palavra_criada[j - 1] == '!' || palavra_criada[j - 1] == '?' || palavra_criada[j - 1] == '.' || palavra_criada[j - 1] == ',' || palavra_criada[j - 1] == ':' || palavra_criada[j - 1] == ';') {
                        
                            char[] palavra_definitiva = new char[j - 1]; 
                            
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                            
                            // transforma vetor de char em uma String
                            palavra = String.copyValueOf(palavra_definitiva);
                            
                            
                            // percorre tabela
                            for (int k = 0; k < Tabela.size(); k++) { 
                            
                                if (Tabela.get(k).getCodigo().equals(palavra)) {
                                    escritor.write(Tabela.get(k).getPalavra());
                                    escritor.flush(); 
                                    escritor.write(palavra_criada[j - 1]);
                                    escritor.flush(); 
                                    escritor.write(" ");
                                }
                            }
                            j = 0;
                            
                            ResetaVetor(palavra_definitiva);
                            ResetaVetor(palavra_criada);
                        } 
                        
                        else {
                        
                            char[] palavra_definitiva = new char[j];// crio um novo vetor do tamanho de minha palavra 
                            
                            palavra_definitiva = StrCpy(palavra_definitiva, palavra_criada); 
                            j = 0;
                            
                            palavra = String.copyValueOf(palavra_definitiva);
                            
                            for (int k = 0; k < Tabela.size(); k++) { 
                            
                                // se o bit lido do Compactado.txt for igual ao da tabela na posi��o k
                                if (Tabela.get(k).getCodigo().equals(palavra)) {
                                    
                                    // escrevo a palavra referente a aquele Bit
                                    escritor.write(Tabela.get(k).getPalavra());
                                    //esvazio o buffer 
                                    escritor.flush(); 
                                    // escrevo um espaco
                                    escritor.write(" ");
                                    // esvazio o buffer
                                    escritor.flush(); 
                                }
                            }
                            
                            ResetaVetor(palavra_definitiva);
                            ResetaVetor(palavra_criada);
                        }
                    }
                }
                escritor.newLine();
                
                // proxima linha do compactado
                linha = leit.readLine(); 
            }
        } catch (FileNotFoundException e) { 
            System.out.println("Arquivo não encontrado!");
        } catch (IOException i) { 
            System.out.println("Erro na leitura arquivo!");
        }
    }

}