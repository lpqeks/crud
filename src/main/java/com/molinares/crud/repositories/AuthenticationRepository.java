package com.molinares.crud.repositories;

import com.molinares.crud.models.Authentication;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<Authentication, Long> {
}
