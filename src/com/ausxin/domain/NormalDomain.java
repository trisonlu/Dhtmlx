package com.ausxin.domain;

/**
 * Created by lushuqin on 2016/11/4.
 */
public class NormalDomain {


    private String name;
    private String code;

    public NormalDomain(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (!(object instanceof NormalDomain)) {
           return false;
        }
        NormalDomain normalDomain = (NormalDomain) object;
        return this.name.equals(normalDomain.getName()) && this.code.equals(normalDomain.getCode());
    }
}
