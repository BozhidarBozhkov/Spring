import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence.createEntityManagerFactory("hospital_db").createEntityManager();

        entityManager.getTransaction().begin();

        Patient patient = new Patient("Petar", "Petrov", "Sofia", "ppppov@abv.bg", LocalDate.of(1980, 7, 12) ,true);
        entityManager.persist(patient);

        Medicament medicament = new Medicament("Aviron");
        entityManager.persist(medicament);

        Diagnose diagnose = new Diagnose("Covid-19", "stay home for 5 days");
        entityManager.persist(diagnose);

        Visitation visitation = new Visitation(LocalDate.now(), "patient is in good condition", patient, diagnose);
        entityManager.persist(visitation);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
