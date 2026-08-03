import { api } from "./api";

import { AddressRequestData } from "@/types/address";

export const createAddress = async (addressData: AddressRequestData) => {
  const response = await api.post("/address", addressData);
  return response.data;
};