package com.wontlost.model.repository;

import com.wontlost.model.data.EditorData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EditorDataRepository extends MongoRepository<EditorData, String> {



}
