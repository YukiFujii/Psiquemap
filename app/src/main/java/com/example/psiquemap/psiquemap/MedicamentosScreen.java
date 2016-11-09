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

import com.example.psiquemap.psiquemap.entidades.Medicamento;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Medicamentos;

public class MedicamentosScreen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lstMedicamentos;
    private ArrayAdapter<Medicamento> adpMedicamentos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Medicamentos medicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstMedicamentos = (ListView)findViewById(R.id.lstMedicamentos);

        if(this.conexaoBD())
        {
            medicamentos = new Medicamentos(this.conn);
            adpMedicamentos = medicamentos.getTodosMedicamentos(this);
            lstMedicamentos.setAdapter(adpMedicamentos);

            lstMedicamentos.setOnItemClickListener(this);
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
        Medicamento medicamento = adpMedicamentos.getItem(position);

        Intent it = new Intent(this, DetalheMedicamento.class);
        it.putExtra("MEDICAMENTO",medicamento);

        startActivityForResult(it,0);
        finish();
    }

}
