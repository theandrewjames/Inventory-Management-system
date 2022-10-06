package demo2.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part>allParts = FXCollections.observableArrayList();
    private static ObservableList<Product>allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart adds a new part to allParts observablelist
     */
    public static void addPart(Part newPart) {allParts.add(newPart);}

    /**
     *
     * @param newProduct adds a new product to allProducts observablelist
     */
    public static void addProduct(Product newProduct) {allProducts.add(newProduct);}

    /**
     *
     * @param partId looks up a part based on partId in allParts
     * @return that part as tempPart
     */
    public static Part lookupPart(int partId) {
        Part tempPart = null;
        for(Part part : allParts) {
            if(partId == part.getId()) {
                tempPart = part;
            }
        }
        return tempPart;
    }

    /**
     *
     * @param productId lookup product based on productId in allProducts
     * @return that part as tempProduct
     */
    public static Product lookupProduct(int productId) {
        Product tempProduct = null;
        for(Product product : allProducts) {
            if(productId == product.getId()) {
                tempProduct = product;
            }
        }
        return tempProduct;
    }

    /**
     *
     * @param partName adds found parts to an observablelist
     * @return observablelist results
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     *
     * @param productName adds found products to an observablelist
     * @return observablelelist results
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * updates parts by providing index and the part to update
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updated products by providing index and product ot update
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes a part from allParts observablelist
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else return false;
    }

    /**
     * deletes a product from allProducts observablelist
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else return false;
    }

    /**
     *
     * @return Returns allParts observable list
     */
    public static ObservableList<Part> getAllParts(){return allParts;}

    /**
     *
     * @return returns allProducts observable list
     */
    public static ObservableList<Product>getAllProducts() {return allProducts;}
}
