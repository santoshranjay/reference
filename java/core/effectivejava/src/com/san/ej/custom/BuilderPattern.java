package com.san.ej.custom;

import com.san.ej.custom.NutritionFacts.NutritionFactsBuilder;
/**
 * Telescoping constructor pattern - does not scale well! - Pages 11-12
 * 
 * @author santosh
 *
 */
class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class NutritionFactsBuilder {
        // Required parameters
        private final int servingSize;
        private final int servings;

        // Optional parameters - initialized to default values
        private int calories      = 0;
        private int fat           = 0;
        private int carbohydrate  = 0;
        private int sodium        = 0;

        public NutritionFactsBuilder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings    = servings;
        }

        public NutritionFactsBuilder calories(int val)
            { calories = val;      return this; }
        public NutritionFactsBuilder fat(int val)
            { fat = val;           return this; }
        public NutritionFactsBuilder carbohydrate(int val)
            { carbohydrate = val;  return this; }
        public NutritionFactsBuilder sodium(int val)
            { sodium = val;        return this; }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(NutritionFactsBuilder builder) {
        servingSize  = builder.servingSize;
        servings     = builder.servings;
        calories     = builder.calories;
        fat          = builder.fat;
        sodium       = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

	@Override
	public String toString() {
		return "NutritionFacts [servingSize=" + servingSize + ", servings="
				+ servings + ", calories=" + calories + ", fat=" + fat
				+ ", sodium=" + sodium + ", carbohydrate=" + carbohydrate + "]";
	}
    
}

public class BuilderPattern {
	 public static void main(String[] args) {
	        NutritionFacts cocaCola = new NutritionFactsBuilder(240, 8)
	        							.calories(100)
	        							.sodium(35)
	        							.carbohydrate(27)
	        							.build();
	        System.out.println(cocaCola);
	    }
}
