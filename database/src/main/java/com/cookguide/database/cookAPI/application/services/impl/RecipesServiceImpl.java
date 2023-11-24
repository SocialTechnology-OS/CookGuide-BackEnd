package com.cookguide.database.cookAPI.application.services.impl;

import com.cookguide.database.cookAPI.application.dto.request.RecipesRequestDTO;
import com.cookguide.database.cookAPI.application.dto.response.RecipeIngredientDetailDTO;
import com.cookguide.database.cookAPI.application.dto.response.RecipesResponseDTO;
import com.cookguide.database.cookAPI.application.services.RecipesService;
import com.cookguide.database.cookAPI.domain.entities.RecipeIngredients;
import com.cookguide.database.cookAPI.domain.entities.Recipes;
import com.cookguide.database.cookAPI.infraestructure.repositories.RecipeIngredientsRepository;
import com.cookguide.database.cookAPI.infraestructure.repositories.RecipesRepository;
import com.cookguide.database.shared.exception.ResourceNotFoundException;
import com.cookguide.database.shared.exception.ValidationException;
import com.cookguide.database.shared.model.dto.response.ApiResponse;
import com.cookguide.database.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipesServiceImpl implements RecipesService {

    private final RecipesRepository recipesRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;

    public RecipesServiceImpl(RecipesRepository recipesRepository, ModelMapper modelMapper) {
        this.recipesRepository = recipesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<List<RecipesResponseDTO>> getAllRecipes() {
        List<Recipes> recipesList = (List<Recipes>) recipesRepository.findAll();
        List<RecipesResponseDTO> recipesDTOList = recipesList.stream()
                .map(entity -> modelMapper.map(entity, RecipesResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All recipes fetched successfully", Estatus.SUCCESS, recipesDTOList);
    }
    @Override
    public ApiResponse<RecipesResponseDTO> createRecipes(RecipesRequestDTO recipesRequestDTO) {
        validateUniqueRecipes(recipesRequestDTO);
        var recipe = modelMapper.map(recipesRequestDTO, Recipes.class);
        recipesRepository.save(recipe);

        var response = modelMapper.map(recipe, RecipesResponseDTO.class);

        return new ApiResponse<>("Sport event created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<RecipesResponseDTO> updateRecipes(int id, RecipesRequestDTO recipesRequestDTO) {
        Optional<Recipes> recipeOptional = recipesRepository.findById((int) id);
        if (recipeOptional.isPresent()) {
            Recipes recipeToUpdate = recipeOptional.get();
            modelMapper.map(recipesRequestDTO, recipeToUpdate);
            recipesRepository.save(recipeToUpdate);
            RecipesResponseDTO responseDTO = modelMapper.map(recipeToUpdate, RecipesResponseDTO.class);
            return new ApiResponse<>("Recipe updated successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Recipe not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<Void> deleteRecipes(int id) {
        if (recipesRepository.existsById((int) id)) {
            recipesRepository.deleteById((int) id);
            return new ApiResponse<>("Recipe deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Recipe not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<RecipesResponseDTO> getRecipeById(int id) {
        Optional<Recipes> recipeOptional = recipesRepository.findById(id);
        if (recipeOptional.isPresent()) {
            RecipesResponseDTO responseDTO = modelMapper.map(recipeOptional.get(), RecipesResponseDTO.class);
            return new ApiResponse<>("Recipe retrieved successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Recipe not found", Estatus.ERROR, null);
        }
    }

    void validateUniqueRecipes(RecipesRequestDTO recipesRequestDTO) {
        boolean exists = recipesRepository.existsByNameAndPreparationAndServingsAndTime(
                recipesRequestDTO.getName(),
                recipesRequestDTO.getPreparation(),
                recipesRequestDTO.getServings(),
                recipesRequestDTO.getTime()
        );

        if (exists) {
            throw new ValidationException("Recipe with the same name, type, time, and ingredients already exists");
        }
    }

    public List<RecipeIngredientDetailDTO> getRecipeIngredients(int recipeId) {
        Optional<Recipes> recipeOptional = recipesRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipes recipe = recipeOptional.get();
            List<RecipeIngredients> recipeIngredients = recipeIngredientsRepository.findByRecipe(recipe);

            return recipeIngredients.stream()
                    .map(ri -> new RecipeIngredientDetailDTO(
                            ri.getRecipe().getUid(),
                            ri.getIngredient().getUid(),
                            ri.getAmount(),
                            ri.getMeasure()))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Recipe not found for this id: " + recipeId);
        }
    }

}
