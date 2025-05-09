package org.example.orishw2.controller;

import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.Recipe;
import org.example.orishw2.service.CategoryService;
import org.example.orishw2.service.IngredientService;
import org.example.orishw2.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/recipes")
public class AdminRecipeController {


    private final RecipeService recipeService;


    private final IngredientService ingredientService;


    private final CategoryService categoryService;

    @GetMapping
    public String showAdminRecipes(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "admin_recipes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Рецепт не найден"));
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit_recipe";
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(@PathVariable Long id,
                               @Valid @ModelAttribute("recipe") Recipe recipe,
                               @RequestParam("ingredientIds") List<Long> ingredientIds,
                               @RequestParam("categoryIds") List<Long> categoryIds,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAllIngredients());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit_recipe";
        }

        try {
            recipe.setId(id);
            if (ingredientIds != null && !ingredientIds.isEmpty()) {
                recipe.setIngredients(ingredientIds.stream()
                        .map(ingredientService::findById)
                        .map(opt -> opt.orElseThrow(() -> new IllegalArgumentException("Ингредиент не найден")))
                        .collect(Collectors.toSet()));
            } else {
                recipe.setIngredients(new HashSet<>());
            }

            if (categoryIds != null && !categoryIds.isEmpty()) {
                recipe.setCategories(categoryIds.stream()
                        .map(categoryService::findById)
                        .map(opt -> opt.orElseThrow(() -> new IllegalArgumentException("Категория не найдена")))
                        .collect(Collectors.toSet()));
            } else {
                recipe.setCategories(new HashSet<>());
            }

            recipeService.createRecipe(recipe);
            return "redirect:/admin/recipes";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении рецепта: " + e.getMessage());
            model.addAttribute("ingredients", ingredientService.getAllIngredients());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit_recipe";
        }
    }

    // Удаление рецепта
    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/admin/recipes";
    }
}