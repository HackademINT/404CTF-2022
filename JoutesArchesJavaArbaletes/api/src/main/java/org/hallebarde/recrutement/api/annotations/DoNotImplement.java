package org.hallebarde.recrutement.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated interface should not be implemented by plugins, and doing so is unsupported.
 * (Meaning things will crash).
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DoNotImplement {
}
