package Service;

import Model.Vacation.Location;
import Repository.LocationRepository;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;
import java.util.List;

@NoArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository =
            new LocationRepository();

    public Location insertLocation(String destination, String description) {
        if(!destination.isEmpty() && !description.isEmpty())
            return locationRepository.insertLocation(destination, description);
        else
            throw new InvalidParameterException();
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public void deleteLocation(Location location) {
        if(location != null)
            locationRepository.deleteLocation(location);
        else
            throw new InvalidParameterException();
    }

    public Location findLocation(String destination) {
        if(!destination.isEmpty())
            return locationRepository.findLocation(destination);
        else
            throw new InvalidParameterException();
    }
}
