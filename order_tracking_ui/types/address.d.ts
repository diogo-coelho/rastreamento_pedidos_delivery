export interface AddressRequestData {
  street: string;
  number: number;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

export interface AddressResponseData extends AddressRequestData {
  id: string;
}