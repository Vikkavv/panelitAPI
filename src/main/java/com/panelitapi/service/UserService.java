package com.panelitapi.service;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.panelitapi.exception.UniqueValueException;
import com.panelitapi.model.Plan;
import com.panelitapi.model.User;
import com.panelitapi.repository.UserRepository;
import com.panelitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository userRepository;
    private PlanService planService;
    private AuthService authService;
    private PanelService panelService;

    @Autowired
    public UserService(UserRepository userRepository, PlanService planService, AuthService authService, PanelService panelService) {
        this.userRepository = userRepository;
        this.planService = planService;
        this.authService = authService;
        this.panelService = panelService;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getGeneralUserInfoByNickname(String nickname) {
        User user = null;
        try{
            user = userRepository.findUserByNickname(nickname).orElseThrow();
        }catch (NoSuchElementException e){
            throw new RuntimeException("User not found");
        }
        user.setPassword(null);
        user.setPhoneNumber(null);
        return user;
    }

    public User getGeneralUserInfoById(Long id) {
        User user = null;
        try{
            user = userRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e){
            throw new RuntimeException("User not found");
        }
        user.setPassword(null);
        user.setPhoneNumber(null);
        return user;
    }

    public boolean existsUserWithPhoneNumber(String phoneNumber) {
        boolean exists = false;
        Optional<User> opUser = userRepository.findUserByPhoneNumber(phoneNumber);
        if(opUser.isPresent())
            exists = true;
        return exists;
    }

    public boolean existsUserWithNickname(String nickname) {
        boolean exists = false;
        Optional<User> opUser = userRepository.findUserByNickname(nickname);
        if (opUser.isPresent())
            exists = true;
        return exists;
    }

    public boolean existsUserWithEmail(String email) {
        boolean exists = false;
        Optional<User> opUser = userRepository.findUserByEmail(email);
        if (opUser.isPresent())
            exists = true;
        return exists;
    }


    public Map<String, String> add(User user) {
        Map<String, String> errors = new HashMap<>();
        user.setPlan(planService.findById(user.getPlan().getId()));
        user.setPlanExpirationDate(calculatePlanExpirationDate(user.getPlan(), false));
        boolean hasErrors = false;

//        Pattern pattern = Pattern.compile("^[-A-Za-z0-9ÑñÇç@_.&%$,]{8,12}$");
//        Matcher matcher = pattern.matcher(user.getPassword());
//        if(!matcher.find()) throw new RuntimeException("Password has to be between 8 and 12 characters long and must include common letters and symbols");

        if(user.getPhoneNumber() != null){
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            try{
                PhoneNumber phoneNumber = phoneNumberUtil.parse(user.getPhoneNumber(), null);
                if(!phoneNumberUtil.isValidNumber(phoneNumber)) throw new Exception();
                user.setPhoneNumber(phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            }catch (Exception e){
                errors.put("phoneNumber", "If you provide a phone number, use a valid format");
                hasErrors = true;
            }
        }

        int nextSentence = 1;
        boolean finish = false;
        while (!finish) {
            try{
                if(nextSentence == 1 && nextSentence++ > 0) userRepository.findUserByNickname(user.getNickname()).ifPresent(u -> {throw new UniqueValueException("nickname");});
                if(nextSentence == 2 && nextSentence++ > 0) userRepository.findUserByEmail(user.getEmail()).ifPresent(u -> {throw new UniqueValueException("email");});
                if(nextSentence == 3 && nextSentence++ > 0) userRepository.findUserByPhoneNumber(user.getPhoneNumber()).ifPresent(u -> {throw new UniqueValueException("phoneNumber");});
                if(nextSentence == 4) finish = true;
            }catch(UniqueValueException e){
                String errorMessage = "A user with that "+ Utils.naturalizeCamelCase(e.getMessage() + " already exists");
                if(!errors.containsKey(e.getMessage())) errors.put(e.getMessage(), errorMessage);
                else errors.put(e.getMessage(), errors.get(e.getMessage()) + ";" + errorMessage);
                hasErrors = true;
            }
        }
        //Encrypt the password
        user.setPassword(authService.testAndEncodePassword(user.getPassword()));
        if(!hasErrors){
            userRepository.save(user);
            errors.put("errors", null);
        }
        return errors;
    }

    public boolean updatePlan(User user, boolean isMonthly) {
        user.setPlan(planService.findById(user.getPlan().getId()));
        user.setPlanExpirationDate(calculatePlanExpirationDate(user.getPlan(), isMonthly));
        userRepository.save(user);
        return true;
    }

    public void update(User user) {
        User userPersisted = findById(user.getId());

        userPersisted.setName(user.getName());
        userPersisted.setLastName(user.getLastName());
        userPersisted.setNickname(user.getNickname());
        userPersisted.setPhoneNumber(user.getPhoneNumber());
        userPersisted.setEmail(user.getEmail());
        userPersisted.setPassword(user.getPassword().isEmpty() || user.getPassword().equals("null") ? userPersisted.getPassword() : authService.testAndEncodePassword(user.getPassword()));
        if(user.getProfilePicture() != null) userPersisted.setProfilePicture(user.getProfilePicture());
        System.out.println(userPersisted);

        userRepository.save(userPersisted);
    }

    public Map<String, String> signIn(String email, String password) {
        Map<String, String> errors = null;
        try{
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NoSuchElementException("email,Email does not exist"));
            if(!authService.validatePassword(password, user.getPassword())) throw new NoSuchElementException("password,Password is incorrect");
        }catch (NoSuchElementException e){
            errors = new HashMap<>();
            String[] split = e.getMessage().split(",");
            errors.put(split[0], split[1]);
        }
        if(errors == null){
            errors = new HashMap<>();
            errors.put("errors", null);
        }
        return errors;
    }

    public User findById(long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow();
    }

    public LocalDateTime calculatePlanExpirationDate(Plan plan, boolean isMonthly) {
        LocalDateTime expirationDate;
        if(plan.getId() == 1){
            expirationDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        }
        else expirationDate = isMonthly ? LocalDateTime.now().plusMonths(1) : LocalDateTime.now().plusYears(1);
        return expirationDate;
    }
}
