package de.solutions.shady.slim.jpa.repository;

import de.solutions.shady.slim.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIs(String username);

    List<User> findByUsernameStartingWith(String partOfUsername);

    List<User> findByAttendancesIsEmpty();

    Optional<User> findByUsernameStartingWithAndPasswordEncodedEqualsIgnoreCaseAndAttendancesIsNotEmpty(String bri, String trewq);


    @Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT a FROM Attendance a WHERE a.user = u)")
    List<User> getUsersWithoutAttendances();

    /*
    @Query(value = "SELECT * FROM user WHERE user.id = ....")
    List<User> complexQuerry();
    */
}
