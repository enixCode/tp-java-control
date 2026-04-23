package tpjava.tpjavacontrole.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import tpjava.tpjavacontrole.model.enume.FishLivEnv;

import java.time.LocalDate;

@Entity
@Table(name = "fish")
public class Fish extends Animal {

    @Enumerated(EnumType.STRING)
    private FishLivEnv livingEnv;

    public Fish() {
    }

    public Fish(LocalDate birth, String couleur, FishLivEnv livingEnv) {
        super(birth, couleur);
        this.livingEnv = livingEnv;
    }

    public FishLivEnv getLivingEnv() {
        return livingEnv;
    }
}
