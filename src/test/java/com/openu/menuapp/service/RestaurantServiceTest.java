/**
 * menuapp
 * 8 ���� 2013 22:06:12
 * RestaurantServiceTest.java
 *
 * Lior Negrin ID: 040829780
 * Nir Barel ID: 032483372
 */
package com.openu.menuapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openu.menuapp.entity.AdditionalOption;
import com.openu.menuapp.entity.Address;
import com.openu.menuapp.entity.Dish;
import com.openu.menuapp.entity.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/hibernateContext.xml"})
public class RestaurantServiceTest {

	@Autowired
    private RestaurantService restaurantService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private AdditionalOptionService additionalOptionService;
	
	@Test
    public void testSave() throws Exception {
    	String uuid = createObject();
        Restaurant found = restaurantService.findByUUID(uuid);
        assertEquals(uuid, found.getUuid());
    }

    static int i = 1;
    
    private String createObject() {
    	try
    	{
	    	Restaurant restaurant = new Restaurant("","La Gardia",new Address("","Tel Aviv666666","Main",6,4,"CP",5,"2",323232),"054-23325654","nir",true);
	        restaurantService.saveOrUpdate(restaurant);

	        Dish dish = new Dish("","havita","nice",50,restaurant);
	    	Dish dish2 = new Dish("","fish","with cola",550,restaurant);
	        Dish dish3 = new Dish("","pizza","hot",505,restaurant);
	        
	        dishService.saveOrUpdate(dish);
	        dishService.saveOrUpdate(dish2);
	        dishService.saveOrUpdate(dish3);
	     	        
	        AdditionalOption adOpt = new AdditionalOption("","gvina");
	    	AdditionalOption adOpt2 = new AdditionalOption("","egg");
	    	AdditionalOption adOpt3 = new AdditionalOption("","cola");

	    	additionalOptionService.saveOrUpdate(adOpt);
	    	additionalOptionService.saveOrUpdate(adOpt2);
	    	additionalOptionService.saveOrUpdate(adOpt3);
	        
	    	dish.addAdditionalOption(adOpt);
	        dish.addAdditionalOption(adOpt2);
	        dish.addAdditionalOption(adOpt3);
	        
	        dish2.addAdditionalOption(adOpt);
	        dish2.addAdditionalOption(adOpt2);
	        dish2.addAdditionalOption(adOpt3);
	        
	        dish3.addAdditionalOption(adOpt3);
	  	    
	        dishService.saveOrUpdate(dish);
	        dishService.saveOrUpdate(dish2);
	        dishService.saveOrUpdate(dish3);
	        
	        
	        restaurant.addDish(dish);
	        restaurant.addDish(dish2);
	        restaurant.addDish(dish3);
	        restaurantService.saveOrUpdate(restaurant);

	        return restaurant.getUuid();
	    }
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	return null;
    }
	
    @Test
    public void testDelete() throws Exception {
    	String uuid = createObject();
        assertNotNull(restaurantService.findByUUID(uuid));
        restaurantService.delete(uuid);
    }

    @Test
    public void testUpdate() throws Exception {
    	String uuid = createObject();
    	Restaurant obj = restaurantService.findByUUID(uuid);
        assertNotNull(obj);
        obj.setName("newUserName");
        restaurantService.saveOrUpdate(obj);

        Restaurant found = restaurantService.findByUUID(uuid);
        assertNotNull(found);
        assertEquals("newUserName", found.getName());
    }
}
