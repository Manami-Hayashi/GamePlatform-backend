import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test"
  to = azurerm_resource_group.res-0
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Compute/virtualMachines/tofu-export-test-vm"
  to = azurerm_linux_virtual_machine.res-1
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkInterfaces/tofu-export-test-vmVMNic"
  to = azurerm_network_interface.res-2
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkInterfaces/tofu-export-test-vmVMNic|/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkSecurityGroups/tofu-export-test-vmNSG"
  to = azurerm_network_interface_security_group_association.res-3
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkSecurityGroups/tofu-export-test-vmNSG"
  to = azurerm_network_security_group.res-4
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/networkSecurityGroups/tofu-export-test-vmNSG/securityRules/default-allow-ssh"
  to = azurerm_network_security_rule.res-5
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/publicIPAddresses/tofu-export-test-vmPublicIP"
  to = azurerm_public_ip.res-6
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/virtualNetworks/tofu-export-test-vmVNET"
  to = azurerm_virtual_network.res-7
}
import {
  id = "/subscriptions/c544b780-c7ba-4416-b71b-dff05a0ef07d/resourceGroups/game-platform-test/providers/Microsoft.Network/virtualNetworks/tofu-export-test-vmVNET/subnets/tofu-export-test-vmSubnet"
  to = azurerm_subnet.res-8
}
