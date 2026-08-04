"use client";

import { AddressResponseData } from "@/types/address";
import { Collapse, Radio } from "antd";

interface DeliveryAddressAccordionProps {
  addresses: AddressResponseData[];
  selectedAddressId: string | null;
  onSelectAddress: (addressId: string) => void;
}

const formatAddress = (address: AddressResponseData) => {
  return `${address.street}, ${address.number} - ${address.city}/${address.state} - CEP ${address.postalCode}`;
};

export default function DeliveryAddressAccordion({
  addresses,
  selectedAddressId,
  onSelectAddress,
}: DeliveryAddressAccordionProps) {
  return (
    <Collapse
      className="pb-6 mt-6"
      items={[
        {
          key: "delivery-address",
          label: "Selecionar endereco de entrega",
          children:
            addresses.length === 0 ? (
              <p>Nenhum endereco encontrado.</p>
            ) : (
              <Radio.Group
                value={selectedAddressId ?? undefined}
                onChange={(event) => onSelectAddress(event.target.value)}
                className="flex w-full flex-col gap-3"
              >
                {addresses.map((address) => (
                  <Radio key={address.id} value={address.id}>
                    {formatAddress(address)}
                  </Radio>
                ))}
              </Radio.Group>
            ),
        },
      ]}
    />
  );
}