package br.edu.ifpb.pod.quest1.pod.node1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Edilva
 */
public class Node1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // 1 - Estabelecer conexão com o Servidor
        // 2 - Trocar mensagem com o Servidor

        //Cria a conexão entre o cliente e o servidor
        System.out.println("Estabelecendo conexão...");
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Conexão estabelecida.");

        //Criação dos streams de entrada e saída
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        Random gerador = new Random();

        System.out.println("Enviando os dois números...");
        int num1 = gerador.nextInt(100);
        int num2 = gerador.nextInt(100);
        String n = num1 + "," + num2;
        output.writeUTF(n);
        output.flush(); // libera buff para envio
        System.out.println("Os números " + num1 + " e " + num2 + " foram enviados.");

        n = input.readUTF();
        if("0".equals(n)){
            System.out.println("Resposta: " + n);
        }

        //Fechar strems de entrada e saída
        output.close();
        input.close();

        //Fechar socket de comunicação entre servidor/cliente
        socket.close();
    }

}
