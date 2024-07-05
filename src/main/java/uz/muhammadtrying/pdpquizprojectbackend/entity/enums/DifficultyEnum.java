package uz.muhammadtrying.pdpquizprojectbackend.entity.enums;

import lombok.Getter;

@Getter
public enum DifficultyEnum {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int factor;

    DifficultyEnum(int factor) {
        this.factor = factor;
    }

}
