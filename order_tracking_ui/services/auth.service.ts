import { AuthRequestData } from "@/types/auth";
import { api } from "./api";

export const login = async (auth: AuthRequestData) => {
  try {
    const response = await api.post("/auth/login", auth);
    const { token } = response.data;
    return token;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const logout = () => {
  try {
    localStorage.removeItem("token");
    api.post("/auth/logout");
  } catch (error) {
    console.error(error);
    throw error;
  }
};