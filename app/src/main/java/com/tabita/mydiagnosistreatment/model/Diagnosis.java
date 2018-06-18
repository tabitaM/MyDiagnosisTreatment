package com.tabita.mydiagnosistreatment.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Diagnosis implements Serializable {

    private String name;
    private List<String> keywords;
    private Treatment treatment;

    public Diagnosis() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diagnosis)) return false;
        Diagnosis diagnosis = (Diagnosis) o;
        return Objects.equals(getName(), diagnosis.getName()) &&
                Objects.equals(getKeywords(), diagnosis.getKeywords()) &&
                Objects.equals(getTreatment(), diagnosis.getTreatment());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getKeywords(), getTreatment());
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "name='" + name + '\'' +
                ", keyword=" + keywords +
                ", treatment=" + treatment +
                '}';
    }
}
