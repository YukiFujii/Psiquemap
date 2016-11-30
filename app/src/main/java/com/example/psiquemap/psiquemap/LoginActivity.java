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

    import com.example.psiquemap.psiquemap.comunicacao.enviarLogin;
    import com.example.psiquemap.psiquemap.entidades.Controle;
    import com.example.psiquemap.psiquemap.entidades.Login;
    import com.example.psiquemap.psiquemap.entidades.Medicamento;
    import com.example.psiquemap.psiquemap.entidades.Paciente;
    import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
    import com.example.psiquemap.psiquemap.entidades.Sintoma;
    import com.example.psiquemap.psiquemap.sql.Controles;
    import com.example.psiquemap.psiquemap.sql.DataBase;
    import com.example.psiquemap.psiquemap.sql.Medicamentos;
    import com.example.psiquemap.psiquemap.sql.Pacientes;
    import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;
    import com.example.psiquemap.psiquemap.sql.PerguntasDoQuestionarioMINI;
    import com.example.psiquemap.psiquemap.sql.Sintomas;

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

         new enviarLogin().execute(login);

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

                 if(this.inserirDados())
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

     // ------------------------- GAMBIARRA --------------------------------------------------------

     private boolean inserirDados()
     {
         boolean ret = false;

         try {
                 Paciente paciente = pacientes.getPaciente();
                 Log.i("Paciente", paciente.getId());

                 switch (paciente.getEmail()) {
                     case "y":

                         this.inserirPerguntasNoDiario();
                         this.inserirPerguntasNoQuestionarioMINI();
                         this.inserirSintomas();
                         this.inserirMedicacao();
                         break;

                     case "ronaldo@gmail.com":
                         paciente = new Paciente("000002", "Ronaldo Teodoro", "R. Campinas",
                                 100, "13000-000", "22/04/1986", "ronaldo@gmail.com", "12.501.108-9", "394.938.437-03", "(19) 99999-9999",
                                 "123", "234567");

                         this.inserirPerguntasNoDiario();
                         this.inserirPerguntasNoQuestionarioMINI();
                         this.inserirSintomas();
                         this.inserirMedicacao();
                         break;

                     default:
                         break;
                 }

                 ret=true;
         }
         catch (Exception e)
         {
             ret=false;
         }

         return ret;

     }

     private void inserirPerguntasNoDiario()
     {
         PerguntasDoDiario perguntasDoDiario = new PerguntasDoDiario(this.conn);

         PerguntaDoQuestionario diario1 = new PerguntaDoQuestionario("1","Você dormiu bem?","boolean");
         PerguntaDoQuestionario diario2 = new PerguntaDoQuestionario("2","Você praticou alguma atividade física durante o dia?","boolean");
         PerguntaDoQuestionario diario3 = new PerguntaDoQuestionario("3","Caso você tome alguma medicação, tomou nas horas corretas? (Caso não tome, assinale a opção 'Sim')","boolean");
         PerguntaDoQuestionario diario4 = new PerguntaDoQuestionario("4","Aconteceu algo que tenha alterado seu humor? (Se sim, adicionar todos " +
                 "acontecimentos selecionando a opção 'ADICIONAR EVENTO' em 'Diário'","boolean");
         PerguntaDoQuestionario diario5 = new PerguntaDoQuestionario("5","Quantas horas você dormiu?","time");

         perguntasDoDiario.insert(diario1);
         perguntasDoDiario.insert(diario2);
         perguntasDoDiario.insert(diario3);
         perguntasDoDiario.insert(diario4);
         perguntasDoDiario.insert(diario5);
     }

     private void inserirPerguntasNoQuestionarioMINI()
     {

         PerguntasDoQuestionarioMINI perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(conn);

         PerguntaDoQuestionario questao1 = new PerguntaDoQuestionario("A","EPISÓDIO DEPRESSIVO MAIOR (EDM)","1",
                 "Nas duas últimas semanas, sentiu-se triste, desanimado(a), deprimido(a), durante a maior " +
                         "parte do dia, quase todos os dias?","boolean");

         PerguntaDoQuestionario questao2 = new PerguntaDoQuestionario("A","EPISÓDIO DEPRESSIVO MAIOR (EDM)","2",
                 "Nas duas últimas semanas, quase todo tempo, teve o sentimento de não ter mais gosto por " +
                         "nada, de ter perdido o interesse e o prazer pelas coisas que lhe agradam habitualmente?","boolean");

         PerguntaDoQuestionario questao3 = new PerguntaDoQuestionario("A","EPISÓDIO DEPRESSIVO MAIOR (EDM)","3",
                 "Durante as duas últimas semanas, quando se sentia deprimido(a) / sem interesse pela " +
                         "maioria das coisas:","null");

         PerguntaDoQuestionario questao4 = new PerguntaDoQuestionario("A","EPISÓDIO DEPRESSIVO MAIOR (EDM)","3a",
                 "O seu apetite mudou de forma significativa, ou o seu peso aumentou ou diminuiu sem que " +
                         "o tenha desejado ? (variação de + 5% ao longo do mês, isto é, + 3,5 Kg, para uma pessoa " +
                         "de 65 Kg)","boolean");

         perguntasDoQuestionarioMINI.insertPerguntaDoQuestionarioMINI(questao1);
         perguntasDoQuestionarioMINI.insertPerguntaDoQuestionarioMINI(questao2);
         perguntasDoQuestionarioMINI.insertPerguntaDoQuestionarioMINI(questao3);
         perguntasDoQuestionarioMINI.insertPerguntaDoQuestionarioMINI(questao4);

     }

     private void inserirSintomas()
     {
         Sintomas sintomas = new Sintomas(conn);

         Sintoma sintoma1 = new Sintoma("01","123","Angustia");
         Sintoma sintoma2 = new Sintoma("01","136","Ansiedade");
         Sintoma sintoma3 = new Sintoma("01","158","Medo");
         Sintoma sintoma4 = new Sintoma("02","865","Dor de Cabeça");
         Sintoma sintoma5 = new Sintoma("02","478","Ansia");
         Sintoma sintoma6 = new Sintoma("01","324","Estresse");

         sintomas.insert(sintoma1);
         sintomas.insert(sintoma2);
         sintomas.insert(sintoma3);
         sintomas.insert(sintoma4);
         sintomas.insert(sintoma5);
         sintomas.insert(sintoma6);

     }

     private void inserirMedicacao()
     {

         Medicamentos medicamentos = new Medicamentos(this.conn);

         Medicamento m1 = new Medicamento(controles.getIdPaciente(),"01","Aurorix",1,100,2,"Sem observações.",0);

         Medicamento m2 = new Medicamento(controles.getIdPaciente(),"02","Stablon",8,25,5,"Sem observações.",1);

         Medicamento m3 = new Medicamento(controles.getIdPaciente(),"03","Tofranil",4,25,7,"Sem observações.",0);

         Medicamento m4 = new Medicamento(controles.getIdPaciente(),"04","Aurorix2",2,100,2,"Sem observações.",0);

         Medicamento m5 = new Medicamento(controles.getIdPaciente(),"05","Aurorix3",3,100,2,"Sem observações.",0);


         medicamentos.insert(m1);
         medicamentos.insert(m2);
         medicamentos.insert(m3);
         medicamentos.insert(m4);
         medicamentos.insert(m5);

     }

 }
