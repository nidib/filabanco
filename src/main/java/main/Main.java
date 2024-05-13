package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    private static final long UNIDADE_TEMPO = 1000;
    private static final int QTD_ATENDENTES = 2;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<AtendimentoCliente> fila = new ArrayList<>();
        ArrayList<AtendimentoCliente> atendidos = new ArrayList<>();
        LocalDateTime horaFechamento = LocalDateTime.from(LocalDateTime.now()).plusSeconds(10);
        PortaDoBanco portaDoBanco = new PortaDoBanco(fila, UNIDADE_TEMPO, horaFechamento);

        Atendente[] atendentes = new Atendente[QTD_ATENDENTES];
        for (int i = 0; i < QTD_ATENDENTES; i++) {
            atendentes[i] = new Atendente(i + 1, fila, atendidos, UNIDADE_TEMPO, horaFechamento);
        }

        portaDoBanco.start();
        for (int i = 0; i < QTD_ATENDENTES; i++) {
            atendentes[i].start();
        }
        portaDoBanco.join();
        for (int i = 0; i < QTD_ATENDENTES; i++) {
            atendentes[0].join();
            System.out.println("Atendente " + (i + 1) + " tchau galera");
        }

        System.out.println("Clientes atendidos: " + atendidos.size());

        Duration tempoDeEsperaMaximo = Duration.between(atendidos.get(0).getChegouNoBanco(), atendidos.get(0).getSaiuDoBanco());
        for (AtendimentoCliente cliente : atendidos) {
            Duration tempo = Duration.between(cliente.getChegouNoBanco(), cliente.getSaiuDoBanco());
            if (tempo.compareTo(tempoDeEsperaMaximo) > 0) {
                tempoDeEsperaMaximo = tempo;
            }
        }
        System.out.println("Tempo máximo de espera: " + tempoDeEsperaMaximo.toMillis());

        Duration tempoDeAtendimentoMaximo = Duration.between(atendidos.get(0).getIniciouAtendimento(), atendidos.get(0).getSaiuDoBanco());
        for (AtendimentoCliente cliente : atendidos) {
            Duration tempo = Duration.between(cliente.getIniciouAtendimento(), cliente.getSaiuDoBanco());
            if (tempo.compareTo(tempoDeAtendimentoMaximo) > 0) {
                tempoDeAtendimentoMaximo = tempo;
            }
        }
        System.out.println("Tempo máximo de atendimento: " + tempoDeAtendimentoMaximo.toMillis());

        long tempoMedioDentroDoBanco = 0;
        for (AtendimentoCliente cliente : atendidos) {
            long tempo = Duration.between(cliente.getChegouNoBanco(), cliente.getSaiuDoBanco()).toMillis();
            tempoMedioDentroDoBanco += tempo;
        }
        tempoMedioDentroDoBanco = tempoMedioDentroDoBanco / atendidos.size();
        System.out.println("Tempo médio dentro do banco: " + tempoMedioDentroDoBanco);

        long tempoMedioEsperaNaFila = 0;
        for (AtendimentoCliente cliente : atendidos) {
            long tempo = Duration.between(cliente.getChegouNoBanco(), cliente.getIniciouAtendimento()).toMillis();
            tempoMedioEsperaNaFila += tempo;
        }
        tempoMedioEsperaNaFila = tempoMedioEsperaNaFila / atendidos.size();
        System.out.println("Tempo médio de espera na fila: " + tempoMedioEsperaNaFila);
    }

}
