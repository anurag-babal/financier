import axios from '../utils/axios-instance'

export async function getMonthExpenses(userId, year, month, category = 'all') {
    const response = await axios
        .get(`/reports?userId=${userId}&year=${year}&month=${month}&category=${category}`);
    return response.data;
}

export async function getYearExpenses(userId, year, category = 'all') {
    const response = await axios
        .get(`/reports?userId=${userId}&year=${year}&category=${category}`);
    return response.data;
}

export async function getExpenseSummary(userId, category = 'all') {
    const response = await axios.get(`/reports?userId=${userId}&category=${category}`);
    return response.data;
}

export async function getExpenseReport(userId, year, month, category) {
    const response = await axios
        .get(`/reports?userId=${userId}&year=${year}&month=${month}&category=${category}`);
    return response.data;
}