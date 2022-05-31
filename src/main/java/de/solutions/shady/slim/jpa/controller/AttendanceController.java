package de.solutions.shady.slim.jpa.controller;

import de.solutions.shady.slim.jpa.domain.Attendance;
import de.solutions.shady.slim.jpa.model.CreateAttendance;
import de.solutions.shady.slim.jpa.model.UpdateAttendance;
import de.solutions.shady.slim.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.solutions.shady.slim.jpa.repository.AttendanceRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value="/attendance", method = { RequestMethod.GET, RequestMethod.POST })
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;

    //@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    @GetMapping
    public List<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Attendance getAttendance(@PathVariable Long id) {
        return attendanceRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    // TODO ResponseEntity what is happening with created if there's an error?
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE}, consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createAttendance(@RequestBody CreateAttendance createAttendance) throws URISyntaxException {
        System.out.println(createAttendance.toString());
        Attendance newAttendance = new Attendance();

        if (userRepository.findById(createAttendance.getUserId()).isEmpty()) {
            return new ResponseEntity<>(
                    "User with ID could not be found",
                    HttpStatus.NOT_FOUND);
        }
        newAttendance.setUser(userRepository.findById(createAttendance.getUserId()).get());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(createAttendance.getDate(), formatter);

        newAttendance.setDate(dateTime);

        Attendance savedAttendance = attendanceRepository.save(newAttendance);
        return ResponseEntity.created(new URI("/attendances/" + savedAttendance.getId())).body(savedAttendance);
    }

    @PutMapping(value="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE}, consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateAttendance(@PathVariable Long id, @RequestBody UpdateAttendance updateAttendance) {
        Attendance currentAttendance = attendanceRepository.findById(id).orElseThrow(RuntimeException::new);

        if (userRepository.findById(updateAttendance.getUserId()).isEmpty()) {
            return new ResponseEntity<>(
                    "User with ID could not be found",
                    HttpStatus.NOT_FOUND);
        }
        currentAttendance.setUser(userRepository.findById(updateAttendance.getUserId()).get());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(updateAttendance.getDate(), formatter);

        currentAttendance.setDate(dateTime);

        currentAttendance = attendanceRepository.save(currentAttendance);

        return ResponseEntity.ok(currentAttendance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAttendance(@PathVariable Long id) {
        attendanceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
