"use client";

import { AuthRequestData } from "@/types/auth";
import {
	createContext,
	ReactNode,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from "react";
import { 
  login as loginService, 
  logout as logoutService 
} from "@/services/auth.service";

interface AuthContextData {
	token: string | null;
	isAuthenticated: boolean;
	loading: boolean;
	signIn: (credentials: AuthRequestData) => Promise<void>;
	signOut: () => void;
}

interface AuthProviderProps {
	children: ReactNode;
}

export const AuthContext = createContext<AuthContextData | undefined>(undefined);

export function AuthProvider({ children }: AuthProviderProps) {
	const [token, setToken] = useState<string | null>(null);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const savedToken = localStorage.getItem("token");
		setToken(savedToken);
		setLoading(false);
	}, []);

	const signIn = useCallback(async (credentials: AuthRequestData) => {
		const authToken = await loginService(credentials);
		localStorage.setItem("token", authToken);
		setToken(authToken);
	}, []);

	const signOut = useCallback(() => {
		logoutService();
		setToken(null);
	}, []);

	const value = useMemo(
		() => ({
			token,
			isAuthenticated: Boolean(token),
			loading,
			signIn,
			signOut,
		}),
		[token, loading, signIn, signOut]
	);

	return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
