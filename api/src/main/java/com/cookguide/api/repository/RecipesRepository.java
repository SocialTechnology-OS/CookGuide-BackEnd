package com.cookguide.api.repository;

import com.cookguide.api.model.Recipes;
import org.springframework.data.repository.CrudRepository;

public interface RecipesRepository extends CrudRepository<Recipes, Integer>{
}
