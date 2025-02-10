package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.Provider;
import no.ntnu.rentalroulette.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

  @Autowired
  private ProviderRepository providerRepository;

  public List<Provider> findAll() {
    return providerRepository.findAll();
  }

  public Optional<Provider> findById(Integer id) {
    return providerRepository.findById(id);
  }

  public Provider save(Provider provider) {
    return providerRepository.save(provider);
  }

  public void deleteById(Integer id) {
    providerRepository.deleteById(id);
  }
}