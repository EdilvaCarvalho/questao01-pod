package br.edu.ifpb.pod.quest1.pod.node3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 *
 * @author Edilva
 */
public class Node3 {

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
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        String n = input.readUTF();
        String num[] = n.split(Pattern.quote(","));
        int num1 = Integer.parseInt(num[0]);
        int num2 = Integer.parseInt(num[1]);
        if (num1 != num2) {
            System.out.println("Números foram recebidos...");
            System.out.println("Os números " + num1 + " e " + num2 + " foram recebidos!");
            double resultado = Math.pow(num1, num1) + Math.pow(num2, num2);
            System.out.println("Resultado: " + resultado);
            input.close();
        } else {
            input.close();
        }

        //Fechar socket de comunicação entre servidor/cliente
        socket.close();
    }

}
