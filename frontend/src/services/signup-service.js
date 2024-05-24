import axios from "../utils/axios-instance";

export async function register(user) {
    const response = await axios.post('/register', user);
    return response.data;
}