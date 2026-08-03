import { api } from "./api";

import { AddressRequestData, AddressResponseData } from "@/types/address";

export const createAddress = async (addressData: AddressRequestData) => {
  const response = await api.post("/address", addressData);
  return response.data;
};

export const listAddresses = async () => {
  const response = await api.get<AddressResponseData[]>("/address");
  return response.data;
};