package com.epam;

import com.epam.cfg.HibernateConfiguration;
import com.epam.dao.UserDao;
import com.epam.entity.Role;
import com.epam.entity.User;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class})
//@Transactional
public class CacheTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    private User user;

    private String name;

    @Before
    public void setUp() {
        name = "SomeUser";

        user = new User();
        user.setName(name);
        user.setRole(Role.USER);
    }

    @Test
    public void testCache() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        userDao.create(user);
        entityTransaction.commit();

        User newUser = userDao.read(name);

        String id = newUser.getId();


        EntityManager em = sessionFactory.createEntityManager();
        System.out.println(em.find(User.class, id));

        printCacheInfo();

        em = sessionFactory.createEntityManager();
        System.out.println(em.find(User.class, id));

    }

    private static void printCacheInfo() {
        List<CacheManager> cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
        if (!cacheManagers.isEmpty()) {
            CacheManager cacheManager = cacheManagers.get(0);
            Cache usersCache = cacheManager.getCache(User.class.getName());
            System.out.println("Users second level cache has size = " + usersCache.getSize());
        } else {
            System.out.println("Hibernate second level cache is disabled.");
        }
    }

}
