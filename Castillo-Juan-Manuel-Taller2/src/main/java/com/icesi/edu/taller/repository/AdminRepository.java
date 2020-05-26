package com.icesi.edu.taller.repository;
import org.springframework.data.repository.CrudRepository;

import com.icesi.edu.taller.model.TsscAdmin;

public interface AdminRepository extends CrudRepository<TsscAdmin, Long> {
public TsscAdmin findByUser(String user);
//
}
