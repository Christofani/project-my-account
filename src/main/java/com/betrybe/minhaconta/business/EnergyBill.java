package com.betrybe.minhaconta.business;

import com.ions.lightdealer.sdk.model.Address;
import com.ions.lightdealer.sdk.model.ElectronicDevice;

/**
 * The type Energy bill.
 */
public class EnergyBill {
  public Address address;
  public boolean residentialPlan;
  public double rate = 0.15;

  /**
   * Construtor da classe EnergyBill.
   *
   * @param address o endereço da pessoa cliente
   * @param residentialPlan se é um plano residencial ou não
   */
  public EnergyBill(Address address, boolean residentialPlan) {
    this.address = address;
    this.residentialPlan = residentialPlan;
  }

  /**
   * Calculates an adjusted tariff for non-residential plans.
   *
   * @param value o valor da conta de luz
   * @return o valor ajustado da conta de luz
   */
  public double adjustedTariff(double value) {
    if (!residentialPlan) {
      return value * 1.10;
    }
    return value;
  }

  /**
   * Calculates the total usage of a collection of devices.
   *
   * @param devices uma coleção de dispositivos eletrônicos
   * @return o consumo total mensal em quilowatt-hora, truncado para um valor inteiro
   */
  public static int calculateTotalUsage(ElectronicDevice[] devices) {
    double totalUsage = 0;

    for (ElectronicDevice device : devices) {
      totalUsage += device.monthlyKwh();
    }

    return (int) totalUsage;
  }

  /**
   * Method that estimates the energy bill value.
   */
  public double estimate() {
    double value = calculateTotalUsage(address.getDevicesAsArray()) * rate;
    return adjustedTariff(value);
  }
}
