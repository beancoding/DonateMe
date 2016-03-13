package com.dmcliver.donateme;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * Marker annotation to mark that the method is intended to be overriden if needed or wanted to i.e.
 * is equivalent to the virtual keyword in C++
 * @author danielmcliver
 */
@Target(METHOD)
public @interface Overridable {

}
