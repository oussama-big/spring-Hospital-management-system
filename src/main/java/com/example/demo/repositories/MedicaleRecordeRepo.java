package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.MedicaleRecorde;
import java.util.List;

public interface MedicaleRecordeRepo extends JpaRepository<MedicaleRecorde, Long>{

    List<MedicaleRecorde>findByPatientIdOrderByCreatedAtDesc(Long patientId);

}
