package de.solutions.shady.slim.jpa;

import de.solutions.shady.slim.jpa.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    public void testUserEntity() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordEncoded("abcdef");

        entityManager.persist(admin);

        assertNotNull(admin.getId());
        assertTrue(admin.getId() > 0);

        User adminFromDb = entityManager.find(User.class, admin.getId());

        assertEquals("admin", adminFromDb.getUsername());
        assertEquals("abcdef", adminFromDb.getPasswordEncoded());

        admin.setPasswordEncoded("fedbca");
        entityManager.persist(admin);

        adminFromDb = entityManager.find(User.class, admin.getId());

        assertEquals("admin", adminFromDb.getUsername());
        assertEquals("fedbca", adminFromDb.getPasswordEncoded());

        entityManager.remove(admin);

        adminFromDb = entityManager.find(User.class, adminFromDb.getId());

        assertNull(adminFromDb);


    }
}
