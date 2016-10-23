package com.example.psiquemap.psiquemap;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Sintomas extends AppCompatActivity {

    private AutoCompleteTextView txtSintomas;
    private Button btnAdicionar;
    private Button btnSalvar;
    private ListView lstSintomas;

    private String nomeSintoma;

    private ArrayAdapter<String> adpTodosSistomas;
    private ArrayAdapter<String> adpSistomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSintomas = (AutoCompleteTextView) findViewById(R.id.txtSintomas);
        lstSintomas = (ListView) findViewById(R.id.lstSintomas);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        adpTodosSistomas = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        adpSistomas = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

        adpTodosSistomas.add("Angustia");
        adpTodosSistomas.add("Aflição");
        adpTodosSistomas.add("Dor de cabeça");

        AutoCompleteTextView txtSintomas = (AutoCompleteTextView) findViewById(R.id.txtSintomas);
        txtSintomas.setAdapter(adpTodosSistomas);

        txtSintomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nomeSintoma = adpTodosSistomas.getItem(position);
            }
        });

        lstSintomas.setAdapter(adpSistomas);

    }

    public void adicionar (View viem)
    {


        if (nomeSintoma != null)
        {
            boolean sintomaAdd = false;

            for(int i=0;i<adpSistomas.getCount();i++)
            {
                if(adpSistomas.getItem(i).equals(nomeSintoma))
                {
                    sintomaAdd = true;
                    break;
                }
            }

            if (sintomaAdd)
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
                adpSistomas.add(nomeSintoma);
                txtSintomas.setText("");
                nomeSintoma = null;
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

    public void salvarSintomas(View view)
    {
        finish();
    }

}
