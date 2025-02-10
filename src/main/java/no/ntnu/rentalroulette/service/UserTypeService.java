package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.UserType;

import java.util.List;

public interface UserTypeService {
  public List<UserType> findAll();

  public UserType findById(Integer id);

  public UserType save(UserType userType);

  public void deleteById(Integer id);
}