package de.solutions.shady.slim.jpa;

import de.solutions.shady.slim.jpa.domain.Attendance;
import de.solutions.shady.slim.jpa.domain.User;
import de.solutions.shady.slim.jpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    private UserRepository userRepository;

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

    @Test
    @Transactional
    public void testAttendanceEntity() {
        User user = new User();
        user.setUsername("Max.Muster");
        user.setPasswordEncoded("qwertz");

        entityManager.persist(user);
        assertNotNull(user.getId());

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDateTime.now());
        attendance.setUser(user);

        entityManager.persist(attendance);
        assertNotNull(attendance.getId());

        Attendance attendanceFromDb = entityManager.find(Attendance.class, attendance.getId());
        assertNotNull(attendanceFromDb);
        assertNotNull(attendanceFromDb.getUser());
        assertEquals("Max.Muster", attendanceFromDb.getUser().getUsername());

        //entityManager.refresh(user);
        assertEquals(1, user.getAttendances().size());

        //entityManager.clear();
        User maxFromDb = entityManager.find(User.class, user.getId());
        assertEquals(1, maxFromDb.getAttendances().size());
    }

    @Test
    public void testUserRepo() {
        User user = new User();
        user.setUsername("brigitte.musterfrau");
        user.setPasswordEncoded("trewq");

        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertTrue(user.getId() > 0);

        Optional<User> userFromDb = userRepository.findById(user.getId());
        assertTrue(userFromDb.isPresent());
        assertEquals("brigitte.musterfrau", userFromDb.get().getUsername());


        Optional<User> max = userRepository.findByUsernameIs("max");
        assertFalse(max.isPresent());

        List<User> users = userRepository.findByUsernameStartingWith("bri");

        List<User> usersWithoutAttendance = userRepository.findByAttendancesIsEmpty();

        Optional<User> brigitte = userRepository.findByUsernameStartingWithAndPasswordEncodedEqualsIgnoreCaseAndAttendancesIsNotEmpty("bri", "TREWQ");
    }



}
