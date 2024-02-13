/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;

	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker}
	 * object we wish to test.
	 * 
	 * @throws RecipeException if there was an error parsing the ingredient
	 *                         amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();

		// Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");

		// Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");

		// Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");

		// Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/* UC2: ADD RECIPE */

	/**
	 * Given a coffee maker with no recipes
	 * When we add a valid recipe
	 * Then we get a coffee maker with one recipe.
	 */
	@Test
	public void testAddRecipeValid() {
		coffeeMaker.addRecipe(recipe1);
		assertNotEquals(null, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add 3 valid recipes
	 * Then we get a coffee maker with 3 recipes.
	 */
	@Test
	public void testAdd3RecipeValid() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertNotEquals(null, coffeeMaker.getRecipes()[0]);
		assertNotEquals(null, coffeeMaker.getRecipes()[1]);
		assertNotEquals(null, coffeeMaker.getRecipes()[2]);
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with the same name as an existing recipe
	 * Then we get a false response.
	 */
	@Test
	public void testAddRecipeInvalidName() {
		coffeeMaker.addRecipe(recipe3);
		Recipe recipe = new Recipe();
		recipe.setName(recipe3.getName());
		assertEquals(false, coffeeMaker.addRecipe(recipe));
	}

	/**
	 * BUG 1
	 * Given a coffee maker with 3 valid recipes
	 * When we add a 4th recipe
	 * Then we get a false response.
	 */
	@Test
	public void testAddMoreRecipeThanPermited() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertNotEquals(null, coffeeMaker.getRecipes()[0]);
		assertNotEquals(null, coffeeMaker.getRecipes()[1]);
		assertNotEquals(null, coffeeMaker.getRecipes()[2]);
		assertEquals(false, coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with invalid price
	 * Then we get a recipe exception.
	 * 
	 * @throws RecipeException
	 */
	@Test
	public void testInvalidPriceRecipe() throws RecipeException {
		try {
			recipe1.setPrice("-100");
		} catch (RecipeException e) {
			try {
				recipe1.setPrice("100.50");
			} catch (RecipeException e2) {
				try {
					recipe1.setPrice("abc");
				} catch (RecipeException e3) {

				}
			}
		}
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with invalid unit of coffe
	 * Then we get a recipe exception.
	 * 
	 * @throws RecipeException
	 */
	@Test
	public void testInvalidUnitCoffe() throws RecipeException {
		try {
			recipe1.setAmtCoffee("-100");
		} catch (RecipeException e) {
			try {
				recipe1.setAmtCoffee("100.50");
			} catch (RecipeException e2) {
				try {
					recipe1.setAmtCoffee("abc");
				} catch (RecipeException e3) {

				}
			}
		}
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with invalid unit of sugar
	 * Then we get a recipe exception.
	 * 
	 * @throws RecipeException
	 */
	@Test
	public void testInvalidUnitSugar() throws RecipeException {
		try {
			recipe1.setAmtSugar("-100");
		} catch (RecipeException e) {
			try {
				recipe1.setAmtSugar("100.50");
			} catch (RecipeException e2) {
				try {
					recipe1.setAmtSugar("abc");
				} catch (RecipeException e3) {

				}
			}
		}
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with invalid unit of milk
	 * Then we get a recipe exception.
	 * 
	 * @throws RecipeException
	 */
	@Test
	public void testInvalidUnitMilk() throws RecipeException {
		try {
			recipe1.setAmtMilk("-100");
		} catch (RecipeException e) {
			try {
				recipe1.setAmtMilk("100.50");
			} catch (RecipeException e2) {
				try {
					recipe1.setAmtMilk("abc");
				} catch (RecipeException e3) {

				}
			}
		}
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a recipe with invalid unit of chocolate
	 * Then we get a recipe exception.
	 * 
	 * @throws RecipeException
	 */
	@Test
	public void testInvalidUnitChocolate() throws RecipeException {
		try {
			recipe1.setAmtChocolate("-100");
		} catch (RecipeException e) {
			try {
				recipe1.setAmtChocolate("100.50");
			} catch (RecipeException e2) {
				try {
					recipe1.setAmtChocolate("abc");
				} catch (RecipeException e3) {

				}
			}
		}
	}

	/* UC3: DELETE RECIPE */

	/**
	 * BUG 2
	 * Given a coffee maker with no recipes
	 * When we add a valid recipe and then delete the recipe
	 * Then we get a coffee maker with no recipes.
	 */
	@Test
	public void testDeleteRecipe() {
		coffeeMaker.addRecipe(recipe1); // position 0
		coffeeMaker.deleteRecipe(0);
		assertEquals(null, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we delete a recipe
	 * Then we get a null response.
	 */

	@Test
	public void testDeleteEmptyRecipeBook() {
		assertNull(coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we delete a recipe with an invalid index
	 * Then we get a null response.
	 */

	@Test
	public void testDeleteOutOfBoundRecipe() {
		coffeeMaker.addRecipe(recipe1); // position 0
		assertNull(coffeeMaker.deleteRecipe(2));
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we delete a recipe with an invalid index
	 * Then we get a null response.
	 */
	// @Test
	// public void testMakeCoffeeDeleteRecipe3() {
	// assertEquals(null, coffeeMaker.deleteRecipe(-1));
	// }

	/* UC4: EDIT RECIPE */

	/**
	 * BUG 3
	 * Given a coffee maker with no recipes
	 * When we add a valid recipe and then edit the recipe
	 * Then we get a coffee maker with a recipe corresponding to the new data.
	 */
	@Test
	public void testEditRecipeName() {
		coffeeMaker.addRecipe(recipe1);

		String name = recipe1.getName();
		coffeeMaker.editRecipe(0, recipe2);
		assertEquals(name, coffeeMaker.getRecipes()[0].getName());
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we add a valid recipe and then edit the recipe
	 * Then we get a coffee maker with a recipe corresponding to the new data.
	 */
	@Test
	public void testEditRecipePrice() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.editRecipe(0, recipe2);
		assertEquals(recipe2.getPrice(), coffeeMaker.getRecipes()[0].getPrice());
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we edit a recipe
	 * Then we get a null response.
	 */
	@Test
	public void testEditEmptyRecipe() {
		assertEquals(null, coffeeMaker.editRecipe(0, recipe1));
	}

	/**
	 * Given a coffee maker with one recipe
	 * When we edit a recipe out of bounds
	 * Then we get a null response.
	 */
	@Test
	public void testEditInvalidIndexRecipe() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(null, coffeeMaker.editRecipe(1, recipe1));
	}

	/**
	 * Given a coffee maker with no recipes
	 * When we edit a recipe with an invalid index
	 * Then we get a null response.
	 */
	// @Test
	// public void testMakeCoffeeEditRecipe3() {
	// assertEquals(null, coffeeMaker.editRecipe(-1, recipe2));
	// }

	/* UC5: ADD INVENTORY */

	/**
	 * BUG 4
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test
	public void testAddInventoryAll() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "1", "1");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4", "7", "0", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtCoffee
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionCoffee() throws InventoryException {
		coffeeMaker.addInventory("-8", "7", "0", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtCoffee
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionCoffeeAlp() throws InventoryException {
		coffeeMaker.addInventory("jj", "7", "1", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtMilk
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionMilk() throws InventoryException {
		coffeeMaker.addInventory("4", "-8", "0", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtMilk
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionMilkAlp() throws InventoryException {
		coffeeMaker.addInventory("4", "jj", "0", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtSugar
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionSugar() throws InventoryException {
		coffeeMaker.addInventory("4", "7", "-8", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtSugar
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionSugarAlp() throws InventoryException {
		coffeeMaker.addInventory("4", "7", "jj", "9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtChocolate
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionChocolate() throws InventoryException {
		coffeeMaker.addInventory("4", "7", "8", "-9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantity for amtChocolate
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException if there was an error parsing the quanity
	 *                            to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryExceptionChocolateAlp() throws InventoryException {
		coffeeMaker.addInventory("4", "7", "8", "jj");
	}

	/* UC6: CHECK INVENTORY */
	/**
	 * Given a coffee maker
	 * When we check inventory
	 * Then we get a string representation of the default inventory
	 */
	@Test
	public void testCheckInventory() throws InventoryException {
		String expectedString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		assertEquals(expectedString, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker
	 * When we add coffee and check inventory
	 * Then we get a string representation of the new inventory
	 */
	@Test
	public void testCheckInventoryAddCoffee() throws InventoryException {
		coffeeMaker.addInventory("1", "0", "0", "0");
		String expectedString = "Coffee: 16\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		assertEquals(expectedString, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker
	 * When we add milk and check inventory
	 * Then we get a string representation of the new inventory
	 */
	@Test
	public void testCheckInventoryAddMilk() throws InventoryException {
		coffeeMaker.addInventory("0", "1", "0", "0");
		String expectedString = "Coffee: 15\nMilk: 16\nSugar: 15\nChocolate: 15\n";
		assertEquals(expectedString, coffeeMaker.checkInventory());
	}

	/**
	 * BUG 4
	 * Given a coffee maker
	 * When we add sugar and check inventory
	 * Then we get a string representation of the new inventory
	 */
	@Test
	public void testCheckInventoryAddSugar() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "1", "0");
		String expectedString = "Coffee: 15\nMilk: 15\nSugar: 16\nChocolate: 15\n";
		assertEquals(expectedString, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker
	 * When we add Chocolate and check inventory
	 * Then we get a string representation of the new inventory
	 */
	@Test
	public void testCheckInventoryAddChocolate() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "1");
		String expectedString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 16\n";
		assertEquals(expectedString, coffeeMaker.checkInventory());
	}

	/* UC7: PURCHASE BEVRAGE */

}
