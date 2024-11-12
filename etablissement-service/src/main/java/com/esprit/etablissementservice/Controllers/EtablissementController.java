package com.esprit.etablissementservice.Controllers;

import com.esprit.etablissementservice.Entities.Etablissement;
import com.esprit.etablissementservice.Services.EtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("etablissement")
@CrossOrigin("*")
public class EtablissementController {
    @Autowired
    private EtablissementService etablissementService;

    @GetMapping
    public List<Etablissement> getAllEtablissements() {
        return etablissementService.getAllEtablissements();
    }

    @GetMapping("/{id}")
    public Optional<Etablissement> getEtablissementById(@PathVariable Long id) {
        return etablissementService.getEtablissementById(id);
    }

    @PostMapping
    public Etablissement createEtablissement(@RequestBody Etablissement etablissement) {
        return etablissementService.createEtablissement(etablissement);
    }

    @PutMapping("/{id}")
    public Etablissement updateEtablissement(@PathVariable Long id, @RequestBody Etablissement etablissement) {
        return etablissementService.updateEtablissement(id, etablissement);
    }

    @DeleteMapping("/{id}")
    public void deleteEtablissement(@PathVariable Long id) {
        etablissementService.deleteEtablissement(id);
    }
}
