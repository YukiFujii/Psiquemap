package com.example.psiquemap.psiquemap.entidades;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YUKI FUJII on 12/09/2016.
 */
public class Questionario implements Serializable
{
    private ArrayList<PerguntaDoQuestionario> listaDePerguntas;
    private ArrayList<PerguntaDoQuestionario> listaDePerguntasDoDiario;
    private String tipoQuestionario;

    public Questionario() {}

    public void buscarQuestionario()
    {

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

            this.listaDePerguntas = new ArrayList<PerguntaDoQuestionario>();
            this.listaDePerguntas.add(questao1);
            this.listaDePerguntas.add(questao2);
            this.listaDePerguntas.add(questao3);
            this.listaDePerguntas.add(questao4);

            this.listaDePerguntas.get(0).setFoiRespondida(0);
            this.listaDePerguntas.get(1).setFoiRespondida(0);
            this.listaDePerguntas.get(2).setFoiRespondida(0);
            this.listaDePerguntas.get(3).setFoiRespondida(0);

    }

    public int perguntasRestantes()
    {
        int ret = 0;

        for (int i = 0; i< this.listaDePerguntas.size(); i++)
        {
            if(this.listaDePerguntas.get(i).getFoiRespondida()==0)
                ret++;
        }

        return ret;
    }

    public int indexDaProximaPegunta()
    {
        int ret;

        ret = this.listaDePerguntas.size() - this.perguntasRestantes();
        return ret;
    }

    public String getTipoProximaPergunta()
    {
        String ret;

        if(this.perguntasRestantes()<1)
            ret="null";
        else
        ret = this.listaDePerguntas.get(this.indexDaProximaPegunta()).getTipoPergunta();

        return ret;
    }

    public ArrayList<PerguntaDoQuestionario> getListaDePerguntas()
    {
        return this.listaDePerguntas;
    }


}
