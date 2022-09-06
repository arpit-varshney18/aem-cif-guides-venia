package com.venia.core.models.commerce;

import com.adobe.cq.commerce.core.components.models.categorylist.FeaturedCategoryList;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface MyCategoryCarousel extends FeaturedCategoryList {
    public int getTotalCount();
}
