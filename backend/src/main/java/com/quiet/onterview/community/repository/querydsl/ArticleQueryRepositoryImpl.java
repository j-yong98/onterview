package com.quiet.onterview.community.repository.querydsl;

import static com.quiet.onterview.community.entity.QArticle.article;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiet.onterview.community.entity.Article;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> searchByCategorySortByOrder(Long memberId, String order, String category, String query) {
        return jpaQueryFactory
                .selectFrom(article)
                .where(eqMemberId(memberId),
                        containsTitleFilter(category, query),
                        containsContentFilter(category,query))
                .orderBy(createArticleOrderSpecifier(order))
                .fetch();
    }

    private BooleanExpression eqMemberId(Long memberId) {
        if(memberId==null) {
            return null;
        }
        return article.member.memberId.eq(memberId);
    }

    private OrderSpecifier createArticleOrderSpecifier(String order) {
        OrderSpecifier orderSpecifier = null;
        if(order.equals("recent") || order==null) {
            orderSpecifier = new OrderSpecifier(Order.DESC, article.createAt);
        } else if(order.equals("like")) {
            orderSpecifier = new OrderSpecifier(Order.DESC, article.likeCount);
        } else if(order.equals("comment")) {
            orderSpecifier = new OrderSpecifier(Order.DESC, article.commentCount);
        }
        return orderSpecifier;
    }

    private BooleanExpression containsTitleFilter(String category, String content) {
        if(!category.equals("title")) {
            return null;
        }
        return article.title.contains(content);
    }

    private BooleanExpression containsContentFilter(String category, String content) {
        if(!category.equals("content")) {
            return null;
        }
        return article.content.contains(content);
    }
}
