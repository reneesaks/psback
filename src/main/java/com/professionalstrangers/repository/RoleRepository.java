package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findAll();

    List<Role> findByRoleName(String roleName);

    Role getById(Long id);
}
