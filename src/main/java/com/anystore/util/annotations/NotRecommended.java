package com.anystore.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Using markAccountDeleted(param) will almost always be more correct
 * as it will not delete the actual entry from the database and instead will
 * change the user status to UserStatus.DELETED.
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotRecommended {
}
