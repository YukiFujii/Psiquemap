package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.psiquemap.psiquemap.entidades.Acontecimento;
import com.example.psiquemap.psiquemap.sql.Acontecimentos;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;

public class AcontecimentoDoDiario extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spnAcontecimento;
    private EditText editTitulo;
    private EditText editDescricao;
    private Button btnSalvarEvento;
    private String sentimentoDoAcontecimento="";
    private String[] nomeSentimentos={"Confiante","De coração partido","Em paz","Muito feliz","Tranquilo","Triste"};
    private int emojis[] = {R.drawable.confiante,R.drawable.de_coracao_partido,R.drawable.em_paz,R.drawable.muito_feliz, R.drawable.tranquilo, R.drawable.triste};
    private int spnPosicao = 0;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Acontecimentos acontecimentos;

    public static Context getApplicationContext;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_do_diario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.spnAcontecimento = (Spinner)findViewById(R.id.spnEvento);
        this.editTitulo = (EditText)findViewById(R.id.editTitulo);
        this.editDescricao = (EditText)findViewById(R.id.editDescricao);
        this.btnSalvarEvento = (Button)findViewById(R.id.btnSalvarEvento);

        this.spnAcontecimento.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),this.emojis,this.nomeSentimentos);
        this.spnAcontecimento.setAdapter(customAdapter);

        getApplicationContext = getApplicationContext();
        thisContext = this;

    }

    public void salvarAcontecimento(View view)
    {
        if(this.validacaoDeCampos())
        {
            if(this.conexaoBD())
            {
                acontecimentos = new Acontecimentos(conn);

                Acontecimento acontecimento = new Acontecimento(this.sentimentoDoAcontecimento, this.editTitulo.getText().toString(), this.editDescricao.getText().toString());

                acontecimentos.insert(acontecimento);

                Controles.setFlagAcontecimento(MetodosEmComum.getIdPaciente(this),1);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                sentimentoDoAcontecimento = "";
                                editTitulo.setText("");
                                editDescricao.setText("");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                finish();
                                break;
                        }
                    }
                };

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Deseja adicionar outro acontecimento?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
                dlg.show();
            }
            else
            {
                android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
                dlg.setMessage("Erro ao conectar com banco!");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("ERRO! Verifique se todos os campos foram preenchidos.");
            dlg.setNeutralButton("OK",null);
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

    public boolean validacaoDeCampos()
    {
        boolean ret;

        sentimentoDoAcontecimento = this.nomeSentimentos[this.spnPosicao];

        if(this.editDescricao.getText().toString().equals(""))
            ret = false;
        else
            ret = true;

        return ret;
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id)
    {
        Log.i("Sentimento",nomeSentimentos[position]);

        this.spnPosicao = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
