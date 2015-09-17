package org.brutusin.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JsonProperty {

    public String title() default "";

    public String description() default "";

    public boolean required() default false;

    /**
     * Json expression of the default value
     * @return 
     */
    public String defaultJsonExp() default "";

    /**
     * Json array expression of the list of allowed values
     *
     * @return
     */
    public String values() default "";
    
    /**
     * Name of the public method static in the class hierarchy returning a dynamic
     * (runtime) list of allowed values. Takes precedence over values()
     *
     * @return
     */
    public String valuesMethod() default "";
}
