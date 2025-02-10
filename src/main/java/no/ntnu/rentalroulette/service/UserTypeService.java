package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.UserType;
import no.ntnu.rentalroulette.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeService {

  @Autowired
  private UserTypeRepository userTypeRepository;

  public List<UserType> findAll() {
    return userTypeRepository.findAll();
  }

  public Optional<UserType> findById(Integer id) {
    return userTypeRepository.findById(id);
  }

  public UserType save(UserType userType) {
    return userTypeRepository.save(userType);
  }

  public void deleteById(Integer id) {
    userTypeRepository.deleteById(id);
  }
}