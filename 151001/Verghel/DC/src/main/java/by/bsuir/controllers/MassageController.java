package by.bsuir.controllers;

import by.bsuir.dto.MassageRequestTo;
import by.bsuir.dto.MassageResponseTo;
import by.bsuir.services.MassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MassageController {
    @Autowired
    MassageService MassageService;

    @GetMapping
    public ResponseEntity<List<MassageResponseTo>> getMassages() {
        return ResponseEntity.status(200).body(MassageService.getMassages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MassageResponseTo> getMassage(@PathVariable Long id) {
        return ResponseEntity.status(200).body(MassageService.getMassageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMassage(@PathVariable Long id) {
        MassageService.deleteMassage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MassageResponseTo> saveMassage(@RequestBody MassageRequestTo Massage) {
        MassageResponseTo savedMassage = MassageService.saveMassage(Massage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMassage);
    }

    @PutMapping()
    public ResponseEntity<MassageResponseTo> updateMassage(@RequestBody MassageRequestTo Massage) {
        return ResponseEntity.status(HttpStatus.OK).body(MassageService.updateMassage(Massage));
    }
}
