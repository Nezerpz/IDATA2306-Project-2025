package no.ntnu.rentalroulette.util;

import no.ntnu.rentalroulette.enums.Manufacturer;

public class ManufacturerUtil {

  /**
   * Remove _ from the manufacturer name and replace it with a space.
   *
   * @param manufacturer the manufacturer to format
   * @return the formatted manufacturer name
   */
  public static String formatManufacturer(Manufacturer manufacturer) {
    return manufacturer.name().replace("_", " ");
  }

  /**
   * Parse the manufacturer name from a string to the Manufacturer enum.
   *
   * @param manufacturer the manufacturer name to parse
   * @return the Manufacturer enum
   */
  public static Manufacturer parseManufacturer(String manufacturer) {
    return Manufacturer.valueOf(manufacturer.replace(" ", "_").toUpperCase());
  }
}