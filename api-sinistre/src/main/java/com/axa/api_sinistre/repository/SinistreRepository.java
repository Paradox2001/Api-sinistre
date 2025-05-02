package com.axa.api_sinistre.repository;

import com.axa.api_sinistre.model.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinistreRepository extends JpaRepository<Sinistre, Long> {
}
