export interface ItemRequestData {
  id: string;
  name: string;
  description: string;
  price: number;
  quantity: number;
}

export interface ItemResponseData {
  id?: string;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
}