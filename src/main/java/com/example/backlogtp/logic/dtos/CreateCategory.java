package com.example.backlogtp.logic.dtos;

public record CreateCategory(
        String name,
        Integer capacity,
        Integer price
) {
}
