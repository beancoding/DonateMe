package com.dmcliver.donateme;

import static com.dmcliver.donateme.WebConstants.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.dmcliver.donateme.models.ProductModel;

/**
 * Product coordinator test aspect for throwing an exception on ProductService.createProduct with matching arg from Test class
 * @param productModel - The argument to match
 */
@Aspect
public class TestAspect {

	/**
	 * Product coordinator test join-point aspect advisor for throwing an exception on ProductService.createProduct with matching arg from Test class
	 * @param productModel - The argument to match
	 */
	@Before("productServiceCreateProductMethodWithMatchingArgPointCut(productModel)")
	public void productCoordinatorTestJoinPoint(ProductModel productModel, JoinPoint jp) throws CommonCheckedException {
		throw new CommonCheckedException(new Exception());
	}
	
	/**
	 * The point-cut for determining which point to apply the advisors advice to the join-point
	 */
	@Pointcut(
		"execution(* com.dmcliver.donateme.services.ProductService*.createProduct(*,..,com.dmcliver.donateme.models.ProductModel,*))" +
		" && args(*,..,productModel,*) && if()"
	)
	public static boolean productServiceCreateProductMethodWithMatchingArgPointCut(ProductModel productModel) {
		
		if(
			productModel == null || !(
					
				modelName.equals(productModel.getModelName()) &&
				description.equals(productModel.getDescription()) &&
				brand.equals(productModel.getBrand()) &&
				newCategory.equals(productModel.getNewCategory())
			)
		)
			return false;
		
		return true;
	}
}
