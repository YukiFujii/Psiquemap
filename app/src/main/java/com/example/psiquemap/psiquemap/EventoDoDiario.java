package com.example.psiquemap.psiquemap;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.psiquemap.psiquemap.entidades.Evento;

public class EventoDoDiario extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spnEvento;
    private EditText editTitulo;
    private EditText editDescricao;
    private Button btnSalvarEvento;
    private String classificacaoEvento="";
    private String[] nomeSentimentos={"--------","Confiante","De coração partido","Em paz","Muito feliz","Tranquilo","Triste"};
    private int emojis[] = {R.drawable.branco,R.drawable.confiante,R.drawable.de_coracao_partido,R.drawable.em_paz,R.drawable.muito_feliz, R.drawable.tranquilo, R.drawable.triste};
    private int spnPosicao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_do_diario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnEvento = (Spinner)findViewById(R.id.spnEvento);
        editTitulo = (EditText)findViewById(R.id.editTitulo);
        editDescricao = (EditText)findViewById(R.id.editDescricao);
        btnSalvarEvento = (Button)findViewById(R.id.btnSalvarEvento);

        spnEvento.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),emojis,nomeSentimentos);
        spnEvento.setAdapter(customAdapter);

    }

    public void salvarEvento(View view)
    {
        if(this.validacaoDeCampos())
        {
            Evento evento = new Evento(this.classificacaoEvento,this.editDescricao.getText().toString());

            //guardar acontecimento no BD

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            classificacaoEvento = "";
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
            dlg.setMessage("Deseja adicionar outro evento?").setPositiveButton("Sim",dialogClickListener).setNegativeButton("Não",dialogClickListener);
            dlg.show();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("ERRO! Verifique se todos os campos foram preenchidos.");
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    public boolean validacaoDeCampos()
    {
        boolean ret;

        classificacaoEvento = this.nomeSentimentos[this.spnPosicao];

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
