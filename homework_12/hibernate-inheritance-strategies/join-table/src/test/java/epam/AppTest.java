package epam;


import com.epam.domain.BankAccount;
import com.epam.domain.BillingDetails;
import com.epam.domain.Buyer;
import com.epam.domain.CreditCard;
import epam.config.HibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
public class AppTest {

    @Autowired
    private SessionFactory sessionFactory;

    private Buyer buyer;

    private Buyer buyer2;

    private BankAccount account;

    private CreditCard creditCard;

    private Set<Buyer> buyers = new HashSet<>();

    @Before
    public void setUp(){
        buyer = new Buyer();
        buyer.setFirstName("Ivan");
        buyer.setLastName("Ivanov");

        buyer2 = new Buyer();
        buyer2.setFirstName("Petr");
        buyer2.setLastName("Petrov");

        buyers.add(buyer);
        buyers.add(buyer2);

        account = new BankAccount();
        account.setBuyers(buyers);
        account.setBankName("Privat");
        account.setAccount("1van");

        creditCard = new CreditCard();
        creditCard.setBuyers(buyers);
        creditCard.setCardNumber("11111");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(2015);
    }


    @Test
    public void testMethod(){
        EntityManager entityManager = sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(buyer);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(buyer2);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(creditCard);
        entityManager.getTransaction().commit();

        List<BillingDetails> resultList = entityManager.createQuery("select bd from BillingDetails bd").getResultList();


        resultList.forEach(System.out::println);
    }

}
