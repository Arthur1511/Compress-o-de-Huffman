package aeds2;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.in;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        Scanner entrada = new Scanner(System.in);
        System.out.println("Menu:\n1 -> Codificar\n2 -> Decodificar\n0 -> Sair");
        int op = entrada.nextInt();
        OperacoesHuffman h = new OperacoesHuffman();
        while (op != 0) {
            switch (op) {
                case 1:
                    h.criaPalavras();
                    System.out.println("\nCompactando\n");
                    break;
                case 2:
                    h.Descompactar();
                    System.out.println("\nDescompactando\n");
                    break;
                default:
                    System.out.print("Opcao invalida");
                    break;

            }
            System.out.println("Menu:\n1 -> Codificar\n2 -> Decodificar\n0 -> Sair");
            op = entrada.nextInt();
        }
    }

}
