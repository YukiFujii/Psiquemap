package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 23/10/16.
 */

public class Paciente
{
    private String id;
    private String nomeCompleto;
    private String rua;
    private int numero;
    private String cep;
    private String dataNasc;
    private String email;
    private String rg;
    private String cpf;
    private String telefone;
    private String senha;
    private String cns;

    public Paciente(String id,String nomeCompleto, String rua, int numero, String cep, String dataNasc,
                    String email, String rg, String cpf, String telefone, String senha, String cns)
    {
        this.setId(id);
        this.setNomeCompleto(nomeCompleto);
        this.setRua(rua);
        this.setNumero(numero);
        this.setCep(cep);
        this.setDataNasc(dataNasc);
        this.setEmail(email);
        this.setRg(rg);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setSenha(senha);
        this.setCns(cns);
    }

    public Paciente() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }
}
