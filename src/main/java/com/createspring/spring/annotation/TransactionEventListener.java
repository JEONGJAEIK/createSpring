package com.createspring.spring.annotation;

import com.createspring.spring.transaction.TransactionPhase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionEventListener {

    TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;
}
