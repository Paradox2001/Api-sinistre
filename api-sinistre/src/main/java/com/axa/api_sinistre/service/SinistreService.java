package com.axa.api_sinistre.service;

import com.axa.api_sinistre.model.Sinistre;
import java.util.List;

public interface SinistreService {
    List<Sinistre> getAll();
    Sinistre create(Sinistre sinistre);
}