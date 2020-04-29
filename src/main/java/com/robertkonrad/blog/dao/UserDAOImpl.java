package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Comment;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.Role;
import com.robertkonrad.blog.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setUser(user);
        session.save(user);
        session.save(role);
    }

    @Override
    public boolean usernameAvailable(String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, username);
        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public List<List> getUsers() {
        Session session = entityManager.unwrap(Session.class);
        List<String> auth = new ArrayList<>();
        List<User> users = session.createQuery("FROM User WHERE username!='*User not exist*'", User.class).getResultList();
        for (User user : users) {
            String role = session.createQuery("SELECT authority FROM Role WHERE username='" + user.getUsername() + "'", String.class).getSingleResult();
            auth.add(role);
        }
        List<List> result = new ArrayList<List>();
        result.add(users);
        result.add(auth);
        return result;
    }

    @Override
    public void deleteUser(String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.createQuery("FROM User WHERE username='" + username + "'", User.class).getSingleResult();
        Role auth = session.createQuery("FROM Role WHERE username='" + username + "'", Role.class).getSingleResult();
        User defaultUser = session.get(User.class, "*User not exist*");
        if (defaultUser == null) {
            User newUser = new User();
            Role newAuth = new Role();
            newUser.setUsername("*User not exist*");
            newUser.setPassword("$2y$12$1T1r/sx/gr3CHkwRMoQdH.B5jcQ65iAzy9JeSX/2f8KF4VxB5kKw6");
            newUser.setEnabled(0);
            newAuth.setAuthority("USER");
            session.save(newUser);
            newAuth.setUser(newUser);
            session.save(newAuth);
            defaultUser = session.get(User.class, "*User not exist*");
        }
        List<Post> posts = session.createQuery("FROM Post WHERE author='" + user.getUsername() + "'", Post.class).getResultList();
        for (Post post : posts) {
            session.createQuery("UPDATE Post SET author='" + defaultUser.getUsername() + "' WHERE id='" + post.getId() + "'").executeUpdate();
        }
        List<Comment> comments = session.createQuery("FROM Comment WHERE author='" + user.getUsername() + "'", Comment.class).getResultList();
        for (Comment comment : comments) {
            session.createQuery("UPDATE Comment SET author='" + defaultUser.getUsername() + "' WHERE id='" + comment.getId() + "'").executeUpdate();
        }
        List<Post> modifiedPosts = session.createQuery("FROM Post WHERE lastModificatedBy='" + user.getUsername() + "'", Post.class).getResultList();
        for (Post modifiedPost : modifiedPosts) {
            session.createQuery("UPDATE Post SET lastModificatedBy='" + defaultUser.getUsername() + "' WHERE id='" + modifiedPost.getId() + "'").executeUpdate();
        }
        session.delete(auth);
        session.delete(user);
    }

    @Override
    public User getUser(String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.createQuery("FROM User WHERE username='" + username + "'", User.class).getSingleResult();
        return user;
    }

    @Override
    public void saveUpdatedUserPassword(User user, String username) {
        Session session = entityManager.unwrap(Session.class);
        User eUser = session.createQuery("FROM User WHERE username='" + username + "'", User.class).getSingleResult();
        eUser.setPassword(passwordEncoder.encode(user.getPassword()));
        session.update(eUser);
    }

    @Override
    public String getUserRole(String username) {
        Session session = entityManager.unwrap(Session.class);
        String role = session.createQuery("SELECT authority FROM Role WHERE username='" + username + "'", String.class).getSingleResult();
        return role;
    }

    @Override
    public void saveChangedUserRole(String username, String role) {
        Session session = entityManager.unwrap(Session.class);
        Role auth = session.createQuery("FROM Role WHERE username='" + username + "'", Role.class).getSingleResult();
        auth.setAuthority(role);
        session.update(auth);
    }

}
