package tpjava.tpjavacontrole.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "cat")
public class Cat extends Animal {

    private String chipId;

    public Cat() {
    }

    public Cat(LocalDate birth, String couleur, String chipId) {
        super(birth, couleur);
        this.chipId = chipId;
    }

    public String getChipId() {
        return chipId;
    }
}
