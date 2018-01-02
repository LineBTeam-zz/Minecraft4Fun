package me.minebuilders.clearlag.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.minebuilders.clearlag.config.ConfigValueType;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ConfigValue
{
  String path() default "";
  
  ConfigValueType valueType() default ConfigValueType.PRIMITIVE;
}

