import axios from '../utils/axios-instance';

export async function getCategories() {
    const response = await axios.get('/categories');
    return response.data;
}

export async function getCategory(id) {
    const response = await axios.get(`/categories/${id}`);
    return response.data;
}

export async function saveCategory(category) {
    const response = await axios.post('/categories', category);
    return response.data;
}

export async function updateCategory(category) {
    const response = await axios.put('/categories/' + category.id, category);
    return response.data;
}

export async function deleteCategory(id) {
    const response = await axios.delete(`/categories/${id}`);
    return response.data;
}