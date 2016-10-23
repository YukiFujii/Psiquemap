package com.example.psiquemap.psiquemap.entidades;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by YUKI FUJII on 14/09/2016.
 */
public class Diario implements Serializable
{
        private ArrayList<PerguntaDoQuestionario> listaDePerguntasDoDiario;

        public Diario() {}

        public void buscarQuestionario()
        {

            PerguntaDoQuestionario diario1 = new PerguntaDoQuestionario("1","Você dormiu bem?","boolean");
            PerguntaDoQuestionario diario2 = new PerguntaDoQuestionario("2","Você praticou alguma atividade física durante o dia?","boolean");
            PerguntaDoQuestionario diario3 = new PerguntaDoQuestionario("3","Caso você tome alguma medicação, tomou nas horas corretas? (Caso não tome, assinale a opção 'Sim')","boolean");
            PerguntaDoQuestionario diario4 = new PerguntaDoQuestionario("4","Aconteceu algo que tenha alterado seu humor? (Se sim, adicionar todos " +
                    "acontecimentos selecionando a opção 'ADICIONAR EVENTO' em 'Diário'","boolean");
            PerguntaDoQuestionario diario5 = new PerguntaDoQuestionario("5","Quantas horas você dormiu?","time");

                listaDePerguntasDoDiario = new ArrayList<PerguntaDoQuestionario>();
                this.listaDePerguntasDoDiario.add(diario1);
                this.listaDePerguntasDoDiario.add(diario2);
                this.listaDePerguntasDoDiario.add(diario3);
                this.listaDePerguntasDoDiario.add(diario4);
                this.listaDePerguntasDoDiario.add(diario5);

                this.listaDePerguntasDoDiario.get(0).setFoiRespondida(false);
                this.listaDePerguntasDoDiario.get(1).setFoiRespondida(false);
                this.listaDePerguntasDoDiario.get(2).setFoiRespondida(false);
                this.listaDePerguntasDoDiario.get(3).setFoiRespondida(false);
                this.listaDePerguntasDoDiario.get(4).setFoiRespondida(false);

        }

        public int perguntasRestantes()
        {
            int ret = 0;

            for (int i = 0; i< this.listaDePerguntasDoDiario.size(); i++)
            {
                if(this.listaDePerguntasDoDiario.get(i).getFoiRespondida()==false)
                    ret++;
            }

            return ret;
        }

        public int indexDaProximaPegunta()
        {
            int ret;

            ret = this.listaDePerguntasDoDiario.size() - this.perguntasRestantes();
            return ret;
        }

        public String getTipoProximaPergunta()
        {
            String ret;

            if(this.perguntasRestantes()<1)
                ret="null";
            else
                ret = this.listaDePerguntasDoDiario.get(this.indexDaProximaPegunta()).getTipoPergunta();

            return ret;
        }

        public ArrayList<PerguntaDoQuestionario> getListaDePerguntas()
        {
            return this.listaDePerguntasDoDiario;
        }

}
