package com.tabita.mydiagnosistreatment.model;

import java.io.Serializable;
import java.util.Objects;

public class Medication implements Serializable {
    private String name;
    private String dose;

    public Medication() {
    }

    public Medication(String name, String dose) {
        this.name = name;
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDose(), that.getDose());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDose());
    }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                '}';
    }
}
