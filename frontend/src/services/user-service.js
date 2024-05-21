import axios from '../utils/axios-instance';

export async function getUserById(userId) {
    try {
        const response = await axios.get(`/users/${userId}`);
        return response.data;
    } catch (error) {
        console.error('Error while fetching user', error);
        throw error;
    }
}

export async function getUsers() {
    try {
        const response = await axios.get('/users');
        return response.data;
    } catch (error) {
        console.error('Error while fetching users', error);
        throw error;
    }
}

export async function saveUser(user) {
    try {
        const response = await axios.post('/users', user);
        return response.data;
    } catch (error) {
        console.error('Error while saving user', error);
        throw error;
    }
}

export async function updateUser(user) {
    try {
        const response = await axios.put(`/users/${user.id}`, user);
        return response.data;
    } catch (error) {
        console.error('Error while updating user', error);
        throw error;
    }
}

export async function deleteUser(userId) {
    try {
        const response = await axios.delete(`/users/${userId}`);
        return response.data;
    } catch (error) {
        console.error('Error while deleting user', error);
        throw error;
    }
}