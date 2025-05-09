package org.example.orishw2.dto;


import lombok.Data;
import java.util.List;
import java.util.Optional;

@Data
public class RecipeDto {
    private Long id;
    private String name;
    private String description;
    private String instructions;
    private Optional<AuthorDto> author = Optional.empty();
    private Optional<List<IngredientDto>> ingredients = Optional.empty();
    private Optional<List<CategoryDto>> categories = Optional.empty();
}

