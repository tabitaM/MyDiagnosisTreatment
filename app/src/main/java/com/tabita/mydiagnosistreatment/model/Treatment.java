package com.tabita.mydiagnosistreatment.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Treatment implements Serializable {

    private String notes;
    private String period;
    private List<Medication> medication;

    public Treatment() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treatment)) return false;
        Treatment treatment = (Treatment) o;
        return Objects.equals(getNotes(), treatment.getNotes()) &&
                Objects.equals(getPeriod(), treatment.getPeriod()) &&
                Objects.equals(getMedication(), treatment.getMedication());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNotes(), getPeriod(), getMedication());
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "notes='" + notes + '\'' +
                ", period='" + period + '\'' +
                ", medication=" + medication +
                '}';
    }
}
