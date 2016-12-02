package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.sql.Acontecimentos;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Feedbacks;
import com.example.psiquemap.psiquemap.sql.Medicamentos;
import com.example.psiquemap.psiquemap.sql.Pacientes;
import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;
import com.example.psiquemap.psiquemap.sql.PerguntasDoQuestionarioMINI;
import com.example.psiquemap.psiquemap.sql.RespostasQuestionarioDiario;
import com.example.psiquemap.psiquemap.sql.RespostasQuestionarioMINI;
import com.example.psiquemap.psiquemap.sql.Sintomas;
import com.example.psiquemap.psiquemap.sql.SintomasSentidos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuki on 01/11/16.
 */

public final class MetodosEmComum
{
    public static final String urlLogin = "http://192.168.0.16:8080/psiquemap-ws/login";
    public static final String urlEnviar = "http://192.168.0.16:8080/psiquemap-ws/receber";

    public static String getDataAtual()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat data = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String str = data.format(calendar.getTime());

        return str;
    }

    public static String getDataAtual(Calendar calendar)
    {
        DateFormat data = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String str = data.format(calendar.getTime());

        return str;
    }

    public static String getHoraAtual()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat f24h = new SimpleDateFormat("HH:mm");
        String str = f24h.format(calendar.getTime());

        return str;
    }

    public static String horaToString(Calendar calendar)
    {
        Date hora = calendar.getTime();

        DateFormat data = new SimpleDateFormat("HH:mm");
        String str = data.format(hora);

        return str;
    }

    public static Calendar stringToCalendar(String horas)
    {
        Date hora=null;
        DateFormat data = new SimpleDateFormat("HH:mm");
        try {
             hora = data.parse(horas);
            Log.i("Conversão to date","sucess");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,hora.getMinutes());
        calendar.set(Calendar.HOUR_OF_DAY,hora.getHours());
        calendar.set(Calendar.SECOND,0);

        Log.i("Calender com hora",MetodosEmComum.horaToString(calendar));

        return calendar;
    }

    public static Calendar ajusteData(Calendar calendar)
    {
        Calendar cal = Calendar.getInstance();

        if(calendar.getTimeInMillis()<cal.getTimeInMillis())
            calendar.add(Calendar.DATE,1);

        return calendar;
    }

    public static SQLiteDatabase conexaoBD(Context context)
    {
        SQLiteDatabase conn = null;

        try {

            DataBase dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

        }
        catch (Exception ex)
        {
            ex.getStackTrace();
        }

        return conn;
    }

    public static boolean verificarConexaoBD(Context context)
    {
        SQLiteDatabase conn = null;

        try {

            DataBase dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();
            return true;
        }
        catch (Exception ex)
        {
            ex.getStackTrace();
            return false;
        }

    }

    public static Context escolherContext()
    {
        if(MetodosEmComum.verificarConexaoBD(MainActivity.thisContext))
        {
            Log.i("Context", "MainActivity");
            return MainActivity.thisContext;
        }

        if(MetodosEmComum.verificarConexaoBD(LoginActivity.thisContext))
        {
            Log.i("Context", "LoginActivity");
            return LoginActivity.thisContext;
        }

        Log.i("Conexao com o banco", "Negada");
        return LoginActivity.thisContext;
    }

    public static String getIdPaciente(Context context)
    {
        Controles controles = new Controles(MetodosEmComum.conexaoBD(context));
        String ret = controles.getIdPaciente();
        return ret;
    }

    public static Dados getDados(Context context)
    {
        SQLiteDatabase conn = MetodosEmComum.conexaoBD(context);

        Dados dados = new Dados();

        dados.setControle(Controles.getControle(MetodosEmComum.getIdPaciente(context)));

        if(dados.getControle().getFlagPaciente()==1)
        {
            Pacientes pacientes =  new Pacientes(conn);
            dados.setPaciente(pacientes.getPaciente());
        }

        if (dados.getControle().getFlagQuestDiario()==1)
        {
            RespostasQuestionarioDiario respostasQuestionarioDiario = new RespostasQuestionarioDiario(conn);
            dados.setRespostasQuestDiario(respostasQuestionarioDiario.getRespostasQuestDiario(dados.getControle().getIdPaciente()));
        }

        if (dados.getControle().getFlagAcontecimento()==1)
        {
            Acontecimentos acontecimentos = new Acontecimentos(conn);
            dados.setAcontecimentos(acontecimentos.getAcontecimentos(dados.getControle().getIdPaciente()));
        }

        if (dados.getControle().getFlagQuestMini()==1)
        {
            RespostasQuestionarioMINI respostasQuestionarioMINI = new RespostasQuestionarioMINI(conn);
            dados.setRespostaQuestMini(respostasQuestionarioMINI.getRespostasQuestMini(dados.getControle().getIdPaciente()));
        }

        if (dados.getControle().getFlagSintomas()==1)
        {
            SintomasSentidos sintomasSentidos = new SintomasSentidos(conn);
            dados.setSintomas(sintomasSentidos.getSintomasSentidos(dados.getControle().getIdPaciente()));
        }

        if (dados.getControle().getFlagFeedback()==1)
        {
            Feedbacks feedbacks = new Feedbacks(conn);
            dados.setFeedbacks(feedbacks.getFeedbacks(dados.getControle().getIdPaciente()));
        }

        return dados;

    }


    public static boolean rebecerDados(Dados dados)
    {
        SQLiteDatabase connDB = MetodosEmComum.conexaoBD(MetodosEmComum.escolherContext());

        try
        {
            if(dados.getControle().getFlagPaciente()==1)
            {
                Pacientes.insert(dados.getPaciente(), connDB);
                Log.i("Paciente", "inserido");
                Controles.setFlagPaciente(dados.getControle().getIdPaciente(),0);
            }

            if(dados.getControle().getFlagPergDiario()==1)
            {
                for (int i=0;i<dados.getPerguntasQuestDiario().size();i++)
                {
                    PerguntasDoDiario.insert(dados.getPerguntasQuestDiario().get(i),connDB);
                    Log.i("PerguntasDoDiario", "inserido");
                }
                Controles.setFlagPergDiario(dados.getControle().getIdPaciente(),0);
            }

            if(dados.getControle().getFlagPergMini()==1)
            {
                for (int i=0;i<dados.getPerguntasQuestMini().size();i++)
                {
                    PerguntasDoQuestionarioMINI.insert(dados.getPerguntasQuestMini().get(i),connDB);
                    Log.i("PerguntasDoQuestMINI", "inserido");
                }
                Controles.setFlagPergMini(dados.getControle().getIdPaciente(),0);
            }

            if(dados.getControle().getFlagTodosSintomas()==1)
            {
                for (int i=0;i<dados.getTodosSintomas().size();i++)
                {
                    Sintomas.insert(dados.getTodosSintomas().get(i),connDB);
                    Log.i("TodosSintomas", "inserido");
                }
                Controles.setFlagTodosSintomas(dados.getControle().getIdPaciente(),0);
            }

            if(dados.getControle().getFlagMedicamento()==1)
            {
                for (int i=0;i<dados.getMedicamentos().size();i++)
                {
                    Medicamentos.insert(dados.getMedicamentos().get(i),connDB);
                    Log.i("Medicamentos", "inserido");
                }
                Controles.setFlagMedicamento(dados.getControle().getIdPaciente(),0);
            }

            return true;

        }
        catch (Exception e)
        {
            return false;
        }

    }

}
