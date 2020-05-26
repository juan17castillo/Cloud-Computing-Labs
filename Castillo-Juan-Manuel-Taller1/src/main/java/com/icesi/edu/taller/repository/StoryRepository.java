package com.icesi.edu.taller.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.icesi.edu.taller.model.TsscStory;


public interface StoryRepository extends CrudRepository<TsscStory, Long> {
//
}
