package logic.dtos;

public record CreateCategory(
        String name,
        Integer capacity,
        Integer price
) {
}
