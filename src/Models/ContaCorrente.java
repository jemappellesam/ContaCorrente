package Models;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ContaCorrente {
    private String titular;
    private double saldo;
    private String cpf;
    private String cartao;
    private String senha;
    private double cheque_especial;
    private double limiteChequeEspecial;
    private double limite_cartao;
    private int numero;
    private String agencia;
    private String[] chave_pix;
    private double juros_cheque = .2;

    private double valorEmJuizo=1.20;
    private List<String> extrato = new ArrayList<>();

    public ContaCorrente(String titular, String cpf, String senha) {
        this.titular = titular;
        this.cpf = cpf;
        this.senha = senha;
        this.saldo = 0;
        this.cheque_especial = 0;
        this.limite_cartao = 0;

        this.agencia = "0001";
        this.numero = (1000000 % new Random().nextInt()) + 1000;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void depositar(double valor) {
        if (this.saldo < 0) {
            valor += this.saldo * (this.juros_cheque);
            this.cheque_especial += (valor - (this.saldo * -1));
        }

        this.saldo += valor-valorEmJuizo;
        RegistrarExtrato();
    }

    public void sacar(double valor) {
        // verificar se o saldo + cheque permite o saque
        if (this.saldo + this.cheque_especial >= valor) {
            if (this.saldo < valor) {
                this.cheque_especial -= (valor - this.saldo);
            }

            this.saldo -= valor;
        } else {
            System.out.println("Nao pode sacar");
        }
        RegistrarExtrato();
    }

    public void transferir(String agencia, int conta, double valor) {
        if (this.saldo + this.cheque_especial >= valor) {
            if (this.saldo < valor) {
                this.cheque_especial -= (valor - this.saldo);
            }

            this.saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente para transferência");
        }
        RegistrarExtrato();
    }

    public void transferirPix(String pix, double valor) {
        if (this.saldo + this.cheque_especial >= valor) {
            if (this.saldo < valor) {
                this.cheque_especial -= (valor - this.saldo);
            }

            this.saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente para pix");
        }
        RegistrarExtrato();
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public double getCheque_especial() {
        return cheque_especial;
    }

    @Override
    public String toString() {
        return String.format("Titular: %s Agência: %s Conta: %s", this.titular, this.agencia, this.numero);
    }
    public String getTitutlar(){
        return this.titular+" "+"["+ this.cpf.substring(0,3)+"."+this.cpf.substring(3,6)+"."+this.cpf.substring(6,9)+"-"+this.cpf.substring(9)+"]";
    }

    public void RegistrarExtrato(){
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String data=dtf.format(now);
        this.extrato.add(methodName + " "+data);
    }
    public String PrintExtrato(){
        return this.extrato.toString();
    }

}