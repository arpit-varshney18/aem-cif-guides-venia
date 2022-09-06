package com.venia.core.models.commerce;

import com.adobe.cq.commerce.core.components.models.categorylist.FeaturedCategoryList;
import com.adobe.cq.commerce.core.components.models.categorylist.FeaturedCategoryListItem;
import com.adobe.cq.commerce.core.components.models.retriever.AbstractCategoriesRetriever;
import com.adobe.cq.commerce.magento.graphql.CategoryTree;
import com.adobe.cq.commerce.magento.graphql.CategoryTreeQuery;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Model(adaptables = SlingHttpServletRequest.class, adapters = MyCategoryCarousel.class, resourceType = MyCategoryCarouselImpl.RESOURCE_TYPE)
public class MyCategoryCarouselImpl implements MyCategoryCarousel {

    protected static final String RESOURCE_TYPE = "venia/components/commerce/categorycarousel";

    @Self
    @Via(type = ResourceSuperType.class)
    private FeaturedCategoryList categoryList;

    @ScriptVariable
    private ValueMap properties;

    private AbstractCategoriesRetriever categoriesRetriever;

    @PostConstruct
    public void initModel() {
        categoriesRetriever = categoryList.getCategoriesRetriever();

        //Extending the current functionality by adding product count to the query
        if (Objects.nonNull(categoriesRetriever)){
            categoriesRetriever.extendCategoryQueryWith(CategoryTreeQuery::productCount);
        }
    }

    @Override
    public int getTotalCount() {
        int totalCount = 0;
        if (categoriesRetriever != null){
            List<CategoryTree> categories = this.categoriesRetriever.fetchCategories();

            for (CategoryTree category : categories) {
                Integer count = category.getProductCount();
                if (count != null) {
                    totalCount += count;
                }
            }
        }
        return totalCount;
    }

    @Override
    public List<CategoryTree> getCategories() {
        return categoryList.getCategories();
    }

    @Override
    public List<FeaturedCategoryListItem> getCategoryItems() {
        return categoryList.getCategoryItems();
    }

    @Override
    public AbstractCategoriesRetriever getCategoriesRetriever() {
        return categoriesRetriever;
    }

    @Override
    public boolean isConfigured() {
        return categoryList.isConfigured();
    }

    @Override
    public String getTitleType() {
        return categoryList.getTitleType();
    }

    @Override
    public String getLinkTarget() {
        return categoryList.getLinkTarget();
    }
}
