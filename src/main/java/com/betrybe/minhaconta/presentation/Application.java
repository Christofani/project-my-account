package com.betrybe.minhaconta.presentation;

import com.betrybe.minhaconta.business.EnergyAccount;
import com.betrybe.minhaconta.business.EnergyBill;
import com.ions.lightdealer.sdk.model.Address;
import com.ions.lightdealer.sdk.model.Client;
import com.ions.lightdealer.sdk.model.ElectronicDevice;
import com.ions.lightdealer.sdk.service.LightDealerApi;

/**
 * The type Application.
 */
public class Application {

  ConsoleUserInterface ui;
  LightDealerApi api;

  /**
   * Constructor that instantiates a new Application.
   */
  public Application(ConsoleUserInterface ui) {
    this.ui = ui;
    this.api = new LightDealerApi();
  }

  /**
   * Req. 4 – Creates CLI menu.
   */
  public void run() {
    String[] options = {
      "1 - Cadastrar cliente",
      "2 - Cadastrar imóvel de cliente",
      "3 - Cadastrar dispositivos de imóvel",
      "4 - Estimar conta de imóvel",
      "5 - Otimizar uso de energia",
      "6 - Sair"
    };

    char option;
    do {
      option = ui.inputMenuOption(options);
      runOptionAction(option);
    } while (option != '6');
  }

  /**
   * Req. 5 – Run menu options.
   */
  public void runOptionAction(char option) {
    switch (option) {
      case '1':
        registerClient();
        break;
      case '2':
        registerClientAddress();
        break;
      case '3':
        registerAddressDevices();
        break;
      case '4':
        estimateAddressBill();
        break;
      case '5':
        optimizeEnergyBill();
        break;
      case '6':
        ui.showMessage("Volte sempre!");
        break;
      default:
        ui.showMessage("Opção inválida!");
        break;
    }
  }

  /**
   * Req. 6 – Register client.
   */
  public void registerClient() {
    Client client = new Client();
    ui.fillClientData(client);
    if (api.addClient(client)) {
      ui.showMessage("Cliente cadastrado com sucesso!");
    } else {
      ui.showMessage("Falha ao cadastrar cliente.");
    }
  }

  /**
   * Req. 7 – Register client address.
   */
  public void registerClientAddress() {
    String cpf = ui.inputClientCpf();
    Client client = api.findClient(cpf);

    if (client == null) {
      ui.showMessage("Pessoa cliente não encontrada!");
      return;
    }

    Address address = new Address();
    ui.fillAddressData(address);
    api.addAddressToClient(address, client);
    ui.showMessage("Endereço cadastrado com sucesso!");
  }


  /**
   * Req. 8 – Register address devices.
   */
  public void registerAddressDevices() {
    String registration = ui.inputAddressRegistration();
    Address address = api.findAddress(registration);

    if (address == null) {
      ui.showMessage("Endereço não encontrado!");
      return;
    }

    int numberOfDevices = ui.inputNumberOfDevices();
    for (int i = 0; i < numberOfDevices; i++) {
      ElectronicDevice device = new ElectronicDevice();
      ui.fillDeviceData(device);
      api.addDeviceToAddress(device, address);
    }
    ui.showMessage("Dispositivos cadastrados com sucesso!");
  }

  /**
   * Req. 9 – Estimates the address energy bill.
   */
  public void estimateAddressBill() {
    String registration = ui.inputAddressRegistration();
    Address address = api.findAddress(registration);

    if (address == null) {
      ui.showMessage("Endereço não encontrado!");
      return;
    }

    // Instancia uma nova conta de energia
    EnergyBill energyBill = new EnergyBill(address, true); // true ou false para residentialPlan

    // Estima a conta de energia
    double estimatedBill = energyBill.estimate();

    // Mostra a mensagem com o valor estimado
    ui.showMessage("Valor estimado para a conta: " + estimatedBill);
  }

  /**
   * Req. 10 – Optimizes the energy bill.
   */
  public void optimizeEnergyBill() {
  }

  /**
   * Req 10 - Aux. Method to display high consumptions devices.
   */
  public void suggestReducedUsage(EnergyAccount energyAccount) {
  }
}
