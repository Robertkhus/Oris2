package org.example.orishw2.service;

import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.Recipe;
import org.example.orishw2.repository.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAllWithDetails();
        return recipes;
    }

    public Recipe createRecipe(Recipe recipe) {
        return(recipeRepository.save(recipe));
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

}