package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.service.IEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public class Controller<Id, RequestTO, ResponseTO, Service extends IEntityService<Id, RequestTO, ResponseTO>>{

    private final Service service;

    @PostMapping
    public ResponseEntity<ResponseTO> create(@RequestBody @Valid RequestTO to){
        ResponseTO result = service.create(to);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<Iterable<ResponseTO>> readAll(){
        Iterable<ResponseTO> result = service.readAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTO> read(@PathVariable Id id){
        ResponseTO result = service.readById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<ResponseTO> update(@RequestBody @Valid RequestTO to){
        ResponseTO result = service.update(to);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Id id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
