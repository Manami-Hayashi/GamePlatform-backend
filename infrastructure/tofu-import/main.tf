resource "azurerm_resource_group" "res-0" {
  location = "westus"
  name     = "game-platform-test"
}
resource "azurerm_linux_virtual_machine" "res-1" {
  admin_username        = "azureuser"
  location              = "westus"
  name                  = "tofu-export-test-vm"
  network_interface_ids = ["/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkInterfaces/tofu-export-test-vmVMNic"]
  resource_group_name   = "game-platform-test"
  secure_boot_enabled   = true
  size                  = "Standard_B1s"
  vtpm_enabled          = true
  admin_ssh_key {
    public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCzRrmG/Z45kVIG14OBOFkGk+Qa+yCLL4IwV4ue2eiBvAf3/j9glJ+H2CSrlzUY4be/RXTvXhf+0pGTzReaUt+PyXh8w74hU1M2CbA4TIja+ngamUTtqTd12Z7e9LRScdt+UmvL0PH7kdpZHcSYcXWiupL1NTmgjJ3cVZHP2cBlSM+tqVUo7hc4FVoJliPEvOrj5MlCgcSCNhxnKSUV/Nl7OmAg/fesuNkRUdIZ7foOL1/UXqBZ9w6JGntwhV4FBKLt09HE2loY4SCihQPq45IgNkOopLcS7p2Y/KR5NCj78P13w0274ggBwEZN1wqHagiblku5GIoL91jDzLQuOarz"
    username   = "azureuser"
  }
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Premium_LRS"
  }
  source_image_reference {
    offer     = "debian-11"
    publisher = "Debian"
    sku       = "11-backports-gen2"
    version   = "latest"
  }
  depends_on = [
    azurerm_network_interface.res-2,
  ]
}
resource "azurerm_network_interface" "res-2" {
  location            = "westus"
  name                = "tofu-export-test-vmVMNic"
  resource_group_name = "game-platform-test"
  ip_configuration {
    name                          = "ipconfigtofu-export-test-vm"
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/publicIPAddresses/tofu-export-test-vmPublicIP"
    subnet_id                     = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/virtualNetworks/tofu-export-test-vmVNET/subnets/tofu-export-test-vmSubnet"
  }
  depends_on = [
    azurerm_public_ip.res-6,
    azurerm_subnet.res-8,
  ]
}
resource "azurerm_network_interface_security_group_association" "res-3" {
  network_interface_id      = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkInterfaces/tofu-export-test-vmVMNic"
  network_security_group_id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkSecurityGroups/tofu-export-test-vmNSG"
  depends_on = [
    azurerm_network_interface.res-2,
    azurerm_network_security_group.res-4,
  ]
}
resource "azurerm_network_security_group" "res-4" {
  location            = "westus"
  name                = "tofu-export-test-vmNSG"
  resource_group_name = "game-platform-test"
}
resource "azurerm_network_security_rule" "res-5" {
  access                      = "Allow"
  destination_address_prefix  = "*"
  destination_port_range      = "22"
  direction                   = "Inbound"
  name                        = "default-allow-ssh"
  network_security_group_name = "tofu-export-test-vmNSG"
  priority                    = 1000
  protocol                    = "Tcp"
  resource_group_name         = "game-platform-test"
  source_address_prefix       = "*"
  source_port_range           = "*"
  depends_on = [
    azurerm_network_security_group.res-4,
  ]
}
resource "azurerm_public_ip" "res-6" {
  allocation_method   = "Static"
  location            = "westus"
  name                = "tofu-export-test-vmPublicIP"
  resource_group_name = "game-platform-test"
  sku                 = "Standard"
}
resource "azurerm_virtual_network" "res-7" {
  address_space       = ["10.0.0.0/16"]
  location            = "westus"
  name                = "tofu-export-test-vmVNET"
  resource_group_name = "game-platform-test"
}
resource "azurerm_subnet" "res-8" {
  address_prefixes     = ["10.0.0.0/24"]
  name                 = "tofu-export-test-vmSubnet"
  resource_group_name  = "game-platform-test"
  virtual_network_name = "tofu-export-test-vmVNET"
  depends_on = [
    azurerm_virtual_network.res-7,
  ]
}
