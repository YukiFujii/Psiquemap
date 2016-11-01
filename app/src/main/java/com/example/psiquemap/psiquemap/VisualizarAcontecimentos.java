package com.example.psiquemap.psiquemap;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.psiquemap.psiquemap.entidades.Acontecimento;
import com.example.psiquemap.psiquemap.entidades.Medicamento;
import com.example.psiquemap.psiquemap.sql.Acontecimentos;
import com.example.psiquemap.psiquemap.sql.DataBase;

public class VisualizarAcontecimentos extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView lstAcontecimentos;
    private ArrayAdapter<Acontecimento> adpAcontecimentos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Acontecimentos acontecimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_acontecimentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.lstAcontecimentos = (ListView)findViewById(R.id.lstAcontecimentos);

        if(this.conexaoBD())
        {
            this.acontecimentos = new Acontecimentos(conn);

            this.adpAcontecimentos = this.acontecimentos.getAcontecimentos(this);
            this.lstAcontecimentos.setAdapter(this.adpAcontecimentos);

            this.lstAcontecimentos.setOnItemClickListener(this);
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

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Acontecimento acontecimento = this.adpAcontecimentos.getItem(position);

        Intent it = new Intent(this, DetalheAcontecimento.class);
        it.putExtra("ACONTECIMENTO",acontecimento);
        startActivityForResult(it,0);;
    }

}
