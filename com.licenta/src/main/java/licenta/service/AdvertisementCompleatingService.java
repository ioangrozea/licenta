package licenta.service;

import licenta.entity.Advertisement;
import licenta.entity.AdvertisementDescription;
import licenta.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementCompleatingService {
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementCompleatingService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void getBetterAdvertisement(Advertisement newAdvertisement, Advertisement savedAdvertisement) {
        addNewInformation(newAdvertisement, savedAdvertisement);
        switch (newAdvertisement.getDescription().getDistributor()){
            case "Persoana fizica":
                savedAdvertisement.getDescription().setDistributor("Persoana fizica");
            case "Proprietar":
                savedAdvertisement.getDescription().setDistributor("Proprietar");
        }
        advertisementRepository.save(savedAdvertisement);
    }

    public void addNewInformation(Advertisement newAdvertisement, Advertisement savedAdvertisement){
        AdvertisementDescription newDescription = newAdvertisement.getDescription();
        AdvertisementDescription oldDescription = savedAdvertisement.getDescription();
        if(newDescription.getHasParking() != null)
            oldDescription.setHasParking(newDescription.getHasParking());
        if(newDescription.getDescription().length() > oldDescription.getDescription().length())
            oldDescription.setDescription(newDescription.getDescription());
        if(newDescription.getArea() != null)
            oldDescription.setArea(newDescription.getArea());
        if(newDescription.getConstructionYear() != null)
            oldDescription.setConstructionYear(newDescription.getConstructionYear());
        if(newDescription.getFloor() != null)
            oldDescription.setFloor(newDescription.getFloor());
        if(newDescription.getHasThermalPowerPlant() != null)
            oldDescription.setHasThermalPowerPlant(newDescription.getHasThermalPowerPlant());
        if(newDescription.getLocation() != null)
            oldDescription.setLocation(newDescription.getLocation());
        if(newDescription.getNumberOfBathrooms() != null)
            oldDescription.setNumberOfBathrooms(newDescription.getNumberOfBathrooms());
        if(newDescription.getPartitioning() != null)
            oldDescription.setPartitioning(oldDescription.getPartitioning());

        if(newAdvertisement.getPrice() < savedAdvertisement.getPrice()){
            savedAdvertisement.setPrice(newAdvertisement.getPrice());
            savedAdvertisement.setAdvertisementUrl(newAdvertisement.getAdvertisementUrl());
            savedAdvertisement.setWebsite(newAdvertisement.getWebsite());
        }
        if(newAdvertisement.getImageUrls().size() > savedAdvertisement.getImageUrls().size())
            savedAdvertisement.setImageUrls(newAdvertisement.getImageUrls());
    }
}
