import { api } from "./api";
import { ItemRequestData, ItemResponseData } from "@/types/item";

export const createItem = async (itemData: ItemRequestData) => {
  const response = await api.post<ItemResponseData>("/item", itemData);
  return response.data;
};

export const ListItems = async () => {
  const response = await api.get<ItemResponseData[]>("/item");
  return response.data;
};