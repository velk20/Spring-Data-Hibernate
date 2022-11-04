

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("relations");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Product product = new Product();
        entityManager.persist(product);

        Customer customer = new Customer();
        entityManager.persist(customer);

        StoreLocation storeLocation = new StoreLocation();
        entityManager.persist(storeLocation);

        Sale sale = new Sale();
        sale.setProductId(entityManager.find(Product.class, product.getId()));
        sale.setCustomerId(entityManager.find(Customer.class, customer.getId()));
        sale.setStoreLocationId(entityManager.find(StoreLocation.class, storeLocation.getId()));
        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
