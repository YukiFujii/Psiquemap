 package com.example.psiquemap.psiquemap;

    import android.content.Intent;
    import android.database.sqlite.SQLiteDatabase;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;

    import com.example.psiquemap.psiquemap.entidades.Paciente;
    import com.example.psiquemap.psiquemap.sql.DataBase;
    import com.example.psiquemap.psiquemap.sql.Pacientes;

 public class LoginActivity extends AppCompatActivity
 {

     private EditText editEmail;
     private EditText editSenha;
     private Button btnLogin;
     private DataBase dataBase;
     private SQLiteDatabase conn;

     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

         editEmail = (EditText)findViewById(R.id.editEmailLogin);
         editSenha = (EditText)findViewById(R.id.editSenhaLogin);
         btnLogin = (Button)findViewById(R.id.btnLogin);

     }

     public void chamarTelaInicial(View v)
     {
         if(this.acessoPermitido()) {
             Intent it = new Intent(this, MainActivity.class);
             startActivityForResult(it, 0);
             finish();
         }
         else
         {
             AlertDialog.Builder dlg = new AlertDialog.Builder(this);
             dlg.setMessage("Login incorreto! Por favor, verifique se seus dados estão corretos.");
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

     // ------------------------- GAMBIARRA --------------------------------------------------------
     private boolean acessoPermitido()
     {
         String email = this.editEmail.getText().toString();
         String senha = this.editSenha.getText().toString();

         if(email.equals("yuki@hotmail.com") && senha.equals("123"))
         {
             inserirPaciente(email);
             return true;
         }

         if (email.equals("ronaldo@gmail.com") && senha.equals("123"))
         {
             inserirPaciente(email);
             return true;
         }

         return false;
     }


     private void inserirPaciente(String email)
     {
         if(this.conexaoBD())
         {
             Pacientes pacientes = new Pacientes(conn);

             switch (email)
             {
                 case "yuki@hotmail.com":
                     Paciente paciente = new Paciente("000001","Alcino Hiroyuki Fujii Júnior","R. Anisio Perissinotto",
                             302,"13140-538","22/04/1992","yuki@hotmail.com","48.612.219-0","405.049.548-14","(19) 99246-6557",
                             "Hiroyuki","123456");

                     pacientes.insert(paciente);
                     break;

                 case "ronaldo@gmail.com":
                     Paciente paciente2 = new Paciente("000002","Ronaldo Teodoro","R. Campinas",
                             100,"13000-000","22/04/1986","ronaldo@gmail.com","12.501.108-9","394.938.437-03","(19) 99999-9999",
                             "123","234567");

                     pacientes.insert(paciente2);
                     break;

                 default:
                     break;
             }

         }
         else
         {
             android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
             dlg.setMessage("Erro ao conectar com banco!");
             dlg.setNeutralButton("OK", null);
             dlg.show();
         }

     }

 }
