package com.bluedream.cliapp.marketplace1.repository;



import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bluedream.cliapp.marketplace1.domain.ListInfo;
import com.bluedream.cliapp.marketplace1.domain.UserInfo;


@Repository
public interface ListInfoRepository extends JpaRepository<ListInfo, Long> {
		
	ListInfo findByListingId(long listingId);
	
	//ListInfo findByUserInfoListingId(UserInfo userInfo, long listingId);
	@Query(value="SELECT * FROM list_info t Where t.category_name = ?1 Order BY price desc",	
		   nativeQuery = true)
	List<ListInfo> qrySortByPriceDesc(String category_name);
	
	@Query(value="SELECT * FROM list_info t Where t.category_name = ?1 Order BY price asc",	
			   nativeQuery = true)
		List<ListInfo> qrySortByPriceAsc(String category_name);
	
	@Query(value="SELECT * FROM list_info t Where t.category_name = ?1 Order BY create_time desc",	
			   nativeQuery = true)
		List<ListInfo> qrySortByCreatetimeDesc(String category_name);
	
	@Query(value="SELECT * FROM list_info t Where t.category_name = ?1 Order BY create_time asc",	
			   nativeQuery = true)
		List<ListInfo> qrySortByCreatetimeAsc(String category_name);
		
	@Query(value="SELECT T2.category_name, COUNT(T2.category_name) " + 
			     "FROM list_info T2  GROUP BY T2.category_name " + 
			     "HAVING COUNT(T2.category_name)=( " + 
			        "SELECT MAX(TTT.mycount) " + 
			        "FROM ( " + 
			                "SELECT T1.category_name, COUNT(T1.category_name) as mycount " + 
			                "FROM list_info T1 " + 
			                "GROUP BY T1.category_name) AS TTT)",	
			   nativeQuery = true)
		ListInfo fetchTopCategoryInfo();
		
	
}
