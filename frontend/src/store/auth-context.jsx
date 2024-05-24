import * as authService from "../services/auth-service";
import {jwtDecode} from "jwt-decode";
import {createContext, useEffect, useState} from "react";

export const AuthContext = createContext({
    userId: null,
    username: null,
    userRole: null,
    isAuthenticated: false,
    login: () => {},
    logout: () => {},
    register: () => {}
});

export default function AuthProvider({children}) {
    const [userId, setUserId] = useState(null);
    const [username, setUsername] = useState(null);
    const [userRoles, setUserRoles] = useState([]);
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            decodeToken(token);
        }
    }, []);

    const decodeToken = (token) => {
        try {
            const decoded = jwtDecode(token);
            setUserId(decoded.userId);
            setUsername(decoded.sub);
            setUserRoles(decoded.roles);
            setIsAuthenticated(true);
        } catch (error) {
            console.error('Error while decoding token', error);
            setIsAuthenticated(false);
        }
    }

    const loginHandler = async (username, password) => {
        try {
            const response = await authService.login(username, password)
            const token = response.data.token;
            decodeToken(token);
            localStorage.setItem('token', token);
            return "Login successful";
        } catch (error) {
            throw error.response.data;
        }
    }

    const logoutHandler = async () => {
        try {
            await authService.logout();
            setIsAuthenticated(false);
            localStorage.removeItem('token');
            return "Logout successful";
        } catch (error) {
            console.error('Error while logging out', error);
            throw error;
        }
    }

    const registerHandler = async (user) => {
        try {
            await authService.register(user);
            return "Registration successful";
        } catch (error) {
            console.error('Error while registering', error);
            throw error;
        }
    }

    return (
        <AuthContext.Provider value={{
            userId: userId,
            username: username,
            userRoles: userRoles,
            isAuthenticated: isAuthenticated,
            login: loginHandler,
            logout: logoutHandler,
            register: registerHandler
        }}>
            {children}
        </AuthContext.Provider>
    );
}
