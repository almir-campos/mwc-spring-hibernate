/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.validators;

import com.javasd.mwc.annotations.MWC_PositiveDouble;
import com.javasd.mwc.annotations.MWC_Test;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.log4j.Logger;

/**
 *
 * @author almir
 */
//@MWC_Test
// javax.validation.UnexpectedTypeException: No validator could be found for type: java.lang.Double
// This message appears if "Double" is not present in the next line
public class PositiveDoubleValidator implements ConstraintValidator<MWC_PositiveDouble, Double>
{

    private final Logger logger = Logger.getLogger( PositiveDoubleValidator.class );
    
    @Override
    public void initialize( MWC_PositiveDouble constraintAnnotation )
    {
    }

    @Override
    @MWC_Test
    public boolean isValid( Double value, ConstraintValidatorContext context )
    {
        logger.info( "#### value: " + value );
        if( value == null )
        {
            return false;
        }
        return value > 0;
    }
    
}
