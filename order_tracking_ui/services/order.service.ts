import { OrderResponseData, OrderRequestData } from "@/types/order";
import { api } from "./api";

export const listOrders = async () => {
  const response = await api.get<OrderResponseData[]>("/order");
  return response.data;
};

export const createOrder = async (orderData: OrderRequestData) => {
  const response = await api.post<OrderResponseData>("/order", orderData);
  return response.data;
};

export const getOrderById = async (orderId: string) => {
  const response = await api.get<OrderResponseData>(`/order/${orderId}`);
  return response.data;
}

export const updateOrderStatus = async (orderId: string, status: number) => {
  const response = await api.patch<OrderResponseData>(`/order/${orderId}/status`, { status });
  return response.data;
}