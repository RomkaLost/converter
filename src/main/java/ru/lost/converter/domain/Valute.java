package ru.lost.converter.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Valute {
    @Id
    private String id;
    private String name;
    private String charCode;
    private String numCode;
    public Valute(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }
}
