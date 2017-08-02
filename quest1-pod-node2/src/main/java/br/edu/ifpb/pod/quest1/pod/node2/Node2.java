package br.edu.ifpb.pod.quest1.pod.node2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Edilva
 */
public class Node2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Node2 servidor = new Node2();
            System.out.println("Aguardando conexão...");
            servidor.criarServerSocket(5555);
            Socket node1 = servidor.esperaConexao();//protocolo
            System.out.println("Node1 conectado.");
            Socket node3 = servidor.esperaConexao();//protocolo
            System.out.println("Node3 conectado.");
            servidor.tratarConexao(node1, node3);
        } catch (IOException ex) {
            Logger.getLogger(Node2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ServerSocket serverSocket;

    /**
     * Cria o servidor de conexão
     *
     * @param porta
     * @throws IOException
     */
    private void criarServerSocket(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    /**
     * Esperar um pedido da conexão
     *
     * @return
     * @throws IOException
     */
    private Socket esperaConexao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void tratarConexao(Socket node1, Socket node3) throws IOException {

        // Cliente ----- Socket ----- Servidor
        //protocolo da aplicação
        /**
         * Criar stream da entrada e da saída Tratar a conversação entre cliente
         * e servidor (tratar protocolo)
         */
        try {
            //Criar stream da entrada e da saída
            ObjectOutputStream output = new ObjectOutputStream(node1.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(node1.getInputStream());

            /*Protocolo
             * Cliente -----> HELLO
             * Servidor <----- HELLO WORD
             */
            //Tratar a conversação entre cliente e servidor (tratar protocolo)
            String n = input.readUTF();
            System.out.println("Números foram recebidos...");
            String num[] = n.split(Pattern.quote(","));
            int num1 = Integer.parseInt(num[0]);
            int num2 = Integer.parseInt(num[1]);
            System.out.println("Os números " + num1 + " e " + num2 + " foram recebidos!");
            if (num1 == num2) {
                output.writeUTF("0");
                output.flush();
            } else {
                output.writeUTF("1");
                output.flush();
            }

            ObjectOutputStream output2 = new ObjectOutputStream(node3.getOutputStream());

            try {
                output2.writeUTF(n);
                output2.flush();
            } catch (IOException ex) {
                //Tratamento de falhas
                System.out.println("Problema no tratamento da conexão com o cliente:" + node3.getInetAddress());
                System.out.println("Erro: " + ex.getMessage());
            } finally {
                //final do tratamento do protocolo
                //Fechar socket de comunicação entre servidor/cliente
                fechaSocket(node3);
                System.out.println("Node3 finalizado.");
            }

            //Fechar strems de entrada e saída
            output.close();
            input.close();
            output2.close();

        } catch (IOException ex) {
            //Tratamento de falhas
            System.out.println("Problema no tratamento da conexão com o cliente:" + node1.getInetAddress());
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            //final do tratamento do protocolo
            //Fechar socket de comunicação entre servidor/cliente
            fechaSocket(node1);
            System.out.println("Node1 finalizado.");
        }

    }

    private void fechaSocket(Socket socket) throws IOException {
        socket.close();
    }
}
