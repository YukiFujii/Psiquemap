package com.example.psiquemap.psiquemap.entidades;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by YUKI FUJII on 15/09/2016.
 */
public class Evento
{
    private String classificacaoEvento;
    private String descricaoEvento;
    private Date dataEvento;

    public Evento(String classificacaoEvento,String descricaoEvento)
    {
        this.setClassificacaoEvento(classificacaoEvento);
        this.setDescricaoEvento(descricaoEvento);
        dataEvento = new Date();
        dataEvento.getTime();
    }

    public String toString()
    {
        String ret="";

        ret= ret+this.classificacaoEvento+": "+this.descricaoEvento+" - "+getDataString();

        return ret;
    }

    public String getDataString()
    {
        String ret="";

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ret = format.format(this.getDataEvento());

        return ret;
    }

    public String getClassificacaoEvento() {
        return classificacaoEvento;
    }

    public void setClassificacaoEvento(String classificacaoEvento) {
        this.classificacaoEvento = classificacaoEvento;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }
}
