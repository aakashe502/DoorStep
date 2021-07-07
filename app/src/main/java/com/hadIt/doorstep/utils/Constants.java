package com.hadIt.doorstep.utils;

import java.util.HashMap;

public class Constants {
    public static final int dbVersion = 1;

    public Constants() {
        products.put("VEGETABLES & FRUITS", new String[]{"Vegetables",
                "Fruits",
                "Others"});

        products.put("GROCERY", new String[]{"Bakery and Bread",
                "Rice and Pulses",
                "Spices",
                "Oils, Sauces, Salad Dressings, and Condiments",
                "Cereals and Breakfast Foods",
                "Soups and Canned Goods",
                "Frozen Foods",
                "Dairy, Cheese, and Eggs",
                "Others"});

        products.put("BEVERAGES", new String[]{"Drinking water",
                "Cold Drinks",
                "Flavoured Cola",
                "Others"});

        products.put("NON_VEG", new String[]{"Meat",
                "Chicken",
                "Fish",
                "Others"});

        products.put("CAKES & BAKERY", new String[]{"Cake",
                "Chocolates",
                "Pastries",
                "Muffins",
                "Bread and Buns",
                "Ice Cream Raw materials",
                "Bakery",
                "Others"});

        products.put("BOOKS & STATIONERY", new String[]{"Notebook",
                "Pen & Pencil",
                "Eraser and Sharpener",
                "Stickers and NameChits",
                "Boxes",
                "Covers and Envelopes",
                "Gift and Chart Paper",
                "Tape and Stapler",
                "Comics",
                "Others"});

        products.put("NUTRITION & HEALTHCARE", new String[]{"Tablets",
                "Syrups",
                "Injection",
                "Protein and Powder",
                "Sanitary Pads",
                "Others"});

        products.put("BEAUTY AND PERSONAL CARE", new String[]{"Baby Products",
                "Lotions oil power and cream",
                "Spray and Fragrance",
                "Hair",
                "Makeup",
                "Oral Hygiene",
                "Shaving Preparations",
                "Skin Care",
                "Manicure",
                "Pedicure",
                "Others"});

        products.put("HOME-MADE", new String[]{"Pickle",
                "Spices",
                "Others"});

        products.put("DAIRY PRODUCTS", new String[]{"Milk",
                "Paneer",
                "Curd",
                "Others"});
    }

    public static HashMap<String, String[]> products = new HashMap<>();

    public static final String FCM_KEY = "AAAA3Sz0p7I:APA91bHbnd7aabTOpzjTRPGS7h3K-1pyw0scF1F67Xzui49UDMPwOc_SQgygSHEGQ3hgWWe-3d4sEhNQ5X3vhH5f3eKU7zRTEFSp3JjJZiMNTHKUQkD3jXZzSoIDDsCtdoOptiNVH77N";
    public static final String FCM_TOPIC = "PUSH_NOTIFICATIONS";

    public static final String[] productCategories = {
            "VEGETABLES & FRUITS",
            "GROCERY", // General stores
            "BEVERAGES",
            "NON_VEG",
            "CAKES & BAKERY",
            "BOOKS & STATIONERY",
            "NUTRITION & HEALTHCARE",
            "BEAUTY AND PERSONAL CARE",
            "HOME-MADE", // Khada mashala, achar
            "DAIRY PRODUCTS"
    };
}
