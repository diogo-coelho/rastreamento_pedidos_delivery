export interface UserRequestData {
  name: string;
  email: string;
  password: string;
}

export interface UserResponseData {
  id: string;
  name: string;
  email: string;
}

type FieldType = {
  email?: string;
  password?: string;
};