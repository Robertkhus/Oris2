package org.example.orishw2.repository;

import org.example.orishw2.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @EntityGraph(attributePaths = {"author", "ingredients", "categories"})
    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAllWithDetails();

    Optional<Recipe> findById(Long id);

}
