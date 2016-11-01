package com.example.psiquemap.psiquemap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.entidades.Acontecimento;

public class DetalheAcontecimento extends AppCompatActivity {

    private TextView txtSentimento;
    private TextView txtTitulo;
    private TextView txtDescricao;
    private TextView txtDataHora;
    private Button btnOkAcontecimento;

    private Acontecimento acontecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_acontecimento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.txtSentimento = (TextView)findViewById(R.id.txtSentimento);
        this.txtTitulo = (TextView)findViewById(R.id.txtTitulo);
        this.txtDescricao = (TextView)findViewById(R.id.txtDescricao);
        this.txtDataHora = (TextView)findViewById(R.id.txtDataHora);
        this.btnOkAcontecimento = (Button)findViewById(R.id.btnOkAcontecimento);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("ACONTECIMENTO")))
            this.acontecimento = (Acontecimento) bundle.getSerializable("ACONTECIMENTO");

        this.txtSentimento.setText(this.acontecimento.getSentimento());
        this.txtTitulo.setText(this.acontecimento.getTitulo());
        this.txtDescricao.setText(this.acontecimento.getDescricao());
        this.txtDataHora.setText("Registrado em: "+this.acontecimento.getData()+" às "+this.acontecimento.getHora());

    }

    public void btnOkAcontecimento(View view)
    {
        finish();
    }

}
