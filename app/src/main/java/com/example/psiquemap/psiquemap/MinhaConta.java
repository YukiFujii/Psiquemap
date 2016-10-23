package com.example.psiquemap.psiquemap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MinhaConta extends AppCompatActivity {

    private EditText editRua;
    private EditText editNumero;
    private EditText editCep;
    private EditText editEmail;
    private EditText editTelefone;
    private EditText editSenha;
    private EditText editConfirmarSenha;

    private Button btnSalvar;

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

    }

    public void salvar (View view){
        if (editSenha.getText().toString().equals(editConfirmarSenha.getText().toString()))
            finish();
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("ERRO! Confirmação de senha está diferente.");
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }
}
