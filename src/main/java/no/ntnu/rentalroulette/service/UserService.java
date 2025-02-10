package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.User;

import java.util.List;

public interface UserService {

  public List<User> findAll();

  public User findById(Integer id);

  public User save(User user);

  public void deleteById(Integer id);
}