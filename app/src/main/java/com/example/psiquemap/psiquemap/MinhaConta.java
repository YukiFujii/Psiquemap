package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Paciente;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Pacientes;

public class MinhaConta extends AppCompatActivity {

    private EditText editRua;
    private EditText editNumero;
    private EditText editCep;
    private EditText editEmail;
    private EditText editTelefone;
    private EditText editSenha;
    private EditText editConfirmarSenha;
    private Paciente paciente;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Pacientes pacientes;

    private Button btnSalvar;

    public static Context getApplicationContext;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editRua = (EditText) findViewById(R.id.editRua);
        editNumero = (EditText) findViewById(R.id.editNumero);
        editCep = (EditText) findViewById(R.id.editCep);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfirmarSenha = (EditText) findViewById(R.id.editConfirmarSenha);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        getApplicationContext = getApplicationContext();
        thisContext = this;

        if(conexaoBD())
        {
            pacientes = new Pacientes(conn);
            paciente = pacientes.getPaciente();

            this.preencherCampos();
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao carregar informações!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public void salvar (View view){

        if(camposOk())
        {
            this.updateCampos();
            Paciente noBanco = pacientes.getPaciente();

            if(!noBanco.equals(this.paciente))
            {
                this.pacientes.update(this.paciente);
                Controles.setFlagPaciente(this.paciente.getId(),1);
                Log.i("FlagPaciente","alterado");
            }

            finish();
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao salvar! Por favor, confira os dados de todos os campos e tente novamente.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    private void updateCampos()
    {
        paciente.setRua(editRua.getText().toString());
        paciente.setNumero(Integer.parseInt(editNumero.getText().toString()));
        paciente.setCep(editCep.getText().toString());
        paciente.setEmail(editEmail.getText().toString());
        paciente.setTelefone(editTelefone.getText().toString());
        paciente.setSenha(editSenha.getText().toString());
    }
    private boolean camposOk()
    {
        if (!(editSenha.getText().toString().equals(editConfirmarSenha.getText().toString()))) {
            this.editSenha.setText("");
            this.editConfirmarSenha.setText("");
            return false;
        }

        if(editRua.getText().toString().equals(""))
            return false;

        if(editNumero.getText().toString().equals(""))
            return false;

        if(editCep.getText().toString().equals(""))
            return false;

        if(editEmail.getText().toString().equals(""))
            return false;

        if(editTelefone.getText().toString().equals(""))
            return false;

        if(editSenha.getText().toString().equals(""))
            return false;

        if(editConfirmarSenha.getText().toString().equals(""))
            return false;

        return true;
    }

    private void preencherCampos()
    {
        this.editRua.setText(paciente.getRua());
        this.editNumero.setText(paciente.getNumero()+"");
        this.editCep.setText(paciente.getCep());
        this.editEmail.setText(paciente.getEmail());
        this.editTelefone.setText(paciente.getTelefone());
        this.editSenha.setText(paciente.getSenha());
        this.editConfirmarSenha.setText(paciente.getSenha());
    }
}
