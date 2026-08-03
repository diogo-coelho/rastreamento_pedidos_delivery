export enum Status {
  RECEBIDO, 
  EM_PREPARO, 
  SAIU_PARA_ENTREGA, 
  ENTREGUE,
  CANCELADO
}

export interface OrderResponseData {
  id: string;
  user: {
    id: string;
    name: string;
    email: string;
  },
  address: {
    street: string;
    number: number;
    city: string;
    state: string;
    postalCode: string;
    country: string;
  },
  items: {
    name: string;
    imageUrl: string;
    price: number;
    quantity: number;
  }[],
  status: Status;
  createdAt: string;
  updatedAt: string;
}

export interface OrderRequestData {
  userId: string;
  addressId: string;
  items: {
    itemId: string;
    quantity: number;
  }[]
}