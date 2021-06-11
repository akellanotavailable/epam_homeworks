package com.epam;


import com.epam.config.HibernateConfiguration;
import com.epam.domain.BankAccount;
import com.epam.domain.BillingDetails;
import com.epam.domain.Buyer;
import com.epam.domain.CreditCard;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
public class AppTest {

    @Autowired
    private SessionFactory sessionFactory;

    private Buyer buyer;

    private BankAccount account;

    private CreditCard creditCard;

    @Before
    public void setUp(){
        buyer = new Buyer();
        buyer.setFirstName("Ivan");
        buyer.setLastName("Ivanov");

        account = new BankAccount();
        account.setBuyer(buyer);
        account.setBankName("Privat");
        account.setAccount("1van");

        creditCard = new CreditCard();
        creditCard.setBuyer(buyer);
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
        entityManager.persist(account);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(creditCard);
        entityManager.getTransaction().commit();

        List<BillingDetails> resultList = entityManager.createQuery("select bd from BillingDetails bd").getResultList();

        resultList.forEach(System.out::println);
    }

}
