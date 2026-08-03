import { api } from "./api";
import { UserRequestData, UserResponseData } from "@/types/user";

export const createUser = async (userData: UserRequestData): Promise<UserResponseData> => {
  const response = await api.post<UserResponseData>("/user/register", userData);
  return response.data;
};