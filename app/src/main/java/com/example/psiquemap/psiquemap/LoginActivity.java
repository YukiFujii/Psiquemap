 package com.example.psiquemap.psiquemap;

    import android.content.Context;
    import android.content.Intent;
    import android.database.sqlite.SQLiteDatabase;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.example.psiquemap.psiquemap.comunicacao.EnviarLogin;
    import com.example.psiquemap.psiquemap.entidades.Controle;
    import com.example.psiquemap.psiquemap.entidades.Login;
    import com.example.psiquemap.psiquemap.entidades.Paciente;
    import com.example.psiquemap.psiquemap.sql.Controles;
    import com.example.psiquemap.psiquemap.sql.DataBase;
    import com.example.psiquemap.psiquemap.sql.Pacientes;
;

 public class LoginActivity extends AppCompatActivity
 {

     private EditText editEmail;
     private EditText editSenha;
     private Button btnLogin;
     private DataBase dataBase;
     private SQLiteDatabase conn;

     private Controle controle;
     private Controles controles;
     private Paciente paciente;
     private Pacientes pacientes;

     public static Context getApplicationContext;
     public static Context thisContext;

     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

         editEmail = (EditText)findViewById(R.id.editEmailLogin);
         editSenha = (EditText)findViewById(R.id.editSenhaLogin);
         btnLogin = (Button)findViewById(R.id.btnLogin);
         getApplicationContext = getApplicationContext();
         thisContext = this;

     }

     public void btnLoginWS(View v)
     {
         Login login = new Login();

         login.setEmail(this.editEmail.getText().toString());
         login.setSenha(this.editSenha.getText().toString());

         new EnviarLogin().execute(login);

         if (this.conexaoBD())
         {
             controles = new Controles(conn);
             pacientes = new Pacientes(conn);

             try {

                 for(int i=0;i<50;i++)
                 {
                     Thread.sleep(100);
                     if(controles.hasControle())
                        break;
                     Log.i("Contador",i+"");
                 }

                 if(controles.hasControle())
                 {
                     Intent it = new Intent(this,MainActivity.class);
                     startActivityForResult(it,0);
                     finish();
                 }
                 else {
                     Toast.makeText(LoginActivity.getApplicationContext, "Dados incorretos! Por favor, verifique e tente novamente.",
                             Toast.LENGTH_LONG).show();
                 }

             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         } else {
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

 }
