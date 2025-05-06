package no.ntnu.rentalroulette.service;

import java.util.List;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }
}
