package com.example.psiquemap.psiquemap.entidades;

public class Login {
	
	protected String email;
	protected String senha;

	public Login() 
	{
	
	}

	public Login(String email,String senha) 
	{
		this.setEmail(email);
		this.setSenha(senha);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
