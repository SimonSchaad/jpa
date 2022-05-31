package de.solutions.shady.slim.jpa.controller;

import de.solutions.shady.slim.jpa.domain.Attendance;
import de.solutions.shady.slim.jpa.model.CreateAttendance;
import de.solutions.shady.slim.jpa.model.UpdateAttendance;
import de.solutions.shady.slim.jpa.repository.AttendanceRepository;
import de.solutions.shady.slim.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    //_______________________________CRUD API____________________________________
    @GetMapping
    public List<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Attendance getAttendance(@PathVariable Long id) {
        return attendanceRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createAttendance(@RequestBody CreateAttendance createAttendance) throws URISyntaxException {
        Attendance newAttendance = new Attendance();
        if (userRepository.findById(createAttendance.getUserId()).isEmpty()) {
            return new ResponseEntity<>(
                    "User with ID could not be found",
                    HttpStatus.NOT_FOUND);
        }
        newAttendance.setUser(userRepository.findById(createAttendance.getUserId()).get());
        newAttendance.setDate(createAttendance.getDate());
        Attendance savedAttendance = attendanceRepository.save(newAttendance);
        return ResponseEntity.created(new URI("/attendances/" + savedAttendance.getId())).body(savedAttendance);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAttendance(@PathVariable Long id, @RequestBody UpdateAttendance updateAttendance) {
        Attendance currentAttendance = attendanceRepository.findById(id).orElseThrow(RuntimeException::new);
        if (userRepository.findById(updateAttendance.getUserId()).isEmpty()) {
            return new ResponseEntity<>(
                    "User with ID could not be found",
                    HttpStatus.NOT_FOUND);
        }
        currentAttendance.setUser(userRepository.findById(updateAttendance.getUserId()).get());
        currentAttendance.setDate(updateAttendance.getDate());
        currentAttendance = attendanceRepository.save(currentAttendance);
        return ResponseEntity.ok(currentAttendance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        attendanceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    //_______________________________CRUD API____________________________________




}