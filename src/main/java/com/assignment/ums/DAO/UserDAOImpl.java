package com.assignment.ums.DAO;

import com.assignment.ums.entity.User;
import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.entity.UserLoginDetails;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.update.UpdateDetails;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public UserLoginDetails login(String Username, String Password) {
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails where userName=:userName and password=:password ");
        query.setParameter("userName",Username);
        query.setParameter("password",Password);
        query.setMaxResults(1);
        UserLoginDetails user =(UserLoginDetails)query.uniqueResult();
        if(user!=null){
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            sessionFactory.getCurrentSession().save(user);
        }
        return user;
    }

    @Override
    public Integer updatePassword(String oldPassword,String newPassword,String token){
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails where token=:token and password=:oldPassword");
        query.setParameter("token",token);
        query.setParameter("oldPassword",oldPassword);
        query.setMaxResults(1);
        UserLoginDetails details=(UserLoginDetails) query.uniqueResult();
        if(details==null)
            return 0;
        details.setPassword(newPassword);
        sessionFactory.getCurrentSession().save(details);
        return 1;
    }

    @Override
    public Integer edit(UpdateDetails details) {
        UserLoginDetails loginDetails=checkUserExists(details);
        if(loginDetails==null)
            return 0;
        System.out.println(loginDetails);
        User user=getUser(loginDetails);
        user.setLastName(details.getLastName()!=null?details.getLastName():user.getLastName());
        user.setFirstName(details.getFirstName()!=null?details.getFirstName():user.getFirstName());
        user.setDateOfBirth(details.getDob()!=null?details.getDob():user.getDateOfBirth());
        user.setEmail(details.getEmail()!=null?details.getEmail():user.getEmail());
        sessionFactory.getCurrentSession().save(user);
        return 1;
    }

    private User getUser(UserLoginDetails loginDetails) {
        Query query=sessionFactory.getCurrentSession().createQuery("from User where id=:userId");
        query.setParameter("userId",loginDetails.getUserId());
        query.setMaxResults(1);
        return (User)query.uniqueResult();
    }

    private UserLoginDetails checkUserExists(UpdateDetails details) {
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails where token=:token");
        query.setParameter("token",details.getToken());
        query.setMaxResults(1);
        return (UserLoginDetails)query.uniqueResult();
    }

    @Override
    @Transactional
    public UserLoginDetails loginUsingToken(String token) {
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails where token=:token");
        query.setParameter("token",token);
        query.setMaxResults(1);
        UserLoginDetails user =(UserLoginDetails)query.uniqueResult();
        return user;
    }

    @Override
    @Transactional
    public UserLoginDetails createUser(Request request) {
        //System.out.println(request);
        User user=new User();
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDob());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        //System.out.println(user);
        sessionFactory.getCurrentSession().save(user);

        UserLoginDetails loginDetails=new UserLoginDetails();
        loginDetails.setUserId(user.getId());
        loginDetails.setUserName(request.getUserName());
        loginDetails.setPassword(createPassword());
        loginDetails.setAdmin(false);
        sessionFactory.getCurrentSession().save(loginDetails);
        return loginDetails;
    }

    @Override
    public List<UserLoginDetails> getAllUsers() {
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails");
        List<UserLoginDetails> userLoginDetails= (List<UserLoginDetails>) query.getResultList();
        return userLoginDetails;
    }

    @Override
    public User getUserDetails(String userName) {
        Query query=sessionFactory.getCurrentSession().createQuery("from UserLoginDetails where username=:username");
        query.setParameter("username",userName);
        query.setMaxResults(1);
        UserLoginDetails loginDetails=(UserLoginDetails) query.uniqueResult();
        if(loginDetails==null)
            return null;
        query=sessionFactory.getCurrentSession().createQuery("from User where id=:userId");
        query.setParameter("userId",loginDetails.getUserId());
        query.setMaxResults(1);
        return (User) query.uniqueResult();
    }

    @Override
    public void persitActivity(UserActivityLog activityLog) {
        sessionFactory.getCurrentSession().save(activityLog);
    }

    private String createPassword() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers="1234567890";
        String specialCharacters = "!@#$";
        String combinedChars =  alphabets + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[8];

        password[0] = alphabets.charAt(random.nextInt(alphabets.length()));
        password[1] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[2] = numbers.charAt(random.nextInt(numbers.length()));
        for(int i = 3; i< 8 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }
}
