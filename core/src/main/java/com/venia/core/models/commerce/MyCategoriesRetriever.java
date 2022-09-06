package com.venia.core.models.commerce;

import com.adobe.cq.commerce.core.components.client.MagentoGraphqlClient;
import com.adobe.cq.commerce.core.components.models.retriever.AbstractCategoriesRetriever;
import com.adobe.cq.commerce.magento.graphql.CategoryTreeQueryDefinition;

public class MyCategoriesRetriever extends AbstractCategoriesRetriever {
    MyCategoriesRetriever(MagentoGraphqlClient client) {
        super(client);
    }

    protected CategoryTreeQueryDefinition generateCategoryQuery() {
        CategoryTreeQueryDefinition categoryTreeQueryDefinition = (q) -> {
            q.uid().name().urlKey().urlPath().position().image();
            if (this.categoryQueryHook != null) {
                this.categoryQueryHook.accept(q);
            }

        };
        return categoryTreeQueryDefinition;
    }
}