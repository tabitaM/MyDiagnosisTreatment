package com.tabita.mydiagnosistreatment.model;

import java.util.List;

public class Diagnosis {

    private String name;
    private List<String> keyword;
    private Treatment treatment;

    public Diagnosis() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "name='" + name + '\'' +
                ", keyword=" + keyword +
                ", treatment=" + treatment +
                '}';
    }
}
