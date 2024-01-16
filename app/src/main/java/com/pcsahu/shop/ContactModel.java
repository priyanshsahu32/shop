package com.pcsahu.shop;

import java.util.PrimitiveIterator;

public class ContactModel {
    private String date,cn,ca,pn,produname,item;
    float tk,rt,ta;
    public ContactModel(String date,String cn,String ca,String pn,String produname,String items,float tk,float rt,float ta){
        this.date = date;
        this.cn = cn;
        this.ca = ca;
        this.pn = pn;
        this.produname = produname;
        this.item = items;
        this.tk = tk;
        this.rt = rt;
        this.ta = ta;
    }

    public String getdate(){
        return date;
    }
    public String getcustname(){
        return cn;
    }
    public String getcustadd(){
        return ca;
    }
    public String getphno(){
        return pn;
    }
    public String getProduname(){
        return produname;
    }
    public String getItem(){
        return item;
    }
    public float gettotal_kgs(){
        return tk;
    }
    public float getRate(){
        return rt;
    }
    public float totalAmt(){
        return ta;
    }


}
