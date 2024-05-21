import axios from '../utils/axios-instance'

export async function getExpensesByUserId(userId) {
    const response = await axios.get('/expenses?userId=' + userId);
    return response.data;
}

export async function getExpense(id) {
    const response = await axios.get(`/expenses/${id}`);
    return response.data;
}

export async function saveExpense(expense) {
    const response = await axios.put('/expenses/' + expense.id, expense);
    return response.data;
}

export async function getLatestExpenses(userId, count = 5) {
    const response = await axios.get(`/expenses?userId=${userId}&size=${count}&page=0&sort=date,desc`);
    return response.data;
}

function sumExpenses(expenses) {
    return expenses.reduce((total, expense) => total + expense.amount, 0);
}

export async function getSumOfMonthlyExpenses(userId, month, year) {
    const response = await axios.get(`/expenses/sum?userId=${userId}&month=${month}&year=${year}`);
    return response.data;
}

export async function deleteExpense(id) {
    const response = await axios.delete(`/expenses/${id}`);
    return response.data;
}