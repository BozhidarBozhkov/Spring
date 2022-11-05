import entities.BankAccount;
import entities.BillingDetail;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("bills_payment_db").createEntityManager();

        entityManager.getTransaction().begin();

        Set<BillingDetail> billingDetails = new HashSet<>();

        User user = new User("Nadezhda", "Borisova", "nadya@yahoo.com", "1234");

        billingDetails.add(new CreditCard("4400123456789012", user, "VISA", 12, 25));

        billingDetails.add(new BankAccount("BGUBBSF1245153743", user, "KBC Bank", "UBBSBGSF"));

        user.setBillingDetails(billingDetails);
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
