package main;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Atendente extends Thread {

    private final int id;

    private final int tempoMinimo = 30;

    private final int tempoMaximo = 120;

    private final ArrayList<AtendimentoCliente> fila;

    private final ArrayList<AtendimentoCliente> atendidos;

    private final long unidadeTempo;

    private final LocalDateTime horaFechamento;

    public Atendente(int id, ArrayList<AtendimentoCliente> fila, ArrayList<AtendimentoCliente> atendidos, long unidadeTempo, LocalDateTime horaFechamento) {
        this.id = id;
        this.fila = fila;
        this.atendidos = atendidos;
        this.unidadeTempo = unidadeTempo;
        this.horaFechamento = horaFechamento;
    }

    @Override
    public void run() {
        System.out.println("Atendente " + this.id + " pronta...");
        SecureRandom secureRandom = new SecureRandom();

        while (!Thread.currentThread().isInterrupted()) {
            if (this.getTerminou()) {
                break;
            }

            long tempoDeAtendimento = secureRandom.nextLong(this.tempoMinimo, this.tempoMaximo + 1L);

            AtendimentoCliente proximoCliente = null;
            synchronized (this.fila) {
                if (!this.fila.isEmpty()) {
                    this.mostraTamanhoDaFila();
                    proximoCliente = this.fila.remove(0);
                }
            }

            if (proximoCliente != null) {
                proximoCliente.setIniciouAtendimento(LocalDateTime.now());
                try {
                    Thread.sleep(tempoDeAtendimento * (1000L / this.unidadeTempo));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                proximoCliente.setSaiuDoBanco(LocalDateTime.now());
                this.atendidos.add(proximoCliente);
            }
        }
    }

    private void mostraTamanhoDaFila() {
        System.out.println("Atendente " + this.id + ": Fila com " + this.fila.size() + " clientes");
    }

    public boolean getTerminou() {
        boolean fechouOBanco = LocalDateTime.now().isAfter(this.horaFechamento);
        if (fechouOBanco && this.fila.isEmpty()) {
            return true;
        }

        return false;
    }

}
