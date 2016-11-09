package com.example.psiquemap.psiquemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private ImageButton btnMinhaConta;
    private ImageButton btnDiario;
    private ImageButton btnQuestionario;
    private ImageButton btnSintomas;
    private ImageButton btnFeedback;
    private ImageButton btnNotificacoes;

    private TextView txtMinhaConta;
    private TextView txtDiario;
    private TextView txtQuestionario;
    private TextView txtSintomas;
    private TextView txtFeedback;
    private TextView txtNotificacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMinhaConta = (ImageButton) findViewById(R.id.btnMinhaConta);
        btnDiario = (ImageButton) findViewById(R.id.btnDiario);
        btnQuestionario = (ImageButton) findViewById(R.id.btnQuestionario);
        btnSintomas = (ImageButton) findViewById(R.id.btnSintomas);
        btnFeedback = (ImageButton) findViewById(R.id.btnFeedback);
        btnNotificacoes = (ImageButton) findViewById(R.id.btnNotificacoes);

        txtMinhaConta = (TextView) findViewById(R.id.txtMinhaConta);
        txtDiario = (TextView) findViewById(R.id.txtDiario);
        txtQuestionario = (TextView) findViewById(R.id.txtQuestionario);
        txtSintomas = (TextView) findViewById(R.id.txtSintomas);
        txtFeedback = (TextView) findViewById(R.id.txtFeedback);
        txtNotificacoes = (TextView) findViewById(R.id.txtNotificacoes);

    }

    public void chamarMinhaConta (View view)
    {
        Intent it = new Intent(this, MinhaConta.class);
        startActivityForResult(it, 0);
    }

    public void chamarSintomas (View view)
    {
        Intent it = new Intent(this, SintomasScreen.class);
        startActivityForResult(it, 0);
    }

    public void chamarQuestinario (View view)
    {
        Intent it = new Intent(this, InicioQuestionario.class);
        startActivityForResult(it, 0);
    }

    public void chamarDiario (View view)
    {
        Intent it = new Intent(this, InicioDiario.class);
        startActivityForResult(it, 0);
    }

    public void chamarMedicacao (View view)
    {
        Intent it = new Intent(this, MedicamentosScreen.class);
        startActivityForResult(it, 0);
    }

    public void chamarFeedback (View view)
    {
        Intent it = new Intent(this, FeedbackScreen.class);
        startActivityForResult(it, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
