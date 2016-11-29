package com.example.psiquemap.psiquemap.entidades;

public class Dados 
{
	protected int flagPaciente;
	protected Paciente paciente;

	public Dados() {

	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public int getFlagPaciente() {
		return flagPaciente;
	}

	public void setFlagPaciente(int flagPaciente) {
		this.flagPaciente = flagPaciente;
	}
	

}
