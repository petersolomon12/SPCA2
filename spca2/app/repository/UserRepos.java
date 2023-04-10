package repository;

import com.google.inject.Inject;
import models.User;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class UserRepos {
    private final JPAApi jpaApi;

    @Inject
    public UserRepos(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public User insertUser(User user){
        return wrap(entityManager -> insertUser(entityManager, user));
    }
    public User getUserByEmail(String email){
        return wrap(entityManager -> getUserByEmail(entityManager, email));
    }

    public User getUser(UUID id){
        return wrap(entityManager -> getUser(entityManager, id));
    }
    public User deleteUser(User user){
        return wrap(entityManager -> deleteUser(entityManager, user));
    }
    public User updateUser(User user){
        return wrap(entityManager -> updateUser(entityManager, user));
    }

    //Returns all users in Database
    public List <User> allUsers(){
        return wrap(this::allUsers);
    }

    private User insertUser(EntityManager entityManager, User user) {
        entityManager.persist(user);
        return user;
    }

    private User getUser(EntityManager entityManager, UUID id) {
        List<User> user = null;
        user =  entityManager.createQuery("select u from User u where id =: id", User.class)
                .setParameter("id" ,id).getResultList();
        return user.isEmpty() ? null : user.get(0) ;
    }

    private User updateUser(EntityManager entityManager, User user){
        entityManager.merge(user);
        return user;
    }

    private List <User> allUsers(EntityManager entityManager) {
        List <User> users;
        users = entityManager.createQuery("select p from User p").getResultList();
        return users;
    }

    private User deleteUser(EntityManager entityManager, User user){
        entityManager.remove(entityManager.merge(user));
        return user;
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    //Returns user by email, where email = String email.
    public User getUserByEmail(EntityManager entityManager, String email) {
        List<User> user = null;
        user =  entityManager.createQuery("select u from User u where email =: email", User.class)
                .setParameter("email" ,email).getResultList();
        //if result is empty then just return otherwise get me the first user in the list
        return user.isEmpty() ? null : user.get(0) ;
    }
}
