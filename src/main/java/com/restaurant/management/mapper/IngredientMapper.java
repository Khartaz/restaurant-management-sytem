package com.restaurant.management.mapper;

import com.restaurant.management.domain.Ingredient;
import com.restaurant.management.domain.dto.IngredientDto;
import com.restaurant.management.web.request.product.IngredientRequest;
import com.restaurant.management.web.response.IngredientResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {

    public Ingredient mapToIngredient(final IngredientDto ingredientDto) {
        return new Ingredient(
                ingredientDto.getId(),
                ingredientDto.getName()
        );
    }

    public IngredientDto mapToIngredientDto(final Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getId(),
                ingredient.getName()
        );
    }

    public IngredientResponse mapToIngredientResponse(final IngredientDto ingredientDto) {
        return new IngredientResponse(
                ingredientDto.getId(),
                ingredientDto.getName()
        );
    }

    public List<Ingredient> mapToIngredientListFromRequest(final List<IngredientRequest> ingredientsRequest) {
        return ingredientsRequest.stream()
                .map(i -> new Ingredient(
                        i.getName()
                )).collect(Collectors.toList());
    }

    public List<IngredientDto> mapToIngredientDtoList(final List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    public List<IngredientResponse> mapToIngredientResponseList(final List<IngredientDto> ingredients) {
        return ingredients.stream()
                .map(this::mapToIngredientResponse)
                .collect(Collectors.toList());
    }

    public List<Ingredient> mapToIngredientList(final List<IngredientDto> ingredients) {
        return ingredients.stream()
                .map(this::mapToIngredient)
                .collect(Collectors.toList());
    }

}
