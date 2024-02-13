package com.application.rest.repository;

import com.application.rest.entities.Maker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMakerRepository extends CrudRepository<Maker, Long> {

}
