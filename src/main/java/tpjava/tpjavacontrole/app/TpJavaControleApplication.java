package tpjava.tpjavacontrole.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import tpjava.tpjavacontrole.model.Adresse;
import tpjava.tpjavacontrole.model.Animal;
import tpjava.tpjavacontrole.model.Cat;
import tpjava.tpjavacontrole.model.Fish;
import tpjava.tpjavacontrole.model.PetStore;
import tpjava.tpjavacontrole.model.Product;
import tpjava.tpjavacontrole.model.enume.FishLivEnv;
import tpjava.tpjavacontrole.model.enume.ProdType;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class TpJavaControleApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(TpJavaControleApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("petstore");
                 EntityManager em = emf.createEntityManager()) {

                em.getTransaction().begin();
                seed(em);
                em.getTransaction().commit();

                printAnimalsOf(em, "AnimalZen Angers");
            }
        };
    }

    private void seed(EntityManager em) {
        Adresse a1 = new Adresse("12", "rue des Lilas", "49000", "Angers");
        Adresse a2 = new Adresse("5", "avenue Foch", "75008", "Paris");
        Adresse a3 = new Adresse("28", "rue du Port", "44000", "Nantes");

        PetStore s1 = new PetStore("AnimalZen Angers", "Marie Durand", a1);
        PetStore s2 = new PetStore("Pat'Paris", "Luc Martin", a2);
        PetStore s3 = new PetStore("Aquanantes", "Sophie Le Gall", a3);

        Cat c1 = new Cat(LocalDate.of(2024, 5, 14), "noir", "CHIP-001");
        Cat c2 = new Cat(LocalDate.of(2023, 9, 2), "tigre", "CHIP-002");
        Cat c3 = new Cat(LocalDate.of(2025, 1, 21), "blanc", "CHIP-003");

        Fish f1 = new Fish(LocalDate.of(2025, 3, 8), "orange", FishLivEnv.FRESH_WATER);
        Fish f2 = new Fish(LocalDate.of(2025, 6, 17), "bleu", FishLivEnv.SEA_WATER);
        Fish f3 = new Fish(LocalDate.of(2025, 7, 3), "rouge", FishLivEnv.FRESH_WATER);

        s1.addAnimal(c1);
        s1.addAnimal(c2);
        s1.addAnimal(f1);
        s2.addAnimal(c3);
        s3.addAnimal(f2);
        s3.addAnimal(f3);

        Product p1 = new Product("CRO-01", "Croquettes premium", ProdType.FOOD, 24.90);
        Product p2 = new Product("LIT-01", "Litiere minerale", ProdType.CLEANING, 9.50);
        Product p3 = new Product("JOU-01", "Souris en peluche", ProdType.ACCESSORY, 4.20);

        s1.addProduct(p1);
        s1.addProduct(p3);
        s2.addProduct(p1);
        s2.addProduct(p2);
        s3.addProduct(p2);

        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
    }

    private void printAnimalsOf(EntityManager em, String storeName) {
        List<Animal> animals = em.createQuery(
                        "SELECT a FROM Animal a WHERE a.petStore.name = :name",
                        Animal.class)
                .setParameter("name", storeName)
                .getResultList();

        System.out.println();
        System.out.println("Animaux : " + storeName);
        for (Animal a : animals) {
            String detail;
            if (a instanceof Cat c) {
                detail = "Cat chipId=" + c.getChipId();
            } else if (a instanceof Fish f) {
                detail = "Fish livingEnv=" + f.getLivingEnv();
            } else {
                detail = "";
            }
            System.out.printf("- id=%d, naissance=%s, couleur=%s, %s%n",
                    a.getId(), a.getBirth(), a.getCouleur(), detail);
        }
    }
}
