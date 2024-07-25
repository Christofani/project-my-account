package com.betrybe.minhaconta.business;

import com.ions.lightdealer.sdk.model.Address;
import com.ions.lightdealer.sdk.model.Client;
import com.ions.lightdealer.sdk.model.ElectronicDevice;

/**
 * The type Energy account.
 */
public class EnergyAccount {

  Client client;

  public EnergyAccount(Client client) {
    this.client = client;
  }

  /**
   * Req. 11 – Find high consumption device per address.
   */
  public ElectronicDevice[] findHighConsumptionDevices() {
    // Get all addresses from the client
    Address[] addresses = client.getAddressesAsArray();
    // Array to hold the highest consumption device for each address
    ElectronicDevice[] highConsumptionDevices = new ElectronicDevice[addresses.length];

    // Iterate through each address
    for (int i = 0; i < addresses.length; i++) {
      Address address = addresses[i];
      ElectronicDevice[] devices = address.getDevicesAsArray();

      // If there are devices, find the one with the highest consumption
      if (devices.length > 0) {
        ElectronicDevice highestConsumptionDevice = devices[0];
        for (ElectronicDevice device : devices) {
          if (device.monthlyKwh() > highestConsumptionDevice.monthlyKwh()) {
            highestConsumptionDevice = device;
          }
        }
        highConsumptionDevices[i] = highestConsumptionDevice;
      } else {
        // No devices in this address
        highConsumptionDevices[i] = null;
      }
    }

    return highConsumptionDevices;
  }
}
