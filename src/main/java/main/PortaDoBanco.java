package main;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class PortaDoBanco extends Thread {

    private final int tempoMinimo = 5;

    private final int tempoMaximo = 50;

    private final long unidadeTempo;

    private final ArrayList<AtendimentoCliente> fila;

    private final LocalDateTime horaFechamento;

    public PortaDoBanco(ArrayList<AtendimentoCliente> fila, long unidadeTempo, LocalDateTime horaFechamento) {
        this.fila = fila;
        this.unidadeTempo = unidadeTempo;
        this.horaFechamento = horaFechamento;
    }

    @Override
    public void run() {
        this.mostraTamanhoDaFila();
        this.mostraTamanhoDaFila();
        SecureRandom secureRandom = new SecureRandom();


        while (this.horaFechamento.isAfter(LocalDateTime.now())) {
            long tempoParaProximoCliente = secureRandom.nextLong(this.tempoMinimo, tempoMaximo + 1L);

            try {
                long tempoEmMilliParaProxCliente = tempoParaProximoCliente * (1000L / this.unidadeTempo);
                Thread.sleep(tempoEmMilliParaProxCliente);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            AtendimentoCliente atendimentoCliente = new AtendimentoCliente(
                    UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    null,
                    null
            );
            synchronized (this.fila) {
                this.fila.add(atendimentoCliente);
                this.mostraTamanhoDaFila();
            }
        }
    }

    private void mostraTamanhoDaFila() {
        System.out.println("Porta: Fila com " + this.fila.size() + " clientes");
    }
}
