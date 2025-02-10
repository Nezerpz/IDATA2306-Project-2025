package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.CarToProvider;
import no.ntnu.rentalroulette.repository.CarToProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarToProviderService {

  @Autowired
  private CarToProviderRepository carToProviderRepository;

  public List<CarToProvider> findAll() {
    return carToProviderRepository.findAll();
  }

  public Optional<CarToProvider> findById(Integer id) {
    return carToProviderRepository.findById(id);
  }

  public CarToProvider save(CarToProvider carToProvider) {
    return carToProviderRepository.save(carToProvider);
  }

  public void deleteById(Integer id) {
    carToProviderRepository.deleteById(id);
  }
}