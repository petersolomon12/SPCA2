package repository;

import com.google.inject.Inject;
import models.Cart;
import models.User;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class CartRepos {

    private final JPAApi jpaApi;

    @Inject
    public CartRepos(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Cart insertCart(Cart cart){
        return wrap(entityManager -> insertCart(entityManager, cart));
    }

    public Cart updateCart(Cart cart){
        return wrap(entityManager -> updateCart(entityManager, cart));
    }

    public Cart deleteCart(Cart cart){
        return wrap(entityManager -> deleteCart(entityManager, cart));
    }

    public Cart getUserCart(User user){
        return wrap(entityManager -> getUserCart(entityManager, user.getId()));
    }
    public Cart getCart(Cart cart){
        return wrap(entityManager -> getCart(entityManager, cart.getId()));
    }

    private Cart insertCart(EntityManager entityManager, Cart cart) {
        entityManager.persist(cart);
        return cart;
    }

    private Cart updateCart(EntityManager entityManager, Cart cart) {
        entityManager.merge(cart);
        return cart;
    }
    private Cart deleteCart(EntityManager entityManager, Cart cart) {
        entityManager.remove(entityManager.merge(cart));
        return cart;
    }

    private Cart getUserCart(EntityManager entityManager, UUID userid) {
        List<Cart> cart = null;
        cart =  entityManager.createQuery("select u from Cart u where user.id =: userid", Cart.class)
                .setParameter("userid" ,userid).getResultList();
        //if result is empty then just return otherwise get me the first user in the list
        return cart.isEmpty() ? null : cart.get(0) ;
    }

    private Cart getCart(EntityManager entityManager, UUID id) {
        List<Cart> cart = null;
        cart =  entityManager.createQuery("select u from Cart u where id =: id", Cart.class)
                .setParameter("id", id).getResultList();
        //if result is empty then just return otherwise get me the first user in the list
        return cart.isEmpty() ? null : cart.get(0) ;
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

}
