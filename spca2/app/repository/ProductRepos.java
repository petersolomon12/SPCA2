package repository;

import com.google.inject.Inject;
import models.Product;
import models.User;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class ProductRepos {

    private final JPAApi jpaApi;

    @Inject
    public ProductRepos(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Product insertProduct(Product product){
        return wrap(entityManager -> insertProduct(entityManager, product));
    }
     public Product updateProduct(Product product){
            return wrap(entityManager -> updateProduct(entityManager, product));
        }

    public Product deleteProduct(Product product){
        return wrap(entityManager -> deleteProduct(entityManager, product));
    }


    public Product getProduct(Product product){
        return wrap(entityManager -> getProduct(entityManager, product.getId()));
    }

    public Product getProductByName(String name, User user){
        return wrap(entityManager -> getProductByName(entityManager, name, user));
    }

    public List<Product> allAdminStocks(UUID userid){
        return wrap(entityManager -> allAdminStocks(entityManager, userid));
    }

    public List <Product> allProducts(){
        return wrap(this::allProducts);
    }




    private Product insertProduct(EntityManager entityManager, Product product) {
        entityManager.persist(product);
        return product;
    }

    private Product updateProduct(EntityManager entityManager, Product product){
        entityManager.merge(product);
        return product;
    }
    private Product deleteProduct(EntityManager entityManager, Product product) {
        entityManager.remove(entityManager.merge(product));
        return product;
    }

    private List<Product> allAdminStocks(EntityManager entityManager, UUID userid) {
        List<Product> products = null;

        products =  entityManager.createQuery("select u from Product u where user.id =: userid", Product.class)
                .setParameter("userid" ,userid).getResultList();
        return products.isEmpty() ? null : products;
    }
   private Product getProduct(EntityManager entityManager, UUID id) {
        List<Product> product = null;
        product =  entityManager.createQuery("select u from Product u where id =: id", Product.class)
                .setParameter("id" ,id).getResultList();
        return product.isEmpty() ? null : product.get(0) ;
    }

    private List <Product> allProducts(EntityManager entityManager) {
        List <Product> products;
        products = entityManager.createQuery("select p from Product p").getResultList();
        return products;
    }



    public Product getProductByName(EntityManager entityManager, String name, User user) {
        List<Product> product = null;
        product =  entityManager.createQuery("select u from Product u where name =: name AND user.id =: userid", Product.class)
                .setParameter("userid", user.getId()).setParameter("name" ,name).getResultList();
        //if result is empty then just return otherwise get me the first user in the list
        return product.isEmpty() ? null : product.get(0) ;
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

}
