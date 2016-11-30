package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import com.example.psiquemap.psiquemap.entidades.Sintoma;
import com.example.psiquemap.psiquemap.entidades.SintomaSentido;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Sintomas;
import com.example.psiquemap.psiquemap.sql.SintomasSentidos;

public class SintomasScreen extends AppCompatActivity {

    private AutoCompleteTextView txtSintomas;
    private Button btnAdicionar;
    private ListView lstSintomas;

    private String nomeSintoma;

    private ArrayAdapter<String> adpTodosSistomas;
    private ArrayAdapter<String> adpSistomasSentidos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Sintomas sintomas;
    private SintomasSentidos sintomasSentidos;

    public static Context getApplicationContext;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.txtSintomas = (AutoCompleteTextView) findViewById(R.id.txtSintomas);
        this.lstSintomas = (ListView) findViewById(R.id.lstSintomas);
        this.btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        this.txtSintomas = (AutoCompleteTextView) findViewById(R.id.txtSintomas);
        getApplicationContext = getApplicationContext();
        thisContext = this;

        if(this.conexaoBD())
        {
            this.sintomas = new Sintomas(this.conn);
            this.sintomasSentidos = new SintomasSentidos(this.conn);

            this.adpTodosSistomas = this.sintomas.getTodosSintomas(this);
            this.adpSistomasSentidos = this.sintomasSentidos.getSintomasSentidos(this);

            txtSintomas.setAdapter(this.adpTodosSistomas);


            txtSintomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nomeSintoma = adpTodosSistomas.getItem(position);
                }
            });

            lstSintomas.setAdapter(adpSistomasSentidos);

        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
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

    public void adicionar (View viem)
    {


        if (nomeSintoma != null)
        {

            Sintoma sintoma = sintomas.getSintomaPorNome(this.nomeSintoma);

            if (sintomasSentidos.hasSintoma(sintoma))
            {
                txtSintomas.setText("");
                nomeSintoma = null;
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Esse sintoma já foi adicionado!");
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }
            else
            {
                SintomaSentido sintomaSentido = new SintomaSentido(sintoma.getIdCategoria(),sintoma.getIdSintoma(),sintoma.getNome());
                SintomasSentidos sintomasSentidos = new SintomasSentidos(this.conn);

                sintomasSentidos.insert(sintomaSentido);

                Controles.setFlagSintomas(MetodosEmComum.getIdPaciente(this),1);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                atualizarTela();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                finish();
                                break;
                        }
                    }
                };

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Deseja adicionar outro sintoma?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener);
                dlg.show();
            }
        }
        else
        {
            txtSintomas.setText("");
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Escolha um dos sintomas que consta na lista!");
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    public void atualizarTela()
    {
        Intent it = new Intent(this, SintomasScreen.class);
        startActivityForResult(it, 0);
        finish();
    }

}
