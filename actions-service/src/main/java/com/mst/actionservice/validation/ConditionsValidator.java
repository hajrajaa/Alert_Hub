package com.mst.actionservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ConditionsValidator  implements ConstraintValidator<ValidConditions,List<List<Integer>>> {


    @Override
    public boolean isValid(List<List<Integer>> conditions , ConstraintValidatorContext context)
    {
        if (conditions==null||conditions.isEmpty())
        {
            return true;
        }
        for (List<Integer> condition : conditions)
        {
            if (condition==null||condition.isEmpty())
            {
                return false;
            }
            for  (Integer value : condition)
            {
                if (value==null)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
