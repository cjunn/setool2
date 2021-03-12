package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.dao.fluent.builder.*;
import com.cjunn.setool.dao.fluent.builder.ext.GroupByExps;
import com.cjunn.setool.dao.fluent.builder.ext.HavingExps;
import com.cjunn.setool.dao.fluent.builder.ext.OrderByExps;
import com.cjunn.setool.dao.fluent.builder.ext.PagingExps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @ClassName ExtFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:29
 * @Version
 */
public class ExtFrame extends AbstractOptFrame<TableFrame> implements ActuatorProvider {
    private RightBiExps topExps=new RightBiExps();
    private GroupByExps groupByExps;
    private RightBiExps havingInnCdtExps;
    private HavingExps havingExps;
    private OrderByExps orderByExps;
    private PagingExps pagingExps;


    private BiExps accept(BiExps left, Exps exps){
        if(exps!=null){
            return left.accept(new SpaceBiExps(exps));
        }
        return left;
    }

    public ExtFrame(TableFrame topFrame) {
        super(topFrame);
    }

    @Override
    public String exportQL() {
        BiExps accept;
        accept = accept(topExps, groupByExps);
        accept = accept(accept, havingExps);
        accept = accept(accept, orderByExps);
        accept = accept(accept, pagingExps);
        return topExps.exportQL();
    }






    public ExtFrame paging(int start ,int size){
        pagingExps = pagingExps==null?new PagingExps():pagingExps;
        pagingExps.setPageRow(start,size);
        return this;
    }

    public ExtFrame groupBy(String ...fieldName){
        groupByExps = groupByExps==null?new GroupByExps(new ArrayList<SimpleQlField>()):groupByExps;
        groupByExps.getQlFields().addAll(Arrays.asList(fieldName).stream().map(i->new SimpleQlField(i)).collect(Collectors.toList()));
        return this;
    }

    public ExtFrame having(Consumer<InnerAndOrFrame> consumer){
        havingInnCdtExps = havingInnCdtExps==null?new RightBiExps():havingInnCdtExps;
        havingExps = havingExps==null?new HavingExps(new BracketExps(havingInnCdtExps)):havingExps;
        consumer.accept(new InnerAndOrFrame(havingInnCdtExps,this.topFrame()));
        return this;
    }

    public ExtFrame sorting(String ...fieldName){
        orderByExps= orderByExps==null?new OrderByExps(new ArrayList<SimpleQlField>()):orderByExps;
        orderByExps.getQlFields().addAll(Arrays.asList(fieldName).stream().map(i->new SimpleQlField(i)).collect(Collectors.toList()));
        return this;
    }

}
