package com.dmcliver.donateme;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * Marker annotation to inform that the method is intended to be overridden if needed or wanted to i.e.
 * is intended to be similar to the virtual keyword in other programming languages
 * @author danielmcliver
 */
@Target(METHOD)
public @interface Overridable {

}
