package tpjava.tpjavacontrole.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "petstore")
public class PetStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String managerName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Adresse address;

    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Animal> animals = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "petstore_product",
            joinColumns = @JoinColumn(name = "petstore_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public PetStore() {
    }

    public PetStore(String name, String managerName, Adresse address) {
        this.name = name;
        this.managerName = managerName;
        this.address = address;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setPetStore(this);
    }

    public void addProduct(Product product) {
        products.add(product);
        product.getPetStores().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    public Adresse getAddress() {
        return address;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
