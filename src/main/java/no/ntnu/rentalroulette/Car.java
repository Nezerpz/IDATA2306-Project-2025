package no.ntnu.rentalroulette;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

/**
 * 
 */
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String carModel;
    private int numberOfSeats;
    private String transmissionType;
    private String fuelType;
    private int productionYear;

    public Car() {
    }

    public Car(
            String model,
            int seats,
            String transmissionType,
            String fuelType,
            int productionYear) {
        this.carModel = model;
        this.numberOfSeats = seats;
        this.transmissionType = transmissionType;
        this.fuelType = fuelType;
        this.productionYear = productionYear;
    }

	/**
     * Get Entity's row ID in Database.
     *
	 * @return id as string
	 */
	public int getId() {
		return id;
	}


	/**
     * Get Entity car model.
     *
	 * @return car model as string
	 */
	public String getCarModel() {
		return carModel;
	}


	/**
     * Get number of seats this car entity has
     *
	 * @return number of seats as int
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}


	/**
     * Get car entity's transmission type.
     *
	 * @return transmission type as string
	 */
	public String getTransmissionType() {
		return transmissionType;
	}


	/**
     * Get car entity's fuel type.
     *
	 * @return fuel type as string
	 */
	public String getFuelType() {
		return fuelType;
	}


	/**
     * Get car entity's production year.
     *
	 * @return production year as string
	 */
	public int getProductionYear() {
		return productionYear;
	}

}
