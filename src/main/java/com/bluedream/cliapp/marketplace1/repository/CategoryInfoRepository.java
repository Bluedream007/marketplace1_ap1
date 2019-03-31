package com.bluedream.cliapp.marketplace1.repository;



import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.bluedream.cliapp.marketplace1.domain.CategoryInfo;
import com.bluedream.cliapp.marketplace1.domain.ListInfo;


@Repository
public interface CategoryInfoRepository extends JpaRepository<CategoryInfo, String> {

		
	CategoryInfo findByCategoryName(String categoryName);
		
}
