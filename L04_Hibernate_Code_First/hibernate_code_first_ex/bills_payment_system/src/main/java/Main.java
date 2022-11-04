import entities.BankAccount;
import entities.BillingDetail;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("relations").createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User("First");
        entityManager.persist(user);

        BillingDetail billingDetail = new CreditCard("123", entityManager.find(User.class, user.getId()), "Credit", 3, 24);
        entityManager.persist(billingDetail);

        User user1 = new User("SEcond");
        entityManager.persist(user1);

        BillingDetail billingDetail2 = new BankAccount("123", entityManager.find(User.class, user1.getId()), "CBD",
                "696969");
        entityManager.persist(billingDetail2);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
