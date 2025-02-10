package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.CarToProvider;

import java.util.List;

public interface CarToProviderService {

  public List<CarToProvider> findAll();

  public CarToProvider findById(Integer id);

  public CarToProvider save(CarToProvider carToProvider);

  public void deleteById(Integer id);
}