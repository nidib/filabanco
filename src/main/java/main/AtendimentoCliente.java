package main;

import javax.swing.*;
import java.time.LocalDateTime;

public class AtendimentoCliente {

    private String idCliente;

    private LocalDateTime chegouNoBanco;

    private LocalDateTime iniciouAtendimento;

    private LocalDateTime saiuDoBanco;

    public AtendimentoCliente(
            String idCliente,
            LocalDateTime chegouNoBanco,
            LocalDateTime iniciouAtendimento,
            LocalDateTime saiuDoBanco
    ) {
        this.idCliente = idCliente;
        this.chegouNoBanco = chegouNoBanco;
        this.iniciouAtendimento = iniciouAtendimento;
        this.saiuDoBanco = saiuDoBanco;
    }

    public void setIniciouAtendimento(LocalDateTime iniciouAtendimento) {
        this.iniciouAtendimento = iniciouAtendimento;
    }

    public void setSaiuDoBanco(LocalDateTime saiuDoBanco) {
        this.saiuDoBanco = saiuDoBanco;
    }

    public LocalDateTime getChegouNoBanco() {
        return chegouNoBanco;
    }

    public LocalDateTime getIniciouAtendimento() {
        return iniciouAtendimento;
    }

    public LocalDateTime getSaiuDoBanco() {
        return saiuDoBanco;
    }
}
