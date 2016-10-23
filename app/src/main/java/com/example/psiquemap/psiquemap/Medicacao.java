package com.example.psiquemap.psiquemap;

import android.content.Intent;
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

public class Medicacao extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lstMedicamentos;
    private ArrayAdapter<Medicamento> adpMedicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstMedicamentos = (ListView)findViewById(R.id.lstMedicamentos);
        adpMedicamentos = Medicamento.buscarMedicacao(this);
        lstMedicamentos.setAdapter(adpMedicamentos);

        lstMedicamentos.setOnItemClickListener(this);
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
