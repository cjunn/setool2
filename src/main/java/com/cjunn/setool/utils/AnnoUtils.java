package com.cjunn.setool.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnoUtils {
    public static List<? extends Annotation> lookupAnnoFromMethod(Method method, Class<? extends Annotation> anno){
        return lookupAnnoFromArray(method.getDeclaredAnnotations(),anno);
    }
    public static <T> List<? extends Annotation> lookupAnnoFromClz(Class clz,Class<? extends Annotation> anno){
        return lookupAnnoFromArray(clz.getDeclaredAnnotations(),anno);
    }
    public static List<? extends Annotation> lookupAnnoFromAnno(Class clz,Class<? extends Annotation> anno){
        return lookupAnnoFromArray(clz.getDeclaredAnnotations(),anno);
    }
    private static List<? extends Annotation> lookupAnnoFromArray(Annotation[] declaredAnnotations, Class<? extends Annotation> anno){
        List<Annotation> annotations=new ArrayList<>();
        for(Annotation annotation:declaredAnnotations){
            Class<? extends Annotation> aClass = annotation.annotationType();
            if(aClass.equals(anno)){
                annotations.add(annotation);
            }else if(aClass.isAnnotationPresent(anno)){
                annotations.addAll(lookupAnnoFromAnno(aClass,anno));
            }
        }
        return annotations;
    }

}
