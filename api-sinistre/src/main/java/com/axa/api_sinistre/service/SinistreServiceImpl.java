package com.axa.api_sinistre.service;

import com.axa.api_sinistre.model.Sinistre;
import com.axa.api_sinistre.repository.SinistreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Add this import
import java.util.List;

@Service
public class SinistreServiceImpl implements SinistreService {
    private final SinistreRepository repository;

    public SinistreServiceImpl(SinistreRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Sinistre create(Sinistre sinistre) {
        System.out.println(" Sinistre reçu : " + sinistre.getDescription() + " - " + sinistre.getType() + " - " + sinistre.getDate());
        return repository.save(sinistre);
    }
   



    @Override
    public List<Sinistre> getAll() {
        return repository.findAll();
    }
}

