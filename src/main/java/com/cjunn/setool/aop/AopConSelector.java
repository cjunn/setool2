package com.cjunn.setool.aop;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

public class AopConSelector extends AdviceModeImportSelector<EnableAopMethod> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                return getAspectJImports();
            default:
                return null;
        }
    }
    private String []getProxyImports(){
        return new String[]{AutoProxyRegistrar.class.getName(),AopConfig.class.getName()};
    }

    private String[] getAspectJImports(){
        throw new IllegalArgumentException("暂不支持AspectJ");
    }
}
