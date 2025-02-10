package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.Provider;

import java.util.List;

public interface ProviderService {

  public List<Provider> findAll();

  public Provider findById(Integer id);

  public Provider save(Provider provider);

  public void deleteById(Integer id);
}