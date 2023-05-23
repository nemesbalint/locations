package locations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double> {

    private double minValue;
    private double maxValue;

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        minValue = constraintAnnotation.minValue();
        maxValue = constraintAnnotation.maxValue();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null &&
                value >= minValue &&
                value <= maxValue;
    }
}
