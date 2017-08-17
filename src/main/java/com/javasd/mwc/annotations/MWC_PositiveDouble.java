/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.annotations;

import com.javasd.mwc.validators.PositiveDoubleValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author almir
 */
@Constraint( validatedBy = PositiveDoubleValidator.class )
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface MWC_PositiveDouble
{
    String message() default "{MWC_PositiveDouble}";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

}
