import axios from "../utils/axios-instance";

export async function login(username, password) {
    const response = await axios.post('/auth/login', {username, password});
    return response.data;
}

export async function logout() {
    try {
        const response = await axios.post('/logout');
        return response.data;
    } catch (error) {
        console.error('Error while logging out', error.error);
        throw error;
    }
}

export async function register(user) {
    try {
        const response = await axios.post('/auth/register', user);
        return response.data;
    } catch (error) {
        console.error('Error while registering', error);
        throw error;
    }
}