package com.quiet.onterview.community.repository.querydsl;

import com.quiet.onterview.community.entity.Article;
import java.util.List;

public interface ArticleQueryRepository {

    List<Article> searchByCategorySortByOrder(Long memberId, String order, String category, String query);
}
