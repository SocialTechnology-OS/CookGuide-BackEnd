package com.cookguide.database.repository;

import com.cookguide.database.model.Recipes;
import org.springframework.data.repository.CrudRepository;

public interface RecipesRepository extends CrudRepository<Recipes,Integer > {
}
